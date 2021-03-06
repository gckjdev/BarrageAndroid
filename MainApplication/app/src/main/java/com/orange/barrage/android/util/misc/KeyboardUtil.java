package com.orange.barrage.android.util.misc;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
	
	public static void showKeyboardForOnCreate(final View view, final Context context) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				showKeyboard(view, context);
			}

		}, 500); // open in 500 seconds
	}

	public static void showKeyboard(View view, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hideKeyboard(View view, Context context) {
		// close keyboard
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	public static void showSoftKeyboard(View view,Context context){
		InputMethodManager inputMgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        inputMgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}
	
	public static void hideSoftKeyboard(View view,Context context){
		InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}


