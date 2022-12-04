package GUI;

import Utilities.DataHelper;
import models.Course;
import models.Department;
import models.UniLesson;
import users.Professor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLessonWindow {
    public static void openLessonsWindow(AdminGUI ag) {
        JFrame frame = new JFrame("Manage Lessons");
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
                ag.openMenu();
            }
        });

        JLabel addLessonLabel = new JLabel("Add Lessons");
        addLessonLabel.setBounds(340, 60, 200, 60);
        addLessonLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(addLessonLabel);

        JLabel lessonNameLabel = new JLabel("Lesson name");
        lessonNameLabel.setBounds(50, 120, 150, 30);
        panel.add(lessonNameLabel);

        JTextField lessonNameField = new JTextField();
        lessonNameField.setBounds(200, 120, 150, 30);
        panel.add(lessonNameField);

        JLabel lessonSemesterLabel = new JLabel("Lesson semester");
        lessonSemesterLabel.setBounds(50, 180, 150, 30);
        panel.add(lessonSemesterLabel);

        JTextField lessonSemesterField = new JTextField();
        lessonSemesterField.setBounds(200, 180, 150, 30);
        panel.add(lessonSemesterField);

        JLabel lessonCourseLabel = new JLabel("Course code");
        lessonCourseLabel.setBounds(50, 240, 150, 30);
        panel.add(lessonCourseLabel);

        JTextField lessonCourseField = new JTextField();
        lessonCourseField.setBounds(200, 240, 150, 30);
        panel.add(lessonCourseField);

        JLabel lessonDepartmentLabel = new JLabel("Lesson department");
        lessonDepartmentLabel.setBounds(50, 300, 150, 30);
        panel.add(lessonDepartmentLabel);

        JTextField lessonDepartmentField = new JTextField();
        lessonDepartmentField.setBounds(200, 300, 150, 30);
        panel.add(lessonDepartmentField);


        JLabel lessonDaysLabel = new JLabel("Lesson days");
        lessonDaysLabel.setBounds(450, 120, 150, 30);
        panel.add(lessonDaysLabel);

        JTextField lessonDaysField = new JTextField();
        lessonDaysField.setBounds(600, 120, 150, 30);
        panel.add(lessonDaysField);

        JLabel lessonTimesLabel = new JLabel("Lesson times");
        lessonTimesLabel.setBounds(450, 180, 150, 30);
        panel.add(lessonTimesLabel);

        JTextField lessonTimesField = new JTextField();
        lessonTimesField.setBounds(600, 180, 150, 30);
        panel.add(lessonTimesField);

        JLabel lessonRoomLabel = new JLabel("Lesson room");
        lessonRoomLabel.setBounds(450, 240, 150, 30);
        panel.add(lessonRoomLabel);

        JTextField lessonRoomField = new JTextField();
        lessonRoomField.setBounds(600, 240, 150, 30);
        panel.add(lessonRoomField);

        JLabel lessonMaxSizeLabel = new JLabel("Lesson max size");
        lessonMaxSizeLabel.setBounds(450, 300, 150, 30);
        panel.add(lessonMaxSizeLabel);

        JTextField lessonMaxSizeField = new JTextField();
        lessonMaxSizeField.setBounds(600, 300, 150, 30);
        panel.add(lessonMaxSizeField);

        JLabel lessonProfessorLabel = new JLabel("Professor");
        lessonProfessorLabel.setBounds(280,360,150,30);
        panel.add(lessonProfessorLabel);

        JTextField lessonProfessorField = new JTextField();
        lessonProfessorField.setBounds(430,360,150,30);
        panel.add(lessonProfessorField);

        JButton addLessonButton = new JButton("ADD");
        addLessonButton.setBounds(300, 420, 200, 50);
        panel.add(addLessonButton);
        addLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = lessonNameField.getText();
                int semester = Integer.parseInt(lessonSemesterField.getText());
                Course course = DataHelper.findCourseByNumberCode(lessonCourseField.getText());
                Department department = DataHelper.findDepartmentByName(lessonDepartmentField.getText());
                String days = lessonDaysField.getText();
                String times = lessonTimesField.getText();
                int room = Integer.parseInt(lessonRoomField.getText());
                int maxSize = Integer.parseInt(lessonMaxSizeField.getText());
                Professor professor = DataHelper.findProfessorByName(lessonProfessorField.getText());


                System.out.println("ADD LESSON:" + name + " " + semester + " " + course.getName() + " " + department.getDepartmentName()
                        + " " + days + " " + times + " " + room + " " + maxSize);
                AdministratorViewpoint adminViewpoint = new AdministratorViewpoint(DataHelper.getSingleAdministrator());
                UniLesson lessonToAdd = new UniLesson();
//                name,semester,course,days,times,room,department,maxSize,professor
                lessonToAdd.setName(name);
                lessonToAdd.setSemester(semester);
                lessonToAdd.setCourse(course);
                lessonToAdd.setDays(days);
                lessonToAdd.setTimes(times);
                lessonToAdd.setRoom(room);
                lessonToAdd.setDepartment(department);
                lessonToAdd.setMaxClassSize(maxSize);
                lessonToAdd.setProfessor(professor);
                adminViewpoint.addLesson(lessonToAdd);

                for (UniLesson uniLesson : DataHelper.getUniLessons()) {
                    System.out.println(uniLesson.getName());
                }

//                     Utilities.DataHelper.addUniLesson(lessonToAdd);
            }
        });


        JLabel removeLessonLabel = new JLabel("Remove Lessons");
        removeLessonLabel.setBounds(320, 500, 200, 60);
        removeLessonLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(removeLessonLabel);


        JLabel lessonCodeRemoveLabel = new JLabel("Lesson code");
        lessonCodeRemoveLabel.setBounds(150, 550, 150, 30);
        panel.add(lessonCodeRemoveLabel);

        JTextField lessonCodeRemoveField = new JTextField();
        lessonCodeRemoveField.setBounds(300, 550, 350, 30);
        panel.add(lessonCodeRemoveField);

        JButton removeLessonButton = new JButton("REMOVE");
        removeLessonButton.setBounds(300, 600, 200, 50);
        panel.add(removeLessonButton);
        removeLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeOfLessonToRemove = lessonCodeRemoveField.getText();
                System.out.println("REMOVE LESSON: " + codeOfLessonToRemove);
                AdministratorViewpoint adminViewpoint = new AdministratorViewpoint(DataHelper.getSingleAdministrator());
                adminViewpoint.removeLesson(codeOfLessonToRemove);



                //          models.UniLesson lessonToRemove = Utilities.DataHelper.findUniLessonByName(name);
                //          Utilities.DataHelper.removeUniLesson(lessonToRemove);

            }
        });


        frame.setVisible(true);
    }

}
