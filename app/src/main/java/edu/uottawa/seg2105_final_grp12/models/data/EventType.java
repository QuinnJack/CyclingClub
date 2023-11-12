package edu.uottawa.seg2105_final_grp12.models.data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean getHasMinAge() {
        return hasMinAge;
    }

    public boolean getHasMaxAge() {
        return hasMaxAge;
    }

    public boolean getHasMinSkillLevel() {
        return hasMinSkillLevel;
    }

    public boolean getHasDifficulty() {
        return hasDifficulty;
    }

    public boolean getHasPace() {
        return hasPace;
    }

    public boolean getHasDuration() {
        return hasDuration;
    }

    public boolean getHasDistance() {
        return hasDistance;
    }

    public boolean getHasParticipants() {
        return hasParticipants;
    }

    public boolean getHasMaxParticipants() {
        return hasMaxParticipants;
    }

    public boolean getHasFee() {
        return hasFee;
    }

    public List<Integer> getFieldStyles(Context context) {
        // temporary workaround to convert to a list of style resources
        return Arrays.stream(EventType.class.getDeclaredFields()).filter(f -> {
                    try {
                        return f.getType() == boolean.class && (boolean) f.get(this);
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                })
                .map(f -> context.getResources().getIdentifier(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                        f.getName().substring(3)), "style", context.getPackageName())).collect(Collectors.toList());
    }
}
