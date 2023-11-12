package edu.uottawa.seg2105_final_grp12.models.data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.R;

public enum EventField {
    MIN_AGE(R.id.field_min_age),
    MAX_AGE(R.id.field_max_age),
    MIN_SKILL_LEVEL(R.id.field_min_skill_level),
    DIFFICULTY(R.id.field_difficulty),
    PACE(R.id.field_pace),
    DURATION(R.id.field_duration),
    DISTANCE(R.id.field_distance),
    PARTICIPANTS(R.id.field_participants),
    MAX_PARTICIPANTS(R.id.field_max_participants),
    FEE(R.id.field_fee);

    private static Map<Integer, EventField> idMap = Arrays.stream(EventField.values()).collect(Collectors.toMap(f -> f.id, f -> f));

    private int id;
    EventField(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    static EventField fromId(int id) {
        return idMap.get(id);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, EventType eventType) {
        view.setVisibility(eventType.get(fromId(view.getId())) ? View.VISIBLE : View.GONE);
    }
}