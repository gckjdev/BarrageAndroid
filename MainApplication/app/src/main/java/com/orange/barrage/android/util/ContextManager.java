package com.orange.barrage.android.util;

import android.content.Context;

/**
 * Created by Rollin on 2014/12/6.
 */
//用来管理Context(上下文的)
public class ContextManager {

    private static Context sApplicationContext;

    public static void init(Context context) {
        sApplicationContext = context;
    }
    //返回一个全局的Context对象
    public static Context getContext() {
        return sApplicationContext;
    }
}
