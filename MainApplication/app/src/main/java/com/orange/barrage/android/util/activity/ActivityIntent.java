package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class ActivityIntent {

    public static void startIntent(Activity activity, Class<?> c, String key, Byte[] b){
        Intent intent = getIntent(activity , c );
        intent.putExtra(key , b);
        startIntent(activity , intent);
    }

    public static void startIntent(Activity activity , Class<?> c , String key , Parcelable parcel){
        Intent intent = getIntent(activity , c );
        intent.putExtra(key , parcel);
        startIntent(activity , intent);
    }

    public static void startIntent(Activity activity , Class<?> c , String key , int i){
        Intent intent =  getIntent(activity , c);
        intent.putExtra(key , i);
        startIntent(activity , intent);
    }

    public static void startIntent(Activity activtiy , Class<?> c , String key, String s){
        Intent intent =  getIntent(activtiy , c);
        intent.putExtra(key , s);
        startIntent(activtiy , intent);
    }

    public static void startIntent(Activity activity , Class<?> c){
        Intent intent = getIntent(activity , c);
        startIntent(activity, intent);
    }

    public static void startIntent(Activity activity , Intent intent){
        activity.startActivity(intent);
        ActivityCompat.startActivity(activity, intent,null);
    }

    private static Intent getIntent(Activity activity , Class<?> c ){
        return new Intent(activity , c);
    }




}
