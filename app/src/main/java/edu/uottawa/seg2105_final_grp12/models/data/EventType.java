package edu.uottawa.seg2105_final_grp12.models.data;

import static edu.uottawa.seg2105_final_grp12.models.data.EventField.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class EventType implements Serializable {
    private String id;
    private String name;
    private final HashSet<EventField> fields = new HashSet<>();
    
    public EventType() {
        // Default constructor required for calls to DataSnapshot.getValue(EventType.class)
    }

    public EventType(String id, String name) {
        this();
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
        put(MIN_AGE, hasMinAge);
    }

    public void setHasMaxAge(boolean hasMaxAge) {
        put(MAX_AGE, hasMaxAge);
    }

    public void setHasMinSkillLevel(boolean hasMinSkillLevel) {
        put(MIN_SKILL_LEVEL, hasMinSkillLevel);
    }

    public void setHasDifficulty(boolean hasDifficulty) {
        put(DIFFICULTY, hasDifficulty);
    }

    public void setHasPace(boolean hasPace) {
        put(PACE, hasPace);
    }

    public void setHasDuration(boolean hasDuration) {
        put(DURATION, hasDuration);
    }

    public void setHasDistance(boolean hasDistance) {
        put(DISTANCE, hasDistance);
    }

    public void setHasParticipants(boolean hasParticipants) {
        put(PARTICIPANTS, hasParticipants);
    }

    public void setHasMaxParticipants(boolean hasMaxParticipants) {
        put(MAX_PARTICIPANTS, hasMaxParticipants);
    }

    public void setHasFee(boolean hasFee) {
        put(FEE, hasFee);
    }

    public boolean getHasMinAge() {
        return fields.contains(MIN_AGE);
    }

    public boolean getHasMaxAge() {
        return fields.contains(MAX_AGE);
    }

    public boolean getHasMinSkillLevel() {
        return fields.contains(MIN_SKILL_LEVEL);
    }

    public boolean getHasDifficulty() {
        return fields.contains(DIFFICULTY);
    }

    public boolean getHasPace() {
        return fields.contains(PACE);
    }

    public boolean getHasDuration() {
        return fields.contains(DURATION);
    }

    public boolean getHasDistance() {
        return fields.contains(DISTANCE);
    }

    public boolean getHasParticipants() {
        return fields.contains(PARTICIPANTS);
    }

    public boolean getHasMaxParticipants() {
        return fields.contains(MAX_PARTICIPANTS);
    }

    public boolean getHasFee() {
        return fields.contains(FEE);
    }

    public boolean hasField(EventField field) {
        return fields.contains(field);
    }

    public void put(EventField field, boolean enabled) {
        if (enabled)
            fields.add(field);
        else
            fields.remove(field);
    }
}
