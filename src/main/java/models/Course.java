package models;

import models.Department;

public class Course {
    private String numberCode;
    private String name;
    private String description;
    private int credits;
    private String books;

    private String preReqs;
    private Department department;

//    private ArrayList<models.Course> preReqs= new ArrayList<models.Course>();
//    private ArrayList<models.Course> preReqs= new ArrayList<models.Course>();

//      public void addPreReqs(String preReqs) {
//          return preReqs;
//      }
//    public void addpreReqs(models.Course course){
//        preReqs.add(course);
//    }

//    // public void addpreReqs(models.Course course){
//        preReqs.add(course);
//    }
//    public models.Course(String numberCode, String name, String description,
//                  int credits, String books, String preReqs, models.Department department) {
//        this.numberCode = numberCode;
//        this.name = name;
//        this.description = description;
//        this.credits = credits;
//        this.books = books;
//        this.preReqs = preReqs;
//        this.department = department;
//    }

    public Course() {}

    public String toString(){
        return numberCode;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

//    public ArrayList<models.Course> getPreReqs() {
//        return preReqs;
//    }
    public String getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(String preReqs) {
          this.preReqs = preReqs;
    }

//    public ArrayList<models.Course> getPreReqs() {
//        return preReqs;
//    }


}
