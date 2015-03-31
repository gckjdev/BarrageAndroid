package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by youjiannuo on 2015/3/14.
 */
public class IntentUtil {

    private Activity mActivity;

    public IntentUtil(Activity activity){
            mActivity = activity;
    }

    public byte[] getIntentByteArrays(String key){
        return mActivity.getIntent().getByteArrayExtra(key);
    }


    public Serializable getIntentSerializable(String key){
        return mActivity.getIntent().getSerializableExtra(key);
    }

    public Parcelable getIntentParcelable(String key){
        return mActivity.getIntent().getParcelableExtra(key);
    }

    public String getIntentString(String key){
        return mActivity.getIntent().getStringExtra(key);
    }

    public String[] getIntentArrayString(String key){
        return mActivity.getIntent().getStringArrayExtra(key);
    }


    public int getIntentInt(String key ,int defaultValue){
        return mActivity.getIntent().getIntExtra(key , defaultValue);
    }


    public int [] getIntentIntArray(String key ){
        return mActivity.getIntent().getIntArrayExtra(key);
    }


}
