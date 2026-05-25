package ui;

import logic.Subject;
import logic.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class subjectAll extends Window {
    private User user;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    public subjectAll(User user) {
        super("Subjects", user);
        this.user = user;
        buildUi();
    }

    public void buildUi() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        JPanel header = headerPanel();
        JPanel table = tablePanel();
        JPanel buttons = buttonsPanel();
        JPanel addSubjects = addSubject();

        panel.add(header);
        panel.add(table);
        panel.add(addSubjects);
        panel.add(buttons);

        add(panel);
    }

    public JPanel headerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.add(new JLabel("Subjects"));
        return panel;
    }

    public JPanel tablePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        String[] header = {"Name", "Grade"};
        Object[][] data = new Object[user.getSubjects().size()][2];

        for (int i = 0; i < user.getSubjects().size(); i++) {
            Object[] row = new Object[2];
            row[0] = user.getSubjects().get(i).getName();
            row[1] = getAverageGrade(user.getSubjects().get(i));
            data[i] = row;
        }

        this.model = new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        this.table = new JTable(model);

        this.scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }

    public JPanel buttonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3));

        JButton goToReview = new JButton("Go to review");
        JButton removeButton = new JButton("Remove");
        JButton detailSubject = new JButton("Detail");

        panel.add(goToReview);
        panel.add(removeButton);
        panel.add(detailSubject);

        return panel;
    }

    public JPanel addSubject() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JTextField nameField = new JTextField(1);
        JButton addButton = new JButton("Add");

        panel.add(nameField);
        panel.add(addButton);

        return panel;
    }

    public double getAverageGrade(Subject subject) {
        if (subject.getGrades() == null) {
            return 0;
        }
        int x = 0;
        for (int i = 0; i < subject.getGrades().size(); i++) {
            x += subject.getGrades().get(i).getValue();
        }
        try{
            return x / subject.getGrades().size();
        } catch (ArithmeticException e) {
            return 0;
        }
    }
}
