package edu.uottawa.seg2105_final_grp12.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.models.data.User;
import edu.uottawa.seg2105_final_grp12.viewmodel.base.ValidatedFormViewModel;

public class EventManagementViewModel extends ValidatedFormViewModel {

    private DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events");
    private EventType eventType = new EventType();
    private List<String> clubEventNames = new ArrayList<>();

    public EventManagementViewModel(@NonNull Application application) {
        super(application);
        setDelay(0);
    }

    public void updateEvent(Event event) {
        String id = event.getId();
        if (id == null) {
            id = databaseEvents.push().getKey();
            event.setId(id);
        }

        databaseEvents.child(id).setValue(event.getProperties().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getKey(), e -> e.getValue())));
        databaseEvents.child(id).child("id").setValue(id);
        databaseEvents.child(id).child("type").setValue(event.getType());
        databaseEvents.child(id).child("clubId").setValue(event.getCyclingClub()); // cycling club managing the event
    }
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        if (eventType == null)
            eventType = new EventType();

        this.eventType = eventType;
    }
}


