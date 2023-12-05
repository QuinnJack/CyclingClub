package edu.uottawa.seg2105_final_grp12.models.data;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;
import edu.uottawa.seg2105_final_grp12.databinding.LayoutClubEventsListBinding;
import edu.uottawa.seg2105_final_grp12.databinding.LayoutEventListBinding;
import edu.uottawa.seg2105_final_grp12.fragment.EditEventFragment;


/**
 * This class as a whole represents the data that the events listView will have to hold
 */
public class ClubEventsAdapter extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> events;

    public ClubEventsAdapter(Activity context, List<Event> events) {
        super(context, R.layout.layout_club_events_list, events);
        this.context = context;
        this.events = events;

    }

    /**
     * This getView is the method by which a singular item is displayed in the list!
     * Not how the list itself is displayed!
     */
    @Override // Will generate an error if ArrayAdapter method isn't properly overridden
    public View getView(int listPosition, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //View listViewEvent = layoutInflater.inflate(R.layout.layout_event_list, null, true); //immediately makes this a child
        LayoutClubEventsListBinding listBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_club_events_list, null, true);
        listBinding.setEvent(events.get(listPosition));

        Button btnJoin = listBinding.buttonJoin;
        int position = listPosition; // This captures the current position

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
                Event event = events.get(position);

                SharedPreferences p = context.getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                String name = p.getString("USERNAME", "");

                // setting new list of participants
                ArrayList<String> participants = event.getParticipants();
                if (participants == null) {
                    participants = new ArrayList<String>();
                }
                participants.add(name);
                event.setParticipants(participants);

                String eventID = event.getId();
                if (eventID != null) {
                    mDatabase.child(eventID).child("participants").setValue(event.getParticipants());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Error: Invalid event ID", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(context, "Joined Event", Toast.LENGTH_SHORT).show();

                /*DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Event");
                LayoutInflater inflater = context.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.layout_event_edit, null));

                AlertDialog alertDialog = builder.create();

                alertDialog.show();*/
                // TODO: add button that finishes the event Editing
                // TODO: change visible layout fields based on eventType




                /*
                PopupMenu popup = new PopupMenu(context, btnEditType);
                // Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_event_type, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String newType = item.getTitle().toString();

                        listBinding.getEvent().setType(newType);
                        databaseEvents.child(listBinding.getEvent().getId()).child("type").setValue(newType);
                        notifyDataSetChanged();
                        return true;
                    }
                });
                popup.show(); // showing popup menu
                */
            }
        });



        return listBinding.getRoot(); // Returns this singular
    }

    private String getValue(View v) {
        return TextView.class.isAssignableFrom(v.getClass())
                ? ((EditText) v).getText().toString()
                : ((TextView) (((Spinner) v).getSelectedView())).getText().toString();
    }

}
