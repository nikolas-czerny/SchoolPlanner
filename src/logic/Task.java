package logic;

import java.util.ArrayList;

public class Task {
    private String name;
    private int day;
    private int month;
    private int year;
    private boolean done;
    private int priority;
    private transient Subject subject;
    private Grade grade;

    public Task(String name, int day, int month, int year, boolean done, int priority, Subject subject) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.done = done;
        this.priority = priority;
        this.subject = subject;
        this.grade = null;
    }

    public String getUIDate() {
        return this.year + "-" + this.month + "-" + this.day;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
