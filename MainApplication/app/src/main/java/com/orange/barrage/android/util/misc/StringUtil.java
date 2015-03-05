package com.orange.barrage.android.util.misc;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roboguice.util.Ln;

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


    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;


        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;


        Pattern pattern = Pattern.compile(expression);


        Matcher matcher = pattern.matcher(inputStr);


        if (matcher.matches()) {
            isValid = true;
        }


        return isValid;
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


}
