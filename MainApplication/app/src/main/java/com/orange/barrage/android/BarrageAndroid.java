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
//        //定义了一个全局的异常处理
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                Ln.e(ex, "uncaughtException");
//            }
//        });
    }

    //加入一个Activity
    public void addActivity(Activity activity){
        if(mList == null) mList = new ArrayList<Activity>();

        mList.add(activity);
    }



    //移除一个Activity
    public void pushActivity(){
        if(mList == null) return;
        mList.remove(mList.size() - 1);
}

    //遍历Activity集合，然后一一清除
    public void clearActivity(){
        if(mList == null) return;
        //第一个参数是类型，第二个参数是名称，第三个参数表示需要遍历的集合
            for(Activity a : mList){
            //假如Activity没有在运行的话，则结束进程
            if(!a.isFinishing()) a.finish();
        }
    }
}
