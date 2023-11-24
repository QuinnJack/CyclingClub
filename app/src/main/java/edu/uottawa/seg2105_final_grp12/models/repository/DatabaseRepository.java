package edu.uottawa.seg2105_final_grp12.models.repository;

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
import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.models.data.User;

public class DatabaseRepository {
    private static DatabaseRepository dataRepo = new DatabaseRepository();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private DatabaseRepository() {}

    public static DatabaseRepository getInstance() {
        return dataRepo;
    }

    public DatabaseReference getReference(String path) {
        return database.child(path);
    }

    public Task<Void> updateDatabase(String ref, Map<String, Object> values) {
        return database.child(ref).updateChildren(values);
    }

    public Task<Void> updateValue(String ref, Object value) {
        return database.child(ref).setValue(value);
    }

    public Task<User> populateUserData(FirebaseUser fUser, String username, String password, String role) {
        TaskCompletionSource<User> registerCompletionSource = new TaskCompletionSource<>();
        Map<String, Object> map = Map.of(
                "uid", fUser.getUid(),
                "username", username, "email", fUser.getEmail(),
                "password", password, "role", role);

        updateDatabase("users/" + fUser.getUid(), map).addOnCompleteListener(task -> {
            if (task.getException() == null)
                registerCompletionSource.setResult(new User(map.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> (String)e.getValue()))));
            else registerCompletionSource.setException(task.getException());
        });
        return registerCompletionSource.getTask();
    }

    public Task<DataSnapshot> singleValueQuery(String ref, String key, String filterKey, String filterValue) {
        TaskCompletionSource<DataSnapshot> queryCompletionSource = new TaskCompletionSource<>();
        Query filterQuery = database.child(ref).orderByChild(filterKey).equalTo(filterValue);

        filterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    queryCompletionSource.setResult(snapshot.getChildren().iterator().next().child(key));
                }
                else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return queryCompletionSource.getTask();
    }

    public Task<DataSnapshot> queryByChild(String ref, String childKey, String childValue) {
        return database.child(ref).orderByChild(childKey).equalTo(childValue).get();
    }
}