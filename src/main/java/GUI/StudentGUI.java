package GUI;

import Utilities.DataHelper;
import Utilities.GuiHelper;
import Utilities.Printer;
import models.UniLesson;
import users.Student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * users.Student GUI is a static class that renders the student viewpoint.
 * Object handoffs:
 * - Create users.Student object
 * - users.Student object -> GUI.StudentGUI methods
 * - Within GUI.StudentGUI Methods, create StudentViewPoint(users.Student) Object
 * - Generate table and event listeners using StudentViewPoint object
 */

public class StudentGUI extends JPanel {
    static JFrame frame;
    // Set up table
    String[] columnNames = {"Class", "Department", "Lecture Days", "Hours",
            "Lecture Room", "Professor", "Class Size", "Seats Remaining"};
    Object[][] data;
    JTable uniLessonsTable;

    Object[][] currentUniLessons;
    DefaultTableModel uniLessonDm;
    DefaultTableModel timeTableDm;

    String[] filterOptions = DataHelper.getListOfDepartments();

    public StudentGUI(StudentViewpoint studentViewpoint) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Panel border
        GuiHelper.addBordersToPanel(this, 10);

        // Show sign in entity
        JLabel welcome = new JLabel("Logged in as: " + studentViewpoint.student.getName());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(welcome, gbc);

        // Log out button
        JButton logout = new JButton("Log Out");
        gbc.gridx = 9;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(logout, gbc);


