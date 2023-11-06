package edu.uottawa.seg2105_final_grp12.models.data;

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
    private Integer participants;
    private Integer maxParticipants;

    private String fee;


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

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
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

    // turn off / on
    public boolean hasMinAge() {
        return minAge != null && minAge > 0;
    }

    public boolean hasMaxAge() {
        return maxAge != null && maxAge > 0;
    }

    public boolean hasMinSkillLevel() {
        return minSkillLevel != null && minSkillLevel > 0;
    }

    public boolean hasDifficulty() {
        return difficulty != null && !difficulty.isEmpty();
    }

    public boolean hasPace() {
        return pace != null && !pace.isEmpty();
    }

    public boolean hasDuration() {
        return duration != null && !duration.isEmpty();
    }

    public boolean hasDistance() {
        return distance != null && distance > 0;
    }

    public boolean hasParticipants() {
        return participants != null && participants > 0;
    }

    public boolean hasMaxParticipants() {
        return maxParticipants != null && maxParticipants > 0;
    }

    public boolean hasFee() {
        return fee != null && !fee.isEmpty();
    }

    public void toggleMinAge(boolean state) {
        if (state) {
            if (minAge == null || minAge == 0) minAge = 1;
        } else {
            minAge = null;
        }
    }

    public void toggleMaxAge(boolean state) {
        if (state) {
            if (maxAge == null || maxAge == 0) maxAge = 1;
        } else {
            maxAge = null;
        }
    }

    public void toggleMinSkillLevel(boolean state) {
        if (state) {
            if (minSkillLevel == null || minSkillLevel == 0) minSkillLevel = 1;
        } else {
            minSkillLevel = null;
        }
    }

    public void toggleDifficulty(boolean state) {
        if (state) {
            if (difficulty == null || difficulty.isEmpty()) difficulty = "Easy";
        } else {
            difficulty = null;
        }
    }

    public void togglePace(boolean state) {
        if (state) {
            if (pace == null || pace.isEmpty()) pace = "Normal";
        } else {
            pace = null;
        }
    }

    public void toggleDuration(boolean state) {
        if (state) {
            if (duration == null || duration.isEmpty()) duration = "1 hour";
        } else {
            duration = null;
        }
    }

    public void toggleDistance(boolean state) {
        if (state) {
            if (distance == null || distance == 0) distance = 1;
        } else {
            distance = null;
        }
    }

    public void toggleParticipants(boolean state) {
        if (state) {
            if (participants == null || participants == 0) participants = 1;
        } else {
            participants = null;
        }
    }

    public void toggleMaxParticipants(boolean state) {
        if (state) {
            if (maxParticipants == null || maxParticipants == 0) maxParticipants = 10;
        } else {
            maxParticipants = null;
        }
    }

    public void toggleFee(boolean state) {
        if (state) {
            if (fee == null || fee.isEmpty()) fee = "10";
        } else {
            fee = null;
        }
    }

}
