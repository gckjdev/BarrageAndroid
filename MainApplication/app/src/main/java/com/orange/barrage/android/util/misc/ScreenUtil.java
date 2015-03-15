package com.orange.barrage.android.util.misc;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.orange.barrage.android.util.ContextManager;

/**
 * Created by Rollin on 2015/2/7.
 */
public class ScreenUtil {

    public static int getHeightPixels(){
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getWidthPixels(){
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private static Display getDisplay(){

        WindowManager manager = (WindowManager) ContextManager.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display;
    }
}
