package com.orange.barrage.android.util.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * Created by youjiannuo on 2015/3/6.
 */
public class FloatWindow  {

    private int mLayoutid;
    private Context mContext;
    private PopupWindow mPpWindow;
    private int mHeight;
    private int mWidth;

    private View mChildView;


    public FloatWindow(int layoutid , Context context){
        this(layoutid , context , LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
    }


    public  FloatWindow(int layoutid , Context context , int height , int width){

        this.mContext = context;
        this.mLayoutid = layoutid;
        this.mHeight =height;
        this.mWidth = width;

    }



    private void initMppWindow(){

        if(mPpWindow != null) return;

        mChildView = LayoutInflater.from(mContext).inflate(mLayoutid , null);
        mPpWindow = new PopupWindow(mChildView, mWidth, mHeight, false);
        // 需要设置一下此参数，点击外边可消失
        mPpWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPpWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        mPpWindow.setFocusable(true);

    }


    /**
     * 显示浮动窗口
     * @param parentView 需要显示在某一个空间旁边
     */
    public void show(View parentView){
        initMppWindow();
        mPpWindow.showAsDropDown(parentView);
    }

    public void close(){
        if(mPpWindow == null) return;
        mPpWindow.dismiss();
    }

}
