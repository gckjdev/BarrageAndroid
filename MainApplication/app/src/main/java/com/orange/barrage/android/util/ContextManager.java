package com.orange.barrage.android.util;

import android.content.Context;

/**
 * Created by Rollin on 2014/12/6.
 */
public class ContextManager {

    private static Context sContext;

    public static void init(Context context){
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

}
