package utils.event;

import domain.Student;

public class StudentChangeEvent implements Event {
    private ChangeEventType type;
    private Student student, oldStudent;

    public StudentChangeEvent(ChangeEventType type, Student student) {
        this.type = type;
        this.student = student;
    }
    public StudentChangeEvent(ChangeEventType type, Student student,Student oldStudent) {
        this.type = type;
        this.student = student;
        this.oldStudent=oldStudent;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Student getStudent() {
        return student;
    }

    public Student getOldStudent() {
        return oldStudent;
    }
}
