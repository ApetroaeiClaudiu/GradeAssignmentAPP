package controller;
import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.GradeService;
import service.StudentService;
import utils.event.StudentChangeEvent;
import utils.observer.Observer;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<StudentChangeEvent> {
    StudentService studentservice;
    GradeService gradeservice;
    ObservableList<Student> model = FXCollections.observableArrayList();

    @FXML
    TableView<Student> tableView;
    @FXML
    TableColumn<Student,String> tableColumnID;
    @FXML
    TableColumn<Student, String> tableColumnGroup;
    @FXML
    TableColumn<Student, String> tableColumnSurname;
    @FXML
    TableColumn<Student, String> tableColumnName;
    @FXML
    TableColumn<Student, String> tableColumnEmail;
    @FXML
    TableColumn<Student, String> tableColumnProfessorLab;
    @FXML
    TextField textFieldFilterName;
    @FXML
    TextField textFieldFilterGroup;
    @FXML
    TextField textFieldFilterProfessor;

    public void setStudentService(StudentService studentService,GradeService gradeService) {
        studentservice = studentService;
        gradeservice = gradeService;
        studentservice.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnID.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getId());
            }
        });
        tableColumnGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("Group"));
        tableColumnSurname.setCellValueFactory(new PropertyValueFactory<Student, String>("Surname"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("Email"));
        tableColumnProfessorLab.setCellValueFactory(new PropertyValueFactory<Student, String>("ProfessorLab"));

        tableView.setItems(model);

        textFieldFilterName.textProperty().addListener((x, y, z) -> handleFilter());
        textFieldFilterGroup.textProperty().addListener((x, y, z) -> handleFilter());
        textFieldFilterProfessor.textProperty().addListener((x, y, z) -> handleFilter());
    }

    private void initModel() {
        Iterable<Student> students = studentservice.findAll();
        model.setAll(getStudents());
    }

    private List<Student> getStudents() {
        Iterable<Student> students = studentservice.findAll();
        List<Student> studentList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        return studentList;
    }

    @FXML
    public void handleFilter() {
        Predicate<Student> byName = x -> x.getName().contains(textFieldFilterName.getText());
        Predicate<Student> bySurname = x -> x.getSurname().contains(textFieldFilterName.getText());
        Predicate<Student> byProfessor = x -> x.getProfessorLab().contains(textFieldFilterProfessor.getText());
        boolean ok = true;
        if (textFieldFilterGroup.getText().isEmpty())
            ok=true;
        else{
            try{
                int ceva = Integer.parseInt(textFieldFilterGroup.getText());
            }
            catch (NumberFormatException e){
                MessageAlert.showErrorMessage(null,"Group should be number !");
                ok=false;
            }
        }
        if(ok){
            Predicate<Student> byGroup = x -> {
                if (!textFieldFilterGroup.getText().isEmpty())
                    return x.getGroup() == Integer.parseInt(textFieldFilterGroup.getText());
                return true;
            };
            model.setAll(getStudents().stream()
                    .filter((byName.or(bySurname)).and(byGroup).and(byProfessor))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        initModel();
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
        Student selected = (Student) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String id = selected.getId();
            Student deleted = studentservice.delete(id);

            Iterable<Grade> grades = gradeservice.findAll();
            List<Grade> gradeList = StreamSupport.stream(grades.spliterator(),false)
                    .collect(Collectors.toList());

            for(Grade g : gradeList){
                if(g.getId().getIDStudent().equals(id)){
                    gradeservice.delete(g.getId());
                }
            }
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete student...", "Student deleted successfully!");
        } else MessageAlert.showErrorMessage(null, "No student selected!");
    }

    @FXML
    public void handleAddStudent(ActionEvent actionEvent) {
        showStudentEditDialog(null);
    }

    public void showStudentEditDialog(Student student) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editStudentView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("/images/logonou.png"));

            EditStudentController editStudentViewController = loader.getController();
            editStudentViewController.setService(studentservice, dialogStage, student);
            dialogStage.setWidth(500);
            dialogStage.setHeight(400);
            dialogStage.setMinHeight(300);
            dialogStage.setMinWidth(400);
            dialogStage.setMaxHeight(600);
            dialogStage.setMaxWidth(600);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateStudent(ActionEvent actionEvent) {
        Student selected = (Student) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showStudentEditDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "No student selected!");
    }
}

