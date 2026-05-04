package ui;

import logic.Grade;
import logic.Subject;
import logic.Task;
import logic.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class detailSubject extends Window {
    private User user;
    private Subject subject;
//    private JTable table;
//    private DefaultTableModel model;
    public String infoText = "";
    private JLabel info;


    public detailSubject(User user, Subject subject) {
        super("School Planner - Detail Subject", user);
        this.user = user;
        this.subject = subject;

        buildUI();
        setVisible(true);
    }

    public void buildUI(){
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        JPanel headerPanel = createHeaderPanel();
        JPanel contentPanel = buildContentLayout();
        JPanel addgradePanel = createAddgradePanel();
        JPanel infoLabelPanel = createInfoLabelPanel();

        mainPanel.add(headerPanel);
        mainPanel.add(contentPanel);
        mainPanel.add(addgradePanel);
        mainPanel.add(infoLabelPanel);

        add(mainPanel);
    }

    public JPanel buildContentLayout(){
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));

        JPanel tablePanel = createTablePanel();
        JPanel gradesPanel = createGradesPanel();

        contentPanel.add(tablePanel);
        contentPanel.add(gradesPanel);

        return contentPanel;
    }

    public JPanel createHeaderPanel(){
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel headerLabel = new JLabel(subject.getName());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        return headerPanel;
    }

//    public JScrollPane makeTable(String[] header, Object[][] data) {
//        DefaultTableModel model = new DefaultTableModel(data, header) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        JTable table = new JTable(model);
//
//        JScrollPane scrollPane = new JScrollPane(table);
//        return scrollPane;
//    }

    public JPanel createTablePanel(){
        JPanel tablePanel = new JPanel(new GridLayout(2, 1));

        JLabel tableLabel = new JLabel("Not Done Tasks");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tablePanel.add(tableLabel);

        String[] header = {"Name", "Priority", "Date"};
        ArrayList<Task> tasksArray = user.getTasksForSubject(subject);
        Object[][] tasks = new Object[tasksArray.size()][header.length];

        int i = 0;
        for (Task task : tasksArray) {
            String taskName = task.getName();
            int taskPriority = task.getPriority();
            String taskDate = task.getUIDate();

            Object[] row = {taskName, taskPriority, taskDate};
            tasks[i] = row;
            i++;
        }

        JScrollPane sc = makeTable(header, tasks);

        tablePanel.add(sc);

        return tablePanel;
    }

    public JPanel createGradesPanel(){
        JPanel gradesPanel = new JPanel(new GridLayout(2, 1));

        JLabel tableLabel = new JLabel("Grades ");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gradesPanel.add(tableLabel);

        String[] header = {"Grade", "Task", "Date"};
        Object[][] grades = new Object[subject.getGrades().size()][header.length];
        int i = 0;
        for (Grade grade : subject.getGrades()) {
            int gradeValue = grade.getValue();
            String task = grade.getTask().getName();
            String date = grade.getTask().getUIDate();
            Object[] row = {gradeValue, task, date};
            grades[i] = row;
            i++;
        }

        JScrollPane sc = makeTable(header, grades);

        gradesPanel.add(sc);

        return gradesPanel;
    }

    public JPanel createAddgradePanel(){
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel buttonsPanel = createButtonsPanel();
        bottomPanel.add(buttonsPanel);

        JPanel addgradePanel = new JPanel(new GridLayout(2, 1));

        JTextField gradeInput = new JTextField(1);
        JButton addGradeButton = new JButton("Add Grade");
        addGradeButton.addActionListener(e -> {
            try {
                String grade = gradeInput.getText();
                int gradeInt = Integer.parseInt(grade);
                if (gradeInt < 1 || gradeInt > 5) {
                    infoText = "Grade must be 1-5";
                    refreshInfoLabel(false);
                } else {
                    infoText = "You added " + grade;
                    refreshInfoLabel(true);

                    Grade g = new Grade(gradeInt, subject, null);
                    subject.addGrade(g);
                }
            } catch (NumberFormatException ex) {
                infoText = "Enter number";
                refreshInfoLabel(false);
                throw new RuntimeException(ex);
            }
        });

        addgradePanel.add(gradeInput);
        addgradePanel.add(addGradeButton);

        bottomPanel.add(addgradePanel);

        return bottomPanel;
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

    public void refreshGradeTable(JScrollPane sc) {

    }

    public JPanel createButtonsPanel(){
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));

        return buttonsPanel;
    }
}
