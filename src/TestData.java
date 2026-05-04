import logic.Grade;
import logic.Subject;
import logic.Task;

import java.util.ArrayList;

public class TestData {
    public static ArrayList<Task> CreateFakeTasks(){
        Subject math = new Subject("Math");
        Subject english = new Subject("English");
        Subject physics = new Subject("Physics");
        Task task1 = new Task("Ukol1", 21, 5, 2026, false, 3, math);
        Task task2 = new Task("test1", 10, 6, 2026, false, 1, physics);
        Task task3 = new Task("test1", 5, 7, 2026, false, 2, english);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        return tasks;
    }
}
