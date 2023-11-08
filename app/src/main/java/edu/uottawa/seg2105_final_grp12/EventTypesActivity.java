package edu.uottawa.seg2105_final_grp12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.models.data.EventTypeAdapter;

public class EventTypesActivity extends AppCompatActivity {

    ListView listViewEventTypes;
    DatabaseReference databaseEventTypes;
    List<EventType> eventTypes;
    EventTypeAdapter eventTypesAdapter;
    EditText editTextEventTypeName;

    Switch switchMinAge;
    Switch switchMaxAge;
    Switch switchMinSkillLevel;
    Switch switchDifficulty;
    Switch switchPace;
    Switch switchDuration;
    Switch switchDistance;
    Switch switchParticipants;
    Switch switchMaxParticipants;
    Switch switchFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_types);



        listViewEventTypes = findViewById(R.id.list_event_types);
        eventTypes = new ArrayList<>();
        eventTypesAdapter = new EventTypeAdapter(EventTypesActivity.this, eventTypes);
        listViewEventTypes.setAdapter(eventTypesAdapter);


        editTextEventTypeName = findViewById(R.id.et_event_type_name);

        switchMinAge = findViewById(R.id.switch_min_age);
        switchMaxAge = findViewById(R.id.switch_max_age);
        switchMinSkillLevel = findViewById(R.id.switch_min_skill_level);
        switchDifficulty = findViewById(R.id.switch_difficulty);
        switchPace = findViewById(R.id.switch_pace);
        switchDuration = findViewById(R.id.switch_duration);
        switchDistance = findViewById(R.id.switch_distance);
        switchParticipants = findViewById(R.id.switch_participants);
        switchMaxParticipants = findViewById(R.id.switch_max_participants);
        switchFee = findViewById(R.id.switch_fee);

        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());

        Button btnAddEventType = findViewById(R.id.btn_add_event_type);
        btnAddEventType.setOnClickListener(view -> addEventType());

    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseEventTypes.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    if (eventType != null) {
                        eventTypes.add(eventType);
                    }
                }
                eventTypesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EventTypesActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addEventType() {

        String name = editTextEventTypeName.getText().toString().trim();
        if (name.isEmpty()) {
            editTextEventTypeName.setError("Event type name is required.");
            editTextEventTypeName.requestFocus();
            return;
        }

        String id = databaseEventTypes.push().getKey();
        EventType eventType = new EventType(id, name);


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

        assert id != null;
        databaseEventTypes.child(id).setValue(eventType)
                .addOnSuccessListener(aVoid -> {
                    runOnUiThread(() -> eventTypesAdapter.notifyDataSetChanged());
                    Toast.makeText(this, "EventType added successfully.", Toast.LENGTH_SHORT).show();
                    editTextEventTypeName.setText("");
                    switchMinAge.setChecked(false);
                    switchMaxAge.setChecked(false);
                    switchMinSkillLevel.setChecked(false);
                    switchDifficulty.setChecked(false);
                    switchPace.setChecked(false);
                    switchDuration.setChecked(false);
                    switchDistance.setChecked(false);
                    switchParticipants.setChecked(false);
                    switchMaxParticipants.setChecked(false);
                    switchFee.setChecked(false);

                })
                .addOnFailureListener(e -> {
                    Log.e("EventTypesActivity", "Failed to add EventType", e);
                    Toast.makeText(this, "Failed to add EventType", Toast.LENGTH_SHORT).show();
                });


    }



}
