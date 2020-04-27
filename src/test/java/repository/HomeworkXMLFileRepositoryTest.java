package repository;



import domain.Homework;
import validator.HomeworkValidator;

import static org.junit.Assert.*;

public class HomeworkXMLFileRepositoryTest {
    public void testAll(){
        testLoad();
        testAdd();
        testDelete();
    }
    public void testLoad() {
        HomeworkValidator homeworkValidator = new HomeworkValidator();
        HomeworkXMLFileRepository homeworkRepo = new HomeworkXMLFileRepository(homeworkValidator, "dataXML/tests/homeworktest.xml");
        Iterable<Homework> list = homeworkRepo.findAll();
        int nr=0;
        for(Homework h:list) {
            assert(h.getId()==1);
            nr++;
        }
        assertEquals(1, nr);
    }
    public void testAdd(){
        HomeworkValidator homeworkValidator = new HomeworkValidator();
        HomeworkXMLFileRepository homeworkRepo = new HomeworkXMLFileRepository(homeworkValidator, "dataXML/tests/homeworktest.xml");
        Homework home = new Homework(2,3,"ana");
        home.setId(2);
        homeworkRepo.save(home);
        HomeworkXMLFileRepository homeworkRepo1 = new HomeworkXMLFileRepository(homeworkValidator, "dataXML/tests/homeworktest.xml");
        Iterable<Homework> list = homeworkRepo1.findAll();
        int nr=0;
        for(Homework h:list) {
            nr++;
        }
        assertEquals(2, nr);
    }
    public void testDelete(){
        HomeworkValidator homeworkValidator = new HomeworkValidator();
        HomeworkXMLFileRepository homeworkRepo = new HomeworkXMLFileRepository(homeworkValidator, "dataXML/tests/homeworktest.xml");
        homeworkRepo.delete(2);
        HomeworkXMLFileRepository homeworkRepo1 = new HomeworkXMLFileRepository(homeworkValidator, "dataXML/tests/homeworktest.xml");
        Iterable<Homework> list = homeworkRepo1.findAll();
        int nr=0;
        for(Homework h:list) {
            nr++;
        }
        assertEquals(1, nr);
    }
}