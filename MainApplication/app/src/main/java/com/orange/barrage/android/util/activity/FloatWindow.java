package com.orange.barrage.android.util.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import com.orange.barrage.android.R;


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

    private Window mWindow;
    private Dialog mDialog;


    public FloatWindow(int layoutid , Context context){
        this(layoutid , context , LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
    }


    public  FloatWindow(int layoutid , Context context , int height , int width){

        this.mContext = context;
        this.mLayoutid = layoutid;
        this.mHeight =height;
        this.mWidth = width;

    }


    private void initDialog(){
        if(mDialog != null)  return;
        mDialog = new Dialog(mContext, R.style.dialog);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        mDialog.setCanceledOnTouchOutside(true);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //				alertDialog.setCancelable(false);
        mDialog.show();
        mDialog.onWindowFocusChanged(true);

        mWindow = mDialog.getWindow();
        mWindow.setContentView(mLayoutid);
        mChildView = mWindow.getDecorView();
        mWindow.setLayout(LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);



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

    public View getContextView(){
        return mChildView;
    }



    /**
     * 显示浮动窗口
     * @param parentView 需要显示在某一个空间旁边
     */
    public void show(View parentView){
       if(parentView == null){
           show();
           return;
       }
        initMppWindow();
        mPpWindow.showAsDropDown(parentView);
    }

    public void show(){
        initDialog();
        mDialog.show();
    }



    public void close(){
        if(mPpWindow != null)
            mPpWindow.dismiss();
        if(mDialog != null) mDialog.cancel();
    }



}
