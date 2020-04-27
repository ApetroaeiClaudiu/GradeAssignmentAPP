package domain;

import java.util.Objects;

public class Grade extends Entity<GradePair>{
    //ID = ID.Student + ID.Tema
    private float value;
    private String professor;
    private String feedback;
    private int deliveryWeek;

    /**
     *
     * @param value - the value of the given grade , it has values between 1 and 10
     * @param professor - the lab professor of the student that received the grade
     * @param deliveryWeek - the  delivery date of the homework aka the date of receiving the note
     */
    public Grade(float value, String professor, int deliveryWeek,String feedback) {
        this.value = value;
        this.professor = professor;
        this.deliveryWeek = deliveryWeek;
        this.feedback = feedback;
    }

    public String getFeedback(){
        return feedback;
    }
    public float getValue() {
        return value;
    }

    public String getProfessor() {
        return professor;
    }

    public int getDeliveryWeek() {
        return deliveryWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Float.compare(grade.value, value) == 0 &&
                deliveryWeek == grade.deliveryWeek &&
                Objects.equals(professor, grade.professor) &&
                Objects.equals(feedback, grade.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, professor, feedback, deliveryWeek);
    }

    @Override
    public String toString() {
        return "Grade{"+
                "ID= " + getId().getIDStudent() +
                " at " + getId().getIDHomework() +
                ", value=" + value +
                ", professor='" + professor + '\'' +
                ", deliveryWeek=" + deliveryWeek +
                '}';
    }
}
