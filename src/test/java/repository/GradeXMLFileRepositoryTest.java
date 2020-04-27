package repository;

import domain.Grade;
import domain.GradePair;
import validator.GradeValidator;

import static org.junit.Assert.*;

public class GradeXMLFileRepositoryTest {
    public void testAll(){
        testLoad();
        testAdd();
        //testDelete();
    }
    public void testLoad() {
        GradeValidator gradeValidator = new GradeValidator();
        GradeXMLFileRepository gradeRepo = new GradeXMLFileRepository(gradeValidator,"dataXML/tests/gradetest.xml");
        Iterable<Grade> list = gradeRepo.findAll();
        int nr=0;
        for(Grade gr:list) {
            assert("ipmn3795".equals(gr.getId().getIDStudent()) && 7==gr.getId().getIDHomework());
            nr++;
        }
        assertEquals(1, nr);
    }
    public void testAdd(){
        GradeValidator gradeValidator = new GradeValidator();
        GradeXMLFileRepository gradeRepo = new GradeXMLFileRepository(gradeValidator,"dataXML/tests/gradetest.xml");
        Grade gr = new Grade(10,"asda",7);
        String id1="abcd1234";
        int id2=5;
        GradePair p = new GradePair(id1,id2);
        gr.setId(p);
        gradeRepo.save(gr);
        GradeXMLFileRepository gradeRepo1 = new GradeXMLFileRepository(gradeValidator,"dataXML/tests/gradetest.xml");
        Iterable<Grade> list = gradeRepo1.findAll();
        int nr=0;
        for(Grade gru:list) {
            nr++;
        }
        assertEquals(2, nr);
        gradeRepo.delete(p);
    }
    /*
    public void testDelete(){
        GradeValidator gradeValidator = new GradeValidator();
        GradeXMLFileRepository gradeRepo = new GradeXMLFileRepository(gradeValidator,"dataXML/tests/gradetest.xml");
        String id1="abcd1234";
        int id2=5;
        GradePair p = new GradePair(id1,id2);
        gradeRepo.delete(p);
        GradeXMLFileRepository gradeRepo1 = new GradeXMLFileRepository(gradeValidator,"dataXML/tests/gradetest.xml");
        Iterable<Grade> list = gradeRepo1.findAll();
        int nr=0;
        for(Grade gru:list) {
            nr++;
        }
        assertEquals(1, nr);
    }
    */
}