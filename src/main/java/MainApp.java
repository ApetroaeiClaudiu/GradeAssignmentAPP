//
//import controller.ProfController;
//import controller.StudController;
//import controller.StudentController;
//import domain.Intrebare;
//import javafx.application.Application;
//        import javafx.fxml.FXMLLoader;
//        import javafx.scene.Scene;
//        import javafx.scene.layout.AnchorPane;
//        import javafx.stage.Stage;
//import repository.InMemoryProiectRepository;
//import repository.IntrebareFileRepository;
//        import service.IntrebareService;
//
//        import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MainApp extends Application {
//    //private InMemoryProiectRepository intrebareRepo;
//    private IntrebareFileRepository intrebareRepo;
//    private IntrebareService intrebareService;
//    static ArrayList<String> ar = new ArrayList<String >();
//
//        private String input(String prompt) {
//        Scanner input = new Scanner(System.in);
//        System.out.print(prompt);
//        return input.nextLine();
//    }
//    public static void main(String[] args) {
//        adauga(args);
//        launch(args);
//
//    }
//    public static void adauga(String[] args){
//            ar.add(args[0]);
//            ar.add(args[1]);
//    }
//    @Override
//    public void start(Stage primaryStage) throws Exception {
////        int nr = Integer.parseInt(input("dati nr :"));
////        for(int k =1;k<=nr;k++){
////            String nume = input("da numele:");
////            ar.add(nume);
////        }
//
//
//
//
//        String filename = "data/quizes.txt";
//        intrebareRepo = new IntrebareFileRepository(filename);
//        //intrebareRepo = new InMemoryProiectRepository<Integer, Intrebare>();
//        intrebareService = new IntrebareService(intrebareRepo);
////        String[] variante = {"a","b","c"};
////        intrebareService.add(1,"ceva","a",5,variante);
////        String[] variante1 = {"a","b","c"};
////        intrebareService.add(2,"altceva","c",5,variante1);
////        String[] variante2 = {"e","d","c"};
////        intrebareService.add(3,"ceva","d",5,variante2);
////        String[] variante3 = {"g","h","c"};
////        intrebareService.add(4,"ceva","g",5,variante3);
//        initialize(primaryStage,ar);
////        primaryStage.setTitle("UBB DATABASE MANAGER");
////        primaryStage.setWidth(600);
////        primaryStage.setHeight(300);
////        primaryStage.setMinHeight(200);
////        primaryStage.setMinWidth(400);
////        primaryStage.setMaxWidth(900);
////        primaryStage.setMaxHeight(600);
////        primaryStage.show();
//    }
//    private void initialize(Stage primaryStage,ArrayList<String> array) throws IOException {
//        int ok= 0;
//        ArrayList<StudController> listacontrol = new ArrayList<StudController>();
////        FXMLLoader mainWindowLoader = new FXMLLoader();
////        mainWindowLoader.setLocation(getClass().getResource("/views/ProfView.fxml"));
////        AnchorPane mainLayout = mainWindowLoader.load();
////        primaryStage.setScene(new Scene(mainLayout));
////        ProfController mainWindowController = mainWindowLoader.getController();
////        mainWindowController.setService(intrebareService,array);
//        //creare ferestre studenti
//        if(ok==0){
//            StudController[] lista = new StudController[100];
//            for(int i=0;i<array.size();i++){
//                FXMLLoader studentLoader = new FXMLLoader();
//                studentLoader.setLocation(getClass().getResource("/views/StudView.fxml"));
//                AnchorPane studentpane = studentLoader.load();
//                Stage dialogueStage = new Stage();
//                dialogueStage.setTitle(array.get(i));
//                Scene scene = new Scene(studentpane);
//                dialogueStage.setScene(scene);
//                StudController studentController = studentLoader.getController();
//                studentController.setService(intrebareService);
//                listacontrol.add(studentController);
//                //studentController;
//                dialogueStage.setWidth(910);
//                dialogueStage.setHeight(500);
//                dialogueStage.setMinHeight(400);
//                dialogueStage.setMinWidth(400);
//                dialogueStage.setMaxWidth(1500);
//                dialogueStage.setMaxHeight(700);
//                dialogueStage.show();
//            }
//            FXMLLoader profLoader = new FXMLLoader();
//            profLoader.setLocation(getClass().getResource("/views/ProfView.fxml"));
//            AnchorPane profpane = profLoader.load();
//            Stage dialogueStageprof = new Stage();
//            dialogueStageprof.setTitle("profesor");
//            Scene profscene = new Scene(profpane);
//            dialogueStageprof.setScene(profscene);
//            ProfController profController = profLoader.getController();
//            profController.setService(intrebareService,array,listacontrol);
//            //studentController;
//            dialogueStageprof.setWidth(910);
//            dialogueStageprof.setHeight(500);
//            dialogueStageprof.setMinHeight(400);
//            dialogueStageprof.setMinWidth(400);
//            dialogueStageprof.setMaxWidth(1500);
//            dialogueStageprof.setMaxHeight(700);
//            dialogueStageprof.show();
//        }
//        }
//}



