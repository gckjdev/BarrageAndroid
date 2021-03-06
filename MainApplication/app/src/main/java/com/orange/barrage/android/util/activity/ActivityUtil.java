package com.orange.barrage.android.util.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;

public class ActivityUtil {
	
	public static void setNoTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	public static void setFullScreen(Activity activity) {
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	

	
	public static void showAlert(Activity activity, String title, String message){
		AlertDialog.Builder ad = new AlertDialog.Builder(activity);
		ad.setTitle(title);
		ad.setMessage(message);
		ad.setNegativeButton("OK", null);
		ad.setCancelable(true);
		ad.show();				
	}
	
	public static void showAlert(Activity activity, String message){
		showAlert(activity, "", message);
	}

	public static void showAlert(Activity activity, int titleStringId, int messageStringId){
		Resources res = activity.getResources();
		showAlert(activity, res.getString(titleStringId), res.getString(messageStringId));		
	}

	public static void showAlert(Activity activity, int messageStringId){
		Resources res = activity.getResources();
		showAlert(activity, "", res.getString(messageStringId));		
	}

	
	public static void sendMessage(Context context,String message){
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
		intent.putExtra("sms_body", message);
        context.startActivity(intent);
	}
	
}
