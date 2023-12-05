package edu.uottawa.seg2105_final_grp12.models.data;

import static edu.uottawa.seg2105_final_grp12.models.data.EventField.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.Map;

// TODO: make class abstract once event types are implemented
public class Event implements Serializable {
    //private EventType eventType;
    private HashMap<EventField, String> properties = new HashMap<>();
    // *** Firebase variables
    private String id;

    // *** Participant Requirements
    private Integer minAge;
    private Integer maxAge;
    private String minSkillLevel; //?? hard to tell if this AND difficulty are needed --> add on --> skill level based on scale of 1-10
                                                                                                        // Difficulty Easy/Medium/Hard?
    // *** Event Specifications
    private String name;
    private String difficulty;
    private String pace;
    private String duration;
    private Integer distance;
    private Integer currentParticipants;
    private Integer maxParticipants;

    private ArrayList<String> participants;
    private String fee;

    private String cyclingClub;

    private String type;

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

    public String setField(String key, String value) {
        return properties.put(EventField.fromString(key), value);
    }

    public String getValue(EventField field) {
        return properties.get(field);
    }

    public HashMap<EventField, String> getProperties() {
        return properties;
    }

    // TODO: Create a dataset that firebase can store for events

    // getters setters
    public void setEventName(String name) { setField(NAME, name); }
    public String getEventName() { return getValue(NAME); }

    public void setMinAge(int age) { setField(MIN_AGE, String.valueOf(age)); }
    public String getMinAge() { return getValue(MIN_AGE); }

    public void setMaxAge(int age) { setField(MAX_AGE, maxAge); }
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

    public String getMinSkillLevel() {return minSkillLevel;}
    public void setMinSkillLevel(String minSkillLevel) {this.minSkillLevel = minSkillLevel;}

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

    public void setParticipants(ArrayList<String> participants) {this.participants = participants; }

    public ArrayList<String>  getParticipants() {
        return participants;
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

    public void setCyclingClub(String cyclingClub) {
        this.cyclingClub = cyclingClub;
    }

    public String getCyclingClub() {
        return cyclingClub;
    }


}