package edu.uottawa.seg2105_final_grp12;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
