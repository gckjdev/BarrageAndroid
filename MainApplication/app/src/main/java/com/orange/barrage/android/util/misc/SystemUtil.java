package com.orange.barrage.android.util.misc;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.orange.barrage.android.util.ContextManager;

import java.util.Timer;
import java.util.TimerTask;

public class SystemUtil {
    private static String deviceId = null;

    public static String getDeviceId(Context context) {
        if (deviceId != null) {
            return deviceId;
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static boolean isPhone(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查SDCard是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取SDCard的地址
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static int[] getPhoneScreenWH(Context context) {
        int wh[] = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        wh[0] = dm.widthPixels;
        wh[1] = dm.heightPixels;
        return wh;
    }

    public static int dipTOpx(float dpValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxTodip(float pxValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void showInputMethodManager(final View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {

                    public void run() {
                        InputMethodManager inputManager =
                                (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(v, 0);
                    }
                },
                200);
    }

    /**
     * 关闭输入软键盘
     *
     * @param v
     */
    public static void closeInputMethodManager(View v) {
        InputMethodManager inputManager =
                (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
