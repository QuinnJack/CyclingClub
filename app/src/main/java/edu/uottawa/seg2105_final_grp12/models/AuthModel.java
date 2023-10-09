package edu.uottawa.seg2105_final_grp12.models;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;

import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.repository.AuthRepository;
import edu.uottawa.seg2105_final_grp12.models.repository.DatabaseRepository;

public class AuthModel {
    private AuthRepository authRepo = AuthRepository.getInstance();
    private DatabaseRepository dataRepo = DatabaseRepository.getInstance();

    private static AuthModel authModel = new AuthModel();

    private AuthModel() {}
    public static AuthModel getInstance() { return authModel; }

    public Task<AuthResult> login(String email, String password) {
        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        authRepo.emailExists(email).addOnCompleteListener(exists -> {
            if(exists.getResult())
                taskCompletionSource.setResult(email);
            else {
                dataRepo.singleValueQuery("users", "email","username", email).addOnCompleteListener(
                        task -> {
                            Log.e(TAG, "login: " + task.getResult().exists() + email);
                            if (task.getResult().exists()) {
                                Log.e(TAG, "login: " + task.getResult().toString());
                                taskCompletionSource.setResult(task.getResult().getValue().toString());
                            }
                            else taskCompletionSource.setException(new NullPointerException());
                        });
            }
        });

        return taskCompletionSource.getTask().onSuccessTask((SuccessContinuation<String, AuthResult>)
                snapshot -> authRepo.signIn(snapshot, password));
    }

    public Task<User> registerUser(String username, String email, String password, String role) {
        return authRepo.registerUser(username, email, password, role);
    }
}
