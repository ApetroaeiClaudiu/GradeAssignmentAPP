import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;

public class Main{

    public static void main(String[] args) {
        MainApp.main(args);
    }

//    @Override
//    public void start(Stage primaryStage) {
//
//    }
}

//package com.proiect;
//
//        import com.proiect.Domain.*;
//        import com.proiect.Repository.*;
//        import com.proiect.Service.GradeService;
//        import com.proiect.Service.GradeServiceTest;
//        import com.proiect.Service.StudentService;
//        import com.proiect.Service.HomeworkService;
//        import com.proiect.UI.UI;
//        import com.proiect.Validator.*;
//
//        import java.time.LocalDate;
//        import java.time.Month;
//
//
//
//public class Main {
//
//    public static void main(String[] args) {
//        /**
//         * creates the university semester and year structure
//         */
//        LocalDate start1Date,end1Date,startVacation,endVacation,start2Date,end2Date;
//        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
//        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
//        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
//        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
//        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
//        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
//        StructuraSemestru sem = new StructuraSemestru(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
//        StructuraAnUniversitar an =new StructuraAnUniversitar(sem);
//        /**
//         *  tests
//         */
//        StudentValidatorTest studentValidatorTest = new StudentValidatorTest();
//        studentValidatorTest.testAll();
//        HomeworkValidatorTest homeworkValidatorTest = new HomeworkValidatorTest();
//        homeworkValidatorTest.testAll();
//        GradeValidatorTest gradeValidatorTest = new GradeValidatorTest();
//        gradeValidatorTest.testAll();
//        InMemoryRepositoryTest test = new InMemoryRepositoryTest();
//        test.setUp();
//        StudentTest studentTest = new StudentTest();
//        studentTest.testAll();
//        HomeworkTest homeworkTest=new HomeworkTest();
//        homeworkTest.testAll();
//        GradeTest gradeTest = new GradeTest();
//        gradeTest.testAll();
//        StructuraAnUniversitarTest structuraAnUniversitarTest = new StructuraAnUniversitarTest();
//        structuraAnUniversitarTest.testAll();
//        HomeworkFileRepositoryTest homeworkFileRepositoryTest = new HomeworkFileRepositoryTest();
//        homeworkFileRepositoryTest.testAll();
//        StudentXMLFileRepositoryTest studentXMLFileRepositoryTest = new StudentXMLFileRepositoryTest();
//        studentXMLFileRepositoryTest.testAll();
//        HomeworkXMLFileRepositoryTest homeworkXMLFileRepositoryTest = new HomeworkXMLFileRepositoryTest();
//        homeworkXMLFileRepositoryTest.testAll();
//        GradeXMLFileRepositoryTest gradeXMLFileRepositoryTest = new GradeXMLFileRepositoryTest();
//        gradeXMLFileRepositoryTest.testAll();
//
//
//        /**
//         * creating the needed classes
//         */
//        HomeworkValidator homeworkValidator =  new HomeworkValidator();
//        StudentValidator studentValidator = new StudentValidator();
//        GradeValidator gradeValidator = new GradeValidator();
//
//
//        InMemoryRepository<String,Student> studentInMemoryRepository = new InMemoryRepository<String,Student>(studentValidator);
//        StudentService studservtest = new StudentService(studentInMemoryRepository);
//        InMemoryRepository<Integer,Homework> homeworkInMemoryRepository = new InMemoryRepository<Integer, Homework>(homeworkValidator);
//        HomeworkService homeworkservicetest = new HomeworkService(homeworkInMemoryRepository,an);
//        GradeFileRepository GradefiletestRepository = new GradeFileRepository(gradeValidator,"data/testsfilter/gradefilter.txt");
//        GradeService gradeservicetest = new GradeService(GradefiletestRepository,studentInMemoryRepository,homeworkInMemoryRepository,an);
//
//
//        GradeServiceTest filtertest = new GradeServiceTest(gradeservicetest,studservtest,homeworkservicetest);
//        filtertest.testAll();
//        //InMemoryRepository<String, Homework> homeworkRepo = new InMemoryRepository<String, Homework>(homeworkValidator);
//        //InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
////        StudentFileRepository studentRepo = new StudentFileRepository(studentValidator,"data/students.txt");
////        HomeworkFileRepository homeworkRepo = new HomeworkFileRepository(homeworkValidator,"data/homework.txt");
////        GradeFileRepository gradeRepo = new GradeFileRepository(gradeValidator,"data/grades.txt");
//        GradeXMLFileRepository gradeRepo = new GradeXMLFileRepository(gradeValidator,"dataXML/grades.XML");
//        StudentXMLFileRepository studentRepo = new StudentXMLFileRepository(studentValidator,"dataXML/students.xml");
//        HomeworkXMLFileRepository homeworkRepo = new HomeworkXMLFileRepository(homeworkValidator,"dataXML/homework.xml");
//
//
//        StudentService studentService = new StudentService(studentRepo);
//        HomeworkService homeworkService = new HomeworkService(homeworkRepo,an);
//        GradeService gradeService = new GradeService(gradeRepo,studentRepo,homeworkRepo,an);
//        UI ui = new UI(studentService,homeworkService,gradeService);
//        ui.showUI();
//    }
//}
