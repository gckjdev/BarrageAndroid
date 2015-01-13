package com.orange.barrage.android;

import android.app.Application;
import android.util.Log;

import com.orange.barrage.android.util.ContextManager;

import roboguice.util.Ln;

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
                Ln.e("uncaught exception thrown "+ex.toString(), ex);
            }
        });

        //injection
    }
}
