package com.orange.barrage.android;

import android.app.Activity;
import android.app.Application;

import com.orange.barrage.android.util.ContextManager;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/10.
 */
public class BarrageAndroid extends Application {

    private final String TAG = "BarrageAndroid";
    //创建了Activity的集合，用来管理Activity的
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
        //第一个参数是类型，第二个参数是名称，第三个参数表示需要遍历的集合
        for(Activity a : mList){
            if(!a.isFinishing()) a.finish();
        }
    }
}
