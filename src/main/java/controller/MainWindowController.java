package controller;

import domain.SemesterStructure;
import domain.UniversityYearStructure;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.HomeworkXMLFileRepository;
import repository.StudentXMLFileRepository;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import validator.GradeValidator;
import validator.HomeworkValidator;
import validator.StudentValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class MainWindowController {
    private UniversityYearStructure year;
    private  StudentService studentService;
    private HomeworkService homeworkService;
    private GradeService gradeService;

    public void setWindow(UniversityYearStructure year,StudentService studentService, HomeworkService homeworkService, GradeService gradeService){
        this.year = year;
        this.studentService = studentService;
        this.homeworkService = homeworkService;
        this.gradeService = gradeService;
    }

    public void handleStudents(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/StudentView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Student Application");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("/images/logonou.png"));

            StudentController studentController = loader.getController();
            studentController.setStudentService(studentService,gradeService);

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
    public void handleHomework(){
        try {
            FXMLLoader loaderHomework = new FXMLLoader();
            loaderHomework.setLocation(getClass().getResource("/views/HomeworkView.fxml"));
            AnchorPane root = (AnchorPane) loaderHomework.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Homework Application");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("/images/logonou.png"));

            HomeworkController homeworkController = loaderHomework.getController();
            homeworkController.setHomeworkService(homeworkService);

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

    public void handleGrades(){
        try {
            FXMLLoader loaderGrade = new FXMLLoader();
            loaderGrade.setLocation(getClass().getResource("/views/GradeView.fxml"));
            AnchorPane root = (AnchorPane) loaderGrade.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Grade Application");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("/images/logonou.png"));

            GradeController gradeController = loaderGrade.getController();
            gradeController.setGradeService(year,gradeService,studentService,homeworkService);

            dialogStage.setWidth(910);
            dialogStage.setHeight(700);
            dialogStage.setMinHeight(500);
            dialogStage.setMinWidth(400);
            dialogStage.setMaxWidth(1500);
            dialogStage.setMaxHeight(1000);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
