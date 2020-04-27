package domain;

import java.time.LocalDate;

public class SemesterStructure {
    private LocalDate startFirstPartDate;
    private LocalDate endFirstPartDate;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;
    private LocalDate startSecondPartDate;
    private LocalDate endSecondPartDate;

    /**
     *
     * @param startFirstPartDate - represents the date of the beginning of the university year
     * @param endFirstPartDate - represents the end of the first part of the university year
     * @param vacationStartDate - represents the beginning of  the vacation
     * @param vacationEndDate - represents the ending of the vacation
     * @param startSecondPartDate - represents the beginning of the second part of the semester
     * @param endSecondPartDate - represents the end of the semester
     */
    public SemesterStructure(LocalDate startFirstPartDate, LocalDate endFirstPartDate, LocalDate vacationStartDate, LocalDate vacationEndDate, LocalDate startSecondPartDate, LocalDate endSecondPartDate) {
        this.startFirstPartDate = startFirstPartDate;
        this.endFirstPartDate = endFirstPartDate;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
        this.startSecondPartDate = startSecondPartDate;
        this.endSecondPartDate = endSecondPartDate;
    }

    public LocalDate getStartFirstPartDate() {
        return startFirstPartDate;
    }

    public LocalDate getEndFirstPartDate() {
        return endFirstPartDate;
    }

    public LocalDate getVacationStartDate() {
        return vacationStartDate;
    }

    public LocalDate getVacationEndDate() {
        return vacationEndDate;
    }

    public LocalDate getStartSecondPartDate() {
        return startSecondPartDate;
    }

    public LocalDate getEndSecondPartDate() {
        return endSecondPartDate;
    }
}

