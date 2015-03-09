package com.orange.barrage.android;

import android.app.Activity;
import android.app.Application;

import com.orange.barrage.android.util.ContextManager;

import org.apache.velocity.runtime.directive.Foreach;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/10.
 */
public class BarrageAndroid extends Application {

    private final String TAG = "BarrageAndroid";

    private List<Activity> mList = null;




    @Override
    public void onCreate(){
        super.onCreate();

        ContextManager.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Ln.e(ex, "uncaughtException");
            }
        });
    }

    public void addActivity(Activity activity){
        if(mList == null) mList = new ArrayList<Activity>();

        mList.add(activity);
    }




    public void pushActivity(){
        if(mList == null) return;
        mList.remove(mList.size() - 1);
    }

    public void clearActivity(){
        if(mList == null) return;
        for(Activity a : mList){
            if(!a.isFinishing()) a.finish();
        }
    }
}
