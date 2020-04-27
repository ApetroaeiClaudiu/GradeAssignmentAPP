package domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class GradeTest {
    public void testAll(){
        testGetValue();
        testGetProfessor();
        testGetDeliveryWeek();
        testToString();
    }

    @Test
    public void testGetValue() {
        Grade gd = new Grade(10,"andrei",6);
        assertEquals(10, gd.getValue(), 0.0);
    }

    @Test
    public void testGetProfessor() {
        Grade gd = new Grade(10,"andrei",6);
        assertEquals("andrei", gd.getProfessor());
    }

    @Test
    public void testGetDeliveryWeek() {
        LocalDate date = LocalDate.of(2019, Month.NOVEMBER,10);
        Grade gd = new Grade(10,"andrei",6);
        assertEquals(6, gd.getDeliveryWeek());
    }

    @Test
    public void testToString() {
        Grade gd = new Grade(10,"andrei",6);
        int IDh=3;
        String IDs = "asda1234";
        GradePair s = new GradePair(IDs,IDh);
        gd.setId(s);
        String ex = "Grade{ID= asda1234 at 3, value=10.0, professor='andrei', deliveryWeek=6}";
        assertEquals(ex,gd.toString());
    }
}