package edu.uottawa.seg2105_final_grp12.viewmodel.base;

import android.app.Application;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import edu.uottawa.seg2105_final_grp12.models.Validators;
import edu.uottawa.seg2105_final_grp12.util.ObserverUtil;

public abstract class ValidatedFormViewModel extends AndroidViewModel {
    private static ValidatedFormViewModel instance;

    private static class Field extends MediatorLiveData<String> {
        private MutableLiveData<String> source = new MutableLiveData<>();
        protected boolean hadFocus = false;

        public MutableLiveData<String> getSource() {
            return source;
        }

        public void addSource(@NonNull MutableLiveData<String> source, @NonNull Observer<String> onChanged) {
            super.addSource(source, onChanged);
            this.source = source;
            onChanged.onChanged(source.getValue());
        }

        public boolean hadFocus() {
            return hadFocus;
        }
    }

    @BindingAdapter("app:errorEnabled")
    public static String bindField(EditText view, String[] errors) {
        if (errors.length == 0)
            return "false";

        instance.createField(view, errors);
        return "true";
    }

    public static String getString(int id) {
        return instance.getApplication().getString(id);
    }

    private final MediatorLiveData<Boolean> isValid = new MediatorLiveData<>();
    private int errorDelay = 0;
    protected final Map<View, Field> fields = new HashMap<>();

    public ValidatedFormViewModel(@NonNull Application application) {
        super(application);
        instance = this;
        isValid.setValue(true);
    }

    public void setDelay(int delay) {
        errorDelay = delay;
    }

    public void addDependent(View view, Function<String, Integer> onChanged, TextView... dependents) {
        for (TextView dependent : dependents)
            getField(view).addSource(getSource(dependent), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (!isValid(view)) return;
                    for (View v : dependents)
                        if (!isValid(v)) return;

                    getField(view).setValue(getString(onChanged.apply(s)));
                }
            });
    }

    public MutableLiveData<String> getSource(TextView view) {
        return fields.containsKey(view) ? fields.get(view).getSource() : new MutableLiveData<>(view.getText().toString());
    }

    public LiveData<String> getErrorLiveData(View view) {
        return getField(view);
    }

    public void setError(View v, String error) {
        getField(v).setValue(error);
    }

    public MutableLiveData<String> createField(TextView view, String... strings) {
        MutableLiveData<String> source = new MutableLiveData<>(view.getText().toString());

        Field field = getField(view);
        field.addSource(source, ObserverUtil.withDelay(s -> {
                for (String error : strings) {
                    if (!view.isShown() || !field.hadFocus) continue;

                    if (Validators.get(error).apply(s)) {
                        field.setValue(error);
                        return;
                    }
                }
            field.setValue(null);
        }, errorDelay));

        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getField(v).hadFocus = true;
                v.setOnFocusChangeListener(null);
            }
        });

        fields.put(view, field);
        isValid.addSource(field, s -> isValid.setValue(s == null && fields.entrySet().stream().noneMatch(entry -> entry.getValue().getValue() != null)));

        return source;
    }

    public String[] validateFor(String... strings) {
        return Arrays.stream(strings).filter(s -> Validators.get(s) != null).toArray(String[]::new);
    }

    public LiveData<Boolean> isValid() {
        return isValid;
    }

    public boolean isValid(View... views) {
        for (View v : views)
            if (getField(v).getValue() != null)
                return false;

        return true;
    }

    public Field getField(View view) {
       if (!fields.containsKey(view))
           fields.put(view, new Field());
       return fields.get(view);
    }
}
