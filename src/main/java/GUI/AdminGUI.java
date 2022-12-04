package GUI;

import Utilities.DataHelper;
import models.*;
import users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AdminGUI {

    public void openMenu() {
        JFrame frame = new JFrame("Admin menu");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setBounds(0, 0, 200, 30);
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    LoginGUI.openWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton manageCoursesButton = new JButton("MANAGE COURSES");
        manageCoursesButton.setBounds(100, 100, 600, 150);
        panel.add(manageCoursesButton);
        manageCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openCoursesWindow();
            }
        });

        JButton manageLessonsButton = new JButton("MANAGE LESSONS");
        manageLessonsButton.setBounds(100, 325, 600, 150);
        panel.add(manageLessonsButton);
        manageLessonsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AdminLessonWindow.openLessonsWindow(AdminGUI.this);
            }
        });

        JButton manageStudentsButton = new JButton("MANAGE STUDENTS");
        manageStudentsButton.setBounds(100, 550, 600, 150);
        panel.add(manageStudentsButton);
        manageStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openStudentsWindow();
            }
        });

        frame.setVisible(true);


    }

    public void openCoursesWindow() {

        JFrame frame = new JFrame("Manage Courses");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(0, 0, 200, 30);
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openMenu();
            }
        });

        JLabel addCourseLabel = new JLabel("Add Courses");
        addCourseLabel.setBounds(340, 60, 200, 60);
        addCourseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(addCourseLabel);

        JLabel courseNumberLabel = new JLabel("Course number");
        courseNumberLabel.setBounds(50, 120, 150, 30);
        panel.add(courseNumberLabel);

        JTextField courseNumberField = new JTextField();
        courseNumberField.setBounds(200, 120, 150, 30);
        panel.add(courseNumberField);

        JLabel courseNameLabel = new JLabel("Course name");
        courseNameLabel.setBounds(50, 180, 150, 30);
        panel.add(courseNameLabel);

        JTextField courseNameField = new JTextField();
        courseNameField.setBounds(200, 180, 150, 30);
        panel.add(courseNameField);

        JLabel courseCreditsLabel = new JLabel("Course credits");
        courseCreditsLabel.setBounds(50, 240, 150, 30);
        panel.add(courseCreditsLabel);

        JTextField courseCreditsField = new JTextField();
        courseCreditsField.setBounds(200, 240, 150, 30);
        panel.add(courseCreditsField);


        JLabel courseBooksLabel = new JLabel("Course books");
        courseBooksLabel.setBounds(450, 120, 150, 30);
        panel.add(courseBooksLabel);

        JTextField courseBooksField = new JTextField();
        courseBooksField.setBounds(600, 120, 150, 30);
        panel.add(courseBooksField);

        JLabel coursePreLabel = new JLabel("Course pre-reqs");
        coursePreLabel.setBounds(450, 180, 150, 30);
        panel.add(coursePreLabel);

        JTextField coursePreField = new JTextField();
        coursePreField.setBounds(600, 180, 150, 30);
        panel.add(coursePreField);

        JLabel courseDepartmentLabel = new JLabel("Course department");
        courseDepartmentLabel.setBounds(450, 240, 150, 30);
        panel.add(courseDepartmentLabel);

        JTextField courseDepartmentField = new JTextField();
        courseDepartmentField.setBounds(600, 240, 150, 30);
        panel.add(courseDepartmentField);

        JLabel courseDescriptionLabel = new JLabel("Course description");
        courseDescriptionLabel.setBounds(150, 300, 150, 30);
        panel.add(courseDescriptionLabel);

        JTextField courseDescriptionField = new JTextField();
        courseDescriptionField.setBounds(300, 300, 350, 30);
        panel.add(courseDescriptionField);

        JButton addCourseButton = new JButton("ADD");
        addCourseButton.setBounds(300, 350, 200, 50);
        panel.add(addCourseButton);
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberCode = courseNumberField.getText();
                String name = courseNameField.getText();
                int credits = Integer.parseInt(courseCreditsField.getText());
                String books = courseBooksField.getText();
                String prereqs = coursePreField.getText();
                Department department = DataHelper.findDepartmentByName(courseDepartmentField.getText());
                String description = courseDescriptionField.getText();

                System.out.println("ADD COURSE:" + numberCode + " " + name + " " + credits + " " + books + " " + prereqs + " " + department.getDepartmentName() + " " + description);

//                Utilities.DataHelper.addCourse(courseToAdd);
                Course courseToAdd = new Course();
                courseToAdd.setNumberCode(numberCode);
                courseToAdd.setName(name);
                courseToAdd.setDescription(description);
                courseToAdd.setCredits(credits);
                courseToAdd.setBooks(books);
                courseToAdd.setPreReqs(prereqs);
