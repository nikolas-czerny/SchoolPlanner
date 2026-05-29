package ui;

import logic.Task;
import logic.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

public class Review extends Window {
    private User user;
    private JTable table;
    private DefaultTableModel model;

    public Review(User user) {
        super("School Planner - Review", user);
        this.user = user;
        buildUI(user);
        setVisible(true);
    }

    private void buildUI(User user) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel headerPanel = createHeaderPanel();
        JPanel filterPanel = createFilterPanel();
        JPanel contentPanel = createContentPanel(user.getTasks(), 2);
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(filterPanel, BorderLayout.NORTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("My Tasks");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        header.add(titleLabel);
        return header;
    }

    private JPanel createFilterPanel() {
        JPanel filters = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton buttonSortBySubject = new JButton("By Subject");
        JButton buttonSortByPriority = new JButton("By Priority");
        JButton buttonSortByDate = new JButton("By Date");

        filters.add(buttonSortBySubject);
        filters.add(buttonSortByPriority);
        filters.add(buttonSortByDate);

        buttonSortBySubject.addActionListener(e -> {

            System.out.println("Sorting by subject");
            refreshTable(user.getTasks(), 1);
        });

        buttonSortByPriority.addActionListener(e -> {
            System.out.println("Sorting by priority");
            refreshTable(user.getTasks(), 2);
        });

        buttonSortByDate.addActionListener(e -> {
            System.out.println("Sorting by date");
            refreshTable(user.getTasks(), 3);
        });

        return filters;
    }

//    private JPanel createContentPanel(ArrayList<Task> tasks) {
//        JPanel content = new JPanel(new BorderLayout(10, 10));
//
//        DefaultListModel<String> taskModel = new DefaultListModel<>();
//        for (Task task : tasks) {
//            String taskName = task.getName();
//            String taskSubject = task.getSubject().getName();
//            String taskDate = task.getUIDate();
//            taskModel.addElement(taskName + " " + taskSubject + " " + taskDate + task.getPriority());
//        }
//
//        JList<String> taskList = new JList<>(taskModel);
//        JScrollPane scrollPane = new JScrollPane(taskList);
//
//        content.add(scrollPane, BorderLayout.CENTER);
//        return content;
//    }

    private JPanel createContentPanel(ArrayList<Task> tasks, int byWhat) {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));

        String[] header = {"Name", "Subject", "Priority", "Date", "Done"};

        Object[][] data = this.getTasks(byWhat, tasks);

        this.model = new DefaultTableModel(data, header) {
            @Override // it is here to set isCellEditAble to false
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);


        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    int selectedRow = table.getSelectedRow();
                    Task task = tasks.get(selectedRow);

                    if (selectedRow != -1) {
                        CloseThisWindow();
                        detailTask detailTask = new detailTask(user, task);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        return contentPanel;
    }

    private void refreshTable(ArrayList<Task> tasks, int byWhat) {
        model.setRowCount(0);

        Object[][] data = this.getTasks(byWhat, tasks);

        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    private void CloseThisWindow() {
        setVisible(false);
    }

    private JPanel createButtonPanel() {
        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton addButton = new JButton("Add task");
        JButton allSubjects = new JButton("List subjects");
        JButton subjectButton = new JButton("Detail subject");

        addButton.addActionListener(e -> {
            addTask addTask = new addTask(user);
            this.setVisible(false);
            addTask.setVisible(true);
        });

        allSubjects.addActionListener(e -> {
            subjectAll subjectAll = new subjectAll(user);
            this.setVisible(false);
            subjectAll.setVisible(true);
        });

        subjectButton.addActionListener(e -> {
            int selected_row = table.getSelectedRow();

            if (selected_row == -1) {
                JOptionPane.showMessageDialog(this, "You must select something to continue");
                return;
            }

            int model_row = table.convertRowIndexToModel(selected_row);

            this.setVisible(false);
            detailSubject detailSubject = new detailSubject(user, user.getSubjects().get(model_row));
            detailSubject.setVisible(true);
        });



        buttons.add(addButton);
        buttons.add(allSubjects);
        buttons.add(subjectButton);

        return buttons;
    }

    private Object[][] getTasks(int byWhat, ArrayList<Task> tasks) {
        if (byWhat == 1) {
            ArrayList<Task> helper = new ArrayList<>();

            for (Task value : tasks) {
                if (!value.isDone()) {
                    helper.add(value);
                }
            }
            helper.sort(Comparator.comparing(e -> e.getSubject().getName()));

            Object[][] data = new Object[helper.size()][5];
            int i = 0;

            for (Task task : helper) {
                if (!task.isDone()) {
                    String taskName = task.getName();
                    String taskSubject = task.getSubject().getName();
                    int taskPriority = task.getPriority();
                    String taskDate = task.getUIDate();

                    Object[] row = {taskName, taskSubject, taskPriority, taskDate, "Not done"};
                    data[i] = row;
                    i++;
                } else {
                    i++;
                }
            }

            return data;
        } else if (byWhat == 2) {
            ArrayList<Task> helper = new ArrayList<>();
            for (Task value : tasks) {
                if (!value.isDone()) {
                    helper.add(value);
                }
            }

            helper.sort(Comparator.comparing(e -> e.getPriority()));

            Object[][] data = new Object[helper.size()][5];
            int i = 0;

            for (Task task : helper) {
                if (!task.isDone()) {
                    String taskName = task.getName();
                    String taskSubject = task.getSubject().getName();
                    int taskPriority = task.getPriority();
                    String taskDate = task.getUIDate();

                    Object[] row = {taskName, taskSubject, taskPriority, taskDate, "Not done"};
                    data[i] = row;
                    i++;
                } else {
                    i++;
                }
            }

            return data;
        } else if (byWhat == 3) {
            ArrayList<Task> sortedTasks = new ArrayList<>(tasks);

            sortedTasks.sort(
                    Comparator.comparingInt(Task::getYear)
                            .thenComparingInt(Task::getMonth)
                            .thenComparingInt(Task::getDay)
            );

            Object[][] data = new Object[sortedTasks.size()][5];
            int i = 0;

            for (Task task : sortedTasks) {
                if (!task.isDone()) {
                    String taskName = task.getName();
                    String taskSubject = task.getSubject().getName();
                    int taskPriority = task.getPriority();
                    String taskDate = task.getUIDate();

                    Object[] row = {taskName, taskSubject, taskPriority, taskDate, "Not done"};
                    data[i] = row;
                    i++;
                } else {
                    i++;
                }
            }

            return data;
        }
        return null;
    }
}