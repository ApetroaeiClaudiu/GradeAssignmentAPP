package domain;


public class Homework extends Entity<Integer> {
    private int startWeek, deadlineWeek;
    private String description;

    /**
     *
     * @param startWeek - the homework's startWeek, it has values between 1 and 14 and it cannot be higher that the deadlineWeek
     * @param deadlineWeek - the homework's deadlineWeek, it has values between 1 and 14 and it annot be lower that the startWeek
     * @param description - the homework's description , it must not be null or empty
     */

    public Homework(int startWeek, int deadlineWeek, String description) {
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
        this.description = description;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "Homework{" +
                "ID=" + getId() +
                ", startWeek=" + startWeek +
                ", deadlineWeek=" + deadlineWeek +
                ", description='" + description + '\'' +
                '}';
    }
}