//            System.out.println(department.getDepartmentName());
                courseToAdd.setDepartment(department);
                AdministratorViewpoint adminViewpoint = new AdministratorViewpoint(DataHelper.getSingleAdministrator());
                adminViewpoint.addCourse(courseToAdd);

                for (Course course1 : DataHelper.getCourses()) {
                    System.out.println(course1.getName());
                }
            }
        });

        JLabel removeCourseLabel = new JLabel("Remove Courses");
        removeCourseLabel.setBounds(320, 450, 200, 60);
        removeCourseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(removeCourseLabel);

        JLabel courseNumberRemoveLabel = new JLabel("Course number");
        courseNumberRemoveLabel.setBounds(150, 500, 150, 30);
        panel.add(courseNumberRemoveLabel);

        JTextField courseNumberRemoveField = new JTextField();
        courseNumberRemoveField.setBounds(300, 500, 350, 30);
        panel.add(courseNumberRemoveField);

        JButton removeCourseButton = new JButton("REMOVE");
        removeCourseButton.setBounds(300, 550, 200, 50);
        panel.add(removeCourseButton);
        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberCode = courseNumberRemoveField.getText();
                AdministratorViewpoint adminViewpoint = new AdministratorViewpoint(DataHelper.getSingleAdministrator());
                adminViewpoint.removeCourse(numberCode);

                ArrayList<UniLesson> toRemove = new ArrayList<UniLesson>();

                for (UniLesson lesson : DataHelper.getUniLessons()) {
                    System.out.println("***************************");
                    System.out.println(lesson.getName());
                    if (lesson.getCourse().getNumberCode().equals(numberCode)){
                        toRemove.add(lesson);
                        System.out.println("REMOVE: " + lesson.getName());
                    }
                }

                for(UniLesson remove : toRemove) {
                    adminViewpoint.removeLesson(remove.getName());
                }

                System.out.println("********************************");
                for(UniLesson lesson : DataHelper.getUniLessons()) {
                    System.out.println(lesson.getName());
                }

            }
        });


        frame.setVisible(true);
    }

    public void openStudentsWindow() {
        JFrame frame = new JFrame("Manage Students");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(0, 0, 200, 30);
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                openMenu();
            }
        });

        JLabel addStudentLabel = new JLabel("Admit Students");
        addStudentLabel.setBounds(340, 60, 200, 60);
        addStudentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(addStudentLabel);

        JLabel studentNameLabel = new JLabel("Student name");
        studentNameLabel.setBounds(50, 120, 150, 30);
        panel.add(studentNameLabel);

        JTextField studentNameField = new JTextField();
        studentNameField.setBounds(200, 120, 150, 30);
        panel.add(studentNameField);

        JLabel studentAddressLabel = new JLabel("Student address");
        studentAddressLabel.setBounds(50, 240, 150, 30);
        panel.add(studentAddressLabel);

        JTextField studentAddressField = new JTextField();
        studentAddressField.setBounds(200, 240, 150, 30);
        panel.add(studentAddressField);

        JLabel studentMajorLabel = new JLabel("Student Major");
        studentMajorLabel.setBounds(200, 360, 150, 30);
        panel.add(studentMajorLabel);

        JTextField studentMajorField = new JTextField();
        studentMajorField.setBounds(350, 360, 150, 30);
        panel.add(studentMajorField);

        JLabel studentEmailLabel = new JLabel("Student email");
        studentEmailLabel.setBounds(450, 120, 150, 30);
        panel.add(studentEmailLabel);

        JTextField studentEmailField = new JTextField();
        studentEmailField.setBounds(600, 120, 150, 30);
        panel.add(studentEmailField);

        JLabel studentPhoneNumberLabel = new JLabel("Student Phone no.");
        studentPhoneNumberLabel.setBounds(450, 240, 150, 30);
        panel.add(studentPhoneNumberLabel);

        JTextField studentPhoneNumberField = new JTextField();
        studentPhoneNumberField.setBounds(600, 240, 150, 30);
        panel.add(studentPhoneNumberField);

        JButton addStudentButton = new JButton("ADMIT");
        addStudentButton.setBounds(300, 450, 200, 50);
        panel.add(addStudentButton);
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameField.getText();
                String address = studentAddressField.getText();
                String major = studentMajorField.getText();
                String email = studentEmailField.getText();
                String phoneNum = studentPhoneNumberField.getText();

                System.out.println("ADMIT STUDENT:" + name + " " + address + " " + major + " " + email + " " + phoneNum);
                 Student studentToAdmit = new Student();
//                name,address,major,phoneNum,email
                studentToAdmit.setName(name);
                studentToAdmit.setAddress(address);
                studentToAdmit.setMajor(major);
                studentToAdmit.setEmailAddress(email);
                studentToAdmit.setPhoneNumber(phoneNum);
                 AdministratorViewpoint adminViewpoint = new AdministratorViewpoint(DataHelper.getSingleAdministrator());
                 adminViewpoint.addNewStudent(studentToAdmit);
                // String name, String address, String major, String phoneNumber, String emailAddress
            }
        });


        frame.setVisible(true);
    }

}
