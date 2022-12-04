import java.io.IOException;
import java.util.List;

import GUI.LoginGUI;
import GUI.ProfessorGUI;
import GUI.ProfessorViewpoint;
import Utilities.DataHelper;
import Utilities.Printer;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import models.*;
import users.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        setUp();
        // Add Default administrator
        Administrator administrator = new Administrator();
        DataHelper.addAdministrator(administrator);

        LoginGUI.openWindow();

//        Student studentB = DataHelper.getStudents().get(0);
//        Student studentC = DataHelper.getStudents().get(1);
//        Course course = DataHelper.findCourseByNumberCode("MATH201");
//        UniLesson uniLesson = DataHelper.findUniLessonByName("MATH201M13");
//        System.out.println("********* users.Student adding course *********");
//        studentB.addLessonToStudent(uniLesson);
//        studentC.addLessonToStudent(uniLesson);
//        System.out.println(studentB.getStudentUniLessonGrades().keySet());
//        System.out.println("************ Completed student adding course***********" );
//
//        ProfessorViewpoint professorViewpoint = new ProfessorViewpoint(DataHelper.findProfessorByName("Samwise Gamgee"));
//        professorViewpoint.setLessonToManage( uniLesson.getName()); ;
//        System.out.println("Found professor:" +professorViewpoint.getProfessor().getName());
//        List<Student> classList = professorViewpoint.getStudentsTakingLesson(professorViewpoint.getLessonToManage());
//        System.out.println("************************** Class list:");
//        System.out.println(classList.toString());
//        System.out.println("++++++++++++++++++++++++++++++ Testing getStudentByID method ++++++++++++++++++");
//        System.out.println(studentB.getStudentID());
//        System.out.println(DataHelper.findStudentByID(studentB.getStudentID()).getName());




//        users.Student studentA = new users.Student();
//        studentA.setName("John Smith");
//        studentA.addCourse(Utilities.DataHelper.findCourseByNumberCode(Utilities.DataHelper.getCourses(), "MGMT103"));
//        studentA.addLessonToStudent(Utilities.DataHelper.findUniLessonByName(Utilities.DataHelper.getUniLessons(), "MGMT103M11"));
//        GUI.LoginGUI.openWindow();

//        ProfessorGUI professorGUI = new ProfessorGUI();
//        Professor professor = DataHelper.getProfessors().get(5);
//        System.out.println(professor.getName());
//        professorGUI.GUI.ProfessorGUI(professor);
    }

    public static void setUp() throws IOException {
        Printer.printPlus();
        System.out.println("Start of setUP method");
        // Set theme
        try {
            UIManager.setLookAndFeel(new FlatNordIJTheme());
//            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize theme. Using fallback.");
        }
        DataHelper.addLoginCredential("A1","password".toCharArray());
        // read departments
        System.out.println("Reading departments csv");
        DataHelper.readDepartmentData();
        System.out.println(DataHelper.getDepartments());
        // read professors
        System.out.println("Reading professors csv");
        DataHelper.readProfessorData();
        System.out.println(DataHelper.getProfessors());
        // read course data
        System.out.println("Reading courses csv");
        DataHelper.readCourseData();
        System.out.println(DataHelper.getCourses());
        System.out.println(DataHelper.getCourses().get(0).getName());
        System.out.println(DataHelper.getCourses().get(0).getDepartment().getDepartmentName());
        // read uniLesson data
        System.out.println("Reading uniLessons csv");
        DataHelper.readUniLessonData();
        System.out.println(DataHelper.getUniLessons());
        System.out.println(DataHelper.getUniLessons().get(0).getName());
        System.out.println(DataHelper.getUniLessons().get(0).getDepartmentName());
        // read student data
        System.out.println("Reading students csv");
        DataHelper.readStudentData();
        System.out.println(DataHelper.getStudents().get(0).getName());
        System.out.println("End of setup method");
        Printer.printMinus();
    }
}
