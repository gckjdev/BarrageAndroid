package com.orange.barrage.android.util.activity;

import android.app.Activity;
import android.app.ProgressDialog;


/**
 * �ȴ������
 * @author Administrator
 *
 */
public class ProgressDialogshow {
	
	private ProgressDialog progressDialog = null;
	
	/**
	 *
	 * @param message
	 */
	public void setMessage(String message){
		progressDialog.setMessage(message);
	}
	
	/**
	 *
	 * @param title
	 */
	public void setTitle(String title){
		progressDialog.setTitle(title);
	}
	
	public ProgressDialogshow(Activity activity,String title,String message){

		progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
	    progressDialog.setCancelable(true);
	    progressDialog.setCanceledOnTouchOutside(false);
	}
	
	public ProgressDialog getProgressDialog(){
		return progressDialog;
	}
	
	public void show(){
		if(progressDialog == null) return;
		progressDialog.show();
	}
	public void close(){
		if(progressDialog == null) return;
		progressDialog.dismiss();
	}
	
	//�Ƿ������ʾ
	public boolean isShow(){
		return progressDialog.isShowing();
	}
	
}
