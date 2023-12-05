package edu.uottawa.seg2105_final_grp12.models.data;

import static edu.uottawa.seg2105_final_grp12.models.data.EventField.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Event implements Serializable {

    private String id;
    private String cyclingClub;
    private String type;
    private HashMap<EventField, String> properties = new HashMap<>();
    private ArrayList<String> participants;

    public Event() {

    }

    public Event(String id) {
        this.id = id;
    }

    public String setField(EventField field, String value) {
        return properties.put(field, value);
    }

    public String setField(EventField field, Number value) {
        return setField(field, String.valueOf(value));
    }

    public String getValue(EventField field) {
        return properties.get(field);
    }

    public HashMap<EventField, String> getProperties() {
        return properties;
    }

    public void setCyclingClub(String cyclingClub) {
        this.cyclingClub = cyclingClub;
    }

    public String getCyclingClub() {
        return cyclingClub;
    }

    public void setParticipants(ArrayList<String> participants) {this.participants = participants; }

    public ArrayList<String>  getParticipants() {
        return participants;
    }



    // used by firebase
    public void setEventName(String name) { setField(NAME, name); }
    public String getEventName() { return getValue(NAME); }

    public void setMinAge(int age) { setField(MIN_AGE, age); }
    public String getMinAge() { return getValue(MIN_AGE); }

    public void setMaxAge(int age) { setField(MAX_AGE, age); }
    public String getMaxAge() { return getValue(MAX_AGE); }

    public void setPace(String pace) { setField(PACE, pace); }
    public String getPace() { return getValue(PACE); }

    public void setType(String type) { this.type = type; }
    public String getType() {return type;}
    /*public void setType(EventType type) { this.eventType = type; }

    public Task<DataSnapshot> setType(String type) {
        return DatabaseRepository.getInstance().getParentByChild("eventTypes", "name", type).addOnSuccessListener(
                dataSnapshot -> setType(dataSnapshot.getValue(EventType.class)));
    }

    public EventType getType() { return eventType; }*/

    public void setId(String id) {this.id = id;}
    public String getId() { return id;}

    public void setDifficulty(String difficulty) { setField(DIFFICULTY, difficulty); }
    public String getDifficulty() {return getValue(DIFFICULTY);}

    public void setDistance(Float distance) {setField(DISTANCE, distance); }
    public String getDistance() {return getValue(DISTANCE);}

    public String getMinSkillLevel() {return getValue(MIN_SKILL_LEVEL);}
    public void setMinSkillLevel(String minSkillLevel) {setField(MIN_SKILL_LEVEL, minSkillLevel);}

    public void setCurrentParticipants(Integer participants) {
        setField(PARTICIPANTS, participants);
    }

    public String getCurrentParticipants() {
        return getValue(PARTICIPANTS);
    }

    public void setMaxParticipants(Integer maxParticipants) {
        setField(MAX_PARTICIPANTS, maxParticipants);
    }

    public String getMaxParticipants() {
        return getValue(MAX_PARTICIPANTS);
    }

    public void setDuration(String duration) {
        setField(DURATION, duration);
    }

    public String getDuration() {
        return getValue(DURATION);
    }

    public void setFee(String fee) {
        setField(FEE, fee);
    }

    public String getFee() {
        return getValue(FEE);
    }
}
