package utils.event;

import domain.Student;

public class StudentStatusEvent implements Event {
    private TaskExecutionStatusEventType type;
    private Student student;

    public StudentStatusEvent(TaskExecutionStatusEventType type, Student student) {
        this.student = student;
        this.type = type;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TaskExecutionStatusEventType getType() {
        return type;
    }

    public void setType(TaskExecutionStatusEventType type) {
        this.type = type;
    }
}

