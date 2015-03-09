package com.orange.barrage.android.util.System;

import android.os.Environment;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class SystemTools {

    /**
     * 检查SDCard是否存在
     * @return
     */
    public static boolean checkSDCardAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取SDCard的地址
     * @return
     */
    public static String getSDCardPath(){
        return  Environment.getExternalStorageDirectory().getAbsolutePath();
    }





}
