package controller;
import domain.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import utils.event.GradeChangeEvent;
import utils.event.StudentChangeEvent;
import utils.observer.Observer;
import validator.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController implements Observer<GradeChangeEvent> {
    UniversityYearStructure year;
    GradeService gradeservice;
    StudentService studentservice;
    HomeworkService homeworkservice;
    ObservableList<Student> modelStudent = FXCollections.observableArrayList();
    ObservableList<Grade> modelGrade = FXCollections.observableArrayList();
    ObservableList<Integer> homeworks = FXCollections.observableArrayList();

    @FXML
    TableView<Student> tableViewStudent;
    @FXML
    TableColumn<Student, String> tableColumnName;
    @FXML
    TableColumn<Student, String> tableColumnSurname;

    @FXML
    TableView<Grade> tableViewGrade;
    @FXML
    TableColumn<Grade, String> tableColumnStudent;
    @FXML
    TableColumn<Grade, Integer> tableColumnHomework;
    @FXML
    TableColumn<Grade, String> tableColumnGradeValue;
    @FXML
    TableColumn<Grade, String> tableColumnProfessor;
    @FXML
    TableColumn<Grade, String> tableColumnDeliveryWeek;
    @FXML
    TableColumn<Grade, String> tableColumnFeedback;

    @FXML
    ComboBox<Integer> comboBoxHomework;
    @FXML
    TextField textFieldGradeValue;
    @FXML
    TextArea textAreaFeedback;
    @FXML
    TextField textFieldMotivatedWeeks;
    @FXML
    TextField textFieldDeliveryWeek;

    @FXML
    TextField textFieldFilterStudents;
    @FXML
    TextField textFieldFilterHomework;

    public void setGradeService(UniversityYearStructure year,GradeService gradeService,StudentService studentService,HomeworkService homeworkService) {
        this.year=year;
        this.gradeservice = gradeService;
        this.studentservice = studentService;
        this.homeworkservice = homeworkService;
        this.gradeservice.addObserver(this);
        homeworks.setAll(getHomework());
        comboBoxHomework.setVisibleRowCount(4);
        comboBoxHomework.getSelectionModel().select(getTodayHomework());
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnSurname.setCellValueFactory(new PropertyValueFactory<Student, String>("Surname"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableViewStudent.setItems(modelStudent);


        tableColumnStudent.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Grade, String> param) {
                return new ReadOnlyObjectWrapper<String>(
                        studentservice.findById(param.getValue().getId().getIDStudent()).getSurname()+ " " +
                                studentservice.findById(param.getValue().getId().getIDStudent()).getName());
            }
        });
        tableColumnHomework.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Grade, Integer> param) {
                return new ReadOnlyObjectWrapper<Integer>(param.getValue().getId().getIDHomework());
            }
        });
        tableColumnProfessor.setCellValueFactory(new PropertyValueFactory<Grade, String>("professor"));
        tableColumnGradeValue.setCellValueFactory(new PropertyValueFactory<Grade, String>("value"));
        tableColumnDeliveryWeek.setCellValueFactory(new PropertyValueFactory<Grade, String>("deliveryWeek"));
        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<Grade, String>("feedback"));
        tableViewGrade.setItems(modelGrade);

        comboBoxHomework.setItems(homeworks);

        textFieldFilterStudents.textProperty().addListener((x, y, z) -> handleFilter());
        textFieldFilterHomework.textProperty().addListener((x,y,z)->handleFilterHomework());
    }

    private void initModel() {
        modelStudent.setAll(getStudents());
        modelGrade.setAll(getGrades());
        LocalDate today = LocalDate.now();
        int numberOfToday = year.getWeek(today);
        textFieldDeliveryWeek.setText(((Integer)numberOfToday).toString());
    }

    private List<Grade> getGrades() {
        Iterable<Grade> grades = gradeservice.findAll();
        List<Grade> gradesList = StreamSupport.stream(grades.spliterator(), false)
                .collect(Collectors.toList());
        return gradesList;
    }

    private List<Student> getStudents() {
        Iterable<Student> students = studentservice.findAll();
        List<Student> studentList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        return studentList;
    }

    private List<Integer> getHomework(){
        Iterable<Homework> homework = homeworkservice.findAll();
        List<Integer> homeworkList = StreamSupport.stream(homework.spliterator(),false)
                .map(Entity::getId)
                .collect(Collectors.toList());
        return homeworkList;
    }

    @FXML
    public void handleFilter() {
        Predicate<Grade> byName = x -> studentservice.findById(x.getId().getIDStudent()).getName().contains(textFieldFilterStudents.getText());
        Predicate<Grade> bySurname = x -> studentservice.findById(x.getId().getIDStudent()).getSurname().contains(textFieldFilterStudents.getText());
        modelGrade.setAll(getGrades().stream()
                .filter(byName.or(bySurname))
                .collect(Collectors.toList()));
        Predicate<Student> byName1 = x -> x.getName().contains(textFieldFilterStudents.getText());
        Predicate<Student> bySurname1 = x -> x.getSurname().contains(textFieldFilterStudents.getText());
        modelStudent.setAll(getStudents().stream()
                .filter(byName1.or(bySurname1))
                .collect(Collectors.toList()));

    }
    @FXML
    public void handleFilterHomework(){
        boolean ok = true;
        if (textFieldFilterHomework.getText().isEmpty())
            ok=true;
        else{
            try{
                int ceva = Integer.parseInt(textFieldFilterHomework.getText());
            }
            catch (NumberFormatException e){
                MessageAlert.showErrorMessage(null,"Homework ID should be number !");
                ok=false;
            }
        }
        if(ok){
            Predicate<Grade> byID = x -> {
                if (!textFieldFilterHomework.getText().isEmpty())
                    return x.getId().getIDHomework() == Integer.parseInt(textFieldFilterHomework.getText());
                return true;
            };
            modelGrade.setAll(getGrades().stream()
                    .filter(byID)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void update(GradeChangeEvent gradeChangeEvent) {
        initModel();
    }

    public int getTodayHomework(){
        LocalDate today = LocalDate.now();
        int numberOfToday = year.getWeek(today);
        Iterable<Homework> homework = homeworkservice.findAll();
        List<Homework> homeworkList = StreamSupport.stream(homework.spliterator(),false)
                .collect(Collectors.toList());
        int index = 0;
        for(Homework h : homeworkList){
            if(h.getDeadlineWeek() == numberOfToday){
                index = h.getId();
                break;
            }
        }
        ObservableList<Integer> items = comboBoxHomework.getItems();
        for(int i =0;i<items.size();i++){
            if(items.get(i) == index){
                return i;
            }
        }
        return -1;
    }

    public int getMinusPoints(int idHomework,int deliveryWeek){
        int value = 0;
        int deadlineWeek = homeworkservice.findById(idHomework).getDeadlineWeek();
        if (deadlineWeek < deliveryWeek) {
            int dif = deliveryWeek-deadlineWeek;
            if (dif == 1) {
                value = 1;
            }
            if (dif == 2) {
                value = 2;
            }
            if(dif >=3){
                value = 9;
            }
        }
        return value;
    }

    public float getGradeValue(float gradeValue,int minusPoints,int motivatedWeeks){
        if(minusPoints == 9)
            return 1;
        if(minusPoints == 0)
            return gradeValue;
        float value = 0;
        if (motivatedWeeks == 0)
            value = gradeValue - minusPoints;
        if (motivatedWeeks == 1) {
            value = gradeValue - minusPoints + motivatedWeeks;
            if (value >= 10)
                value = 10;
        }
        if (motivatedWeeks == 2) {
            value = gradeValue - minusPoints + motivatedWeeks;
            if (value >=9 )
                value = 10;
        }
        return value;
    }

    @FXML
    public void handleFeedback(){
        int idHomework = (Integer) comboBoxHomework.getSelectionModel().getSelectedItem();
        LocalDate today = LocalDate.now();
        int deliveryWeek = year.getWeek(today);
        int minusPoints = getMinusPoints(idHomework,deliveryWeek);
        if(minusPoints!=0)
            if(minusPoints!=9)
                textAreaFeedback.setText("The grade value has been\n diminshed by "+ minusPoints+ " due to late \n delivery time !\n");
            if(minusPoints==9)
                textAreaFeedback.setText("The given grade is 1\n due to more than 2 weeks \n late delivery time !");
        if(minusPoints==0)
            textAreaFeedback.setText("");
    }

    public String getIDStudentFromTextField(){
        Student selected = (Student) tableViewStudent.getSelectionModel().getSelectedItem();
        String idStudent="";
        if (selected != null)
            idStudent = selected.getId();
        return idStudent;
    }

    public String getProfessorStudentFromTextField(){
        Student selected = (Student) tableViewStudent.getSelectionModel().getSelectedItem();
        String professor="";
        if (selected != null)
            professor = selected.getProfessorLab();
        return professor;
    }

    public boolean handleStudent(){
        if(getIDStudentFromTextField().equals("") || getProfessorStudentFromTextField().equals("")){
            MessageAlert.showErrorMessage(null, "No student selected!");
            return false;}
        return true;
    }

    public int getIDHomework(){
        return (Integer) comboBoxHomework.getSelectionModel().getSelectedItem();
    }

    public int getMotivatedWeeks() {
        int motivatedWeeks = 0;
        String motivated = textFieldMotivatedWeeks.getText();
        if(motivated.equals(""))
            motivatedWeeks=0;
        else{
            try{
                motivatedWeeks = Integer.parseInt(motivated);
            }catch(NumberFormatException e){
                MessageAlert.showErrorMessage(null,"Number of weeks must either be a number or empty !");
                motivatedWeeks=-99;
            }
        }
        if(motivatedWeeks!=0 && motivatedWeeks !=1 && motivatedWeeks!=2 && motivatedWeeks!=-99){
            MessageAlert.showErrorMessage(null,"The number of motivated weeks cannot be anything else than 0,1 or 2 !");
            motivatedWeeks = -99;
        }
        return motivatedWeeks;
    }

    public int getDeliveryWeek(int idHomework) {
        LocalDate today = LocalDate.now();
        int deliveryWeek = year.getWeek(today);
        String delivery = textFieldDeliveryWeek.getText();
        if(delivery.equals(""))
            deliveryWeek= year.getWeek(today);
        else{
            try{
                deliveryWeek = Integer.parseInt(delivery);
            }catch (NumberFormatException e){
                MessageAlert.showErrorMessage(null,"Delivery week must either be a number or empty !");
                deliveryWeek=-99;
            }
        }
        if(deliveryWeek<homeworkservice.findById(idHomework).getStartWeek() && deliveryWeek!=-99){
            MessageAlert.showErrorMessage(null,"Delivery week cannot be less than the homework's start week !");
            deliveryWeek = -99;
        }
        return deliveryWeek;
    }

    public float getGrade() {
        float gradeValue = -99;
        try{
            gradeValue = Float.parseFloat(textFieldGradeValue.getText());
        }
        catch (NumberFormatException e){
            MessageAlert.showErrorMessage(null,"Grade value should be a number !");
        }
        if((gradeValue<1 || gradeValue>10) && gradeValue!=-99) {
            MessageAlert.showErrorMessage(null, "Grade value must be between 1 and 10 !");
            gradeValue=-99;
        }
        return gradeValue;
    }


    @FXML
    public void handleAddGrade(ActionEvent actionEvent) {
        if (handleStudent()) {
            String idstudent = getIDStudentFromTextField();
            String professor = getProfessorStudentFromTextField();
            int idhomework = getIDHomework();
            GradePair id = new GradePair(idstudent, idhomework);
            int deliveryweek = getDeliveryWeek(idhomework);
            int motivatedweeks = getMotivatedWeeks();
            int minuspoints = getMinusPoints(idhomework, deliveryweek);
            float value = getGrade();
            String feedback = textAreaFeedback.getText();
            if (deliveryweek != -99 && value != -99 && motivatedweeks != -99) {
                //if they exist , we search for the grade
                Grade grade = gradeservice.findById(id);
                if (grade == null) {
                    //if the student has no  grade for this homework , we will add him
                    showNotification(idstudent, idhomework, deliveryweek, professor, value, feedback, motivatedweeks, minuspoints);
                } else {
                    //if the student has a grade for this homework :
                    MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Trying to save...", "The student already has a grade for this homework !");
                }
            }
        }
    }

    public void showNotification(String idStudent,int idHomework, int deliveryWeek,String professor,float gradeValue,String feedback,int motivatedWeeks,int minusPoints) {
        //getting the name and surname of the student
        String name = studentservice.findById(idStudent).getName();
        String surname = studentservice.findById(idStudent).getSurname();
        //finding the final value for the grade
        gradeValue = getGradeValue(gradeValue,minusPoints,motivatedWeeks);

        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/NotificationView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Save grade ?");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //setting the scene
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            //controller that handles the add operation
            ViewController viewController = loader.getController();
            viewController.setService(gradeservice, dialogStage,idHomework,idStudent,gradeValue,minusPoints,motivatedWeeks,deliveryWeek,professor,feedback,name,surname);

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
    public void handleRaports(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaportsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Raports");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            RaportsController raportscontroller=loader.getController();
            raportscontroller.setService(gradeservice,studentservice,homeworkservice);

            dialogStage.setWidth(910);
            dialogStage.setHeight(500);
            dialogStage.setMinHeight(400);
            dialogStage.setMinWidth(400);
            dialogStage.setMaxWidth(1500);
            dialogStage.setMaxHeight(700);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//@FXML
//    //public void handleAddGrade(ActionEvent actionEvent) {
////        boolean ok = true;
////
////        //getting the student info from the table : id and professor
////        Student selected = (Student) tableViewStudent.getSelectionModel().getSelectedItem();
////        String idStudent="";
////        String professor="";
////        if (selected != null) {
////            idStudent = selected.getId();
////            professor = selected.getProfessorLab();
////        } else {
////            ok = false;
////            MessageAlert.showErrorMessage(null, "No student selected!");
////        }
//        //getting the id of the homework from the combobox
////        int idHomework = (Integer) comboBoxHomework.getSelectionModel().getSelectedItem();
//        //if there is a student selected
//        if(ok){
//            //create the id of the grade
//            GradePair id = new GradePair(idStudent,idHomework);
//            //handle the motivated weeks field
////            int motivatedWeeks = 0;
////            String motivated = textFieldMotivatedWeeks.getText();
////            if(motivated.equals(""))
////                motivatedWeeks=0;
////            else{
////                try{
////                    motivatedWeeks = Integer.parseInt(motivated);
////                }catch(NumberFormatException e){
////                    ok =false;
////                    MessageAlert.showErrorMessage(null,"Number of weeks must either be a number or empty !");
////                }
////            }
//            //handle the delivery week field
////            LocalDate today = LocalDate.now();
////            int deliveryWeek = year.getWeek(today);
////            String delivery = textFieldDeliveryWeek.getText();
////            if(delivery.equals(""))
////                deliveryWeek= year.getWeek(today);
////            else{
////                try{
////                    deliveryWeek = Integer.parseInt(delivery);
////                }catch (NumberFormatException e){
////                    ok =false;
////                    MessageAlert.showErrorMessage(null,"Delivery week must either be a number or empty !");
////                }
////            }
//            //delivery week being lower than the deadline week
//            if(deliveryWeek<homeworkservice.findById(idHomework).getStartWeek()){
//                ok=false;
//                MessageAlert.showErrorMessage(null,"Delivery week cannot be less than the homework's start week !");
//            }
//            //handle the grade value field
////            float gradeValue = -1;
////            try{
////                gradeValue = Float.parseFloat(textFieldGradeValue.getText());
////            }
////            catch (NumberFormatException e){
////                ok = false;
////                MessageAlert.showErrorMessage(null,"Grade value should be a number !");
////            }
//            //finding the minus points
//            int minusPoints = getMinusPoints(idHomework,deliveryWeek);
//            String feedback = textAreaFeedback.getText();
//
////            if(feedback.equals("NOTA NU MAI POATE FI\n ASIGNATA (MAI MULT DE 2 \n SAPTAMANI INTARZIERE)")){
////                MessageAlert.showErrorMessage(null,"Grade cannot be given because the student was too late !");
////                ok = false;
////            }
//            //if everything is good and the motivated weeks are okay
//            if( ok && gradeValue != -1 && (motivatedWeeks==0 || motivatedWeeks==1 || motivatedWeeks==2)){
//                try{
//                    //if the infos are not existent
//                    if(homeworkservice.findById(idHomework)==null || studentservice.findById(idStudent)==null){
//                        MessageAlert.showErrorMessage(null,"Grade cannot be given to inexistent student or homework !");
//                    }
//                    else{
//                        //if they exist , we search for the grade
//                        Grade grade = gradeservice.findById(id);
//                        if(grade==null){
//                            //if the student has no  grade for this homework , we will add him
//                            showNotification(idStudent,idHomework,deliveryWeek,professor,gradeValue,feedback,motivatedWeeks,minusPoints);
//                        }
//                        else{
//                            //if the student has a grade for this homework :
//                            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Trying to save...","The student already has a grade for this homework !");
//                        }
//                    }
//                }catch (ValidationException e) {
//                    MessageAlert.showErrorMessage(null,e.getMessage());
//                }
//            }
//            else{
//                if(motivatedWeeks!=0 && motivatedWeeks !=1 && motivatedWeeks!=2){
//                    MessageAlert.showErrorMessage(null,"The number of motivated weeks cannot be anything else than 0,1 or 2 !");
//                }
//            }
//        }
//    }


