package GUI;

import Utilities.*;
import users.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Login GUI is a static class that renders the login page.
 * Authentication:
 * - Receives username and password combination
 * - Calls authentication method from Utilities.DataHelper
 * -- returns String of "users.Student", "users.Professor", "users.Administrator", "False" depending on outcome
 * -- Queries Utilities.DataHelper for the entity object depending on what string was passed from above
 * - Calls corresponding GUI pages
 */
public class LoginGUI extends JPanel {
    static JFrame frame;
    static JPanel panel;
    static JTextField userNameField;
    static JLabel userLabel;
    static JLabel passwordLabel;
    static JPasswordField passwordField;
    static JButton loginButton;
    static JLabel statusLabel;

    public LoginGUI() throws IOException {
        // Custom font
        Font customFont = null;
        try {
            //create the font to use. Specify the size!
            InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/media/baker-signet-regular.ttf"));
            customFont = Font.createFont(Font.TRUETYPE_FONT, myStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Panel border
        GuiHelper.addBordersToPanel(this, 9);

        // Add school label
        JLabel orgIcon = new JLabel();
        try {
            InputStream imageStream = this.getClass().getResourceAsStream("/media/javaLogo.png");
            BufferedImage image = ImageIO.read(imageStream);
            Image scaled = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            ImageIcon imageIcon = new ImageIcon(scaled);
            orgIcon.setIcon(imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 20, 10, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(orgIcon, gbc);

        // Add title label
        JLabel intro = new JLabel("Java University");
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        intro.setFont(customFont.deriveFont(32f).deriveFont(attributes));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(intro, gbc);

        JLabel title = new JLabel("Course Management System");
        title.setFont(new Font(title.getFont().toString(), Font.PLAIN,16));
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(title, gbc);

        // Create log in form
        JPanel formPanel = new JPanel(new SpringLayout());
        userLabel = new JLabel("Username", JLabel.TRAILING);
        formPanel.add(userLabel);
        userNameField = new JTextField(15);
        userLabel.setLabelFor(userNameField);
        formPanel.add(userNameField);

        passwordLabel = new JLabel("Password", JLabel.TRAILING);
        formPanel.add(passwordLabel);
        passwordField = new JPasswordField(15);
        passwordLabel.setLabelFor(passwordField);
        formPanel.add(passwordField);
        // Compact spring layout components
        Utilities.SpringUtilities.makeCompactGrid(
                formPanel,
                2, 2,
                5, 5,
                25, 30
        );
        formPanel.setMaximumSize(new Dimension(400, 500));

        // Adding log in form
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 40, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(formPanel, gbc);

        // Log in button
        loginButton = new JButton("Log In");
        gbc.gridx = 5;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.ipady = 25;
        gbc.ipadx = 100;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(loginButton, gbc);

        // Log in failure message
        statusLabel = new JLabel("", SwingConstants.CENTER);
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(statusLabel, gbc);


        // Event listener for log in button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                char[] password = passwordField.getPassword();
                String userType = DataHelper.authenticateUser(username, password);
                switch (userType) {
                    case "Student":
                        Student student = DataHelper.findStudentByID(username);
                        StudentGUI.openWindow(student);
                        frame.dispose();
                        break;
                    case "Professor":
                        Printer.printStars();
                        System.out.println("Entering professor case in switch");
                        Professor professor = DataHelper.findProfessorByID(username);
                        ProfessorGUI professorGUI = new ProfessorGUI();
                        professorGUI.ProfessorGUI(professor);
                        frame.dispose();
                        break;
                    case "Administrator":
                        new AdminGUI().openMenu();
                        frame.dispose();
                        break;
                    case "Fail":
                        statusLabel.setText("<html><i>Invalid username password combination</i><html>");
                        break;
                }
            }
        });
    }

    public static void openWindow() throws IOException {

        // Create and set up the window.
        frame = new JFrame("Student models.Course Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and set up panel

        LoginGUI newContentPane = new LoginGUI();
        newContentPane.setOpaque(true); // content panes must be opaque

        // Display the window.
        frame.add(newContentPane);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Student goStudent(String username) {
        Student student = new Student();
        return student;
    }

    public static Professor goProfessor(String username) {
        Professor professor = new Professor();
        return professor;
    }

    public static Administrator goAdministrator(String username) {
        Administrator administrator = new Administrator();
        return administrator;
    }


}
