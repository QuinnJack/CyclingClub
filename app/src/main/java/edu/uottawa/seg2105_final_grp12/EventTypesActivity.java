package edu.uottawa.seg2105_final_grp12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;

public class EventTypesActivity extends AppCompatActivity {

    EditText editTextEventName;
    EditText editTextMinAge;
    EditText editTextMaxAge;
    EditText editTextPace;
    EditText editTextMinSkillLevel;
    EditText editTextDifficulty;
    Button buttonAddEvent;
    ListView listViewEvents;
    DatabaseReference databaseEvents;
    List<Event> events;
    EventAdapter eventsAdapter;
    Spinner eventTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        // Firebase database
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        editTextEventName = findViewById(R.id.et_event_name);
        editTextMinAge = findViewById(R.id.et_min_age);
        editTextMaxAge = findViewById(R.id.et_max_age);
        editTextPace = findViewById(R.id.et_pace);
        editTextMinSkillLevel = findViewById(R.id.et_min_skill_level);
        editTextDifficulty = findViewById(R.id.et_difficulty);
        listViewEvents = findViewById(R.id.list_events);
        buttonAddEvent = findViewById(R.id.btn_add_event);

        eventTypeSpinner = findViewById(R.id.spinner_event_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(EventTypesActivity.this, WelcomeActivity.class);
            startActivity(intent);

        });


        events = new ArrayList<>();
        eventsAdapter = new EventAdapter(EventTypesActivity.this, events);
        listViewEvents.setAdapter(eventsAdapter);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addEvent(); }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    events.add(event);
                }
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EventTypesActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addEvent() {

        eventTypeSpinner = findViewById(R.id.spinner_event_type);
        String type = eventTypeSpinner.getSelectedItem().toString();

        String name = editTextEventName.getText().toString().trim();
        String minAgeString = editTextMinAge.getText().toString().trim();
        String maxAgeString = editTextMaxAge.getText().toString().trim();
        String pace = editTextPace.getText().toString().trim();
        String minSkillLevelString = editTextMinSkillLevel.getText().toString().trim();
        String difficulty = editTextDifficulty.getText().toString().trim();
        if (name.isEmpty() || minAgeString.isEmpty() || maxAgeString.isEmpty() || pace.isEmpty() || minSkillLevelString.isEmpty() || difficulty.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        Integer minAge = Integer.parseInt(minAgeString);
        Integer maxAge = Integer.parseInt(maxAgeString);
        Integer minSkillLevel = Integer.parseInt(minSkillLevelString);
        String id = databaseEvents.push().getKey();

        Event event = new Event(id);
        event.setEventName(name);
        event.setMinAge(minAge);
        event.setMaxAge(maxAge);
        event.setPace(pace);
        event.setType(type);
        event.setMinSkillLevel(minSkillLevel);
        event.setDifficulty(difficulty);

        databaseEvents.child(id).setValue(event);

        editTextEventName.setText("");
        editTextMinAge.setText("");
        editTextMaxAge.setText("");
        editTextPace.setText("");
        editTextMinSkillLevel.setText("");
        editTextDifficulty.setText("");

        //Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();

    }
    public void editEvent(Event evenToEdit){ // REMOVE COMMENT AFTER
        if(evenToEdit != null){

        }


    }
    public void deleteEvent(Event eventToDelete) {
        if (eventToDelete.getId() != null) {
            databaseEvents.child(eventToDelete.getId()).removeValue();
            Toast.makeText(this, "Event deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error: Unable to delete event", Toast.LENGTH_LONG).show();
        }
    }



}