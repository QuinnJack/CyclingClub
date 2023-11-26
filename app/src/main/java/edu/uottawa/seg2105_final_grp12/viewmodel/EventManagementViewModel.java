package edu.uottawa.seg2105_final_grp12.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.viewmodel.base.ValidatedFormViewModel;

public class EventManagementViewModel extends ValidatedFormViewModel {

    DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events");
    public EventManagementViewModel(@NonNull Application application) {
        super(application);
        setDelay(0);
    }

    public void createEvent(Event event) {
        String id = databaseEvents.push().getKey();
        event.setId(id);

        databaseEvents.child(id).setValue(event.getProperties().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getKey(), e -> e.getValue())));
        databaseEvents.child(id).child("id").setValue(id);
        databaseEvents.child(id).child("type").setValue(event.getType());
        databaseEvents.child(id).child("clubId").setValue(event.getCyclingClub()); // cycling club managing the event
    }
}


