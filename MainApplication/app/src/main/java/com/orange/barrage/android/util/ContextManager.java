package com.orange.barrage.android.util;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Rollin on 2014/12/6.
 */
public class ContextManager {

    private static Context sApplicationContext;

    public static void init(Context context) {
        sApplicationContext = context;
    }

    public static Context getContext() {
        return sApplicationContext;
    }
}
