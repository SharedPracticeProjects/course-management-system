package GUI;

import Utilities.*;
import models.*;
import users.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


public class ProfessorGUI {

    Professor professor;

    public void ProfessorGUI(Professor professor) {
        this.professor = professor;

        JFrame frame = new JFrame("Professor Window");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Overall Panel
        JPanel fullPanel = new JPanel();
        fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout());
        JLabel loginStatus = new JLabel("Logged in as: " + professor.getName());
        Border border = new LineBorder(Color.black);
        Border margin = new EmptyBorder(4, 5, 4, 5);
        loginStatus.setBorder(new CompoundBorder(border, margin));
        navPanel.add(loginStatus);

        navPanel.add(Box.createHorizontalStrut(500));

        JButton logOutButton = new JButton("Log Out");
        // logOutButton.setPreferredSize(new Dimension(50,30));
        navPanel.add(logOutButton);

        fullPanel.add(navPanel);
        // Panel for Table
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 20, 30, 20));

        JLabel tableLabel = new JLabel("Assigned Classes");
        tableLabel.setFont((tableLabel.getFont().deriveFont(20.0f)));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(tableLabel, BorderLayout.NORTH);

        final String[] tableHeader = {"Class", "Time", "Day", "Location", "Students"};

        Printer.printStars();
        System.out.println("Entered Professor GUI");
        System.out.println("Creating new professor viewpoint");
        ProfessorViewpoint professorViewpoint = new ProfessorViewpoint(professor);
        System.out.println("Finished creating new professor viewpoint");
        System.out.println("Trying to copy the UniLessons List");
        List<UniLesson> testList = professorViewpoint.professorUniLesson;
        System.out.println("Completed copying UniLessons List");
        System.out.println(testList);

        // Set up table with Prof's schedule using scrollpane
        final Object[][] data = professorViewpoint.displayProfessorSchedule();
        final JTable table = new JTable(data, tableHeader);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] uniLessonSelection = {null};
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("GUI.ProfessorGUI unilesson table listselection listener called");
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                    if (e.getValueIsAdjusting()) {
                        Printer.printStars();
                        System.out.println("Start of unilesson ListSelectionEvent if statement");
                        uniLessonSelection[0] = (String) table.getValueAt(table.getSelectedRow(), 0);
                        System.out.println(uniLessonSelection[0]);
                        professorViewpoint.setLessonToManage(uniLessonSelection[0]);
                        System.out.println("End of unilesson ListSelectionEvent if statement");
                        Printer.printStars();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 0, 15, 0));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton manageGradesButton = new JButton("Add Grades ");
