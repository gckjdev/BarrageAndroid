package com.orange.barrage.android.util.misc;

import android.os.Build;

public class UiUtil {
    
    public static int sdkVersion() {
    	return new Integer(Build.VERSION.SDK).intValue();
    }
}
