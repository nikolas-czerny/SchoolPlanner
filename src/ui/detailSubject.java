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
    private DefaultTableModel gradesTable;
    private DefaultTableModel tasksTable;

    private JTable grade_table;
    private Object[][] grades;

    private JScrollPane scrollOne;
    private JScrollPane scrollTwo;

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

        refreshTasksTable();

        tablePanel.add(scrollOne);

        return tablePanel;
    }

    public JPanel createGradesPanel(){
        JPanel gradesPanel = new JPanel(new GridLayout(2, 1));

        JLabel tableLabel = new JLabel("Grades ");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gradesPanel.add(tableLabel);

        refreshGradesTable();

        gradesPanel.add(scrollTwo);

        return gradesPanel;
    }

    public JPanel createAddgradePanel(){
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel buttonsPanel = createButtonsPanel();
        bottomPanel.add(buttonsPanel);

        JPanel addgradePanel = new JPanel(new GridLayout(1, 2));
        JPanel add_grade_inputs = new JPanel(new GridLayout(2, 1));

        JTextField gradeInput = new JTextField(1);
        JTextField descriptionInput = new JTextField(1);
        JButton addGradeButton = new JButton("Add Grade");
        addGradeButton.addActionListener(e -> {
            try {
                String grade = gradeInput.getText();
                String description = descriptionInput.getText();
                int gradeInt = Integer.parseInt(grade);
                if (gradeInt < 1 || gradeInt > 5) {
                    infoText = "Grade must be 1-5";
                    refreshInfoLabel(false);
                } else {
                    infoText = "You added " + grade;
                    refreshInfoLabel(true);

                    Grade g = new Grade(gradeInt, subject, null, description);
                    subject.addGrade(g);

                    refreshGradesTable();
                }
            } catch (NumberFormatException ex) {
                infoText = "Enter number";
                refreshInfoLabel(false);
                throw new RuntimeException(ex);
            }
        });

        add_grade_inputs.add(gradeInput);
        add_grade_inputs.add(descriptionInput);

        addgradePanel.add(add_grade_inputs);
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

    public Object[][] GetDataGrade() {
        Object[][] grades = new Object[subject.getGrades().size()][GetHeaderGrade().length];
        int i = 0;
        for (Grade grade : subject.getGrades()) {
            int gradeValue = grade.getValue();
            String task = "";
            String date = grade.get_formated_date();

            if (grade.getTask() == null) {
                task = grade.getName();
            } else {
                task = grade.getTask().getName();
            }
            Object[] row = {gradeValue, task, date};
            grades[i] = row;
            i++;
        }
        return grades;
    }

    public String[] GetHeaderGrade() {
        String[] header = {"Grade", "Task/Name", "Date"};
        return header;
    }

    public Object[][] GetDataTask(){
        ArrayList<Task> tasksArray = user.getTasksForSubject(subject);
        Object[][] tasks = new Object[tasksArray.size()][GetHeaderTask().length];

        int i = 0;
        for (Task task : tasksArray) {
            String taskName = task.getName();
            int taskPriority = task.getPriority();
            String taskDate = task.getUIDate();

            Object[] row = {taskName, taskPriority, taskDate};
            tasks[i] = row;
            i++;
        }

        return tasks;
    }

    public String[] GetHeaderTask(){
        String[] header = {"Name", "Priority", "Date"};
        return header;
    }

    public void refreshTasksTable(){
        if (scrollOne != null) {
            tasksTable.setRowCount(0);

            for (Object[] row: GetDataTask()) {
                tasksTable.addRow(row);
            }
        } else {
            tasksTable = new DefaultTableModel(GetDataTask(), GetHeaderTask()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JTable table = new JTable(tasksTable);

            scrollOne = new JScrollPane(table);
        }
    }

    public void refreshGradesTable(){
        if (scrollTwo != null) {
            gradesTable.setRowCount(0);

            grades = GetDataGrade();

            for (Object[] row : grades) {
                gradesTable.addRow(row);
            }
        } else {
            gradesTable = new DefaultTableModel(GetDataGrade(), GetHeaderGrade()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            grade_table = new JTable(gradesTable);

            scrollTwo = new JScrollPane(grade_table);
        }
    }

    public JPanel createButtonsPanel(){
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));

        JButton button1 = new JButton("Review");
        JButton button2 = new JButton("Remove grade");
        JButton button3 = new JButton("Set as done");

        button1.addActionListener(e -> {
            this.setVisible(false);
            Review review = new Review(user);
        });

        button2.addActionListener(e -> {
            int selectedRow = grade_table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Nejdřív vyber známku v tabulce.");
                return;
            }

            // Pokud máš tabulku se sortováním/filtrováním, musíš převést řádek:
            int modelRow = grade_table.convertRowIndexToModel(selectedRow);

            subject.getGrades().remove(modelRow);
            refreshGradesTable();
        });

        button3.addActionListener(e->{
            subjectAll window = new subjectAll(user);
            this.setVisible(false);
            window.setVisible(true);
        });

        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        buttonsPanel.add(button3);

        return buttonsPanel;
    }
}
