package edu.uottawa.seg2105_final_grp12;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.databinding.ActivityEventManagementBinding;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.EventField;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;

import android.widget.Spinner;

public class EventManagementActivity extends AppCompatActivity {

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
    DatabaseReference databaseEventTypes;
    List<EventType> eventTypes;
    List<String> eventTypeNames;

    ActivityEventManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(EventManagementActivity.this, R.layout.activity_event_management);
        binding.setLifecycleOwner(this);
        binding.setEventType(new EventType());
        setContentView(binding.getRoot());

        // Firebase database
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

        eventTypes = new ArrayList<EventType>();
        eventTypeSpinner = findViewById(R.id.spinner_event_type);
        // just pull the existing event types once whenever the activity opens
        databaseEventTypes.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getResult().getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EventManagementActivity.this, android.R.layout.simple_spinner_item,
                        eventTypes.stream().map(EventType::getName).collect(Collectors.toList()));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                eventTypeSpinner.setAdapter(adapter);
            }
        });

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.setEventType(eventTypes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listViewEvents = findViewById(R.id.list_events);
        buttonAddEvent = findViewById(R.id.btn_add_event);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(EventManagementActivity.this, WelcomeActivity.class);
            startActivity(intent);

        });

        events = new ArrayList<>();
        eventsAdapter = new EventAdapter(EventManagementActivity.this, events);
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
                Toast.makeText(EventManagementActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
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

        Integer minAge;
        Integer maxAge;
        try {
            minAge = Integer.parseInt(minAgeString);
            maxAge = Integer.parseInt(maxAgeString);

            if (maxAge < minAge) {
                Toast.makeText(this, "Maximum age is lower than minimum age!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ages must be integers!", Toast.LENGTH_LONG).show();
            return;
        }

        Integer minSkillLevel = null;
        try {
            minSkillLevel = Integer.parseInt(minSkillLevelString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Skill Level must be an integer!", Toast.LENGTH_LONG).show();
            return;
        }

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


    }
    public void editEvent(Event evenToEdit){
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