//        manageGradesButton.setBorder(new EmptyBorder(30,0,30,0));
        panel.add(manageGradesButton, BorderLayout.SOUTH);
        manageGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGradesWindow(professorViewpoint);
                frame.dispose();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
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


        fullPanel.add(panel);
        frame.add(fullPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void openGradesWindow(ProfessorViewpoint professorViewpoint) {
        JFrame frame = new JFrame("Manage Grades");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel border
        GuiHelper.addBordersToPanel(panel, 12);

        // Show sign in entity
        JLabel welcome = new JLabel("Logged in as: " + professorViewpoint.professor.getName());
        welcome.setBorder(new LineBorder(Color.BLACK));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(welcome, gbc);

        // Log out button
        JButton logout = new JButton("Log Out");
        gbc.gridx = 9;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        panel.add(logout, gbc);


        // Create and set up the content pane for the list of available lessons

        List<Student> classList = professorViewpoint.getStudentsTakingLesson(professorViewpoint.getLessonToManage());
        System.out.println(classList.toString());
        Object[][] data = professorViewpoint.getAllStudentsCourseGrades(professorViewpoint.getLessonToManage());
        System.out.println(data);
        String[] courseHeader = {"Student", "ID", "Grade"};
        JTable studentTable = new JTable(data, courseHeader);
        // uniLessonsTable.setFillsViewportHeight(true);

        // Configure table rows to be selectable
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final String[] studentSelection = {null};
        ListSelectionModel rowSM = studentTable.getSelectionModel();

        // Auto adjust table cell width
        GuiHelper.tableAutoAdjustWidth(studentTable);

        // Create panel with box layout to hold label, header and table
        JPanel classListPanel = new JPanel();
        classListPanel.setLayout(new BoxLayout(classListPanel, BoxLayout.Y_AXIS));
        JLabel classListLabel = new JLabel("Class List", JLabel.CENTER);
        classListLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        classListLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        classListPanel.add(classListLabel);
        classListPanel.add(studentTable.getTableHeader());
        classListPanel.add(studentTable);
        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(classListPanel);

        int visible_rows = studentTable.getRowCount();

//        uniLessonsTable.setPreferredScrollableViewportSize(
//                new Dimension(
//                        uniLessonsTable.getPreferredSize().width,
////                        uniLessonsTable.getRowHeight() * visible_rows
//                        300
//                ));
        // Add table label

        // Add scroll pane to panel.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 8;
        gbc.ipady = 400;
        gbc.weightx = 0;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(scrollPane, gbc);

        JButton backButton = new JButton("Back to Lessons List");
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        panel.add(backButton, gbc);

        JLabel studentNameLabel = new JLabel("Student Selected:");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 0, 20);
        studentNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(studentNameLabel, gbc);

        final JLabel selectedStudentName = new JLabel("<html><i>Select a student</i></html>");
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 10, 0);
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        panel.add(selectedStudentName, gbc);

        JLabel addStudentGrade = new JLabel("users.Student Grade:");
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 10, 20);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        addStudentGrade.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(addStudentGrade, gbc);

        JTextField gradeField = new JTextField();
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 20, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        addStudentGrade.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(gradeField, gbc);

        gbc.gridx = 7;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 10, 20);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
//        panel.add(Box.createHorizontalStrut(500), gbc);

        gbc.gridx = 5;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.ipady = 45;
        gbc.ipadx = 150;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.PAGE_END;
        addStudentGrade.setFont(new Font("Arial", Font.BOLD, 18));
        JButton addGradeButton = new JButton("ADD");
        panel.add(addGradeButton, gbc);

        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Printer.printPlus();
                System.out.println("ProfessorGUI get student List selection event called");
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                    if (e.getValueIsAdjusting()) {
                        Printer.printStars();
                        System.out.println("Start of get student ListSelectionEvent if statement ");
                        studentSelection[0] = (String) studentTable.getValueAt(studentTable.getSelectedRow(), 1);
                        System.out.println("In if statement for adjusting cell");
                        System.out.println(studentSelection[0]);
                        professorViewpoint.setStudentIDToManage(studentSelection[0]);
                        System.out.println("Student name: ");
                        System.out.println(DataHelper.findStudentByID(professorViewpoint.getStudentIDToManage()).getName());
                        selectedStudentName.setText(DataHelper.findStudentByID(professorViewpoint.getStudentIDToManage()).getName());
                        System.out.println(professorViewpoint.studentIDToManage);
                        System.out.println("End of get student ListSelectionEvent if statement");
                    }
                    System.out.println("ProfessorGUI end of list selection event called");
                    Printer.printMinus();
                }
            }
        });

        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("****************** entered action event of addGradeButton");
                Student student = DataHelper.findStudentByID(professorViewpoint.getStudentIDToManage());
                String grade = gradeField.getText();
                DecimalFormat df = new DecimalFormat("0.00");
                double result = 0;
                try {
                    result = Double.parseDouble(grade);
                    result = Double.parseDouble(df.format(result));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                System.out.println("Student Grade: " + student.getStudentID() + ", " + result);
                professorViewpoint.markAStudent(student.getName(), professorViewpoint.lessonToManage, result);
            }
        });

        backButton.addActionListener(e -> {
            ProfessorGUI(professor);
            frame.dispose();
        });

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

        frame.add(panel);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
