package edu.uottawa.seg2105_final_grp12.util;

import android.os.Handler;

import androidx.lifecycle.Observer;

public class ObserverUtil {

    private ObserverUtil() {}

    public static <T> Observer<T> withDelay(Observer<T> observer, int delay) {
        return new Observer<T>() {
            private final Handler handler = new Handler();
            private Runnable runnable;
            @Override
            public void onChanged(T o) {
                handler.removeCallbacks(runnable);

                runnable = () -> observer.onChanged(o);

                handler.postDelayed(runnable, delay);
            }
        };
    }
}
