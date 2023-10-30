package edu.uottawa.seg2105_final_grp12.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.repository.DatabaseRepository;

public class AuthModel {
    private DatabaseRepository dataRepo = DatabaseRepository.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User currentUser;

    private static AuthModel authModel = new AuthModel();


    private AuthModel() {}
    public static AuthModel getInstance() { return authModel; }

    public Task<User> login(String username, String password) {
        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        emailExists(username).addOnCompleteListener(exists -> {
            if(exists.getResult()) {
                taskCompletionSource.setResult(username);
            }
            else {
                dataRepo.singleValueQuery("users", "email","username", username).addOnCompleteListener(
                        task -> {
                            if (!task.isSuccessful())
                                taskCompletionSource.setException(new NullPointerException());
                            else if (task.getResult().exists()) {
                                taskCompletionSource.setResult(task.getResult().getValue().toString());
                            }
                        });
            }
        });

        return taskCompletionSource.getTask()
                .onSuccessTask(email -> auth.signInWithEmailAndPassword(email, password))
                .onSuccessTask((SuccessContinuation<AuthResult, User>) authResult -> getUserData(authResult.getUser()));
    }

    public Task<User> getUserData(FirebaseUser fUser) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();

        dataRepo.getReference("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> userData = (Map<String, String>) snapshot.getValue(true);

                if (!snapshot.hasChild("uid")) { // update older accounts to store uid by value
                    userData.put("uid", snapshot.getKey());
                    snapshot.getRef().child("uid").setValue(snapshot.getKey());
                }

                taskCompletionSource.setResult(new User(userData));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskCompletionSource.setException(error.toException());
            }
        });
        return taskCompletionSource.getTask();
    }

    public Task<User> registerUser(String username, String email, String password, String role) {
        return auth.createUserWithEmailAndPassword(email, password)
                .onSuccessTask(authResult -> dataRepo.populateUserData(authResult.getUser(), username, password, role));
    }

    public Task<Boolean> emailExists(String email) {
        TaskCompletionSource<Boolean> emailExists = new TaskCompletionSource<>();

        try {
            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
                emailExists.setResult(task.getException() == null
                        && !task.getResult().getSignInMethods().isEmpty());
            });
        } catch (IllegalArgumentException e) {
            emailExists.setException(e);
        }
        return emailExists.getTask();
    }
}