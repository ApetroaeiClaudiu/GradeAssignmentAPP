package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    public void testAll(){
        testGetGroup();
        testGetSurname();
        testGetName();
        testGetEmail();
        testGetProfessorLab();
        testToString();
    }

    @Test
    public void testGetGroup() {
        Student st=new Student("aclp1234",1,"a","c","d","e");
        assertEquals(1, st.getGroup());
    }

    @Test
    public void testGetSurname() {
        Student st=new Student("aclp123",1,"a","c","d","e");
        assertEquals("a", st.getSurname());
    }

    @Test
    public void testGetName() {
        Student st=new Student("aclp123",1,"a","c","d","e");
        assertEquals("c", st.getName());
    }

    @Test
    public void testGetEmail() {
        Student st=new Student("aclp1234",1,"a","c","d","e");
        assertEquals("d", st.getEmail());
    }

    @Test
    public void testGetProfessorLab() {
        Student st=new Student("aclp1234",1,"a","c","d","e");
        assertEquals("e", st.getProfessorLab());
    }

    @Test
    public void testToString(){
        Student st = new Student("aalp1234",1,"abc","asda","pollasda","llll");
        String ex = "Student{ID= aalp1234, group=1, surname='abc', name='asda', email='pollasda', professorLab='llll'}";
        assertEquals(ex,st.toString());
    }
}