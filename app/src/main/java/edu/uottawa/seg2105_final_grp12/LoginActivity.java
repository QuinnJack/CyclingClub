package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import edu.uottawa.seg2105_final_grp12.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.tv_register).setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });


        inputUsername = findViewById(R.id.et_username);
        inputPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(view -> {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();

            if (signIn(username, password)) {

                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                // TODO firebase auth
                User signedInUser = new User("1", username, "test@test.com", "user");
                intent.putExtra("UID", signedInUser.getUid());
                intent.putExtra("USERNAME", signedInUser.getUsername());
                intent.putExtra("EMAIL", signedInUser.getEmail());
                intent.putExtra("ROLE", signedInUser.getRole());
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean signIn(String username, String password) {
        // Check if username or password is null or empty
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        // TODO Add firebase auth lookup instead of hard coded.
        // TODO sanitize user input
        return true;
    }
}