package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import edu.uottawa.seg2105_final_grp12.models.User;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;

    private EditText inputEmail;
    private Button btnRegister;
    private RadioGroup radioAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.et_username);
        inputPassword = findViewById(R.id.et_password);
        inputEmail = findViewById(R.id.et_email);
        btnRegister = findViewById(R.id.btn_register);
        radioAccountType = findViewById(R.id.radio_account_type);

        btnRegister.setOnClickListener(view -> {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();
            String email = inputEmail.getText().toString();
            String role = "";
            int selectedId = radioAccountType.getCheckedRadioButtonId();
            if (selectedId == R.id.radio_button_participant) {
                role = "Participant";
            } else if (selectedId == R.id.radio_button_club) {
                role = "Cycling Club";
            }

            if (register(username, password, email,role)) {
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                User registeredUser = new User("1", username, email, role);

                intent.putExtra("UID", registeredUser.getUid());
                intent.putExtra("USERNAME", registeredUser.getUsername());
                intent.putExtra("EMAIL", registeredUser.getEmail());
                intent.putExtra("ROLE", registeredUser.getRole());
                startActivity(intent);

            } else {
                // TODO show error to user when registration goes wrong
                Toast.makeText(RegisterActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.tv_login).setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private boolean register(String username, String password,String email, String role) {

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        // TODO Add firebase auth lookup instead of hard coded.
        // TODO sanitize user input
        return true;
    }
}
