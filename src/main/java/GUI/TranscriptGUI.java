package GUI;

import Utilities.*;
import users.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TranscriptGUI extends JPanel {
    static JFrame frame;
    // Set up table
    String[] columnNames = {"Class", "Title", "Lecture Days", "Hours",
            "Professor", "Grade"};
    Object[][] data;
    JTable uniLessonsTable;

    Object[][] currentUnilessons;
    DefaultTableModel dm;

    public TranscriptGUI(StudentViewpoint studentViewpoint){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Panel border
        GuiHelper.addBordersToPanel(this, 8);

        // Show sign in entity
        JLabel welcome = new JLabel("Logged in as: "+ studentViewpoint.student.getName());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0,0,10,0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(welcome, gbc);

        JButton logout = new JButton("Log Out");
        gbc.gridx = 9;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0,0,10,0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(logout, gbc);

        // Set up student timetable
        currentUnilessons = studentViewpoint.displayTranscript();
        dm = new DefaultTableModel(currentUnilessons, columnNames);
        JTable timeTable = new JTable(dm);
//        timeTable.setFillsViewportHeight(true);

        // Auto adjust table cell width
        GuiHelper.tableAutoAdjustWidth(timeTable);

        // Create panel with box layout to hold label, header and table
        JPanel timeTablePanel = new JPanel();
        timeTablePanel.setLayout(new BoxLayout(timeTablePanel,BoxLayout.Y_AXIS));
        JLabel timeTableLabel = new JLabel("Enrolled Classes", JLabel.CENTER);
        timeTableLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        timeTableLabel.setBorder(new EmptyBorder(10,0,10,0));
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
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 200;
        add(timeTableScrollPane, gbc);

        // Add label with GPA
        JLabel gpaLabel = new JLabel("GPA: "+studentViewpoint.student.calculateStudentGPA(),SwingConstants.CENTER);
        gpaLabel.setFont(gpaLabel.getFont().deriveFont(22f));
        gpaLabel.setBorder(new LineBorder(Color.WHITE));

        gbc.gridx = 5;
        gbc.gridwidth = 2;
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 0;

        add(gpaLabel, gbc);

        // Add back button
        JButton backButton = new JButton("Back to Course Management");
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(backButton, gbc);

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

        // Action listener on back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentGUI.openWindow(studentViewpoint.student);
                frame.dispose();
            }
        });
    }

    public static void openWindow(Student student) {
        StudentViewpoint studentViewpoint = new StudentViewpoint(student);
        // Create and set up the window.
        frame = new JFrame("Student Transcript");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and set up panel

        TranscriptGUI newContentPane = new TranscriptGUI(studentViewpoint);
        newContentPane.setOpaque(true); // content panes must be opaque

        // Display the window.
        frame.add(newContentPane);
        frame.pack();
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
