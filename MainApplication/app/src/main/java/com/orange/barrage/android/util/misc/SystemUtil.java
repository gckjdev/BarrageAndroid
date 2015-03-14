package com.orange.barrage.android.util.misc;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class SystemUtil {
	private static String deviceId = null;

	public static String getDeviceId(Context context) {
		if (deviceId != null) {
			return deviceId;
		}

		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	public static boolean isPhone(Context context){
		TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
            return false;
        }else{
            return true;
        }
	}

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
