package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;

import edu.uottawa.seg2105_final_grp12.models.AuthModel;
import edu.uottawa.seg2105_final_grp12.models.data.User;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.atomic.AtomicBoolean;


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

            if (register(username, password, email, role)) {
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                AuthModel.getInstance().registerUser(username, email, password, role)
                        .addOnCompleteListener(new OnCompleteListener<User>() { // Account has been added to FB
                            public void onComplete(Task<User> task) {
                                // TODO change to push data with SharedPreferences like LoginActivity
                                User registeredUser = task.getResult();
                                intent.putExtra("UID", registeredUser.getUid());
                                intent.putExtra("USERNAME", registeredUser.getUsername());
                                intent.putExtra("EMAIL", registeredUser.getEmail());
                                intent.putExtra("ROLE", registeredUser.getRole());
                                startActivity(intent);
                            }
                        }
                        );
                // TODO show error to user when registration goes wrong
            } else Toast.makeText(RegisterActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.tv_login).setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private boolean register(String username, String password, String email, String role) {

        AtomicBoolean valid = new AtomicBoolean(true);
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }

        if (password.length() < 5) {
            Toast.makeText(RegisterActivity.this, "Password must be at least 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }


        AuthModel.getInstance().emailExists(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult()) {
                        Toast.makeText(this, "Email in use", Toast.LENGTH_SHORT).show();
                        valid.set(false);
                    }});

        // TODO Add firebase auth lookup instead of hard coded.
        // TODO sanitize user input
        return valid.get();
    }
}
