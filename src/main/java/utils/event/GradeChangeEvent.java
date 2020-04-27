package utils.event;

import domain.Grade;

public class GradeChangeEvent implements Event {
    private ChangeEventType type;
    private Grade grade,oldGrade;

    public GradeChangeEvent(ChangeEventType type, Grade grade) {
        this.type = type;
        this.grade= grade;
    }
    public GradeChangeEvent(ChangeEventType type,Grade grade,Grade oldGrade) {
        this.type = type;
        this.grade = grade;
        this.oldGrade = oldGrade;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Grade getGrade() {
        return grade;
    }

    public Grade getOldGrade() {
        return oldGrade;
    }
}
