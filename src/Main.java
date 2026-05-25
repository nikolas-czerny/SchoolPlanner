import logic.Task;
import logic.User;
import ui.Window;
import ui.Review;

import java.time.LocalDate;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        LocalDate myObj = LocalDate.now(); // Create a date object
        System.out.println(myObj); // Display the current date

        User user = new User();

        TestData.CreateFakeTasks();

        user.setTasks(TestData.tasks);
        user.setSubjects(TestData.subjects);

        Window test = new Review(user);

        test.setVisible(true);
    }
}