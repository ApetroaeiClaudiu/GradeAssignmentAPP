package utils.event;

import domain.Homework;

public class HomeworkChangeEvent implements Event {
    private ChangeEventType type;
    private Homework homework,oldHomework;

    public HomeworkChangeEvent(ChangeEventType type, Homework homework) {
        this.type = type;
        this.homework = homework;
    }

    public HomeworkChangeEvent(ChangeEventType type,Homework homework,Homework oldHomework) {
        this.type = type;
        this.homework = homework;
        this.oldHomework = oldHomework;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Homework getHomework() {
        return homework;
    }

    public Homework getOldHomework() {
        return oldHomework;
    }
}
