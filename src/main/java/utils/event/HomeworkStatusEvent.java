package utils.event;

import domain.Homework;

public class HomeworkStatusEvent implements Event{
    private TaskExecutionStatusEventType type;
    private Homework homework;

    public HomeworkStatusEvent(TaskExecutionStatusEventType type, Homework homework) {
        this.homework = homework;
        this.type = type;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Homework getHomework() {
        return homework;
    }

    public TaskExecutionStatusEventType getType() {
        return type;
    }

    public void setType(TaskExecutionStatusEventType type) {
        this.type = type;
    }
}
