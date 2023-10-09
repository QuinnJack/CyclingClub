package edu.uottawa.seg2105_final_grp12;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.uottawa.seg2105_final_grp12.models.User;

public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // TODO Change hardcoded user to fetch from FB database
        User user = new User("12345", "testUser", "test@test.com", "participant");

        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvRole = findViewById(R.id.tv_role);

        tvUsername.setText("Username: " + user.getUsername());
        tvRole.setText("Role: " + user.getRole());
    }
}