        // Selectoin label
        JLabel selectionLabel = new JLabel("Department:");
        gbc.gridx = 10;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(8, 10, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(selectionLabel, gbc);

        // Set up selection filter
        JComboBox<String> jComboBox = new JComboBox<>(filterOptions);
        gbc.gridx = 10;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(30, 5, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(jComboBox, gbc);

        // Set up uniLessonTable in content pane for the list of available lessons
        data = studentViewpoint.showLessonList();
        uniLessonDm = new DefaultTableModel(data, columnNames);
        uniLessonsTable = new JTable(uniLessonDm);
//        uniLessonsTable.setFillsViewportHeight(true);

        // Auto adjust table cell width
        GuiHelper.tableAutoAdjustWidth(uniLessonsTable);

        // Create panel with box layout to hold label, header and table
        JPanel uniLessonsPanel = new JPanel();
        uniLessonsPanel.setLayout(new BoxLayout(uniLessonsPanel, BoxLayout.Y_AXIS));
        JLabel uniLessonsLabel = new JLabel("Available Lessons", JLabel.CENTER);
        uniLessonsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniLessonsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        uniLessonsPanel.add(uniLessonsLabel);
        uniLessonsPanel.add(uniLessonsTable.getTableHeader());
        uniLessonsPanel.add(uniLessonsTable);
        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(uniLessonsPanel);

        // Add table label

        // Add scroll pane to panel.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 8;
        gbc.ipady = 200;
        gbc.weightx = 0;
        gbc.weighty = 0.7;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(scrollPane, gbc);

        // Define new button to add unilesson
        JButton submitButton = new JButton(("Enroll"));
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(submitButton, gbc);

        // Show selected course info
        JLabel lessonTitleLabel = new JLabel("Title:", SwingConstants.RIGHT);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 10);
        add(lessonTitleLabel, gbc);

        JLabel lessonTitleName = new JLabel("<html><i>select a class</i></html>");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(lessonTitleName, gbc);

        JLabel lessonDescriptionLabel = new JLabel("Description:", SwingConstants.RIGHT);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(0, 0, 0, 10);
        add(lessonDescriptionLabel, gbc);

        JLabel lessonDescriptionName = new JLabel("");
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(lessonDescriptionName, gbc);

        // Set up student timetable
        currentUniLessons = studentViewpoint.displayTimetable();
        timeTableDm = new DefaultTableModel(currentUniLessons, columnNames);
        JTable timeTable = new JTable(timeTableDm);
//        timeTable.setFillsViewportHeight(true);

        // Auto adjust table cell width
        GuiHelper.tableAutoAdjustWidth(timeTable);

        // Create panel with box layout to hold label, header and table
        JPanel timeTablePanel = new JPanel();
        timeTablePanel.setLayout(new BoxLayout(timeTablePanel, BoxLayout.Y_AXIS));
        JLabel timeTableLabel = new JLabel("Your Timetable", JLabel.CENTER);
        timeTableLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        timeTableLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        timeTablePanel.add(timeTableLabel);
        timeTablePanel.add(timeTable.getTableHeader());
        timeTablePanel.add(timeTable);
        // Create the scroll pane and add the table to it.
        JScrollPane timeTableScrollPane = new JScrollPane(timeTablePanel);

        int timeTableVisibleRows = timeTable.getRowCount();
        timeTable.setPreferredScrollableViewportSize(
                new Dimension(
                        timeTable.getPreferredSize().width,
//                        timeTable.getRowHeight() * timeTableVisibleRows
                        200)
        );

        // Add scroll pane to panel.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridwidth = 8;
        gbc.gridy = 6;
        gbc.weighty = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipady = 200;

        add(timeTableScrollPane, gbc);

        // Button to drop course
        JButton dropButton = new JButton(("Drop Class"));
        gbc.gridx = 5;
        gbc.gridwidth = 2;
        gbc.gridy = 7;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(10, 0, 20, 0);
        add(dropButton, gbc);

        // Button to see transcript
        JButton transcript = new JButton("View Transcript");
        gbc.gridx = 8;
        gbc.gridwidth = 2;
        gbc.gridy = 8;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        add(transcript, gbc);

        // Action listeners:
        // Action listener for unilessonTable rows to be selectable
        timeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] dropSelection = {null};
        ListSelectionModel rowSM2 = timeTable.getSelectionModel();
        rowSM2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("StudentGUI drop unilesson List selection event called");
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                    if (e.getValueIsAdjusting()) {
                        System.out.println("************* Start of drop unilesson ListSelectionEvent if statement **********");
                        System.out.println(timeTable.getSelectedRow());
                        dropSelection[0] = (String) timeTable.getValueAt(timeTable.getSelectedRow(), 0);
                        System.out.println("In if statement for adjusting cell");
                        System.out.println(dropSelection[0]);
                        studentViewpoint.unilessonToDrop = dropSelection[0];
                        System.out.println(studentViewpoint.unilessonToDrop);
                        System.out.println("************* End of drop unilesson ListSelectionEvent if statement **********");
                    }
                }
            }
        });

        // Action listener to filter uniLessonTable
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    System.out.println("Selected " + jComboBox.getSelectedItem());
                    Object[][] currentUnilessons = studentViewpoint.showLessonsFiltered(jComboBox.getSelectedItem().toString());
                    System.out.println("Updated table contents: ");
                    System.out.println(Arrays.deepToString(currentUnilessons));
                    uniLessonDm.setDataVector(currentUnilessons, columnNames);
                    GuiHelper.tableAutoAdjustWidth(uniLessonsTable);
                    timeTable.clearSelection();
                    Printer.printMinus();
                }
            }
        });

        // Action Listener to configure unilessonTable rows to be selectable
        uniLessonsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] addSelection = {null};
        ListSelectionModel rowSM = uniLessonsTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("StudentGUI add unilesson List selection event called");
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                    if (e.getValueIsAdjusting()) {
                        Printer.printStars();
                        System.out.println("Start of add unilesson ListSelectionEvent if statement");
                        addSelection[0] = (String) uniLessonsTable.getValueAt(uniLessonsTable.getSelectedRow(), 0);
                        System.out.println("In if statement for adjusting cell. The following value has been selected:");
                        System.out.println(addSelection[0]);
                        studentViewpoint.setUnilessonToAdd(addSelection[0]);
                        System.out.println(studentViewpoint.getUnilessonToAdd());
                        lessonTitleName.setText(DataHelper.findUniLessonByName(addSelection[0]).getCourse().getName());
                        lessonDescriptionName.setText(DataHelper.findUniLessonByName(addSelection[0]).getCourse().getDescription());
                        System.out.println("End of add unilesson ListSelectionEvent if statement ");
                        Printer.printStars();
                    }
                }
            }
        });


        // Action listener for adding uniLesson
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Printer.printEquals();
                System.out.println("Entered actionevent for add course button");
                UniLesson uniLesson = DataHelper.findUniLessonByName(studentViewpoint.getUnilessonToAdd());
                studentViewpoint.student.addLessonToStudent(uniLesson);
                System.out.println(studentViewpoint.student.getStudentUniLessonGrades());
                // Get refreshed table of uniLessons
                Object[][] currentUniLesson = studentViewpoint.showLessonList();
                System.out.println("Updated uniLessonTable contents: ");
                System.out.println(Arrays.deepToString(currentUniLesson));
                uniLessonDm.setDataVector(currentUniLesson, columnNames);
                GuiHelper.tableAutoAdjustWidth(uniLessonsTable);
                Printer.printMinus();
                // Get refreshed table of timeTable
                Object[][] currentTimeTable = studentViewpoint.displayTimetable();
                System.out.println("Updated timetable contents: ");
                System.out.println(Arrays.deepToString(currentTimeTable));
                timeTableDm.setDataVector(currentTimeTable, columnNames);
                GuiHelper.tableAutoAdjustWidth(timeTable);
                timeTable.clearSelection();
                Printer.printMinus();
            }
        });

        // Action listener fpr dropping lesson
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Printer.printEquals();
                System.out.println("Entered actionevent for drop course button");
                System.out.println(studentViewpoint.student.getStudentUniLessonGrades());
                studentViewpoint.student.removeUniLessonFromStudent(studentViewpoint.getUnilessonToDrop());
                // Get refreshed table of uniLessons
                Object[][] currentUniLesson = studentViewpoint.showLessonList();
                System.out.println("Updated uniLessonTable contents: ");
                System.out.println(Arrays.deepToString(currentUniLesson));
                uniLessonDm.setDataVector(currentUniLesson, columnNames);
                GuiHelper.tableAutoAdjustWidth(uniLessonsTable);
                Printer.printMinus();
                // Get refreshed table of uniLessons
                Object[][] currentUnilessons = studentViewpoint.displayTimetable();
                System.out.println("Updated table contents: ");
                System.out.println(Arrays.deepToString(currentUnilessons));
                timeTableDm.setDataVector(currentUnilessons, columnNames);
                GuiHelper.tableAutoAdjustWidth(timeTable);
                timeTable.clearSelection();
                Printer.printMinus();
            }
        });

        // Action listener on log out
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginGUI.openWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        transcript.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TranscriptGUI.openWindow(studentViewpoint.student);
                frame.dispose();
            }
        });
    }

    public static void openWindow(Student student) {
        StudentViewpoint studentViewpoint = new StudentViewpoint(student);
        // Create and set up the window.
        frame = new JFrame("Student Course Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and set up panel
        StudentGUI newContentPane = new StudentGUI(studentViewpoint);
        newContentPane.setOpaque(true); // content panes must be opaque
        // Display the window.
        frame.add(newContentPane);
        frame.pack();
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}