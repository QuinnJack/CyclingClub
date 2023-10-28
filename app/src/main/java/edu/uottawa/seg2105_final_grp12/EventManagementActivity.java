package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;

public class EventManagementActivity extends AppCompatActivity {

    EditText editTextEventName;
    EditText editTextMinAge;
    EditText editTextMaxAge;
    EditText editTextPace;
    Button buttonAddEvent;
    ListView listViewEvents;

    DatabaseReference databaseEvents;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        // Firebase database
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        // View elements
        editTextEventName = (EditText) findViewById(R.id.et_event_name);
        editTextMinAge = (EditText) findViewById(R.id.et_min_age);
        editTextMaxAge = (EditText) findViewById(R.id.et_max_age);
        editTextPace = (EditText) findViewById(R.id.et_pace);
        listViewEvents = (ListView) findViewById(R.id.list_events);
        buttonAddEvent = (Button) findViewById(R.id.btn_add_event);

        events = new ArrayList<>();

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addEvent(); }
        });
    }

    protected void onStart() {
        super.onStart();

        databaseEvents.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    events.add(event);

                    EventAdapter eventsAdapter = new EventAdapter(EventManagementActivity.this, events);
                    listViewEvents.setAdapter(eventsAdapter);
                }
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvent() {
        String name = editTextEventName.getText().toString().trim();
        Integer minAge = Integer.parseInt(String.valueOf(editTextMinAge.getText().toString()));
        Integer maxAge = Integer.parseInt(String.valueOf(editTextMaxAge.getText().toString()));
        String pace = editTextPace.getText().toString().trim();

        String id = databaseEvents.push().getKey();

        Event event = new Event(id);
        event.setEventName(name);
        event.setMinAge(minAge);
        event.setMaxAge(maxAge);
        event.setPace(pace);

        databaseEvents.child(id).setValue(event);

        editTextEventName.setText("");
        editTextMinAge.setText("");
        editTextMaxAge.setText("");
        editTextPace.setText("");

        //Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();

    }
}