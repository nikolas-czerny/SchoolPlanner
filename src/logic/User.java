package logic;

import java.util.ArrayList;

public class User {
    private ArrayList<Task> tasks;
    private ArrayList<Subject> subjects;

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void makeTask(String name, int day, int month, int year, boolean done, int priority, Subject subject) {
        Task newTask = new Task(name, day, month, year, done, priority, subject);
        tasks.add(newTask);
    }

    public ArrayList<Task> getTasksForSubject(Subject subject) {
        ArrayList<Task> tasksForSubject = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getSubject().equals(subject)) {
                tasksForSubject.add(task);
            }
        }

        return tasksForSubject;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
