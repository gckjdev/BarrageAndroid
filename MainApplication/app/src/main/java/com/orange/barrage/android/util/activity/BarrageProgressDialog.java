package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.app.ProgressDialog;


/**
 * �ȴ������
 * @author Administrator
 *
 */
public class BarrageProgressDialog {
	
	private ProgressDialog mProgressDialog = null;
    final private Activity mActivity;

    private ProgressDialog getProgressDialog(){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(mActivity);
            mProgressDialog.setTitle("");
            mProgressDialog.setMessage("");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        return mProgressDialog;
    }

	/**
	 *
	 * @param message
	 */
	public void setMessage(String message){
        getProgressDialog().setMessage(message);
	}
	
	/**
	 *
	 * @param title
	 */
	public void setTitle(String title){
        getProgressDialog().setTitle(title);
	}
	
	public BarrageProgressDialog(Activity activity){

        this.mActivity = activity;
	}

	public void show(String text){
        getProgressDialog().setMessage(text);
        getProgressDialog().show();
	}
	public void close(){
        getProgressDialog().dismiss();
	}
	
	public boolean isShow(){
		return getProgressDialog().isShowing();
	}
	
}
