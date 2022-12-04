package Utilities;

import models.*;
import users.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

public class DataHelper {

    // Declare initial data sets
    static ArrayList<Department> departments = new ArrayList<>();
    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<UniLesson> uniLessons = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Professor> professors = new ArrayList<>();
    static ArrayList<Administrator> administrators = new ArrayList<>();
    public static HashMap<String, char[]> logInCredentials = new HashMap<>();

    // store global values
    public static int maxCredits = 61;


    // Methods to read in csv to populate initial data sets
    public static void readDepartmentData() throws IOException {
        Printer.printPlus();
        System.out.println("readDepartmentData method start in DataHelper");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Departments.csv"));
        String input;
        br.readLine();
        while ((input = br.readLine()) != null) {
            String[] values = input.split(Pattern.quote(","));
            Department department = new Department(values[0].trim());
            departments.add(department);
        }
        System.out.println("readDepartmentData method end");
        Printer.printMinus();
    }

    public static void readCourseData() throws IOException {
        Printer.printPlus();
        System.out.println("readCourseData method start in DataHelper");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Courses.csv"));
        String input;
        br.readLine();
        Printer.printStars();
        System.out.println("readCourseData while loop");
        while ((input = br.readLine()) != null) {
            String[] values = input.split(Pattern.quote(","));
            Course course = new Course();
            course.setNumberCode(values[0]);
            course.setName(values[1].trim());
            course.setDescription(values[2].trim());
            course.setCredits(Integer.parseInt(values[3]));
            course.setBooks(values[4].trim());
            course.setPreReqs(values[5].trim());
            Department department = findDepartmentByName(values[6].trim());
            course.setDepartment(department);
            courses.add(course);
        }
        System.out.println("readCourseData while loop complete and method end");
        Printer.printMinus();
    }

    public static void readUniLessonData() throws IOException {
        Printer.printPlus();
        System.out.println("readUniLessonData method start in DataHelper");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/UniLessons.csv"));
        String input;
        br.readLine();
        int i = 1;
        while ((input = br.readLine()) != null) {
            UniLesson uniLesson = new UniLesson();
            String[] values = input.split(Pattern.quote(","));
            uniLesson.setName(values[0].trim());
            uniLesson.setSemester(Integer.parseInt(values[1]));
            Course course = findCourseByNumberCode(values[2].trim());
            System.out.println("Checking if UniLessons correctly included course object number " + i);
            System.out.println(course.getNumberCode());
            System.out.println(course.getDepartment().getDepartmentName());
            uniLesson.setCourse(course);
            System.out.println(uniLesson.getDepartmentName());  // each uniLesson can return the associated course's dept name through shortcut
            uniLesson.setDays(values[3].trim());
            uniLesson.setTimes(values[4].trim());
            uniLesson.setRoom(Integer.parseInt(values[5]));
            Professor professor = findProfessorByName(values[6].trim());
            System.out.println("Adding professor " + i + ": " + professor.getName());  // check that professor is successfully returned from method call
            uniLesson.setProfessor(professor);
            uniLesson.setMaxClassSize(Integer.parseInt(values[7]));
            if (!uniLessons.contains(uniLesson)) {
                uniLessons.add(uniLesson);
            } else {
                System.out.println(uniLesson.getName() + " already exists in the list of uniLessons and was not added. Loop#" + i);
            }
            i++;
        }
        System.out.println("readUniLessonData method end");
        Printer.printMinus();
    }

    public static void readStudentData() throws IOException {
        Printer.printPlus();
        System.out.println("readStudentData in DataHelper method start");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Students.csv"));
        String input;
        br.readLine();
        int i = 1;
        while ((input = br.readLine()) != null) {
            String[] values = input.split(Pattern.quote(","));
            Student student = new Student();
            student.setName(values[0].trim());
            student.setAddress(values[1].trim());
            student.setMajor((values[2].trim()));
            student.setPhoneNumber(values[3].trim());
            student.setEmailAddress(values[4].trim());
            if (!students.contains(student)) {
                students.add(student);
            } else {
                System.out.println(student.getName() + " is already in students list and was not added. Loop#" + i);
            }
            i++;
        }
        System.out.println("readStudentData method end");
        Printer.printMinus();
    }

    public static void readProfessorData() throws IOException {
        Printer.printPlus();
        System.out.println("readProfessorData in DataHelper method start");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Professors.csv"));
        String input;
        br.readLine();
        int i = 1;
        while ((input = br.readLine()) != null) {
            String[] values = input.split(Pattern.quote(","));
            Professor professor = new Professor();
            professor.setName(values[0].trim());
            professor.setAddress(values[1].trim());
            professor.setPhoneNumber(values[2].trim());
            professor.setDepartment(values[3].trim());
            professor.setOfficeRoomNumber(Integer.parseInt(values[4]));
            if (!professors.contains(professor)) {
                professors.add(professor);
            } else {
                System.out.println(professor.getName() + " is already in professors list and was not added. Loop#" + i);
            }
            i++;
        }
        System.out.println("readProfessorData method end");
        Printer.printMinus();
    }

    // Find objects by name
    public static Department findDepartmentByName(String name) {
        System.out.println("findDepartmentByName called in DataHelper");
        return departments.stream().filter(department -> name.equals(department.getDepartmentName())).findFirst().orElse(null);
    }

    public static UniLesson findUniLessonByName(String name) {
        System.out.println("findUniLessonByName called in DataHelper");
        return uniLessons.stream().filter(u -> name.equals(u.getName())).findFirst().orElse(null);
    }

    public static Student findStudentByName(String name) {
        System.out.println("findStudentByName called in DataHelper");
        return students.stream().filter(s -> name.equals(s.getName())).findFirst().orElse(null);
    }

