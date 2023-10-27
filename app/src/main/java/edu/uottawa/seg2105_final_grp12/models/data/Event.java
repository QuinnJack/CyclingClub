package edu.uottawa.seg2105_final_grp12.models.data;

public abstract class Event {

    // *** Participant Requirements
    private int minAge;
    private int maxAge;
    private int minSkillLevel; //?? hard to tell if this AND difficulty are needed

    // *** Event Specifications
    private String difficulty;
    private String pace;
    private int distance;

    public Event() {

    }

    // TODO: Create a dataset that firebase can store for events
    public void setMinAge(int age) {
        minAge = age;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMaxAge(int age) {
        maxAge = age;
    }

    public int getMaxAge() {
        return minAge;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getPace() {
        return pace;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
