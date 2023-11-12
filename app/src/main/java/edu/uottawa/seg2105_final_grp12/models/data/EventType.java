package edu.uottawa.seg2105_final_grp12.models.data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static edu.uottawa.seg2105_final_grp12.models.data.EventField.*;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventType extends HashMap<EventField, Boolean> {
    private String id;
    private String name;

    public EventType() {
        for (EventField f : EventField.values())
            put(f, false);
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
        return get(MIN_AGE);
    }

    public boolean getHasMaxAge() {
        return get(MAX_AGE);
    }

    public boolean getHasMinSkillLevel() {
        return get(MIN_SKILL_LEVEL);
    }

    public boolean getHasDifficulty() {
        return get(DIFFICULTY);
    }

    public boolean getHasPace() {
        return get(PACE);
    }

    public boolean getHasDuration() {
        return get(DURATION);
    }

    public boolean getHasDistance() {
        return get(DISTANCE);
    }

    public boolean getHasParticipants() {
        return get(PARTICIPANTS);
    }

    public boolean getHasMaxParticipants() {
        return get(MAX_PARTICIPANTS);
    }

    public boolean getHasFee() {
        return get(FEE);
    }
}
