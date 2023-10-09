package edu.uottawa.seg2105_final_grp12;

import org.junit.Test;
import edu.uottawa.seg2105_final_grp12.models.User;
import edu.uottawa.seg2105_final_grp12.models.Admin;
import edu.uottawa.seg2105_final_grp12.models.CyclingClub;
import edu.uottawa.seg2105_final_grp12.models.Participant;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// TODO: Account creation, login, logout test cases
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userCreationTest() {
        User user = new User("12345", "testUser", "Test", "user");
        assertEquals("testUser", user.getUsername());
        assertEquals("Test", user.getFirstName());
        assertEquals("user", user.getRole());
        assertEquals("12345", user.getUid());
    }

    @Test
    public void adminCreationTest() {
        Admin admin = new Admin("12345");
        assertEquals("admin", admin.getUsername());
        assertEquals("Admin", admin.getFirstName());
        assertEquals("admin", admin.getRole());
    }

    @Test
    public void cyclingClubCreationTest() {
        CyclingClub club = new CyclingClub("12345", "MyClub");
        assertEquals("MyClub", club.getUsername());
    }

    @Test
    public void participantCreationTest() {
        Participant participant = new Participant("12345", "testUser", "Test");
        assertEquals("testUser", participant.getUsername());
        assertEquals("Test", participant.getFirstName());
        assertEquals("12345", participant.getUid());

    }

     @Test
     public void signUpTest() {
         User user = new User("12345", "testUser", "Test", "participant");
         boolean result = user.createAccount("test@example.com", "password123");
         assertTrue(result);
     }

    @Test
     public void signInTest() {
        User user = new User("12345", "testUser", "Test", "participant");
        boolean loggedIn = user.login("test@example.com", "passwordzzzz");
        assertTrue(loggedIn);
    }
    @Test
    public void wrongPasswordTest() {
        User user = new User("12345", "testUser", "Test", "participant");
        boolean loggedIn = user.login("test@example.com", "theWrongPassword");
        assertFalse(loggedIn);
    }
}