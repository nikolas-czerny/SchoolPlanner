package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class User {
    private ArrayList<Subject> subjects;

    public User() {
        this.loadSubjects();
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Subject s : subjects) {
            for (Task task : s.getTasks()) {
                if (!task.isDone()) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void makeTask(String name, int day, int month, int year, boolean done, int priority, Subject subject) {
        subject.addTask(new Task(name, day, month, year, done, priority, subject));
    }

    public ArrayList<Task> getTasksForSubject(Subject subject) {
        return subject.getTasks();
    }

    public Subject getSubjectByName(String name) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(name)) {
                return subject;
            }
        }
        return null;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void loadSubjects() {
        String filename = "school_planner_data.json";

        Gson gson = new Gson();

        try{
            FileReader reader = new FileReader(filename);

            Type subjectListType = new TypeToken<ArrayList<Subject>>() {}.getType();

            ArrayList<Subject> loadedSubjects = gson.fromJson(reader, subjectListType);

            if (loadedSubjects == null) {
                this.subjects = new ArrayList<>();
            } else {
                this.subjects = loadedSubjects;
            }

            for (Subject subject : subjects) {
                subject.reconnectLinks();
            }

            System.out.println("Data loaded");
        } catch (IOException e) {
            System.out.println("Error loading data");
            this.subjects = new ArrayList<>();
        }
    }

    public void saveSubjects() {
        String filename = "school_planner_data.json";

        System.out.println("printing data");
        for (Subject s : subjects) {
            System.out.println("Saving subject " + s.getName());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter fw = new FileWriter(filename);
            gson.toJson(subjects, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
