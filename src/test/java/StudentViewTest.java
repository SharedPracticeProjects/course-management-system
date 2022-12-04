import net.bytebuddy.jar.asm.Handle;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentViewTest {

//    StudentViewPoint uut;
//    ILessons mockLessons;
//
//
//    @Test
//    public void showUniClassList(){
//        uut.showLessonList(ArrayList)
//    }
    String[] []showClassesTestArray = {
            {"models.Course", "models.Department", "Day", "time", "Location", "users.Professor", "Max Class Size"},
            {"COMMERCIAL LAW101", "Law", "Tuesday/Friday", "09:00-11:00", "Turing Building", "users.Professor Ross", "19"},
            {"CRIMINAL LAW101", "Law", "Monday/Friday", "13:00-15:00", "Turing Building", "users.Professor Keeting", "20"},
            {"ALGEBRA101", "Mathematics", "Tuesday/Thursday", "13:00-15:00", "Babbage House", "users.Professor Rand", "25"},
            {"MATH101", "Mathematics", "Monday/Wednesday", "09:00-11:00", "Babbage House", "users.Professor Pie", "21"}
    };
}
