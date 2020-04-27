package domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class UniversityYearStructureTest {

    public void testAll(){
        testGetWeekFirstPart();
        testGetWeekVacation();
        testGetWeekSecondPart();
        testGetWeekElse();
    }

    @Test
    public void testGetWeekFirstPart() {
        LocalDate start1Date,end1Date,startVacation,endVacation,start2Date,end2Date;
        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
        SemesterStructure sem = new SemesterStructure(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
        UniversityYearStructure an =new UniversityYearStructure(sem);
        LocalDate now = LocalDate.of(2019,Month.DECEMBER,20);
        assertEquals(12, an.getWeek(now));
    }

    public void testGetWeekVacation(){
        LocalDate start1Date,end1Date,startVacation,endVacation,start2Date,end2Date;
        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
        SemesterStructure sem = new SemesterStructure(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
        UniversityYearStructure an =new UniversityYearStructure(sem);
        LocalDate now = LocalDate.of(2019,Month.DECEMBER,26);
        assertEquals(an.getWeek(now), -1);
    }

    public void testGetWeekSecondPart() {
        LocalDate start1Date, end1Date, startVacation, endVacation, start2Date, end2Date;
        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
        SemesterStructure sem = new SemesterStructure(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
        UniversityYearStructure an =new UniversityYearStructure(sem);
        LocalDate now = LocalDate.of(2020, Month.JANUARY, 8);
        assertEquals(13, an.getWeek(now));
    }

    public void testGetWeekElse(){
        LocalDate start1Date, end1Date, startVacation, endVacation, start2Date, end2Date;
        start1Date = LocalDate.of(2019, Month.SEPTEMBER, 30);
        end1Date = LocalDate.of(2019, Month.DECEMBER, 20);
        startVacation = LocalDate.of(2019, Month.DECEMBER, 23);
        endVacation = LocalDate.of(2020, Month.JANUARY, 5);
        start2Date = LocalDate.of(2020, Month.JANUARY, 6);
        end2Date = LocalDate.of(2020, Month.JANUARY, 17);
        SemesterStructure sem = new SemesterStructure(start1Date,end1Date,startVacation,endVacation,start2Date,end2Date);
        UniversityYearStructure an =new UniversityYearStructure(sem);
        LocalDate now = LocalDate.of(2021, Month.JANUARY, 8);
        assertEquals(an.getWeek(now), -1);
    }
}