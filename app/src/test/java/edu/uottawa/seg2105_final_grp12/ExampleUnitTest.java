package edu.uottawa.seg2105_final_grp12;

import org.junit.Test;

import edu.uottawa.seg2105_final_grp12.models.AuthModel;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.models.data.HillClimb;
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
    public void UserCreationTest(){
        User newUser = new User("3000", "testUsername","userTest@test.com", "User");
        assertEquals("3000", newUser.getUid());
        assertEquals("testUsername", newUser.getUsername());
        assertEquals("userTest@test.com", newUser.getEmail());
        assertEquals("User", newUser.getRole());
    }
    @Test
    public void AdminCreationTest() throws Exception{
        Admin newAdmin = new Admin("3001", "admin@test.com");
        assertEquals("3001", newAdmin.getUid());
        assertEquals("admin@test.com", newAdmin.getEmail());
        assertEquals("Admin", newAdmin.getRole());
    }

    @Test
    public void cyclingClubCreationTest() {
        CyclingClub newClub = new CyclingClub("3002", "MyClub", "club@test.com");
        assertEquals("3002", newClub.getUid());
        assertEquals("MyClub", newClub.getUsername());
        assertEquals("club@test.com", newClub.getEmail());
    }

    @Test
    public void participantCreationTest() {
        Participant newParticipant = new Participant("3003", "testUser", "test@test.com");
        assertEquals("testUser", newParticipant.getUsername());
        assertEquals("test@test.com", newParticipant.getEmail());
        assertEquals("3003", newParticipant.getUid());
        assertEquals("Participant", newParticipant.getRole());
    }

    //TODO: in Progress
     @Test
     public void signUpTest() {
        String testId = "signUpTest" + Random.Default.nextInt(1000000);
        AuthModel.getInstance().registerUser(testId, "@signin.test", "password", "Participant")
                .addOnCompleteListener(task -> assertTrue(task.isSuccessful()));
     }
    @Test
     public void signInTest() {
        User userSignIn = new User("3004", "SignInTest", "SignInName", "Participant");
        AuthModel.getInstance().login("testagainagain", "password")
                .addOnCompleteListener(task -> assertTrue(task.isSuccessful()));
    }
    @Test
    public void wrongPasswordTest() {
        User user = new User("3005", "wrongPasswordTest", "password@test.com", "participant");
        AuthModel.getInstance().login("testagainagain", "wrongPassword")
                .addOnCompleteListener(task -> assertFalse(task.isSuccessful()));
    }
    @Test
    public void evenTypes(){
        EventType newEventType = new EventType();
    }
    // TODO Sanitize user inputs tests
}