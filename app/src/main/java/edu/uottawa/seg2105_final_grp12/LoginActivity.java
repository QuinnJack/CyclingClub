package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;

import edu.uottawa.seg2105_final_grp12.models.AuthModel;
import edu.uottawa.seg2105_final_grp12.models.data.Admin;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.repository.DatabaseRepository;

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

            if (username.equals("admin") && (password.equals("admin"))) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                User signedInUser = new Admin("111","admin@admin.com" );
                intent.putExtra("USERNAME", signedInUser.getUsername());
                intent.putExtra("ROLE", signedInUser.getRole());
                startActivity(intent);
            }

            // TODO: split below onComplete failures into "boolean signIn"
            if (cleanInputs(username, password)) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                AuthModel.getInstance().login(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(Task<AuthResult> authTask) {
                                if (authTask.isSuccessful()) {
                                    DatabaseRepository.getInstance()
                                            .singleValueQuery("users", "role", "username", username)
                                            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                public void onComplete(Task<DataSnapshot> task) {

                                                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getValue() != null ) {
                                                        String role = task.getResult().getValue().toString();
                                                        User signedInUser = new User(authTask.getResult().getUser(), username, role);
                                                        intent.putExtra("UID", signedInUser.getUid());
                                                        intent.putExtra("USERNAME", signedInUser.getUsername());
                                                        intent.putExtra("EMAIL", signedInUser.getEmail());
                                                        intent.putExtra("ROLE", signedInUser.getRole());
                                                        startActivity(intent);
                                                    }
                                                    if (task.getException() != null) {
                                                        Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                                                    }

                                                }

                                            });

                                } else {
                                    Log.d("error", "signinwithEmail:failure", authTask.getException());
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private boolean cleanInputs(String username, String password) {
        // Check if username or password is null or empty
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    }