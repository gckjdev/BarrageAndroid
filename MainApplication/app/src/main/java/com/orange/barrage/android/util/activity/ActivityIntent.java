package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class ActivityIntent {

    public static void startIntent(Activity activity , Class<?> c){
        Intent intent = new Intent(activity,c);
        startIntent(activity,intent);
    }

    public static void startIntent(Activity activity , Intent intent){
        activity.startActivity(intent);
    }

}
