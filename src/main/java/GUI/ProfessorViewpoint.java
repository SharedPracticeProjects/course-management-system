package GUI;

import Utilities.*;
import models.*;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProfessorViewpoint {
    Professor professor;
    List<UniLesson> professorUniLesson;

    String lessonToManage = "";

    String studentIDToManage = "";


    public ProfessorViewpoint(Professor professor) {
        Printer.printPlus();
        System.out.println("Entered Professor constructor, name:");
        this.professor = professor;
        ArrayList<UniLesson> lessons = DataHelper.getSortedLessons();
        System.out.println(professor.getName());
        professorUniLesson = getProfessorsUniLessons(lessons);
        System.out.println("In PV constructor");
        System.out.println(professorUniLesson.size());
        System.out.println(professorUniLesson.get(0).getName());
        System.out.println("Exited PV constructor");
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }




    public List<UniLesson> getProfessorsUniLessons(ArrayList<UniLesson> lessons) {
        Printer.printEquals();
        System.out.println("getProfessorsUniLessons method called in ProfessorViewpoint class");
        List<UniLesson> list = lessons.stream()
                .filter(p -> p.getProfessor().getName().equals(professor.getName()))
                .collect(Collectors.toList());
        System.out.println(list);
        System.out.println("getProfessorUniLessons method ending");
        Printer.printEquals();
        return list;
        //        return lessons.stream()
//                .filter(p -> p.getProfessor().getName().equals(professor.getName()))
//                .collect(Collectors.toList());
    }

    public String[][] displayProfessorSchedule() {
        Printer.printPlus();
        System.out.println("displayProfessorSchedule method called in ProfessorViewpoint class");
        String[][] lessonArr = new String[professorUniLesson.size()][5];
        for (int i = 0; i < professorUniLesson.size(); i++) {
            lessonArr[i][0] = professorUniLesson.get(i).getName();
            lessonArr[i][1] = professorUniLesson.get(i).getDays();
            lessonArr[i][2] = professorUniLesson.get(i).getTimes();
            lessonArr[i][3] = String.valueOf(professorUniLesson.get(i).getRoom());
            lessonArr[i][4] = String.valueOf(professorUniLesson.get(i).getMaxClassSize());
        }
        System.out.println("displayProfessorSchedule method ending");
        Printer.printMinus();
        return lessonArr;
    }

    public List<Student> getStudentsTakingLesson(String lesson) {
        Printer.printPlus();
        System.out.println("Entered getStudentsTakingLesson method");
        List<Student> studentsInLesson = DataHelper.getStudents().stream()
                .filter(s -> s.getStudentUniLessonGrades().containsKey(lesson)).collect(Collectors.toList());
        System.out.println("Exited getStudentsTakingLesson method");
        Printer.printMinus();
        return studentsInLesson;
    }

    public String[][] getAllStudentsCourseGrades(String lesson) {
        Printer.printPlus();
        System.out.println("Entered getAllStudentCourseGrades method.");
        List<Student> students = getStudentsTakingLesson(lesson);
        String[][] gradesTableData = new String[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            System.out.println("Start of for loop #" + i);
            gradesTableData[i][0] = students.get(i).getName();
            gradesTableData[i][1] = students.get(i).getStudentID();
            gradesTableData[i][2] = students.get(i).getStudentUniLessonGrades().get(lesson).toString();
        }
        System.out.println("Exit getAllStudentCourseGrades method.");
        Printer.printMinus();
        return gradesTableData;
    }

    public List<UniLesson> getProfessorUniLesson() {
        return professorUniLesson;
    }

    public void setProfessorUniLesson(List<UniLesson> professorUniLesson) {
        this.professorUniLesson = professorUniLesson;
    }

    public String getLessonToManage() {
        return lessonToManage;
    }

    public void setLessonToManage(String lessonToManage) {
        this.lessonToManage = lessonToManage;
    }

    public String getStudentIDToManage() {
        return studentIDToManage;
    }

    public void setStudentIDToManage(String studentIDToManage) {
        this.studentIDToManage = studentIDToManage;
    }

    public void markAStudent(String sName, String lName, double mark) {
        Printer.printPlus();
        System.out.println("markAStudent method called in ProfessorViewpoint");
        Student student = DataHelper.getStudents().stream()
                .filter(s -> s.getName().equals(sName))
                .findFirst().orElse(null);
        UniLesson lesson = DataHelper.getUniLessons().stream()
                .filter(l -> l.getName().equals(lName))
                .findFirst().orElse(null);
        System.out.println(mark);
        System.out.println(lesson.getCourse().getCredits());
        System.out.println(student.getGpa());

        student.updateGPA(mark, lesson.getCourse().getCredits());

        System.out.println(student.getGpa());


        student.getStudentUniLessonGrades().put(lesson.getName(), mark);
        System.out.println("markAStudent method finished in ProfessorViewpoint");
        Printer.printMinus();

        // getters and setters


    }
}
