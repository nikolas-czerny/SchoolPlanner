package ui;

import logic.User;

public class runner {
    private User user;
    public runner() {
        this.user = new User();
    }

   public void run() {
        Window window = new Review(user);

        window.setVisible(true);
   }
}
