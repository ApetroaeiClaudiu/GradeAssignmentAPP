package repository;

import domain.Student;
import validator.StudentValidator;

import static org.junit.Assert.*;

public class StudentXMLFileRepositoryTest {
    public void testAll(){
        testLoad();
        testAdd();
        testDelete();
    }
    public void testLoad() {
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLFileRepository studentRepo = new StudentXMLFileRepository(studentValidator, "dataXML/tests/studenttest.xml");
        Iterable<Student> list = studentRepo.findAll();
        int nr=0;
        for(Student st:list) {
            assertEquals("izop9999", st.getId());
            nr++;
        }
        assertEquals(1, nr);
    }
    public void testAdd(){
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLFileRepository studentRepo = new StudentXMLFileRepository(studentValidator, "dataXML/tests/studenttest.xml");
        Student st = new Student("abcd1234",2,"ana","banga","asda","asda");
        studentRepo.save(st);
        StudentXMLFileRepository studentRepo1 = new StudentXMLFileRepository(studentValidator, "dataXML/tests/studenttest.xml");
        Iterable<Student> list = studentRepo1.findAll();
        int nr=0;
        for(Student stu:list) {
            nr++;
        }
        assertEquals(2, nr);
    }
    public void testDelete(){
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLFileRepository studentRepo = new StudentXMLFileRepository(studentValidator, "dataXML/tests/studenttest.xml");
        studentRepo.delete("abcd1234");
        StudentXMLFileRepository studentRepo1 = new StudentXMLFileRepository(studentValidator, "dataXML/tests/studenttest.xml");
        Iterable<Student> list = studentRepo1.findAll();
        int nr=0;
        for(Student stu:list) {
            nr++;
        }
        assertEquals(1, nr);
    }
}