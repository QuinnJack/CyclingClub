package edu.uottawa.seg2105_final_grp12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import edu.uottawa.seg2105_final_grp12.models.data.ClubReview;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventAdapter;
import edu.uottawa.seg2105_final_grp12.models.data.EventField;
import edu.uottawa.seg2105_final_grp12.models.data.ReviewAdapter;
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

        SharedPreferences p = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String name = p.getString("USERNAME", "");

        // Basic club info TextViews
        TextView tvClubName = findViewById(R.id.tv_club_name);
        TextView tvSocialLink = findViewById(R.id.tv_social_link);
        TextView tvClubContact = findViewById(R.id.tv_main_contact);
        TextView tvPhoneNumber = findViewById(R.id.tv_phone_number);
        ImageView clubLogo = findViewById(R.id.image_view_logo);

        ListView listViewReviews = findViewById(R.id.list_club_reviews);
        List<ClubReview> reviews = new ArrayList<ClubReview>();
        ReviewAdapter reviewsAdapter = new ReviewAdapter(ClubInfoActivity.this, reviews);
        listViewReviews.setAdapter(reviewsAdapter);

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
                        if (club.getReviews() != null) {
                            reviews.clear();
                            reviews.addAll(club.getReviews());
                            reviewsAdapter.notifyDataSetChanged();
                        }
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

        ClubEventsAdapter adapter = new ClubEventsAdapter(ClubInfoActivity.this, events);
        DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference("events");

        eventDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event event = new Event();

                    Map<String, Object> values = postSnapshot.getValue(new GenericTypeIndicator<Map<String, Object>>() {
                    });

                    Object hostId = values.remove("clubId");
                    if (hostId == null) {
                        continue;
                    }
                    if (hostId != null && !hostId.toString().equals(clubId)) {
                        continue;
                    }

                    ArrayList<String> l = null;
                    Object o = values.remove("participants");
                    if (o != null && o instanceof ArrayList) {
                        l = (ArrayList<String>) o;
                    }
                    if (l != null && l.contains(name)) {
                        continue;
                    }

                    event.setId(values.remove("id").toString());
                    event.setType(values.remove("type").toString());
                    values.entrySet().stream().filter(e -> EventField.fromString(e.getKey()) != null)
                            .forEach(e -> event.setField(EventField.fromString(e.getKey()), e.getValue().toString()));
                    events.add(event);
                }

                adapter.notifyDataSetChanged();
                listViewEvents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnAddReview = findViewById(R.id.button_add_review);
        EditText etFeedback = findViewById(R.id.et_review_feedback);
        Spinner ratingSpinner = findViewById(R.id.spinner_rating);

        String[] ratings = {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ratings);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(spinnerAdapter);

        btnAddReview.setOnClickListener(view -> {
            int rating = Integer.parseInt(ratingSpinner.getSelectedItem().toString());
            String feedback = etFeedback.getText().toString().trim();

            if (feedback.isEmpty()) {
                Toast.makeText(ClubInfoActivity.this, "Error: Must enter feedback!", Toast.LENGTH_LONG).show();
                return;
            }

            ClubReview newReview = new ClubReview(rating, feedback, name);
            reviews.add(newReview);
            clubAccount.child("reviews").setValue(reviews);
            reviewsAdapter.notifyDataSetChanged();
        });

    }
}