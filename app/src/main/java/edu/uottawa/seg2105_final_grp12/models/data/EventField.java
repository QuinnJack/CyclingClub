package edu.uottawa.seg2105_final_grp12.models.data;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.uottawa.seg2105_final_grp12.App;
import edu.uottawa.seg2105_final_grp12.R;

public enum EventField {
    NAME(R.id.field_event_name, R.string.hint_event_name),
    MIN_AGE(R.id.field_min_age, R.string.label_min_age, "minAge"),
    MAX_AGE(R.id.field_max_age, R.string.label_max_age, "maxAge"),
    MIN_SKILL_LEVEL(R.id.field_min_skill_level, R.string.label_min_skill_level, "minSkillLevel"),
    DIFFICULTY(R.id.field_difficulty, R.string.label_difficulty),
    PACE(R.id.field_pace, R.string.label_pace),
    DURATION(R.id.field_duration, R.string.label_duration),
    DISTANCE(R.id.field_distance, R.string.label_distance),
    PARTICIPANTS(R.id.field_participants, R.string.label_participants),
    MAX_PARTICIPANTS(R.id.field_max_participants, R.string.label_max_participants, "maxParticipants"),
    FEE(R.id.field_fee, R.string.label_fee);

    private static Map<String, EventField> fieldMap = Arrays.stream(EventField.values())
            .flatMap(f -> Stream.of(String.valueOf(f.id), f.label, f.key).map(s -> new AbstractMap.SimpleImmutableEntry<>(s, f)))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

    private final int id;
    private final String label;
    private final String key; // firebase path
    private final Converter<String, String> pathFormat = CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
    public static EventField fromId(int id) {
        return fieldMap.get(String.valueOf(id));
    }

    public static EventField fromString(String label) {
        return fieldMap.get(label);
    }

    public static EventField fromString(int id) {
        return fromString(App.getContext().getResources().getString(id));
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, EventType eventType) {
        setVisibility(view, Boolean.TRUE.equals(eventType.hasField(fromId(view.getId()))));
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    EventField(int id, int label) {
        this.id = id;
        this.label = App.getContext().getString(label);
        key = pathFormat.convert(this.label.replace(' ', '_').toLowerCase());
    }

    EventField(int id, int label, String key) {
        this.id = id;
        this.label = App.getContext().getString(label);
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getKey() {
        return key;
    }
}