package GUI;

import Utilities.*;
import users.*;
import models.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AdministratorViewpoint {

    Administrator administrator;
    public AdministratorViewpoint(Administrator administrator){
        this.administrator= administrator;
    }

    //add in new professor
    public void addNewProfessor(String name, String address, String phoneNumber, String emailAddress,String department,int officeRoomNumber){
        Professor professor=new Professor(name, address, phoneNumber,emailAddress,department, officeRoomNumber);
        if(!DataHelper.getProfessors().contains(professor)){
            DataHelper.addProfessor(professor);
        }
    }

    public void addProfessorToCSV(Professor professor){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/users.Professors.csv",true);
            bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.write(professor.getName()+','+professor.getAddress()+","+professor.getPhoneNumber()+","+professor.getDepartment()+","+professor.getOfficeRoomNumber());
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //admit students
//    public void addNewStudent(String name, String address, String major, String phoneNumber, String emailAddress){
//       users.Student student= new users.Student(name, address,major,phoneNumber,emailAddress);
//       if(!Utilities.DataHelper.getStudents().contains(student)){
//       Utilities.DataHelper.addStudent(student);
//    }}

    public void addNewStudent(Student student) {
        DataHelper.addStudent(student);
        addStudentToCSV(student);
    }

    public void addStudentToCSV(Student student){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/Students.csv",true);
            bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.newLine();
            bufferedWriter.write(student.getName()+','+student.getAddress()+","+student.getMajor()+","+student.getPhoneNumber()+","+student.getEmailAddress());
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






    //remove/add courses, remove from students independently, remove course number since it is unique
    public void removeCourse( String numberCode){
        DataHelper.removeCourse(numberCode);
            rewriteCourseCSV();
        }

    public void rewriteCourseCSV(){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/Courses.csv");
            bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.write("models.Course Number ,models.Course Name ,models.Course Description ,Number of Credits ,Required Books ,Pre-Reqs, models.Department");
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Course course1: DataHelper.getCourses()){
          addCourseToCSV(course1);
        }
    }



    public void addCourse( Course course){
        DataHelper.addCourse(course);
        addCourseToCSV(course);
    }

    public void addCourseToCSV(Course course){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/Courses.csv",true);
        bufferedWriter= new BufferedWriter(writer);
        bufferedWriter.newLine();
        bufferedWriter.write(course.getNumberCode()+","+course.getName()+","+course.getDescription()+","+course.getCredits()+","+course.getBooks()+","+course.getPreReqs()+","+course.getDepartment());
        bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //remove/add lessons, remove all lesson from datahelper, from students, schedule lesson when create name
    public void removeLesson( String name){
            DataHelper.removeUniLesson(name);
            reWriteLessonInCSV();
        }
    //add lesson
    public void addLesson(UniLesson uniLesson ){
        DataHelper.addUniLesson(uniLesson);
        addLessonToCSV(uniLesson);
    }
    public void addLessonToCSV(UniLesson uniLesson){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/UniLessons.csv",true);
            bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.newLine();
            bufferedWriter.write(uniLesson.getName()+","+uniLesson.getSemester()+","+uniLesson.getCourse().getNumberCode()+","+uniLesson.getDays()+","+uniLesson.getTimes()+","+uniLesson.getRoom()+","+uniLesson.getProfessor()+","+ uniLesson.getMaxClassSize());
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reWriteLessonInCSV(){
        BufferedWriter bufferedWriter;
        FileWriter writer;
        try{writer=new FileWriter("src/main/resources/UniLessons.csv");
            bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.write("Lesson Name,Semester ,models.Course ,Days,Times ,Room , Professor,Capacity");
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(UniLesson uniLesson1: DataHelper.getUniLessons()){
            addLessonToCSV(uniLesson1);
        }
    }
    }





