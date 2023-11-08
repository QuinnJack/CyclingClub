package edu.uottawa.seg2105_final_grp12.models.data;

import android.os.Looper;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.Switch;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.List;
import android.os.Handler;

import edu.uottawa.seg2105_final_grp12.R;

public class EventTypeAdapter extends ArrayAdapter<EventType> {
    private Activity context;
    private List<EventType> eventTypes;

    private DatabaseReference databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

    public EventTypeAdapter(Activity context, List<EventType> eventTypes) {
        super(context, R.layout.layout_event_type_list, eventTypes); // Pass eventTypes to the superclass constructor
        this.context = context;
        this.eventTypes = eventTypes;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) { // 'final' is important here
        View listViewItem = convertView;
        if (listViewItem == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            listViewItem = inflater.inflate(R.layout.layout_event_type_list, parent, false);
        }


        TextView textEventTypeLabel = listViewItem.findViewById(R.id.tvEventTypeLabel);
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
        Button btnDelete = listViewItem.findViewById(R.id.btnDelete);


        EventType eventType = eventTypes.get(position);

        textEventTypeLabel.setText(eventType.getName());
        switchMinAge.setChecked(eventType.getHasMinAge());
        switchMaxAge.setChecked(eventType.getHasMaxAge());
        switchMinSkillLevel.setChecked(eventType.getHasMinSkillLevel());
        switchDifficulty.setChecked(eventType.getHasDifficulty());
        switchPace.setChecked(eventType.getHasPace());
        switchDuration.setChecked(eventType.getHasDuration());
        switchDistance.setChecked(eventType.getHasDistance());
        switchParticipants.setChecked(eventType.getHasParticipants());
        switchMaxParticipants.setChecked(eventType.getHasMaxParticipants());
        switchFee.setChecked(eventType.getHasFee());

        btnSave.setOnClickListener(v -> {
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

            // Update Firebase database
            databaseEventTypes.child(eventType.getId()).setValue(eventType)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this.getContext(), "EventType Edited", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    });

            notifyDataSetChanged(); // Update the ListView
        });

        btnDelete.setOnClickListener(v -> {
            String idToDelete = eventType.getId();

            databaseEventTypes.child(idToDelete).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Do not manually update the local list. Let the ValueEventListener handle this.
                        Toast.makeText(context, "EventType deleted successfully.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to delete EventType", Toast.LENGTH_SHORT).show();
                    });
        });

        return listViewItem;
    }

    @Override
    public int getCount() {
        return eventTypes.size();
    }


}
