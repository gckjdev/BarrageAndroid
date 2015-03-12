package com.orange.barrage.android.util.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.litl.leveldb.NotFoundException;
import com.orange.barrage.android.BarrageAndroid;
import com.orange.barrage.android.R;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class FramgActivity extends RoboFragmentActivity {

    /*进度条*/
    private ProgressDialogshow mProgressDialog;

    /*Application*/
    protected BarrageAndroid mBarrageAndroid;



    /**
     *
     * @param savedInstanceState
     * @param layoutresid 布局资源
     * @param titleid  屏幕标题的字符串资源
     * @param rightid  右边按钮的图标资源
     */
    protected  void onCreate(Bundle savedInstanceState , int layoutresid, int titleid , int rightid){
        onCreate(savedInstanceState ,layoutresid , getString(titleid) , rightid);
    }

    /**
     *
     * @param savedInstanceState
     * @param titleString 屏幕标题的字符串
     * @param rightid 右边按钮的图标资源
     */
    protected void onCreate(Bundle savedInstanceState,int layoutresid ,String titleString , int rightid ){
        super.onCreate(savedInstanceState);

        mBarrageAndroid = (BarrageAndroid)getApplication();

        setContentView(layoutresid);
        setTitleText(titleString);
        setRightButton(rightid);
        closeLeftButton();
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
        TextView mTitle = ((TextView)findViewById(R.id.top_title));
        if(mTitle != null)
            mTitle.setText(s);
    }

    //关闭导航栏左边的按钮
    public void closeLeftButton(){
        ImageButton button = (ImageButton) findViewById(R.id.top_back_button);
        if(button != null) button.setVisibility(View.GONE);
    }


    public void setRightButton(int resid){
        if(resid <= 0)
            return ;

        try {
            ImageButton image = ((ImageButton)(findViewById(R.id.top_right_button)));
            image.setImageResource(resid);
            image.setVisibility(View.VISIBLE);
        }catch (NotFoundException e){
            TextView tv = (TextView)findViewById(R.id.top_right_text);
            tv.setText(getString(resid));
            tv.setVisibility(View.VISIBLE);
        }

    }

    public String getIntentString(String key){
        return getIntent().getStringExtra(key);
    }

    public int getIntentInt(String key ,int defaultValue){
        return getIntent().getIntExtra(key , defaultValue);
    }



    /**
     * 打开等待进度条
     * @param text
     */
    protected  void progresShow(String text){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialogshow(this , "", text);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭等待进度条
     */
    protected  void progresClose(){
        if(mProgressDialog != null)
            mProgressDialog.close();
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
