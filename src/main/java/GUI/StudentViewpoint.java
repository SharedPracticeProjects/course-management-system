package GUI;

import Utilities.*;
import models.*;
import users.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StudentViewpoint {

    public ArrayList<UniLesson> sortedLessons;

    Student student;

    String unilessonToAdd = "";

    String unilessonToDrop = "";

    public StudentViewpoint(Student student) {
        this.student = student;
    }


    // Call this following method at some point to generate the JTable
    public String[][] showLessonList() {
        Printer.printPlus();
        System.out.println("Entered showLessonList in StudentViewpoint");
        sortedLessons = DataHelper.getSortedLessons();
        System.out.println("Sorted lessons: ");
        for (UniLesson uniLesson : sortedLessons) {
            System.out.print(uniLesson.getName() + " ");
        }
        String[][] lessonArr = new String[sortedLessons.size()][8];
        for (int i = 0; i < sortedLessons.size(); i++) {
            System.out.print("For loop instance " + i + " " + sortedLessons.get(i).getName());
            lessonArr[i][0] = sortedLessons.get(i).getName();
            lessonArr[i][1] = sortedLessons.get(i).getDepartmentName();
            lessonArr[i][2] = sortedLessons.get(i).getDays();
            lessonArr[i][3] = sortedLessons.get(i).getTimes();
            lessonArr[i][4] = String.valueOf(sortedLessons.get(i).getRoom());
            lessonArr[i][5] = sortedLessons.get(i).getProfessor().getName();
            lessonArr[i][6] = String.valueOf(sortedLessons.get(i).getMaxClassSize());
            lessonArr[i][7] = String.valueOf(sortedLessons.get(i).getSeatsRemaining());
        }
        System.out.println("Checking data array:");
        System.out.println(Arrays.deepToString(lessonArr));
        System.out.println("showLessonList method ending in StudentViewpoint class");
        Printer.printMinus();
        return lessonArr;
    }

    // List with filter
    public String[][] showLessonsFiltered(String criteria) {
        Printer.printPlus();
        System.out.println("Entered showLessonList in StudentViewpoint");
        sortedLessons = DataHelper.getSortedLessons();
        String[][] lessonArr;
        ArrayList<UniLesson> filteredList;
        if (criteria.equals("All")) {
            filteredList = sortedLessons;
        } else {
            filteredList = (ArrayList<UniLesson>) sortedLessons.stream()
                    .filter(uniLesson -> uniLesson.getDepartmentName().equals(criteria)).collect(Collectors.toList());
        }
        System.out.println("FilteredList: ");
        for (UniLesson uniLesson : filteredList) {
            System.out.print(uniLesson.getName() + " ");
        }
        lessonArr = new String[filteredList.size()][8];
        for (int i = 0; i < filteredList.size(); i++) {
            System.out.print("For loop instance " + i + " " + filteredList.get(i).getName());
            lessonArr[i][0] = filteredList.get(i).getName();
            lessonArr[i][1] = filteredList.get(i).getDepartmentName();
            lessonArr[i][2] = filteredList.get(i).getDays();
            lessonArr[i][3] = filteredList.get(i).getTimes();
            lessonArr[i][4] = String.valueOf(filteredList.get(i).getRoom());
            lessonArr[i][5] = filteredList.get(i).getProfessor().getName();
            lessonArr[i][6] = String.valueOf(filteredList.get(i).getMaxClassSize());
            lessonArr[i][7] = String.valueOf(filteredList.get(i).getSeatsRemaining());
        }
        System.out.println("Checking data array:");
        System.out.println(Arrays.deepToString(lessonArr));
        System.out.println("showLessonList method ending in StudentViewpoint class");
        Printer.printMinus();
        return lessonArr;
    }

    public String[][] displayTimetable() {   // given the uniLessons schedule, output a table ready list of all enrolled classes/uniLessons
        Printer.printPlus();
        System.out.println("displayTimetable method in StudentViewpoint class");
        HashMap<String, Double> studentUniLessonGrades = student.getStudentUniLessonGrades();
        String[][] lessonArr = new String[studentUniLessonGrades.size()][8];
        int i = 0;
        for (String uniLessonName : studentUniLessonGrades.keySet()) {
            UniLesson uniLesson = DataHelper.findUniLessonByName(uniLessonName);
            if(uniLesson == null) continue;
                lessonArr[i][0] = uniLesson.getName();
                lessonArr[i][1] = uniLesson.getDepartmentName();
                lessonArr[i][2] = uniLesson.getDays();
                lessonArr[i][3] = uniLesson.getTimes();
                lessonArr[i][4] = String.valueOf(uniLesson.getRoom());
                lessonArr[i][5] = uniLesson.getProfessor().getName();
                lessonArr[i][6] = Integer.toString(uniLesson.getEnrollment());
                lessonArr[i][7] = Integer.toString(uniLesson.getSeatsRemaining());
                i++;

        }
        System.out.println("Checking data array:");
        System.out.println(Arrays.deepToString(lessonArr));
        System.out.println("displayTimetable method ending in StudentViewpoint class");
        Printer.printMinus();
        return lessonArr;
    }

    public String[][] displayTranscript() {
        Printer.printPlus();
        System.out.println("displayTranscript method called in StudentViewpoint class");
        HashMap<String, Double> studentUniLessonGrades = student.getStudentUniLessonGrades();
        String[][] lessonArr = new String[studentUniLessonGrades.size()][6];
        int i = 0;
        for (String uniLessonName : studentUniLessonGrades.keySet()) {
            UniLesson uniLesson = DataHelper.findUniLessonByName(uniLessonName);
            lessonArr[i][0] = uniLesson.getName();
            lessonArr[i][1] = uniLesson.getCourse().getName();
            lessonArr[i][2] = uniLesson.getDays();
            lessonArr[i][3] = uniLesson.getTimes();
            lessonArr[i][4] = uniLesson.getProfessor().getName();
            lessonArr[i][5] = Double.toString(studentUniLessonGrades.get(uniLessonName));
            i++;
        }
        System.out.println("Checking data array:");
        System.out.println(Arrays.deepToString(lessonArr));
        System.out.println("displayTranscript method ending in StudentViewpoint class");
        Printer.printMinus();
        return lessonArr;
    }

    public String getUnilessonToAdd() {
        return unilessonToAdd;
    }

    public void setUnilessonToAdd(String unilessonToAdd) {
        this.unilessonToAdd = unilessonToAdd;
    }

    public String getUnilessonToDrop() {
        return unilessonToDrop;
    }

    public void setUnilessonToDrop(String unilessonToDrop) {
        this.unilessonToDrop = unilessonToDrop;
    }
}



















