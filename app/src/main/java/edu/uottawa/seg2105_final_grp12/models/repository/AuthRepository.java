package edu.uottawa.seg2105_final_grp12.models.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.uottawa.seg2105_final_grp12.models.data.User;
import kotlin.NotImplementedError;

public class AuthRepository {
    private static AuthRepository authRepo = new AuthRepository();
    private DatabaseRepository dataRepo = DatabaseRepository.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private User currentUser;

    private AuthRepository() {}

    public static AuthRepository getInstance() {
        return authRepo;
    }

    public Task</*User*/AuthResult> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
                //.onSuccessTask(authResult -> getUserData(authResult.getUser().getUid()));
    }

    public Task<User> registerUser(String username, String email, String password, String role) {
        return auth.createUserWithEmailAndPassword(email, password)
                .onSuccessTask(authResult -> dataRepo.populateUserData(authResult.getUser(), username, password, role))
                .addOnSuccessListener(user -> currentUser = user);
    }

    public Task<Boolean> emailExists(String email) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            taskCompletionSource.setResult(task.isSuccessful());
        });
        return taskCompletionSource.getTask();
    }

    public void signOut() {
        auth.signOut();
    }

    public Task<AuthResult> deleteUser() {
        throw new NotImplementedError();
    }
}
