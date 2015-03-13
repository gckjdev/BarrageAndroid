package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.litl.leveldb.NotFoundException;
import com.orange.barrage.android.R;

/**
 * Created by pipi on 15/3/13.
 */
public class TopBarView {

    final Activity mActivity;

    public TopBarView(Activity activity){
        this.mActivity = activity;
    }

    public RelativeLayout getTopBar(){
        if (mActivity == null){
            return null;
        }

        return (RelativeLayout)mActivity.findViewById(R.id.top_main);
    }

    public boolean hasTopBar(){
        return (getTopBar() != null);
    }

    private String getString(int resId){
        if (mActivity == null){
            return "";
        }

        return mActivity.getString(resId);
    }

    public  void setNavigationBackgroundChangeOtherType(){
        if (!hasTopBar()){
            return;
        }

        getTopBar().setBackgroundColor(Color.rgb(12, 12, 12));

    }

    /**
     * 设置标题
     * @param resId
     */
    public void setTitleText(int resId){
        setTitleText(getString(resId));
    }
    /**
     * 设置标题
     * @param s
     */
    public void setTitleText(String s){
        if (!hasTopBar()){
            return;
        }

        TextView mTitle = ((TextView)mActivity.findViewById(R.id.top_title));
        if (mTitle != null) {
            mTitle.setText(s);
        }
    }


    // 隐藏导航栏左边的按钮
    public void hideLeftButton(){
        if (!hasTopBar()){
            return;
        }

        ImageButton button = (ImageButton)mActivity.findViewById(R.id.top_back_button);
        if (button != null){
            button.setVisibility(View.GONE);
        }
    }


    public void setRightButton(int resId){

        if (!hasTopBar()){
            return;
        }

        if(resId <= 0) {
            return;
        }

        ImageButton image = ((ImageButton)(mActivity.findViewById(R.id.top_right_button)));
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), resId);
        if(bitmap != null) {
            image.setImageBitmap(bitmap);
            image.setVisibility(View.VISIBLE);
        }else {
            TextView tv = (TextView)mActivity.findViewById(R.id.top_right_text);
            tv.setText(getString(resId));
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRight(v);
                }
            });
        }

    }

    /**
     * 点击按钮
     * @param v
     */
    public void onClickRight(View v){
    }

    public void doBack(View v){
        if (!hasTopBar()){
            return;
        }

        mActivity.finish();
    }
}
