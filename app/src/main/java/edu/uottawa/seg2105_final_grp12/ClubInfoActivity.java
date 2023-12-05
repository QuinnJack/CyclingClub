package edu.uottawa.seg2105_final_grp12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uottawa.seg2105_final_grp12.models.data.ClubEventsAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.EventField;
import edu.uottawa.seg2105_final_grp12.models.data.User;

public class ClubInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_info);

        Intent creationIntent = getIntent(); // intent that started this activity
        String clubId = creationIntent.getStringExtra("clubId");

        // Getting the selected club from the database
        DatabaseReference clubAccount = FirebaseDatabase.getInstance().getReference("users").child(clubId);

        // Basic club info TextViews
        TextView tvClubName = findViewById(R.id.tv_club_name);
        TextView tvSocialLink = findViewById(R.id.tv_social_link);
        TextView tvClubContact = findViewById(R.id.tv_main_contact);
        TextView tvPhoneNumber = findViewById(R.id.tv_phone_number);
        ImageView clubLogo = findViewById(R.id.image_view_logo);

        clubAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User club = dataSnapshot.getValue(User.class);
                    if (club != null) {
                        if (club.getUsername() != null) {
                            tvClubName.setText(club.getUsername());
                        }
                        if (club.getSocialMediaLink() != null) {
                            tvSocialLink.setText(club.getSocialMediaLink());
                        }
                        if (club.getMainContactName() != null) {
                            tvClubContact.setText(club.getMainContactName());
                        }
                        if (club.getPhoneNumber() != null) {
                            tvPhoneNumber.setText(club.getPhoneNumber());
                        }

                        if (club.getLogo() != null) {
                            int resourceId = getResources().getIdentifier(club.getLogo(),
                                    "drawable", getPackageName());
                            clubLogo.setImageResource(resourceId);
                        }
                        /*
                        // Load user's event types
                        if (user.getEventTypes() != null) {
                            userEventTypeNames.clear();
                            userEventTypeNames.addAll(user.getEventTypes());
                            userEventTypesAdapter.notifyDataSetChanged();
                        }

                         */
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ClubInfoActivity.this, FindClubActivity.class);
            startActivity(intent);
        });

        ListView listViewEvents = findViewById(R.id.list_view_events);
        List<Event> events = new ArrayList<>();

        DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference("events");
        eventDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                events.clear();

                for (DataSnapshot postSnapshot : task.getResult().getChildren()) {
                    Event event = new Event();
                    Map<String, String> values = postSnapshot.getValue(new GenericTypeIndicator<Map<String, String>>() {
                    });
                    //.forEach((key, value) -> event.setField(EventField.fromString(key), value));

                    String hostId = values.remove("clubId");

                    if (hostId == null || !hostId.equals(clubId)) {
                        continue;
                    }
                    event.setId(values.remove("id"));
                    event.setType(values.remove("type"));
                    values.entrySet().stream().filter(e -> EventField.fromString(e.getKey()) != null)
                            .forEach(e -> event.setField(EventField.fromString(e.getKey()), e.getValue()));
                    events.add(event);
                }

                Log.d("Test10", events.toString());

                ClubEventsAdapter adapter = new ClubEventsAdapter(ClubInfoActivity.this, events);
                listViewEvents.setAdapter(adapter);
            }
        });



    }
}