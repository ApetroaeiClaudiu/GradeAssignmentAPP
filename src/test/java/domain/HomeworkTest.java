package domain;


import org.junit.Test;

import static org.junit.Assert.*;

public class HomeworkTest {
    public void testAll(){
        testGetStartWeek();
        testGetDeadlineWeek();
        testGetDescription();
        testToString();
    }

    @Test
    public void testGetStartWeek() {
        Homework tema = new Homework(1,5,"asda");
        assertEquals(1, tema.getStartWeek());
    }

    @Test
    public void testGetDeadlineWeek() {
        Homework tema = new Homework(1,5,"asda");
        assertEquals(5, tema.getDeadlineWeek());
    }

    @Test
    public void testGetDescription() {
        Homework tema = new Homework(1,5,"asda");
        assertEquals("asda", tema.getDescription());
    }

    @Test
    public void testToString(){
        Homework tema = new Homework(1,5,"asda");
        tema.setId(1);
        String ex = "Homework{ID=1, startWeek=1, deadlineWeek=5, description='asda'}";
        assertEquals(ex,tema.toString());
    }
}