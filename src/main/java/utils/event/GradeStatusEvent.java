package utils.event;

import domain.Grade;

public class GradeStatusEvent implements Event{
    private TaskExecutionStatusEventType type;
    private Grade grade;

    public GradeStatusEvent(TaskExecutionStatusEventType type, Grade grade) {
        this.grade = grade;
        this.type = type;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public TaskExecutionStatusEventType getType() {
        return type;
    }

    public void setType(TaskExecutionStatusEventType type) {
        this.type = type;
    }
}
