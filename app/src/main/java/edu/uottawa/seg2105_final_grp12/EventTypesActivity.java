package edu.uottawa.seg2105_final_grp12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_types);

        listViewEventTypes = findViewById(R.id.list_event_types);
        eventTypes = new ArrayList<>();
        eventTypesAdapter = new EventTypeAdapter(EventTypesActivity.this, eventTypes);
        listViewEventTypes.setAdapter(eventTypesAdapter);

        editTextEventTypeName = findViewById(R.id.et_event_type_name);

        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());

        Button btnAddEventType = findViewById(R.id.btn_add_event_type);
        btnAddEventType.setOnClickListener(view -> addEventType());

        loadEventTypes();
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
        databaseEventTypes.child(id).setValue(eventType)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If Firebase operation was successful, update the local list and UI
                        eventTypes.add(eventType);
                        eventTypesAdapter.notifyDataSetChanged();
                        editTextEventTypeName.setText(""); // Clear the input field after successful addition
                    } else {
                        // Handle failure
                    }
                });
    }

    private void loadEventTypes() {
        // TODO: Add Firebase ValueEventListener to listen for changes, then update the UI
        databaseEventTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }
                eventTypesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
