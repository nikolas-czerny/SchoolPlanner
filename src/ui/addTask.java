package ui;

import logic.Subject;
import logic.Task;
import logic.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class addTask extends Window {
    private User user;
    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;
    private JTextField priorityField;
    private JTextField typeField;
    private popUp subjectField;
    private JTextField nameField;
    JLabel errorLabel;


    String errorMessage = "";

    public addTask(User user) {
        super("School Planner - Add Task", user);
        this.user = user;
        buildUi();
    }

    public void buildUi(){
//        JPanel mainPanel = new JPanel(new GridLayout(6, 1));
//
//        JPanel headerPanel = createHeaderPanel();
//        JPanel inputPanel = createInputPanel();
//        JPanel createDatePanel = createDatePanel();
//        JPanel createMoreInfoPanel = createMoreInfoPanel();
//        JPanel buttonPanel = createButtonPanel();
//        JPanel errorMessage = createErrorMessage();
//
//        mainPanel.add(headerPanel);
//        mainPanel.add(inputPanel);
//        mainPanel.add(createDatePanel);
//        mainPanel.add(createMoreInfoPanel);
//        mainPanel.add(buttonPanel);
//        mainPanel.add(errorMessage);
//
//        add(mainPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();
        JPanel createDatePanel = createDatePanel();
        JPanel createMoreInfoPanel = createMoreInfoPanel();
        JPanel inputMain = new JPanel(new GridLayout(3, 1));
        inputMain.add(inputPanel);
        inputMain.add(createDatePanel);
        inputMain.add(createMoreInfoPanel);

        mainPanel.add(inputMain, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        add(mainPanel);
    }

    public JPanel createHeaderPanel(){
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Add Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(titleLabel);

        return header;
    }

    public JPanel createInputPanel(){
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel titleLabel = new JLabel("Name");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        inputPanel.add(titleLabel);

        nameField = new JTextField();
        inputPanel.add(nameField);

        return inputPanel;
    }

    public JPanel createDatePanel(){
        JPanel dates = new JPanel(new GridLayout(2, 4, 10, 10));

        JLabel dayLabel = new JLabel("Day");
        JLabel monthLabel = new JLabel("Month");
        JLabel yearLabel = new JLabel("Year");

        dayField = new JTextField();
        monthField = new JTextField();
        yearField = new JTextField();

        dates.add(dayLabel);
        dates.add(monthLabel);
        dates.add(yearLabel);

        dates.add(dayField);
        dates.add(monthField);
        dates.add(yearField);

        return dates;
    }

    public JPanel createMoreInfoPanel(){
        JPanel moreInfo = new JPanel(new GridLayout(2, 3, 5, 5));

        JLabel priority = new JLabel("Priority (1 small - 5 big)");
        priority.setFont(new Font("Arial", Font.BOLD, 15));
        moreInfo.add(priority);
        JLabel type = new JLabel("Type (homework, exam etc.)");
        priority.setFont(new Font("Arial", Font.BOLD, 15));
        moreInfo.add(type);
        JLabel subject = new JLabel("Subject");
        priority.setFont(new Font("Arial", Font.BOLD, 15));
        moreInfo.add(subject);

        priorityField = new JTextField();
        typeField = new JTextField();
        subjectField = new popUp();

        ArrayList<String> subs = new ArrayList<>();
        for (Subject sub: user.getSubjects()) {
            subs.add(sub.getName());
        }
        JToggleButton toolBar = subjectField.run(subs);

        moreInfo.add(priorityField);
        moreInfo.add(typeField);
        moreInfo.add(toolBar);

        return moreInfo;
    }

    public void updateErrorMessage(){
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel createButtonPanel(){
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton addTaskButton = new JButton("Add Task");
        JButton goToSummary = new JButton("Cancel");

        addTaskButton.addActionListener(e -> {
            String name = nameField.getText();
            String day = dayField.getText();
            String month = monthField.getText();
            String year = yearField.getText();
            String priority = priorityField.getText();
            String type = typeField.getText();
            String subject = subjectField.getValue();

            if (name.length() < 5) {
                System.out.println("Name is too short");
                errorMessage = "Name is too short";
                updateErrorMessage();
                return;
            }

            if (subject == null) {
                errorMessage = "Choose a subject";
                updateErrorMessage();
                return;
            }

            if (type.length() < 3) {
                System.out.println("Subject is too short");
                errorMessage = "Subject is too short";
                updateErrorMessage();
                return;
            }

            try {
                int dayInt = Integer.parseInt(day);
                int monthInt = Integer.parseInt(month);
                int yearInt = Integer.parseInt(year);
                int priorityInt = Integer.parseInt(priority);

                if (dayInt < 1 || dayInt > 31) {
                    errorMessage = "Enter day between 1 - 31";
                    updateErrorMessage();
                    return;
                }

                if (monthInt < 1 || monthInt > 12) {
                    errorMessage = "Enter month between 1 - 12";
                    updateErrorMessage();
                    return;
                }

                if (yearInt < 2025 || yearInt > 2028) {
                    errorMessage = "Enter year between 2026 - 2027";
                    updateErrorMessage();
                    return;
                }

                if (priorityInt < 1 || priorityInt > 5) {
                    errorMessage = "Enter priority between 1 - 5";
                    updateErrorMessage();
                    return;
                }

                user.makeTask(name, dayInt, monthInt, yearInt, false, priorityInt, user.getSubjectByName(subject));

                this.setVisible(false);
                Review review = new Review(user);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid numbers");
                errorMessage = "Please enter a valid numbers";
                updateErrorMessage();
                return;
            }
        });

        System.out.println(errorMessage);

        goToSummary.addActionListener(e-> {
            this.setVisible(false);
            Review review = new Review(user);
        });

        buttons.add(addTaskButton);
        buttons.add(goToSummary);

        return buttons;
    }


}
