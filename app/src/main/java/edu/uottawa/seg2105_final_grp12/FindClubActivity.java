package edu.uottawa.seg2105_final_grp12;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.models.data.ClubSearchAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.data.UserAdapter;


public class FindClubActivity extends AppCompatActivity {

    DatabaseReference databaseEvents;
    DatabaseReference databaseEventTypes;
    List<EventType> eventTypes;
    Spinner eventTypeSpinner;
    DatabaseReference databaseUsers;
    List<User> clubs;
    ClubSearchAdapter clubAdapter;
    ListView listViewClubs;

    //TODO add search button that trims List<User> clubs to fit desired variables
    //TODO clicking on a club list entry should open the events they are holding
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_clubs);
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(FindClubActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });

        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");

        eventTypes = new ArrayList<EventType>();
        eventTypeSpinner = findViewById(R.id.spinner_browse_event_types);
        // just pull the existing event types once whenever the activity opens
        databaseEventTypes.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getResult().getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(FindClubActivity.this, android.R.layout.simple_spinner_item);

                adapter.add("Any");
                adapter.addAll(eventTypes.stream().map(EventType::getName).collect(Collectors.toList()));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                eventTypeSpinner.setAdapter(adapter);
            }
        });

        EditText etEventName = findViewById(R.id.eventNameField);
        EditText etClubName = findViewById(R.id.clubNameField);

        listViewClubs = findViewById(R.id.searchResultsListView);
        clubs = new ArrayList<>();
        clubAdapter = new ClubSearchAdapter(FindClubActivity.this, clubs);
        listViewClubs.setAdapter(clubAdapter);

        updateClubList(null, null, null);
        clubAdapter.notifyDataSetChanged();

        Button btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(view -> {
            String eventName = etEventName.getText().toString().trim();
            String clubName = etClubName.getText().toString().trim();
            String eventType = eventTypeSpinner.getSelectedItem().toString();

            if (eventType.equals("Any")) {
                eventType = null;
            }

            if (eventName.isEmpty()) {
                eventName = null;
            }

            if (clubName.isEmpty()) {
                clubName = null;
            }

            updateClubList(eventType, eventName, clubName);
            clubAdapter.notifyDataSetChanged();

        });

        listViewClubs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                User chosenClub = clubs.get(i); // User associated with the club at position i of listView
                String clubId = chosenClub.getUid();

                Intent intent = new Intent(FindClubActivity.this, ClubInfoActivity.class);
                intent.putExtra("clubId", clubId);
                startActivity(intent);

                return true; // should be irrelevant, but a boolean return is required by the method
            }
        });
    }

    // If null is passed as any of the input arguments, they will ignored
    // while refining the club list to the search parameters
    public void updateClubList(String eventType, String eventName, String clubName) {
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubs.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user.getRole().equals("Cycling Club")) {

                        // if an eventType (other than "Any") was chosen, it must exist in user.eventTypes
                        if (eventType != null && (user.getEventTypes() == null
                                || !user.getEventTypes().contains(eventType))) {
                            continue;
                        }
                        // If a clubName was entered to the search, this username must contain it
                        if (clubName != null && !user.getUsername().contains(clubName)) {
                            continue;
                        }

                        //TODO Event name search
                        clubs.add(user);
                        clubAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FindClubActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
