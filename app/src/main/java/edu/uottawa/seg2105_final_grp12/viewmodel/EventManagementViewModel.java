package edu.uottawa.seg2105_final_grp12.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import edu.uottawa.seg2105_final_grp12.viewmodel.base.ValidatedFormViewModel;

public class EventManagementViewModel extends ValidatedFormViewModel {

    public EventManagementViewModel(@NonNull Application application) {
        super(application);
        setDelay(0);
    }
}


