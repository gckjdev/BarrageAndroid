package com.orange.barrage.android.util.misc;

import java.util.UUID;

public class StringUtil {
	public static boolean isEmpty(String str){
		return (str == null || str.length() == 0||str.trim().equalsIgnoreCase(""));
	}

	public static boolean isNotEmpty(String str){
		return !(str == null || str.length() == 0);
	}


    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
