package edu.uottawa.seg2105_final_grp12.models.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;
import edu.uottawa.seg2105_final_grp12.databinding.LayoutEventListBinding;


/**
 * This class as a whole represents the data that the events listView will have to hold
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> events;

    public EventAdapter(Activity context, List<Event> events) {
        super(context, R.layout.layout_event_list, events);
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
        LayoutEventListBinding listBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_event_list, null, true);
        listBinding.setEvent(events.get(listPosition));



        // Creating references to views within the layout
        /*TextView textViewEventName = (TextView) listViewEvent.findViewById(R.id.textViewEventName);
        TextView textViewEventType = (TextView) listViewEvent.findViewById(R.id.textViewEventType);
        ViewStub eventTypeLayout = (ViewStub) listViewEvent.findViewById(R.id.eventTypeLayout); // TODO: have this layout change depending on value of textViewEventType
        TextView textViewMinAge = (TextView) listViewEvent.findViewById(R.id.textViewMinAge);
        TextView textViewMaxAge = (TextView) listViewEvent.findViewById(R.id.textViewMaxAge);
        TextView textViewPace = (TextView) listViewEvent.findViewById(R.id.textViewPace);
        TextView textViewMinSkillLevel = (TextView) listViewEvent.findViewById(R.id.textViewMinSkillLevel);
        TextView textViewDifficulty = (TextView) listViewEvent.findViewById(R.id.textViewDifficulty);*/

        /*textViewEventName.setText(event.getEventName());
        textViewEventType.setText(event.getType() + " "); // TODO: display instance class
        //eventTypeLayout.setInflatedId(R.layout.eventTypeID); // TODO: change sub-layout with eventType
        if (event.getMinAge() == null ) { textViewMinAge.setVisibility(View.GONE); } // TODO: this null doesn't work? user shouldn't have to enter certain attributes
            else { textViewMinAge.setText("Minimum Age: " + event.getMinAge().toString()); }
        textViewMaxAge.setText("Maximum Age: " + event.getMaxAge().toString());
        textViewPace.setText("Recommended Pace: " + event.getPace());
        textViewMinSkillLevel.setText("Minimum Skill Level: " + event.getMinSkillLevel());
        textViewDifficulty.setText("Difficulty Level: " + event.getDifficulty());*/





        Button btnEditType = listBinding.buttonEdit;
        Button btnDelete = listBinding.buttonDel;
        int position = listPosition; // This captures the current position
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
                Event event = events.get(position);

                events.remove(position);
                String eventID = event.getId();
                if (eventID != null) {
                    mDatabase.child(eventID).removeValue();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Error: Invalid event ID", Toast.LENGTH_SHORT).show();
                }
            }});

        btnEditType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events");
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
            }
        });



        return listBinding.getRoot(); // Returns this singular
    }

}
