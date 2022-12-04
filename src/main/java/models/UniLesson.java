package models;

import users.Professor;

import java.util.Objects;

public class UniLesson {

    private String name;
    private int semester;
    private Course course;
    private String days;
    private String times;
    private int room;
    private String departmentName;
    private Department department;
    private int maxClassSize;
    private int enrollment;
    private int seatsRemaining;
    private Professor professor;

    //define when we count two unilessons as equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniLesson uniLesson = (UniLesson) o;
        return semester == uniLesson.semester && room == uniLesson.room && name.equals(uniLesson.name) && course.equals(uniLesson.course) && days.equals(uniLesson.days) && times.equals(uniLesson.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, semester, course, days, times, room);
    }

    public String toString(){
        return name;
    }

    // getters and setters
    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getMaxClassSize() {
        return maxClassSize;
    }

    public void setMaxClassSize(int maxClassSize) {
        this.maxClassSize = maxClassSize;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public int getSeatsRemaining() {
        seatsRemaining = maxClassSize - enrollment;
        return seatsRemaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        this.departmentName = course.getDepartment().getDepartmentName();
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
