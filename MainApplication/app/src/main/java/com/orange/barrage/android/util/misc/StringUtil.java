package com.orange.barrage.android.util.misc;

import com.loopj.android.http.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String md5base64encode(String input) {
        try {
            if (input == null)
                return null;

            if (input.length() == 0)
                return null;

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes("UTF-8"));
            byte[] enc = md.digest();
            String base64str = Base64.encodeToString(enc, 0);
            return base64str;

        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }

    }

    public static String base64encode(String input) {
        if (input == null)
            return null;

        if (input.length() == 0)
            return null;

        String base64str = Base64.encodeToString(input.getBytes(), 0);
        return base64str;
    }
}
