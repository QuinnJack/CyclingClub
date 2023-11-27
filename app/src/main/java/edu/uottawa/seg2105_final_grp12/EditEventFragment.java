package edu.uottawa.seg2105_final_grp12;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uottawa.seg2105_final_grp12.databinding.LayoutEventEditBinding;
import edu.uottawa.seg2105_final_grp12.models.data.Event;
import edu.uottawa.seg2105_final_grp12.models.data.EventField;
import edu.uottawa.seg2105_final_grp12.models.data.EventType;
import edu.uottawa.seg2105_final_grp12.viewmodel.EventManagementViewModel;

public class EditEventFragment extends DialogFragment {

    private EventManagementViewModel eventManagementViewModel;
    private LayoutEventEditBinding binding;

    public EditEventFragment() {}

    public EditEventFragment(EventType eventType) {
        Bundle args = new Bundle();
        args.putSerializable("type", eventType);
        setArguments(args);
    }

    public EditEventFragment(Event event) {
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        setArguments(args);
    }

    private void initBinding() {
        eventManagementViewModel = new ViewModelProvider(this).get(EventManagementViewModel.class);

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_event_edit, null, false);
        binding.setViewModel(eventManagementViewModel);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        initBinding();
        addListeners();
        observeErrorLiveData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = requireArguments();

        EventType eventType = args.getSerializable("type", EventType.class);
        eventManagementViewModel.setEventType(eventType);

        Event event = args.getSerializable("event", Event.class);
        if (event != null) {
            builder.setTitle(event.getType());
            updateFields(event);
            builder.setPositiveButton(R.string.button_update_event, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addEvent(event);
                }
            });
        }
        else {
            builder.setTitle(eventType.getName());
            builder.setPositiveButton(R.string.button_create_event, (new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Event event = new Event();
                    event.setType(eventType.getName());
                    addEvent(event);
                }
            }));
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(binding.getRoot());
        binding.etEventName.requestFocus();
        return builder.create();
    }

    private void updateFields(Event event) {
        for (Map.Entry<EventField, String> e : event.getProperties().entrySet()) {
            eventManagementViewModel.getEventType().put(e.getKey(), true);
            setValue(getField(binding.getRoot().findViewById(e.getKey().getId())), e.getValue());
        }
        binding.etEventName.setText(event.getEventName());
    }

    private void addEvent(Event event) {
        if (!eventManagementViewModel.isValid().getValue())
            return;

        // Getting the cycling club making the event
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String uid = sharedPreferences.getString("UID", "");

        event.setCyclingClub(uid);
        for (View v : getFields())
            if (v.isShown())
                event.setField(EventField.fromId(((ViewGroup) v.getParent()).getId()), getValue(v));

        eventManagementViewModel.updateEvent(event);
        //updateEventsAdapter();
    }

    private String getValue(View v) {
        return TextView.class.isAssignableFrom(v.getClass())
                ? ((EditText) v).getText().toString()
                : ((TextView) (((Spinner) v).getSelectedView())).getText().toString();
    }

    private void setValue(View v, String s) {
        if (TextView.class.isAssignableFrom(v.getClass()))
            eventManagementViewModel.getSource((TextView) v).setValue(s);
        else {
            Spinner spn = (Spinner) v;
            for (int i = 0; i < spn.getAdapter().getCount(); i++) {
                if (spn.getAdapter().getItem(i).equals(s)) {
                    spn.setSelection(i);
                    return;
                }
                spn.setSelected(false);
                spn.setPrompt(s);
            }
        }
    }

    private List<View> getFields() {
        List<View> inputs = new ArrayList<>();

        for (int i = 0; i < binding.eventFieldsLayout.getChildCount(); i++) {
            ViewGroup fieldLayout = (ViewGroup) binding.eventFieldsLayout.getChildAt(i);
            inputs.add(fieldLayout.getChildAt(fieldLayout.getChildCount()-1));
        }

        return inputs;
    }

    private View getField(LinearLayout layout) {
        return layout.getChildAt(layout.getChildCount()-1);
    }

    private void addListeners() {
        EditText minAge = binding.etMinAge;
        EditText maxAge = binding.etMaxAge;
        View.OnFocusChangeListener compareMinMaxListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (minAge.isShown() && maxAge.isShown() && minAge.length() > 0 && maxAge.length() > 0 && !minAge.hasFocus() && !maxAge.hasFocus()) {
                    if (Integer.parseInt(minAge.getText().toString()) > Integer.parseInt(maxAge.getText().toString())) {
                        if (eventManagementViewModel.isValid(minAge, maxAge)) {
                            eventManagementViewModel.setError(minAge, getString(R.string.error_invalid_age_range));
                            eventManagementViewModel.setError(maxAge, getString(R.string.error_invalid_age_range));
                        }
                    }
                    else {
                        // refresh livedata
                        minAge.setText(minAge.getText());
                        maxAge.setText(maxAge.getText());
                    }
                }
            }
        };

        minAge.setOnFocusChangeListener(compareMinMaxListener);
        maxAge.setOnFocusChangeListener(compareMinMaxListener);
    }

    private void observeErrorLiveData() {
        LinearLayout fields = binding.eventFieldsLayout;

        for (int i = 1; i < fields.getChildCount(); i++) {
            ViewGroup fieldLayout = (ViewGroup) fields.getChildAt(i);

            // only EditTexts need validation for now
            if (fieldLayout.getChildAt(1).getClass() != AppCompatEditText.class)
                continue;

            EditText et = (EditText) fieldLayout.getChildAt(1);
            eventManagementViewModel.getErrorLiveData(et).observe(this, et::setError);
        }
    }
}
