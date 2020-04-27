package service;

import domain.Grade;
import domain.GradePair;
import domain.Student;
import org.junit.Test;
import repository.InMemoryRepository;

import static org.junit.Assert.*;

public class GradeServiceTest {
    private GradeService gradeService;
    private StudentService studentService;
    private HomeworkService homeworkService;
    private InMemoryRepository<GradePair, Grade> gradeRepo;

    public GradeServiceTest(GradeService gradeService,StudentService studentService,HomeworkService homeworkService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.homeworkService = homeworkService;
    }
    public void testAll(){
        addSome();
        testGroupByDeliveredHomework();
        testGroupByDeliveredHomeworkAndProfessor();
        testGroupByHomeworkAndDeliveryWeek();
        testFilterGroup();
    }
    public void addSome(){

        studentService.add("abcd1234",1,"alex","bula","alex@yahoo.com","prof1");
        studentService.add("amcl9999",2,"alex","mateiuc","alexmat@yahoo.com","prof1");
        studentService.add("iilp1234",1,"ionut","iancu","iontian@yahoo.com","prof2");
        studentService.add("fzlm5673",2,"florin","zu","florin@yahoo.com","prof3");
        studentService.add("ipam1236",3,"ioana","pomparau","ionica@yahoo.com","prof2");

        homeworkService.add(1,10,"da");
        homeworkService.add(2,10,"da");
//
//        gradeService.add("abcd1234",1,2,"prof1",10,"nu",0);
//        gradeService.add("amcl9999",1,3,"prof1",10,"nu",0);
//        gradeService.add("iilp1234",2,4,"prof2",10,"nu",0);
//        gradeService.add("fzlm5673",2,4,"prof3",10,"nu",0);
//        gradeService.add("ipam1236",1,3,"prof3",10,"nu",0);
    }
    @Test
    public void testGroupByDeliveredHomework() {
        int i=0;
        Iterable<Student> list = gradeService.groupByDeliveredHomework(1);
        for(Student st : list){
            i++;
        }
        assertEquals(3,i);

    }

    @Test
    public void testGroupByDeliveredHomeworkAndProfessor() {
        int i=0;
        Iterable<Student> list = gradeService.groupByDeliveredHomeworkAndProfessor(1,"prof1");
        for(Student st : list){
            i++;
        }
        assertEquals(2,i);
    }

    @Test
    public void testGroupByHomeworkAndDeliveryWeek() {
        int i=0;
        Iterable<Grade> list = gradeService.groupByHomeworkAndDeliveryWeek(2,4);
        for(Grade gr : list){
            i++;
        }
        assertEquals(2,i);
    }
    @Test
    public void testFilterGroup(){
        int i=0;
        Iterable<Student> list = studentService.filterGroup(1);
        for(Student st : list ){
            i++;
        }
        assertEquals(2,i);
    }
}