    public static Professor findProfessorByName(String name) {
        System.out.println("findProfessorByName called in DataHelper");
        return professors.stream().filter(professor -> name.equals(professor.getName())).findFirst().orElse(null);
    }

    public static Administrator findAdministratorByName(String name) {
        System.out.println("findAdministratorByName called in DataHelper");
        return administrators.stream().filter(administrator -> name.equals(administrator.getName())).findFirst().orElse(null);
    }

    // Find objects by ID
    public static Course findCourseByNumberCode(String numberCode) {
        System.out.println("findCourseByNumberCode called in DataHelper");
        return courses.stream().filter(course -> numberCode.equals(course.getNumberCode())).findFirst().orElse(null);
    }

    public static Student findStudentByID(String id) {
        System.out.println("findStudentByID called in DataHelper");
        return students.stream().filter(student -> id.equals(student.getStudentID())).findFirst().orElse(null);
    }

    public static Professor findProfessorByID(String id) {
        System.out.println("findProfessorByID called in DataHelper");
        return professors.stream().filter(professor -> id.equals(professor.getProfessorID())).findFirst().orElse(null);
    }

    // Adding and removing entries from data set

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static void removeCourse(String numberCode) {
        courses.removeIf(course -> course.getNumberCode().equals(numberCode));
    }

    public static void addUniLesson(UniLesson uniLesson) {
        uniLessons.add(uniLesson);
    }

    public static void removeUniLesson(String name) {
        uniLessons.removeIf(uniLesson -> uniLesson.getName().equals(name));
    }

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static void removeStudent(String id) {
        students.removeIf(student -> student.getStudentID().equals(id));
    }

    public static void addProfessor(Professor professor) {
        professors.add(professor);
    }

    // no remove prof

    public static void addAdministrator(Administrator administrator) {
        administrators.add(administrator);
    }
    // no remove admin

    // Sort datasets with options
    // Sort list of lessons by department and course
    public static ArrayList<UniLesson> getSortedLessons() {
        Printer.printPlus();
        System.out.println("getSortedLessons called in DataHelper. Printing original uniLessons list");
        for (UniLesson uniLesson : uniLessons) {  // Print original list to check that it is not empty and see the contents
            System.out.print(uniLesson.getName() + " ");
            System.out.print(uniLesson.getDepartmentName());
        }
        ArrayList<UniLesson> lessonsCopy = new ArrayList<>(uniLessons);  // make a copy of the list and return the copy
        lessonsCopy.sort(Comparator.comparing(UniLesson::getDepartmentName).thenComparing(UniLesson::getName));
        System.out.println("Copied and sorted list is printed below:");
        for (UniLesson uniLesson : lessonsCopy) {
            System.out.print(uniLesson.getName() + "  ");
        }
        System.out.println("\nExiting GetSortedList in DataHelper");
        Printer.printMinus();
        return lessonsCopy;
    }

    // Getters
    public static ArrayList<Department> getDepartments() {
        return departments;
    }
    public static String[] getListOfDepartments(){
        Printer.printPlus();
        System.out.println("Etnered getList of departments");
        System.out.println(departments.size());
        String[] result = new String[departments.size()+1];
        result[0] = "All";
        for (int i = 1; i<departments.size()+1; i++){
            System.out.println("In for loop #"+i);
            result[i] = departments.get(i-1).getDepartmentName();
        }
        return result;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<UniLesson> getUniLessons() {
        return uniLessons;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static ArrayList<Professor> getProfessors() {
        return professors;
    }

    public static ArrayList<Administrator> getAdministrators() {
        return administrators;
    }

    public static Administrator getSingleAdministrator() {
        return administrators.get(0);
    }

    public static int getMaxCredits() {
        return maxCredits;
    }

    public static void setMaxCredits(int maxCredits) {
        DataHelper.maxCredits = maxCredits;
    }

    /*
    Password authentication
    - Add in passcode fields
        1) Check if the username password combo is valid
        2) If the password is correct, then basically the user is authenticated and the GUI instance just needs
        to query the correct database (student vs prof vs admin) for the object
        3) authenticateUser will then call getTypeOfUser and return the result back up to whoever called it
        4) In the GUI, given that we know which database to query, the GUI will query the relevant database for the user Object
     */

    public static HashMap<String, char[]> getLogInCredentials() {
        return logInCredentials;
    }

    public static void addLoginCredential(String username, char[] password) {
        if (logInCredentials.containsKey(username)) {
            System.out.println("Username " + username + " already exists");
        } else {
            logInCredentials.put(username, password);
        }
    }

    private static String getTypeOfUser(String logInIDS) {
        if (logInIDS.startsWith("S")) {
            return "Student";
        } else if (logInIDS.startsWith("P")) {
            return "Professor";
        } else if (logInIDS.startsWith("A")) {
            return "Administrator";
        } else {
            return "False";
        }
    }

    public static String authenticateUser(String username, char[] password) {
        Printer.printPlus();
        System.out.println("authenticateUser method start in DataHelper");
        StringBuilder result = new StringBuilder();
        String failureMessage = "Did not find username password combination";
        String failureResult = "Fail";
        try {
            char[] expectedPassword = logInCredentials.get(username);
            boolean isAuthenticated = Arrays.equals(expectedPassword, password);
            if (isAuthenticated) {
                result.append(getTypeOfUser(username));
                System.out.println("User " + username + " authenticated successfully");
            } else {
                System.out.println(failureMessage);
                result.append(failureResult);
            }
        } catch (NullPointerException e) {
            System.out.println(failureMessage);
            result.append(failureResult);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            result.append(failureResult);
        }
        System.out.println("authenticateUser method ends");
        Printer.printMinus();
        return result.toString();
    }
}
