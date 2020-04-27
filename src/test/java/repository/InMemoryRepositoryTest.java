package repository;


import domain.Student;
import org.junit.Before;
import org.junit.Test;
import validator.StudentValidator;


import static org.junit.Assert.*;

public class InMemoryRepositoryTest {

    @Before
    public void setUp() {
        testSave();
        testSaveNull();
        testDelete();
        testDeleteNull();
        testUpdate();
        testUpdateNull();
        testUpdateLastEntityNull();
        testFindOne();
        testFindOneNull();
        findAll();
    }

    @Test
    public void testSave() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("acor1234",1, "a", "c", "d", "e");
        studentRepo.save(st1);
        Student student = studentRepo.findOne("acor1234");
        assertEquals("a", student.getSurname());
    }
    public void testSaveNull(){
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        try{
            Student st2=null;
            studentRepo.save(st2);
        }catch(IllegalArgumentException e){ }
    }

    @Test
    public void testDelete() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("acor1234",1,"a","c","d","e");
        studentRepo.save(st1);
        studentRepo.delete("acor1234");
        Iterable<Student> list = studentRepo.findAll();
        int nr=0;
        for(Student st:list)
            nr++;
        assertEquals(0, nr);
    }
    @Test
    public void testDeleteNull() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("acor1234",1,"a","c","d","e");
        studentRepo.save(st1);
        try{
            studentRepo.delete(null);
        }catch(IllegalArgumentException e){ }
    }

    @Test
    public void testUpdate() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("acip4567",1,"a","c","d","e");
        studentRepo.save(st1);
        Student st2 = new Student("acip4567",2,"a","c","h","i");
        studentRepo.update(st2);
        Iterable<Student> list = studentRepo.findAll();
        int nr=0;
        for(Student st:list){
            assertEquals("a", st.getSurname());
            nr++;
        }
        assertEquals(1, nr);
    }
    @Test
    public void testUpdateNull() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        try{
            studentRepo.update(null);
        }catch(IllegalArgumentException e){ }
    }

    @Test
    public void testUpdateLastEntityNull(){
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("acip9999",1,"a","c","d","e");
        studentRepo.save(st1);
        Student st2 = new Student("acip4567",2,"a","c","h","i");
        assertSame(studentRepo.update(st2), st2);
    }

    @Test
    public void testFindOne() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("aciq4567",1,"a","c","d","e");
        studentRepo.save(st1);
        Student st2 = new Student("acla1234",2,"a","c","h","i");
        studentRepo.save(st2);
        Student student = studentRepo.findOne("acla1234");
        assertEquals("a", student.getSurname());
    }

    @Test
    public void testFindOneNull() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("aciq4567",1,"a","c","d","e");
        studentRepo.save(st1);
        Student st2 = new Student("acla1234",2,"a","c","h","i");
        studentRepo.save(st2);
        try{
            Student student = studentRepo.findOne(null);
        }catch(IllegalArgumentException e){ }
    }

    @Test
    public void findAll() {
        StudentValidator studentValidator = new StudentValidator();
        InMemoryRepository<String, Student> studentRepo = new InMemoryRepository<String, Student>(studentValidator);
        Student st1 = new Student("accz8763",1,"a","c","d","e");
        studentRepo.save(st1);
        Student st2 = new Student("oglm9999",2,"o","g","h","i");
        studentRepo.save(st2);
        Iterable<Student> list = studentRepo.findAll();
        int nr=0;
        for(Student st:list){
            nr++;
        }
        assertEquals(2, nr);
    }
}