package logic;

import java.time.LocalDate;

public class Grade {
    private String name;
    private int value;
    private Subject subject;
    private Task task;
    private int day;
    private int month;
    private int year;

    public Grade(int value, Subject subject, Task task, int day, int month, int year) {
        this.value = value;
        this.subject = subject;
        this.task = task;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Grade(int value, Subject subject, Task task){
        this.value = value;
        this.subject = subject;
        this.task = task;
        this.setCurrentDate();
    }

    public Grade(int value, Subject subject, Task task, String name){
        this.value = value;
        this.subject = subject;
        this.task = task;
        this.name = name;
        this.setCurrentDate();
    }

    public void setCurrentDate(){
        LocalDate current_date = LocalDate.now(); // Create a date object
        this.day = current_date.getDayOfMonth();
        this.month = current_date.getMonthValue();
        this.year = current_date.getYear();
    }

    public String get_formated_date(){
        return this.year + "-" + this.month + "-" + this.day;
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
