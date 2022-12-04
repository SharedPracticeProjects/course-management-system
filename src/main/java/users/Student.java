package users;

import Utilities.*;
import models.*;

import java.lang.reflect.Array;
import java.util.*;

public class Student {

    private String name;
    private String address;
    private String major;
    private String phoneNumber;
    private String emailAddress;
    private int studentNumber;
    private String studentID;
    private String rawPassword;
    private int creditsObtained;

    private double creditsForGpaCalc = 0;

    public double getCreditsForGpaCalc() {
        return this.creditsForGpaCalc;
    }

    public void setCreditsForGpaCalc(double creditsForGpaCalc) {
        this.creditsForGpaCalc = creditsForGpaCalc;

    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getGpa() {
        return gpa;
    }

    private double gpa;

    private static int studentCounter = 0; // Global counter to generate unique student numbers

    private HashMap<String, Double> studentUniLessonGrades = new HashMap<>(); // each student has hashtable storing uniLesson's name and grades from professor
    private ArrayList<Course> courses = new ArrayList<>(); // list of courses the student is taking

    public Student() {
        Printer.printPlus();
        System.out.println("Entered Student constructor");
        studentNumber = studentCounter;
        studentID = "S" + studentNumber;
        studentCounter++;
        rawPassword = "password";
        char[] password = rawPassword.toCharArray();
        DataHelper.addLoginCredential(studentID, password);
        System.out.println("Existing Student constructor");
        Printer.printMinus();
    }

    public Student(String name, String address, String major, String phoneNumber, String emailAddress) {
        this.name = name;
        if (isValidAddress(address)) {
            this.address = address;
        }
        this.major = major;
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
        if (isValidEmailAddress(emailAddress)) {
            this.emailAddress = emailAddress;
        }
        studentNumber = studentCounter;
        studentID = "S" + studentNumber;
        studentCounter++;
        DataHelper.addLoginCredential(studentID, "password".toCharArray());
    }

     public double calculateStudentGPA() {    // returns GPA and does not change the gpa class variable
        Printer.printStars();
        System.out.println("calculateStudentGPA method start in Student class");
        //Formula: gpa = sum of [credits*(mark/100) ] / credits obtained (*100)   [mickey: the 100 cancels out]
        double numerator = 0;
        int totalCredits = 0;
        gpa = 0;
        for (Map.Entry<String, Double> entry : getStudentUniLessonGrades().entrySet()) {
            Course course = DataHelper.findUniLessonByName(entry.getKey()).getCourse();
            numerator += (course.getCredits() * entry.getValue()) / 100;  // multiples course's credits with the grades of the course's uniLesson
            if (entry.getValue() != 0.0) {
                totalCredits += course.getCredits();
//                System.out.println("**In the IF-For loop-adding total credits**");
//                System.out.println(totalCredits);
            }
        }
        gpa = (numerator / totalCredits) * 100;
        gpa = Math.round(gpa * 100.0) / 100.0;
        System.out.println("calculateStudentGPA method ended. Calculated GPA: ");
        System.out.printf("-> %s's GPA is: %s\n", getName(), gpa);
        Printer.printMinus();
        return gpa;
    }

    // uniLesson registration process
    private void addCourseToStudent(Course course) {    // changes list ${courses} and int ${creditsObtained}
        Printer.printPlus();                            // Should only be called by addLessonToStudent method
        System.out.println("entering enrollCourse in Student class");
        courses.add(course);
        System.out.printf("%s has been assigned to course: %s\n", getName(), course.getNumberCode());
        System.out.println("enrollCourse in student classs ending");
        Printer.printMinus();
    }

    public void addLessonToStudent(UniLesson uniLesson) {       //add lesson by name of a lesson, wants to call addCourseToStudent
        Printer.printPlus();                                    //can only register for lessons associated with course
        // [Mickey: changed implementation such that adding uniLessson will add course iff course was not previously in courses]
        System.out.println("addLessonToStudent call in Student class");
        System.out.println("Number of credits currenlty: " + creditsObtained);
        Course course = uniLesson.getCourse();
        int seatsRemaining = uniLesson.getSeatsRemaining();
        if (courses.contains(course)) {  // don't allow any updates to happen if an instance of the course is already being taken
            System.out.println("Already enrolled in this class section");
        } else if (getCreditsObtained() + course.getCredits() > DataHelper.getMaxCredits()) {
            System.out.println("Enrolling would bring your credits above the maximum. "); // <- maxCredits is now a global static variable in DataHelper
        } else if (seatsRemaining <= 0) {
            System.out.println("Max capacity reached. Cannot add student to class"); // don't allow updates if there is not enough space in the class
        } else {        // add the course and increase total credits taken
            courses.add(course);
            System.out.println("Class enrollment before:" + uniLesson.getEnrollment());
            uniLesson.setEnrollment(uniLesson.getEnrollment() + 1);
            System.out.println("Class enrollment after:" + uniLesson.getEnrollment());
            System.out.println("Credits obtained before: " + getCreditsObtained());
            setCreditsObtained(getCreditsObtained() + course.getCredits());
            System.out.printf("%s has been assigned to course: %s\n", getName(), course.getNumberCode());
            System.out.println("Credits obtained after: " + getCreditsObtained());
            if (!courses.contains(uniLesson.getCourse())) {
                addCourseToStudent(uniLesson.getCourse());
            }
            studentUniLessonGrades.put(uniLesson.getName(), 0.0);
            System.out.printf("%s has been assigned to lesson: %s\n", getName(), uniLesson.getName());
            System.out.println("addLessonToStudent ending");
            Printer.printMinus();
        }
    }

    public void addLessonToStudent(String uniLessonName) {    // Overloaded method for passing String name instead of object
        Printer.printPlus();
        System.out.println("addLessonToStudent (String version) call in Student class");
        UniLesson uniLesson = DataHelper.findUniLessonByName(uniLessonName);
        Course course = uniLesson.getCourse();
        int seatsRemaining = uniLesson.getSeatsRemaining();
        if (studentUniLessonGrades.containsKey(uniLesson.getName())) {  // don't allow any updates to happen if the uniLesson is already in hashmap
            System.out.println("Already enrolled in this class section");
        } else if (getCreditsObtained() + course.getCredits() > DataHelper.getMaxCredits()) {
            System.out.println("Enrolling would bring your credits above the maximum. "); // <- maxCredits is now a global static variable in DataHelper
        } else if (seatsRemaining <= 0) {
            System.out.println("Max capacity reached. Cannot add student to class"); // don't allow updates if there is not enough space in the class
        } else {        // add the course and increase total credits taken
            courses.add(course);
            System.out.println("Class enrollment before:" + uniLesson.getEnrollment());
            uniLesson.setEnrollment(uniLesson.getEnrollment() + 1);
            System.out.println("Class enrollment after:" + uniLesson.getEnrollment());
            System.out.println("Credits obtained before: " + getCreditsObtained());
            setCreditsObtained(getCreditsObtained() + course.getCredits());
            System.out.printf("%s has been assigned to course: %s\n", getName(), course.getNumberCode());
            System.out.println("Credits obtained after: " + getCreditsObtained());
            if (!courses.contains(uniLesson.getCourse())) {
                addCourseToStudent(uniLesson.getCourse());
            }
            studentUniLessonGrades.put(uniLesson.getName(), 0.0);
            System.out.printf("%s has been assigned to lesson: %s\n", getName(), uniLesson.getName());
            System.out.println("addLessonToStudent ending");
            Printer.printMinus();
        }
    }

    private void removeCourseFromStudent(Course course) {
        Printer.printPlus();
        System.out.println("removeCourseFromStudent method call in Student class");
        ArrayList<Course> courses = getCourses();
        boolean result = courses.remove(course);
        if (result) {
            System.out.println("Successfully removed " + course.getNumberCode());
        } else {
            System.out.println("Error removing course " + course.getNumberCode());
        }
        System.out.println("removeCoursesFromStudent ending in Student class");
        Printer.printMinus();
    }

    public void removeUniLessonFromStudent(UniLesson uniLesson) {   // Removes UniLesson Name + grades entry from HashMap. Removes Course from courses. Decreases total credits. Empties enrollment seat
        Printer.printPlus();
        System.out.println("removeUniLessonFromStudent method call in Student class");
        HashMap<String, Double> grades = getStudentUniLessonGrades();
        if (grades.containsKey(uniLesson.getName())) {
            System.out.println("Removing " + uniLesson.getName() + " from grades HashMap. Doing so will remove the associated course and grades stored as well");
            grades.remove(uniLesson.getName());
            removeCourseFromStudent(uniLesson.getCourse());
            setCreditsObtained(getCreditsObtained() - uniLesson.getCourse().getCredits());
            uniLesson.setEnrollment(uniLesson.getEnrollment() - 1);
            System.out.println("Finished removing uniLesson");
        } else {
            Printer.printEquals();
            System.out.println("Cannot find uniLesson");
        }
        System.out.println("removeUniLessonFromStudent method ending");
        Printer.printMinus();
    }


    public void removeUniLessonFromStudent(String uniLessonName) { // Overloaded method to use String instead
        Printer.printPlus();
        System.out.println("removeUniLessonFromStudent (String version) method call in Student class");
        HashMap<String, Double> grades = getStudentUniLessonGrades();
        UniLesson uniLesson = DataHelper.findUniLessonByName(uniLessonName);
        if (grades.containsKey(uniLesson.getName())) {
            System.out.println("Removing " + uniLesson.getName() + " from grades HashMap. Doing so will remove the associated course and grades stored as well");
            grades.remove(uniLesson.getName());
            removeCourseFromStudent(uniLesson.getCourse());
            setCreditsObtained(getCreditsObtained() - uniLesson.getCourse().getCredits());
            uniLesson.setEnrollment(uniLesson.getEnrollment() - 1);
            System.out.println("Finished removing uniLesson");
        } else {
            Printer.printEquals();
            System.out.println("Cannot find uniLesson");
        }
        System.out.println("removeUniLessonFromStudent (String version) method ending");
        Printer.printMinus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && address.equals(student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    public String toString() {
        return name;
    }

    //check that email exist
    public static boolean isValidEmailAddress(String emailAddress) {
        boolean result = emailAddress.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        return result;
    }

    //check that phone number exists
    public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean result = phoneNumber.matches("^[0-9]{11}$");
        return result;
    }

    //valid address
    public static boolean isValidAddress(String address) {
        boolean result = address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        return result;
    }

    //getters and setters
    //need to control whether email,etc. is valid
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (isValidAddress(address))
            this.address = address;
        else {
            System.out.println("This is not a valid address");
        }
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else {
            System.out.println("This is not a valid phone number");
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (isValidEmailAddress(emailAddress))
            this.emailAddress = emailAddress;
        else {
            System.out.println("This is not a valid email address");
        }
    }

    public int getCreditsObtained() {
        return creditsObtained;
    }

    public void setCreditsObtained(int creditsObtained) {
        this.creditsObtained = creditsObtained;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public HashMap<String, Double> getStudentUniLessonGrades() {
        return studentUniLessonGrades;
    }

    public void updateGPA(double mark, int credits)
    {

        double gpa = ((mark * credits) + (this.getGpa() * this.getCreditsForGpaCalc()))/(this.getCreditsForGpaCalc() + credits);
        gpa = Math.round(gpa * 100)/100.0;
        this.setGpa(gpa);
        this.setCreditsForGpaCalc(this.getCreditsForGpaCalc()+credits);
    }

}
