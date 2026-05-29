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
//            row[1] = getAverageGrade(user.getSubjects().get(i));
            float g = getAverageGrade(user.getSubjects().get(i));

            if (g <= 0) {
                row[1] = "-";
            } else {
                row[1] = g;
            }
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

    public void refreshTable() {
        model.setRowCount(0);
        for (int i = 0; i < user.getSubjects().size(); i++) {
            Object[] row = new Object[2];
            row[0] = user.getSubjects().get(i).getName();
//            row[1] = getAverageGrade(user.getSubjects().get(i));
            float g = getAverageGrade(user.getSubjects().get(i));

            if (g <= 0) {
                row[1] = "-";
            } else {
                row[1] = g;
            }
            model.addRow(row);
        }
    }

    public JPanel buttonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3));

        JButton goToReview = new JButton("Go to review");
        JButton removeButton = new JButton("Remove");
        JButton detailSubject = new JButton("Detail");

        goToReview.addActionListener(e-> {
            this.setVisible(false);
            Review review = new Review(user);
            review.setVisible(true);
        });

        removeButton.addActionListener(e-> {
            int selected_row = table.getSelectedRow();

            if (selected_row == -1) {
                JOptionPane.showMessageDialog(this, "Choose subject in table.");
                return;
            }

            int modelRow = table.convertRowIndexToModel(selected_row);

            user.getSubjects().remove(modelRow);
            this.refreshTable();
        });

        detailSubject.addActionListener(e-> {
            int selected_row = table.getSelectedRow();

            if (selected_row == -1) {
                JOptionPane.showMessageDialog(this, "Choose subject in table.");
                return;
            }

            int modelRow = table.convertRowIndexToModel(selected_row);

            this.setVisible(false);
            ui.detailSubject detailSubject1 = new detailSubject(user, user.getSubjects().get(modelRow));
            detailSubject1.setVisible(true);
        });

        panel.add(goToReview);
        panel.add(removeButton);
        panel.add(detailSubject);

        return panel;
    }

    public JPanel addSubject() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JTextField nameField = new JTextField(1);
        JButton addButton = new JButton("Add");

        addButton.addActionListener(e-> {
            String name = nameField.getText();

            for (Subject subject: user.getSubjects()) {
                if (subject.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Subject already exists.");
                    return;
                }
            }

            Subject subject = new Subject(name);
            user.getSubjects().add(subject);

            this.refreshTable();
        });

        panel.add(nameField);
        panel.add(addButton);

        return panel;
    }

    public float getAverageGrade(Subject subject) {
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
