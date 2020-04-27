package controller;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import utils.event.GradeChangeEvent;
import utils.event.StudentChangeEvent;
import utils.event.StudentStatusEvent;
import utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RaportsController implements Observer<StudentChangeEvent> {
    private GradeService gradeservice;
    private StudentService studentservice;
    private HomeworkService homeworkservice;

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
    TableColumn<Student, Float> tableColumnGrade;

    public void setService(GradeService gradeService, StudentService studentService,HomeworkService homeworkService) {
        this.gradeservice = gradeService;
        this.studentservice = studentService;
        this.homeworkservice = homeworkService;
        studentservice.addObserver(this);
        initModel();
    }

    public void initialize() {
        tableColumnID.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getId());
            }
        });
        tableColumnSurname.setCellValueFactory(new PropertyValueFactory<Student, String>("Surname"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("Group"));
        tableColumnGrade.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<Student,Float> param){
                return new ReadOnlyObjectWrapper<Float>(0F);
            }
        });

        tableView.setItems(model);
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

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        initModel();
    }

    public float getAverage (Student student){
        Iterable<Grade> grades = gradeservice.findAll();
        float value=0;
        int weight;
        int weightSum=0;
        for(Grade grade : grades){
            if(grade.getId().getIDStudent().equals(student.getId())){
                Homework homework = homeworkservice.findById(grade.getId().getIDHomework());
                weight = homework.getDeadlineWeek() - homework.getStartWeek();
                value = value + grade.getValue()*weight;
                weightSum = weightSum + weight;
            }
        }
//        if(value==0 || weightSum==0){
//            return 0;
//        }
        return value/weightSum;
    }

    public boolean notLate(Student student){
        Iterable<Grade> grades = gradeservice.findAll();
        for(Grade grade : grades){
            if((grade.getId().getIDStudent().equals(student.getId()))){
                if(grade.getDeliveryWeek() > homeworkservice.findById(grade.getId().getIDHomework()).getDeadlineWeek()){
                    return false;
                }
            }
        }
        return true;
    }

    public void handleClear(){
        initModel();
    }

    public void handleRaportLabGrade(){
        tableColumnGrade.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<Student,Float> param){
                return new ReadOnlyObjectWrapper<Float>(getAverage(param.getValue()));
            }
        });
        tableView.refresh();
    }

    public void handleRaportHardestHomework(){
        int hardestHomeworkID = -999;
        float min = 9999999;
        int count ;
        float sum ;
        Iterable<Grade> grades = gradeservice.findAll();
        Iterable<Homework> homeworks = homeworkservice.findAll();
        for(Homework homework : homeworks){
            count = 0;
            sum = 0;
            for(Grade grade : grades){
                if(grade.getId().getIDHomework()==homework.getId()){
                   count++;
                   sum=sum+grade.getValue();
                }
            }
            if(sum/count < min){
                hardestHomeworkID = homework.getId();
                min = sum/count;
            }
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "The Hardest Homework...", "The hardest homework is \n" + homeworkservice.findById(hardestHomeworkID) + "\nWith the average of " +min);

    }
    public void handleRaportAvailableStudents(){
        Predicate<Student> byPassed = x -> getAverage(x)>=4;
        model.setAll(getStudents().stream()
                .filter(byPassed)
                .collect(Collectors.toList()));
    }

    public void handleRaportCleanStudents(){
        Predicate<Student> byNotLate = this::notLate;
        model.setAll(getStudents().stream()
                .filter(byNotLate)
                .collect(Collectors.toList()));
    }
}