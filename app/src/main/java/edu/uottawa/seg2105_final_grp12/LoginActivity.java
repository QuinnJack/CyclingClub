package edu.uottawa.seg2105_final_grp12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

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

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.tv_register).setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });


        sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        inputUsername = findViewById(R.id.et_username);
        inputPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(view -> {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();

            if (username.equals("admin") && (password.equals("admin"))) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                User signedInUser = new Admin("111","admin@admin.com" );

                sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UID", "111");
                editor.putString("USERNAME", "Admin");
                editor.putString("EMAIL", "admin@admin.com");
                editor.putString("ROLE", "Admin");
                editor.apply();
                startActivity(intent);
            }

            // TODO: split below onComplete failures into "boolean signIn"
            if (cleanInputs(username, password)) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                AuthModel.getInstance().login(username, password)
                        .addOnCompleteListener(new OnCompleteListener<User>() {
                            public void onComplete(Task<User> authTask) {
                                if (authTask.isSuccessful()) {
                                    User signedInUser = authTask.getResult();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("UID", signedInUser.getUid());
                                    editor.putString("USERNAME", signedInUser.getUsername());
                                    editor.putString("EMAIL", signedInUser.getEmail());
                                    editor.putString("ROLE", signedInUser.getRole());
                                    editor.apply();
                                    startActivity(intent);

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