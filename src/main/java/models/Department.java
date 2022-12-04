package models;

public class Department implements Comparable<Department> {
    String departmentName;
    String professorsInDepartment;
    String coursesInDepartment;

    public Department(String departmentName){
        this.departmentName=departmentName;
    }

    @Override
    public String toString() {
        return departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProfessorsInDepartment() {
        return professorsInDepartment;
    }

    public void setProfessorsInDepartment(String professorsInDepartment) {
        this.professorsInDepartment = professorsInDepartment;
    }

    public String getCoursesInDepartment() {
        return coursesInDepartment;
    }

    public void setCoursesInDepartment(String coursesInDepartment) {
        this.coursesInDepartment = coursesInDepartment;
    }

    //comparing departments based on name
    @Override
    public int compareTo(Department o) {
        return this.getDepartmentName().compareTo(o.getDepartmentName());
    }
}
