import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.testng.annotations.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import users.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class StudentTest {

    Student uut;
    String emailAddress;
    boolean actualResult;
    boolean expectedResult;

    @BeforeEach
    public void setUp(){
        uut= new Student();

    }

    @Test
    public void testTrueEmailAddress(){
        emailAddress="username@domain.com";

        boolean actualResult= Student.isValidEmailAddress(emailAddress);
        boolean expectedResult=true;

        assertEquals(actualResult, expectedResult);
    }
    @Test
    public void testFalseEmailAddress(){
        emailAddress="username.com";

        boolean actualResult= Student.isValidEmailAddress(emailAddress);
        boolean expectedResult=false;

        assertEquals(actualResult, expectedResult);
    }

   @ParameterizedTest
    @CsvSource({"07458729381,true","232458, false","+44746437182,false","07283746271,true","+%3!@34~@#$~, false"})
    public void testingRangeOfPhoneNumbers(String phoneNumber, boolean expectedResult){
        boolean  actualResult=Student.isValidPhoneNumber(phoneNumber);
        assertEquals(expectedResult,actualResult);
   }

   @ParameterizedTest
    @CsvSource({"123 SherryDrive, true", "@!$!#*Hello, false","372 StreetName G12 8TN,false"})
    public void testingRangeofAddresses(String address, boolean expectedResult){
        actualResult=Student.isValidAddress(address);
        assertEquals(expectedResult,actualResult);
   }




}
