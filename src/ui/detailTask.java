package ui;

import logic.Grade;
import logic.Task;
import logic.User;

import javax.swing.*;
import java.awt.*;

public class detailTask extends Window {
    private User user;
    private Task task;

    private JLabel info;
    private JTextField gradeInput;

    private JLabel dateLabel;
    private JLabel gradeLabel;
    private JLabel doneLabel;

    JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 5));

    String infoText;

    private JPanel gradePanel;

    public detailTask(User user, Task task) {
        super("School Planner - Detail Task", user);
        this.user = user;
        this.task = task;
        buildUI();
        setVisible(true);
    }

    public void buildUI() {
        JPanel mainPanel = new JPanel(new GridLayout(6, 1));

        JPanel headerPanel = createHeaderPanel();
        JPanel descriptionPanel = createDescriptionPanel();
        createInfoPanel();
        JPanel buttonsPanel = createButtonPanel();
        JPanel InfoLabelPanel = createInfoLabelPanel();
        refreshGradePanel();

        mainPanel.add(headerPanel);
        mainPanel.add(descriptionPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(buttonsPanel);
        mainPanel.add(InfoLabelPanel);
        mainPanel.add(gradePanel);

        add(mainPanel);
    }

    public JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel(task.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    public JPanel createDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        String firstLabelText = task.getSubject().getName();
        String secondLabelText = "" + task.getPriority() + ". priority (1 - small 5 - big)";

        String finalLabelText = firstLabelText + " - " + secondLabelText;

        JLabel finalLabel = new JLabel(finalLabelText);
        finalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        descriptionPanel.add(finalLabel);

        return descriptionPanel;
    }

    public void createInfoPanel() {
        try {
            infoPanel.removeAll();

            dateLabel = new JLabel(task.getUIDate());
            dateLabel.setFont(new Font("Arial", Font.BOLD, 20));

            try {
                gradeLabel = new JLabel("" + task.getGrade().getValue());
            } catch (NullPointerException e) {
                gradeLabel = new JLabel("-");
            }
            gradeLabel.setFont(new Font("Arial", Font.BOLD, 20));

            if (task.isDone()) {
                doneLabel = new JLabel("Done");
            } else {
                doneLabel = new JLabel("Not Done");
            }
            doneLabel.setFont(new Font("Arial", Font.BOLD, 20));

            infoPanel.add(dateLabel);
            infoPanel.add(gradeLabel);
            infoPanel.add(doneLabel);

            infoPanel.revalidate();
            infoPanel.repaint();
        } catch (RuntimeException e) {
            dateLabel = new JLabel(task.getUIDate());
            dateLabel.setFont(new Font("Arial", Font.BOLD, 20));

            gradeLabel = new JLabel("" + task.getGrade().getValue());
            gradeLabel.setFont(new Font("Arial", Font.BOLD, 20));

            if (task.isDone()) {
                doneLabel = new JLabel("Done");
            } else {
                doneLabel = new JLabel("Not Done");
            }
            doneLabel.setFont(new Font("Arial", Font.BOLD, 20));

            infoPanel.add(dateLabel);
            infoPanel.add(gradeLabel);
            infoPanel.add(doneLabel);
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        JButton doneButton = new JButton("Mark as Done");
        JButton viewSubject = new JButton("View subject (" + task.getSubject().getName() + ")");
        JButton goHome = new JButton("Go to review");

        buttonPanel.add(doneButton);
        buttonPanel.add(viewSubject);
        buttonPanel.add(goHome);

        doneButton.addActionListener(e -> {
            try {
                task.setDone(true);
                refreshGradePanel();
                createInfoPanel();
                infoText = "Task done!";
                refreshInfoLabel(true);
                return;
            } catch (Exception ex) {
                infoText = "something went wrong";
                refreshInfoLabel(false);
                return;
            }
        });

        viewSubject.addActionListener(e -> {
            this.setVisible(false);
            detailSubject deSu = new detailSubject(user, task.getSubject());
        });

        goHome.addActionListener(e -> {
            this.setVisible(false);
            Review review = new Review(user);
        });

        return buttonPanel;
    }

    public void refreshGradePanel() {
        if (task.isDone()) {
            try {
                gradePanel.removeAll();
                gradeInput = new JTextField(1);
                JButton addGradeButton = new JButton("Add Grade");
                addGradeButton.addActionListener(e -> {
                    try {
                        String grade = gradeInput.getText();
                        int gradeInt = Integer.parseInt(grade);
                        if (gradeInt < 1 || gradeInt > 5) {
                            infoText = "Grade must be 1-5";
                            refreshInfoLabel(false);
                        } else {
                            infoText = "You set" + grade;
                            refreshInfoLabel(true);

                            if (task.getGrade() == null) {
                                Grade g = new Grade(gradeInt, task.getSubject(), task);

                                task.setGrade(g);
                                task.getSubject().addGrade(g);
                                createInfoPanel();
                            } else {
                                task.getGrade().setValue(gradeInt);
                                createInfoPanel();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        infoText = "Enter number";
                        refreshInfoLabel(false);
                        throw new RuntimeException(ex);
                    }
                });

                gradePanel.add(gradeInput);
                gradePanel.add(addGradeButton);

                gradePanel.revalidate();
                gradePanel.repaint();
            } catch (RuntimeException a) {
                gradePanel = new JPanel(new GridLayout(1, 1));
                gradeInput = new JTextField(1);
                JButton addGradeButton = new JButton("Add Grade");
                addGradeButton.addActionListener(e -> {
                    try {
                        String grade = gradeInput.getText();
                        int gradeInt = Integer.parseInt(grade);
                        if (gradeInt < 1 || gradeInt > 5) {
                            infoText = "Grade must be 1-5";
                            refreshInfoLabel(false);
                        } else {
                            infoText = "You set" + grade;
                            refreshInfoLabel(true);

                            if (task.getGrade() == null) {
                                Grade g = new Grade(gradeInt, task.getSubject(), task);

                                task.setGrade(g);
                                task.getSubject().addGrade(g);
                                createInfoPanel();
                            } else {
                                task.getGrade().setValue(gradeInt);
                                createInfoPanel();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        infoText = "Enter number";
                        refreshInfoLabel(false);
                        throw new RuntimeException(ex);
                    }
                });

                gradePanel.add(gradeInput);
                gradePanel.add(addGradeButton);
            }
        } else {
            try {
                gradePanel.removeAll();

                JLabel label = new JLabel("Mark as 'done'");
                label.setFont(new Font("Arial", Font.BOLD, 16));
                gradePanel.add(label);

                gradePanel.revalidate();
                gradePanel.repaint();
            } catch (RuntimeException e) {
                gradePanel = new JPanel(new GridLayout(1, 1));
                JLabel label = new JLabel("Mark as 'done'");
                label.setFont(new Font("Arial", Font.BOLD, 16));
                gradePanel.add(label);
            }
        }
    }

    public JPanel createInfoLabelPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        info = new JLabel(infoText);

        infoPanel.add(info);
        return infoPanel;
    }

    public void refreshInfoLabel(boolean positive) {
        info.setText(infoText);
        if (positive) {
            info.setForeground(Color.GREEN);
        } else {
            info.setForeground(Color.RED);
        }
    }
}
