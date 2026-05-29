package logic;

import java.util.ArrayList;

public class Subject {
    private String name;
    private ArrayList<Grade> grades;
    private ArrayList<Task> tasks;

    public Subject(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void reconnectLinks() {
        if (grades != null) {
            for (Grade grade : grades) {
                grade.setSubject(this);
            }
        }

        if (tasks != null) {
            for (Task task : tasks) {
                task.setSubject(this);
            }
        }
    }
}
