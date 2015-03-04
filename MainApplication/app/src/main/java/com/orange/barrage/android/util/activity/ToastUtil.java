package com.orange.barrage.android.util.activity;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class ToastUtil {

    public static void makeTextShort(String s,Context context){
        Toast.makeText(context , s , Toast.LENGTH_SHORT).show();
    }

    public static void makeTextShort(int resid, Context context){
        makeTextShort(context.getString(resid) , context);
    }

    public static void makeTextLong(String s,Context context){
        Toast.makeText(context , s , Toast.LENGTH_LONG).show();
    }

    public static void makeTextLong(int resid, Context context){
        makeTextLong(context.getString(resid) , context);
    }

}
