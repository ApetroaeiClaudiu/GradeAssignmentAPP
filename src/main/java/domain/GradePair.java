package domain;


import java.util.Objects;

public class GradePair {
    private String IDStudent;
    private int IDHomework;

    public GradePair(String IDStudent, int IDHomework) {
        this.IDStudent = IDStudent;
        this.IDHomework = IDHomework;
    }

    public String getIDStudent() {
        return IDStudent;
    }

    public int getIDHomework() {
        return IDHomework;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradePair gradePair = (GradePair) o;
        return IDHomework == gradePair.IDHomework &&
                Objects.equals(IDStudent, gradePair.IDStudent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDStudent, IDHomework);
    }
}
