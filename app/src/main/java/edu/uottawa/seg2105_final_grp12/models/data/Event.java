package edu.uottawa.seg2105_final_grp12.models.data;

import java.util.ArrayList;

// TODO: make class abstract once event types are implemented
public class Event {

    // *** Firebase variables
    private String id;

    // *** Participant Requirements
    private Integer minAge;
    private Integer maxAge;
    private Integer minSkillLevel; //?? hard to tell if this AND difficulty are needed --> add on --> skill level based on scale of 1-10
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

    // TODO: Create a dataset that firebase can store for events

    // getters setters
    public void setEventName(String name) { this.name = name; }
    public String getEventName() { return name; }

    public void setMinAge(int age) { minAge = age; }
    public Integer getMinAge() { return minAge; }

    public void setMaxAge(int age) { maxAge = age; }
    public Integer getMaxAge() { return maxAge; }


    public void setPace(String pace) { this.pace = pace; }
    public String getPace() { return pace; }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setId(String id) {this.id = id;}
    public String getId() { return id;}

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getDifficulty() {return difficulty;}

    public void setDistance(Integer distance) {this.distance = distance;}
    public Integer getDistance() {return distance;}

    public Integer getMinSkillLevel() {return minSkillLevel;}
    public void setMinSkillLevel(int minSkillLevel) {this.minSkillLevel = minSkillLevel;}

    public void setCurrentParticipants(Integer participants) {
        this.currentParticipants = participants;
    }

    public Integer getCurrentParticipants() {
        return currentParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setParticipants(ArrayList<String> participants) {this.participants = participants; }

    public ArrayList<String>  getParticipants() {
        return participants;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setCyclingClub(String cyclingClub) {
        this.cyclingClub = cyclingClub;
    }

    public String getCyclingClub() {
        return cyclingClub;
    }


}
