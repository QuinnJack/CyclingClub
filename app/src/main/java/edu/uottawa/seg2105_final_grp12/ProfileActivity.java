package edu.uottawa.seg2105_final_grp12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.models.data.EventTypeAdapter;

public class ProfileActivity extends Activity {

    private DatabaseReference databaseEventTypes;
    private ListView listViewEventTypes;
    private EventTypeAdapter eventTypesAdapter;
    private List<EventType> eventTypes;
    Spinner eventTypeSpinner;
    private Spinner spinnerEventTypes;
    private ArrayAdapter<String> eventTypesArrayAdapter;
    private List<String> eventTypeNames;

    private EditText socialMediaInput, nameInput, phoneInput;
    private ListView listEvents;
    private Button btnSave, btnBack;

    // TODO: SaveProfile() method updates fb database.
    // TODO: onClick listeners for Add and Delete buttons, methods and onValue change listeners
    // TODO: add and deleting event types updates fb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        socialMediaInput = findViewById(R.id.socialMediaInput);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        spinnerEventTypes = findViewById(R.id.spinner_event_types);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btn_back);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });


        spinnerEventTypes = findViewById(R.id.spinner_event_types);
        eventTypeNames = new ArrayList<>();
        eventTypesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypeNames);
        eventTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventTypes.setAdapter(eventTypesArrayAdapter);

        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

        databaseEventTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypeNames.clear();
                for (DataSnapshot eventTypeSnapshot : dataSnapshot.getChildren()) {
                    EventType eventType = eventTypeSnapshot.getValue(EventType.class);
                    eventTypeNames.add(eventType.getName());
                }
                eventTypesArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void saveProfile() {

        if (socialMediaInput.getText().toString().isEmpty() || phoneInput.getText().toString().isEmpty()) {
            Toast.makeText(ProfileActivity.this, "These fields are mandatory!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: Save the profile information, keep showing it in the fields?? 
    }

}
