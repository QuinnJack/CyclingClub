package edu.uottawa.seg2105_final_grp12;

import org.junit.Test;

import edu.uottawa.seg2105_final_grp12.models.AuthModel;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.data.Admin;
import edu.uottawa.seg2105_final_grp12.models.data.CyclingClub;
import edu.uottawa.seg2105_final_grp12.models.data.Participant;
import kotlin.random.Random;


import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
        User user = new User("12345", "testUser", "test@gmail.com", "user");
        assertEquals("testUser", user.getUsername());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("user", user.getRole());
        assertEquals("12345", user.getUid());
    }

    @Test
    public void adminCreationTest() {
        Admin admin = new Admin("12345", "admin@test.com");
        assertEquals("admin", admin.getUsername());
        assertEquals("admin@test.com", admin.getEmail());
        assertEquals("admin", admin.getRole());
    }

    @Test
    public void cyclingClubCreationTest() {
        CyclingClub club = new CyclingClub("12345", "MyClub", "club@gmail.com");
        assertEquals("MyClub", club.getUsername());
        assertEquals("club@gmail.com", club.getEmail());

    }

    @Test
    public void participantCreationTest() {
        Participant participant = new Participant("12345", "testUser", "test@test.com");
        assertEquals("testUser", participant.getUsername());
        assertEquals("test@test.com", participant.getEmail());
        assertEquals("12345", participant.getUid());

    }

     @Test
     public void signUpTest() {
        String testId = "signUpTest" + Random.Default.nextInt(1000000);
        AuthModel.getInstance().registerUser(testId, testId + "@test.test", "password", "participant")
                .addOnCompleteListener(task -> assertTrue(task.isSuccessful()));
     }
    @Test
     public void signInTest() {
        User user = new User("12345", "testUser", "test@test.com", "participant");
        AuthModel.getInstance().login("test5", "password")
                .addOnCompleteListener(task -> assertTrue(task.isSuccessful()));
    }
    @Test
    public void wrongPasswordTest() {
        User user = new User("12345", "testUser", "test@test.com", "participant");
        AuthModel.getInstance().login("test5", "wrongPassword")
                .addOnCompleteListener(task -> assertFalse(task.isSuccessful()));
    }
    // TODO Sanitize user inputs tests
}