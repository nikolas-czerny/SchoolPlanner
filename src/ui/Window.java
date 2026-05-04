package ui;

import logic.User;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(String title, User user) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}