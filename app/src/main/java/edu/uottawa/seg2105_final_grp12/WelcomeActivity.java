package edu.uottawa.seg2105_final_grp12;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import android.content.Intent;


public class WelcomeActivity extends AppCompatActivity {

    private Button eventManagement; // TODO: make only admin see this

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // TODO fetch from FB database?
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        String username = intent.getStringExtra("USERNAME");
        String email = intent.getStringExtra("EMAIL");
        String role = intent.getStringExtra("ROLE");

        User user = new User(uid, username, email, role);

        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvRole = findViewById(R.id.tv_role);
        Button btnEventManagement = findViewById(R.id.btn_event_management);

        tvUsername.setText("Username: " + user.getUsername());
        tvRole.setText("Role: " + user.getRole());

        // Access event management screen
        btnEventManagement.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, EventManagementActivity.class));
        });


    }
}
