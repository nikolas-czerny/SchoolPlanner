package logic;

public class Grade {
    private int value;
    private Subject subject;
    private Task task;

    public Grade(int value, Subject subject, Task task) {
        this.value = value;
        this.subject = subject;
        this.task = task;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
