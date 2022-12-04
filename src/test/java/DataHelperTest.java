import Utilities.DataHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataHelperTest {

    DataHelper uut;

//     ILessons mockLesson;

    @BeforeEach
    public void setUp() {
        uut = new DataHelper();
    }

    @AfterEach
    public void tearDown() {
        uut = null;
    }


    @ParameterizedTest
    @CsvSource({"username1, password1, Student", "username2, password2, Professor", "username3, password 3, Administrator", "username4, password4, Fail"})
    public void authenticateUserTest(String username, char[] password, String expectedResult) {
        String actualResult = uut.authenticateUser(username, password);
        assertEquals(expectedResult, actualResult);
    }

}
