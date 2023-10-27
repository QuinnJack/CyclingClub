package edu.uottawa.seg2105_final_grp12.models.data;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.uottawa.seg2105_final_grp12.R;


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
     * This getView is the method by which a singlular item is displayed in the list!
     * Not how the list itself is displayed!
     */
    @Override // Will generate an error if ArrayAdapter method isn't properly overridden
    public View getView(int listPosition, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewEvent = layoutInflater.inflate(R.layout.layout_event_list, null, true); //immediately makes this a child

        // Creating references to views within the layout
        TextView textViewEventName = (TextView) listViewEvent.findViewById(R.id.textViewEventName);
        TextView textViewEventType = (TextView) listViewEvent.findViewById(R.id.textViewEventType);
        ViewStub eventTypeLayout = (ViewStub) listViewEvent.findViewById(R.id.eventTypeLayout); // TODO: have this layout change depending on value of textViewEventType
        TextView textViewMinAge = (TextView) listViewEvent.findViewById(R.id.textViewMinAge);
        TextView textViewMaxAge = (TextView) listViewEvent.findViewById(R.id.textViewMaxAge);
        TextView textViewPace = (TextView) listViewEvent.findViewById(R.id.textViewPace);

        Event event = events.get(listPosition);
        textViewEventName.setText(event.getEventName());
        textViewEventType.setText("NOT IMPLEMENTED"); // TODO: display instance class
            // eventTypeLayout.setInflatedId(R.layout.eventTypeID); // TODO: change sub-layout with eventType
        textViewMinAge.setText(event.getMinAge());
        textViewMaxAge.setText(event.getMaxAge());
        textViewPace.setText(event.getPace());

        return listViewEvent; // Returns this singular
    }

}
