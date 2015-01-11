package com.orange.barrage.android;

import android.app.Application;
import android.util.Log;

import com.orange.barrage.android.util.ContextManager;

/**
 * Created by Rollin on 2015/1/10.
 */
public class BarrageAndroid extends Application {

    private final String TAG = "BarrageAndroid";
    @Override
    public void onCreate(){
        super.onCreate();

        ContextManager.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e(TAG, "uncaught exception thrown", ex);
            }
        });

        //injection
    }
}
