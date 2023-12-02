package edu.uottawa.seg2105_final_grp12;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class WelcomeActivity extends AppCompatActivity {

    private Button eventManagement; // TODO: make only admin see this

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // TODO fetch from FB database?
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String uid = sharedPreferences.getString("UID", "");
        String username = sharedPreferences.getString("USERNAME", "");
        String email = sharedPreferences.getString("EMAIL", "");
        String role = sharedPreferences.getString("ROLE", "");

        ImageView logoImageView = findViewById(R.id.logoImageView);

        String selectedLogo = sharedPreferences.getString("selectedLogo", "default_logo");
        Log.d("test1", selectedLogo);
        int resourceId = getResources().getIdentifier(selectedLogo, "drawable", getPackageName());
        logoImageView.setImageResource(resourceId);
        User user = new User(uid, username, email, role);

        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvRole = findViewById(R.id.tv_role);

        tvUsername.setText("Username: " + user.getUsername());
        tvRole.setText("Role: " + user.getRole());

        Button btnEventManagement = findViewById(R.id.btn_event_management);
            btnEventManagement.setOnClickListener(view -> {
                startActivity(new Intent(WelcomeActivity.this, EventManagementActivity.class));

        });


        Button btnEventTypes = findViewById(R.id.btn_event_types);
        btnEventTypes.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, EventTypesActivity.class));

        });


        Button btnUsers = findViewById(R.id.btn_users);
        btnUsers.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, UsersActivity.class));
        });


        Button btnProfile = findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
        });

        Button btnFindClubs = findViewById(R.id.btn_find_clubs);
        btnFindClubs.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, FindClubActivity.class));
        });

        if (!role.equals("Admin")) {
            btnEventTypes.setVisibility(View.GONE);
            btnUsers.setVisibility(View.GONE);

        }
        if (!role.equals("Cycling Club")) {
            btnEventManagement.setVisibility(View.GONE);
            btnProfile.setVisibility(View.GONE);
        }

        if (!role.equals("Participant")) {
            btnFindClubs.setVisibility(View.GONE);
        }
    }
}
