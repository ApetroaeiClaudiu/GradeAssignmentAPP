package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UniversityYearStructure {

    private LocalDate startFirstPartDate;
    private LocalDate endFirstPartDate;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;
    private LocalDate startSecondPartDate;
    private LocalDate endSecondPartDate;

    public UniversityYearStructure(SemesterStructure semester) {
        this.startFirstPartDate = semester.getStartFirstPartDate();
        this.endFirstPartDate = semester.getEndFirstPartDate();
        this.vacationStartDate = semester.getVacationStartDate();
        this.vacationEndDate = semester.getVacationEndDate();
        this.startSecondPartDate = semester.getStartSecondPartDate();
        this.endSecondPartDate = semester.getEndSecondPartDate();
    }
    /**
     * @param inceput  the date that we want to calculate its number
     * @return and integer ,  meaning the startweek for the homeworks
     * that are added in real time
     */
    public int getWeek(LocalDate inceput){
        //if the "today" date is between the beginning of the semester and the ending of the first part
        //calculates the number of the current week and returns it
        //by finding the number of weeks between and adding 1
        if (inceput.isAfter(startFirstPartDate) && inceput.isBefore(vacationStartDate)) {
            long weeksInYear = ChronoUnit.WEEKS.between(startFirstPartDate, inceput);
            return (int) weeksInYear + 1;
        }
        //if the "today" date is in vacation
        //return -1 so the startWeek will be invalid
        if (inceput.isAfter(vacationStartDate) && inceput.isBefore(vacationEndDate)) {
            return -1;
        }
        //if the "today" date is in the second part of the semester
        //calculates the number of the current week and returns it
        //by finding the number of weeks between , adding 1
        //and then adding the number of weeks in the first part of the semester
        if (inceput.isAfter(startSecondPartDate) && inceput.isBefore(endSecondPartDate)) {
            long weeksInYear = ChronoUnit.WEEKS.between(startSecondPartDate, inceput);
            long weeksBetween = ChronoUnit.WEEKS.between(startFirstPartDate,vacationStartDate) +1;
            return (int) weeksInYear + (int)weeksBetween ;
        }
        //if the "today" date is either sooner than the beginning or after the semester ends
        else{
            return -1;
        }
    }
}