import controller.HomeworkController;
import controller.MainWindowController;
import controller.ProfController;
import controller.StudentController;
import domain.SemesterStructure;
import domain.Student;
import domain.UniversityYearStructure;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import repository.*;
import service.GradeService;
import service.HomeworkService;
import service.IntrebareService;
import service.StudentService;
import validator.GradeValidator;
import validator.HomeworkValidator;
import validator.StudentValidator;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;


public class MainApp extends Application {
    UniversityYearStructure year;
    private StudentXMLFileRepository studentRepo;
    private StudentValidator studentValidator;
    private StudentService studentService;
    private HomeworkValidator homeworkValidator;
    private HomeworkXMLFileRepository homeworkRepo;
    private HomeworkService homeworkService;
    private GradeValidator gradeValidator;
    private GradeXMLFileRepository gradeRepo;
    private GradeService gradeService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String studentFile = ApplicationContext.getPROPERTIES().getProperty("data.tasks.Student");
        studentValidator = new StudentValidator();
        studentRepo = new StudentXMLFileRepository(studentValidator,studentFile);
        studentService = new StudentService(studentRepo);

        String homeworkFile = ApplicationContext.getPROPERTIES().getProperty("data.tasks.Homework");
        homeworkValidator = new HomeworkValidator();
        homeworkRepo =  new HomeworkXMLFileRepository(homeworkValidator,homeworkFile);
        LocalDate start1Date,end1Date,startVacation,endVacation,start2Date,end2Date;
        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
        SemesterStructure sem = new SemesterStructure(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
        year = new UniversityYearStructure(sem);
        homeworkService = new HomeworkService(homeworkRepo,year);

        String gradeFile= ApplicationContext.getPROPERTIES().getProperty("data.tasks.Grade");
        gradeValidator = new GradeValidator();
        gradeRepo = new GradeXMLFileRepository(gradeValidator,gradeFile);
        gradeService = new GradeService(gradeRepo,studentRepo,homeworkRepo,year);

        initialize(primaryStage);
        primaryStage.setTitle("UBB DATABASE MANAGER");
        primaryStage.getIcons().add(new Image("/images/logonou.png"));
        primaryStage.setWidth(600);
        primaryStage.setHeight(300);
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxWidth(900);
        primaryStage.setMaxHeight(600);
        primaryStage.show();
    }

    private void initialize(Stage primaryStage) throws IOException {
        FXMLLoader mainWindowLoader = new FXMLLoader();
        mainWindowLoader.setLocation(getClass().getResource("/views/mainWindow.fxml"));
        AnchorPane mainLayout = mainWindowLoader.load();
        primaryStage.setScene(new Scene(mainLayout));
        MainWindowController mainWindowController = mainWindowLoader.getController();
        mainWindowController.setWindow(year,studentService,homeworkService,gradeService);
    }
}
