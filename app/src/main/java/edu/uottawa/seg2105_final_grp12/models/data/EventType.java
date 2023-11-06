package edu.uottawa.seg2105_final_grp12.models.data;

public class EventType {
    private String id;
    private String name;
    private boolean hasMinAge;
    private boolean hasMaxAge;
    private boolean hasMinSkillLevel;
    private boolean hasDifficulty;
    private boolean hasPace;
    private boolean hasDuration;
    private boolean hasDistance;
    private boolean hasParticipants;
    private boolean hasMaxParticipants;
    private boolean hasFee;

    public EventType() {
        // Default constructor required for calls to DataSnapshot.getValue(EventType.class)
    }

    public EventType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHasMinAge(boolean hasMinAge) {
        this.hasMinAge = hasMinAge;
    }

    public void setHasMaxAge(boolean hasMaxAge) {
        this.hasMaxAge = hasMaxAge;
    }

    public void setHasMinSkillLevel(boolean hasMinSkillLevel) {
        this.hasMinSkillLevel = hasMinSkillLevel;
    }

    public void setHasDifficulty(boolean hasDifficulty) {
        this.hasDifficulty = hasDifficulty;
    }

    public void setHasPace(boolean hasPace) {
        this.hasPace = hasPace;
    }

    public void setHasDuration(boolean hasDuration) {
        this.hasDuration = hasDuration;
    }

    public void setHasDistance(boolean hasDistance) {
        this.hasDistance = hasDistance;
    }

    public void setHasParticipants(boolean hasParticipants) {
        this.hasParticipants = hasParticipants;
    }

    public void setHasMaxParticipants(boolean hasMaxParticipants) {
        this.hasMaxParticipants = hasMaxParticipants;
    }

    public void setHasFee(boolean hasFee) {
        this.hasFee = hasFee;
    }

    public boolean hasMinAge() {
        return hasMinAge;
    }

    public boolean hasMaxAge() {
        return hasMaxAge;
    }

    public boolean hasMinSkillLevel() {
        return hasMinSkillLevel;
    }

    public boolean hasDifficulty() {
        return hasDifficulty;
    }

    public boolean hasPace() {
        return hasPace;
    }

    public boolean hasDuration() {
        return hasDuration;
    }

    public boolean hasDistance() {
        return hasDistance;
    }

    public boolean hasParticipants() {
        return hasParticipants;
    }

    public boolean hasMaxParticipants() {
        return hasMaxParticipants;
    }

    public boolean hasFee() {
        return hasFee;
    }

}
