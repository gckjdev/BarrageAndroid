package com.orange.barrage.android.util.activity;



import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import com.orange.barrage.android.R;

/**
 * Created by youjiannuo on 2015/3/3.
 */
public class BarrageCommonActivity extends RoboActivity {


    Resources mResource;
    protected  String getStrings(int resid){
        if(mResource == null) mResource = getResources();
        return mResource.getString(resid);
    }

    protected  String[] getArrays(int resid){
        if(mResource == null) mResource = getResources();

        return mResource.getStringArray(resid);
    }

    /**
     * 设置标题
     * @param resid
     */
    public void setTitleText(int resid){
        setTitleText(getString(resid));
    }
    /**
     * 设置标题
     * @param s
     */
    public void setTitleText(String s){
        TextView mTitle = ((TextView)findViewById(R.id.title));
         mTitle.setText(s);
    }

    /**
     * 点击按钮
     * @param v
     */
    public void onClickRight(View v){  }

    public void onClickFinish(View v){
        finish();
    }
}
