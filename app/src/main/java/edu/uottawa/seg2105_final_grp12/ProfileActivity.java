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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import edu.uottawa.seg2105_final_grp12.models.data.User;

public class ProfileActivity extends Activity {

    private DatabaseReference databaseEventTypes;

    private FirebaseUser currentUser;

    private ListView listViewEventTypes;
    private EventTypeAdapter eventTypesAdapter;
    private List<EventType> eventTypes;
    Spinner eventTypeSpinner;
    private Spinner spinnerEventTypes;
    private ArrayAdapter<String> eventTypesArrayAdapter;
    private List<String> eventTypeNames;

    private EditText socialMediaInput, nameInput, phoneInput;
    private ListView listEvents;
    private Button btnSave, btnBack, btnAdd, btnDel;
    private ListView userEventTypesListView;
    private ArrayAdapter<String> userEventTypesAdapter;
    private DatabaseReference databaseUser;

    private List<String> userEventTypeNames;


    // TODO: onClick listeners for Add and Delete buttons, methods and onValue change listeners
    // TODO: add and deleting event types updates fb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        socialMediaInput = findViewById(R.id.socialMediaInput);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        userEventTypesListView = findViewById(R.id.user_event_types);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btn_back);
        spinnerEventTypes = findViewById(R.id.spinner_event_types);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDelete);


        userEventTypeNames = new ArrayList<>();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(userId);
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getSocialMediaLink() != null) {
                                socialMediaInput.setText(user.getSocialMediaLink());
                            }
                            if (user.getMainContactName() != null) {
                                nameInput.setText(user.getMainContactName());
                            }
                            if (user.getPhoneNumber() != null) {
                                phoneInput.setText(user.getPhoneNumber());
                            }

                            // Load user's event types
                            if (user.getEventTypes() != null) {
                                userEventTypeNames.clear();
                                userEventTypeNames.addAll(user.getEventTypes());
                                userEventTypesAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedEventType = spinnerEventTypes.getSelectedItem().toString();
                if (!userEventTypeNames.contains(selectedEventType)) {
                    userEventTypeNames.add(selectedEventType);
                    userEventTypesAdapter.notifyDataSetChanged();
                    databaseUser.child("eventTypes").setValue(userEventTypeNames);
                } else {
                    Toast.makeText(ProfileActivity.this, "Event type already added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedEventType = spinnerEventTypes.getSelectedItem().toString();
                if (userEventTypeNames.contains(selectedEventType)) {
                    userEventTypeNames.remove(selectedEventType);
                    userEventTypesAdapter.notifyDataSetChanged();
                    databaseUser.child("eventTypes").setValue(userEventTypeNames);
                } else {
                    Toast.makeText(ProfileActivity.this, "Event type not in your list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userEventTypeNames = new ArrayList<>();
        userEventTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userEventTypeNames);
        userEventTypesListView.setAdapter(userEventTypesAdapter);

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

        String socialMediaLink = socialMediaInput.getText().toString().trim();
        String mainContactName = nameInput.getText().toString().trim();
        String phoneNumber = phoneInput.getText().toString().trim();

        boolean check =validateInfo(mainContactName, socialMediaLink, phoneNumber);
        if(check==true){
            Toast.makeText(getApplicationContext(), "Data is Valid.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Check information again.", Toast.LENGTH_SHORT).show();
        }

        databaseUser.child("socialMediaLink").setValue(socialMediaLink);
        databaseUser.child("mainContactName").setValue(mainContactName);
        databaseUser.child("phoneNumber").setValue(phoneNumber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update profile.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private Boolean validateInfo(String mainContactName, String socialMediaLink, String phoneNumber) {
        if (mainContactName.length() == 0) {
            nameInput.requestFocus();
            nameInput.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!mainContactName.matches("[a-zA-Z]+")) {
            nameInput.requestFocus();
            nameInput.setError("ENTER ONLY ALPHABETICAL CHARACTERS");
            return false;
        } else if (socialMediaLink.length() == 0) {
            socialMediaInput.requestFocus();
            socialMediaInput.setError("FIELD CANNOT BE EMPTY");
        } else if (!socialMediaLink.matches("[https://]+[a-zA-Z0-9._-]+.[com]+")) {
            socialMediaInput.requestFocus();
            socialMediaInput.setError("Correct format: https://xxxxxxxxxx.com");
            return false;

        } else if (phoneNumber.length() == 0) {
            phoneInput.requestFocus();
            nameInput.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!phoneNumber.matches("^[+][0-9]{10,12}$")) {
            phoneInput.requestFocus();
            phoneInput.setError("Correct format: +1xxxxxxxxxx");
        }
        else{return true;}
        return null;
    }
}

