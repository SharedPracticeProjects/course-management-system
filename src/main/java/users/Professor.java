package users;

import Utilities.DataHelper;

import java.util.Objects;

public class Professor {
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String department;
    private int officeRoomNumber;
    private String professorID;
    private int professorNumber;
    private static int professorCounter = 0;

    public Professor(String name, String address, String phoneNumber, String emailAddress, String department, int officeRoomNumber) {
        this.name = name;
        if (isValidAddress(address)) {
            this.address = address;
        }
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
        if (isValidEmailAddress(emailAddress)) {
            this.emailAddress = emailAddress;
        }
        this.department = department;
        this.officeRoomNumber = officeRoomNumber;
//        DataHelper.addProfessor(this);
        professorNumber = professorCounter;
        professorID = "P" + professorNumber;
        professorCounter++;
        DataHelper.addLoginCredential(professorID, "password".toCharArray());
    }

    public Professor() {
//        DataHelper.addProfessor(this);
        professorNumber = professorCounter;
        professorID = "P" + professorNumber;
        professorCounter++;
        DataHelper.addLoginCredential(professorID, "password".toCharArray());
    }

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

    // unique professor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return name.equals(professor.name) && emailAddress.equals(professor.emailAddress) && department.equals(professor.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, emailAddress, department);
    }

    //when you print a professor you are printing their name,don't want location in memory
    public String toString() {
        return name;
    }

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
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAdress() {
        return emailAddress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAddress = emailAdress;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getOfficeRoomNumber() {
        return officeRoomNumber;
    }

    public void setOfficeRoomNumber(int officeRoomNumber) {
        this.officeRoomNumber = officeRoomNumber;
    }

    public String getProfessorID() {
        return professorID;
    }

//    public  setProfessorNumber() {
//        this.professorID = professorID;
//    }

    public static int getProfessorCounter() {
        return professorCounter;
    }

    public static void setProfessorCounter(int professorCounter) {
        Professor.professorCounter = professorCounter;
    }

//    public ArrayList<models.UniLesson> getProfessorUniLesson() {
//        return professorUniLesson;
//    }
//
//    public void setProfessorUniLesson(ArrayList<models.UniLesson> professorUniLesson) {
//        this.professorUniLesson = professorUniLesson;
//    }
}
