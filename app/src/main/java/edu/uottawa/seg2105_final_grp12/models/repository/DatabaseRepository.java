package edu.uottawa.seg2105_final_grp12.models.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.models.data.UserData;
import kotlin.NotImplementedError;

public class DatabaseRepository {
    private static DatabaseRepository dataRepo = new DatabaseRepository();
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    private DatabaseRepository() {}

    public static DatabaseRepository getInstance() {
        return dataRepo;
    }

    public Task<Void> updateDatabase(String ref, Map<String, Object> values) {
        return databaseRef.child(ref).updateChildren(values);
    }
    public Task<User> populateUserData(FirebaseUser fUser, String username, String password, String role) {
        TaskCompletionSource<User> registerCompletionSource = new TaskCompletionSource<>();
        Map<String, Object> map = Map.of(
                "username", username, "email", fUser.getEmail(),
                "password", password, "role", role);

        updateDatabase("users/" + fUser.getUid(), map).addOnCompleteListener(task -> {
            if (task.getException() == null)
                registerCompletionSource.setResult(new User(fUser, username, role));
            else registerCompletionSource.setException(task.getException());
        });
        return registerCompletionSource.getTask();
    }

    public Task<UserData> getUserData(String uid) {
        throw new NotImplementedError();
    }

    public Task<DataSnapshot> singleValueQuery(String ref, String key, String value) {
        TaskCompletionSource<DataSnapshot> queryComletionSource = new TaskCompletionSource<>();
        Query filterQuery = databaseRef.child("users/");
        databaseRef.child(ref).orderByChild("key").equalTo(value);

        filterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: " + snapshot.getRef());
                queryComletionSource.setResult(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return queryComletionSource.getTask();
    }
}
