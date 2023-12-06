package edu.uottawa.seg2105_final_grp12;

import static android.app.PendingIntent.getActivity;

import org.junit.Test;

import edu.uottawa.seg2105_final_grp12.models.data.ClubReview;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventField;
import edu.uottawa.seg2105_final_grp12.models.data.GroupRides;
import edu.uottawa.seg2105_final_grp12.models.data.HillClimb;
import edu.uottawa.seg2105_final_grp12.models.data.RoadStageRace;
import edu.uottawa.seg2105_final_grp12.models.data.TimeTrial;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.data.Admin;
import edu.uottawa.seg2105_final_grp12.models.data.CyclingClub;
import edu.uottawa.seg2105_final_grp12.models.data.Participant;


import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// TODO: Account creation, login, logout test cases
public class ExampleUnitTest {
    @Test
    public void userCreationTest(){
        User newUser = new User("3000", "testUsername","userTest@test.com", "User");
        assertEquals("3000", newUser.getUid());
        assertEquals("testUsername", newUser.getUsername());
        assertEquals("userTest@test.com", newUser.getEmail());
        assertEquals("User", newUser.getRole());
    }
    @Test
    public void adminCreationTest() throws Exception{
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
     @Test
     public void clubReviewTest() {
         ClubReview clubReviewTest = new ClubReview(4, "More inclusivity for elders", "John");
         assertEquals(4, clubReviewTest.getRating());
         assertEquals("More inclusivity for elders", clubReviewTest.getFeedback());
         assertEquals("John", clubReviewTest.getReviewerName());
     }

     @Test
     public void newEventType() {
         Event newEvent = new Event("3004");
         assertEquals("3004", newEvent.getId());

         newEvent.setCyclingClub("Tour De France");
            assertEquals("Tour De France", newEvent.getCyclingClub());
         newEvent.setDuration("3 weeks");
            assertEquals("3 weeks", newEvent.getDuration());
         newEvent.setFee("$65000");
            assertEquals("$65000", newEvent.getFee());
         newEvent.setMaxParticipants(160);
            assertEquals("160", newEvent.getMaxParticipants());
     }

    @Test
    public void HillClimbEventType(){
        HillClimb newEventType = new HillClimb("3005");
        assertEquals("3005", newEventType.getId());

        newEventType.setField(EventField.NAME, "Hill Climb");
            assertEquals("Hill Climb", newEventType.getValue(EventField.NAME));
        newEventType.setField(EventField.DIFFICULTY, "Easy");
            assertEquals("Easy", newEventType.getValue(EventField.DIFFICULTY));
        newEventType.setField(EventField.DURATION, "50 Mins");
            assertEquals("50 Mins", newEventType.getValue(EventField.DURATION));
        newEventType.setField(EventField.FEE, "$75");
            assertEquals("$75", newEventType.getValue(EventField.FEE));
        newEventType.setField(EventField.MAX_AGE, 40);
            assertEquals("40", newEventType.getValue(EventField.MAX_AGE));
        newEventType.setField(EventField.MIN_AGE, 16);
                assertEquals("16", newEventType.getValue(EventField.MIN_AGE));
        newEventType.setField(EventField.MAX_PARTICIPANTS, 10);
            assertEquals("10", newEventType.getValue(EventField.MAX_PARTICIPANTS));
    }
    @Test
    public void GroupRidesEventType(){
        GroupRides newEventType = new GroupRides("3006");
        assertEquals("3006", newEventType.getId());

        newEventType.setField(EventField.NAME, "Group Rides");
            assertEquals("Group Rides", newEventType.getValue(EventField.NAME));
        newEventType.setField(EventField.DIFFICULTY, "Medium");
            assertEquals("Medium", newEventType.getValue(EventField.DIFFICULTY));
        newEventType.setField(EventField.DURATION, "5 hours");
            assertEquals("5 hours", newEventType.getValue(EventField.DURATION));
        newEventType.setField(EventField.FEE, "$150");
            assertEquals("$150", newEventType.getValue(EventField.FEE));
        newEventType.setField(EventField.MAX_AGE, 65);
            assertEquals("65", newEventType.getValue(EventField.MAX_AGE));
        newEventType.setField(EventField.MIN_AGE, 8);
            assertEquals("8", newEventType.getValue(EventField.MIN_AGE));
        newEventType.setField(EventField.MAX_PARTICIPANTS, 50);
            assertEquals("50", newEventType.getValue(EventField.MAX_PARTICIPANTS));

    }
    @Test
    public void RoadStageRaceEventType(){
        RoadStageRace newEventType = new RoadStageRace("3007");
        assertEquals("3007", newEventType.getId());

        newEventType.setField(EventField.NAME, "Road Race");
            assertEquals("Road Race", newEventType.getValue(EventField.NAME));
        newEventType.setField(EventField.DIFFICULTY, "Hard");
            assertEquals("Hard", newEventType.getValue(EventField.DIFFICULTY));
        newEventType.setField(EventField.DURATION, "2 Days");
            assertEquals("2 Days", newEventType.getValue(EventField.DURATION));
        newEventType.setField(EventField.FEE, "$100");
            assertEquals("$100", newEventType.getValue(EventField.FEE));
        newEventType.setField(EventField.MAX_AGE, 30);
            assertEquals("30", newEventType.getValue(EventField.MAX_AGE));
        newEventType.setField(EventField.MIN_AGE, 18);
            assertEquals("18", newEventType.getValue(EventField.MIN_AGE));
        newEventType.setField(EventField.MAX_PARTICIPANTS, 50);
            assertEquals("50", newEventType.getValue(EventField.MAX_PARTICIPANTS));
    }
    @Test
    public void TimeTrialEventType(){
        TimeTrial newEventType = new TimeTrial("3008");
        assertEquals("3008", newEventType.getId());

        newEventType.setField(EventField.NAME, "Time Trial");
            assertEquals("Time Trial", newEventType.getValue(EventField.NAME));
        newEventType.setField(EventField.DIFFICULTY, "Hard");
            assertEquals("Hard", newEventType.getValue(EventField.DIFFICULTY));
        newEventType.setField(EventField.DURATION, "2 Mins");
            assertEquals("2 Mins", newEventType.getValue(EventField.DURATION));
        newEventType.setField(EventField.FEE, "$20");
            assertEquals("$20", newEventType.getValue(EventField.FEE));
        newEventType.setField(EventField.MAX_AGE, 30);
            assertEquals("30", newEventType.getValue(EventField.MAX_AGE));
        newEventType.setField(EventField.MIN_AGE, 16);
            assertEquals("16", newEventType.getValue(EventField.MIN_AGE));
        newEventType.setField(EventField.MAX_PARTICIPANTS, 20);
            assertEquals("20", newEventType.getValue(EventField.MAX_PARTICIPANTS));
    }
}