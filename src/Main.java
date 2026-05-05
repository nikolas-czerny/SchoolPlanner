import logic.Task;
import logic.User;
import ui.Window;
import ui.Review;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        User user = new User();

        ArrayList<Task> testdata = TestData.CreateFakeTasks();

        user.setTasks(testdata);

        Window test = new Review(user);

        test.setVisible(true);
    }
}