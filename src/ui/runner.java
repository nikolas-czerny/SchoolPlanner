package ui;

import logic.User;

public class runner {
    User user = new User();
    public runner(User user) {
        this.user = user;
    }

   public void run() {
        Window window = new Review(user);

        window.setVisible(true);
   }
}
