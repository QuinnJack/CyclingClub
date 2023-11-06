package edu.uottawa.seg2105_final_grp12.models.data;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.Switch;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import edu.uottawa.seg2105_final_grp12.R;

public class EventTypeAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private List<EventType> eventTypes;

    public EventTypeAdapter(Activity context, List<EventType> eventTypes) {
        super(context, R.layout.layout_event_type_list);
        this.context = context;
        this.eventTypes = eventTypes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_event_type_list, null, true);

        Switch switchMinAge = listViewItem.findViewById(R.id.switch_min_age);
        Switch switchMaxAge = listViewItem.findViewById(R.id.switch_max_age);
        Switch switchMinSkillLevel = listViewItem.findViewById(R.id.switch_min_skill_level);
        Switch switchDifficulty = listViewItem.findViewById(R.id.switch_difficulty);
        Switch switchPace = listViewItem.findViewById(R.id.switch_pace);
        Switch switchDuration = listViewItem.findViewById(R.id.switch_duration);
        Switch switchDistance = listViewItem.findViewById(R.id.switch_distance);
        Switch switchParticipants = listViewItem.findViewById(R.id.switch_participants);
        Switch switchMaxParticipants = listViewItem.findViewById(R.id.switch_max_participants);
        Switch switchFee = listViewItem.findViewById(R.id.switch_fee);
        Button btnSave = listViewItem.findViewById(R.id.btnSave);

        EventType eventType = eventTypes.get(position);


        switchMinAge.setChecked(eventType.hasMinAge());
        switchMaxAge.setChecked(eventType.hasMaxAge());
        switchMinSkillLevel.setChecked(eventType.hasMinSkillLevel());
        switchDifficulty.setChecked(eventType.hasDifficulty());
        switchPace.setChecked(eventType.hasPace());
        switchDuration.setChecked(eventType.hasDuration());
        switchDistance.setChecked(eventType.hasDistance());
        switchParticipants.setChecked(eventType.hasParticipants());
        switchMaxParticipants.setChecked(eventType.hasMaxParticipants());
        switchFee.setChecked(eventType.hasFee());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventType.setHasMinAge(switchMinAge.isChecked());
                eventType.setHasMaxAge(switchMaxAge.isChecked());
                eventType.setHasMinSkillLevel(switchMinSkillLevel.isChecked());
                eventType.setHasDifficulty(switchDifficulty.isChecked());
                eventType.setHasPace(switchPace.isChecked());
                eventType.setHasDuration(switchDuration.isChecked());
                eventType.setHasDistance(switchDistance.isChecked());
                eventType.setHasParticipants(switchParticipants.isChecked());
                eventType.setHasMaxParticipants(switchMaxParticipants.isChecked());
                eventType.setHasFee(switchFee.isChecked());
                // todo: update event type in firebase database
            }
        });

        return listViewItem;
    }
}
