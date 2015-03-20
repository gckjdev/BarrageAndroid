package com.orange.barrage.android.util.misc;

import android.util.Base64;

import java.util.UUID;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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



    public static String md5base64encode(String input) {
        try {
            if (input == null)
                return null;

            if (input.length() == 0)
                return null;

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes("UTF-8"));
            byte[] enc = md.digest();
            String base64str = Base64.encodeToString(enc, Base64.NO_WRAP);
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

        String base64str = Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
        return base64str;
    }


    public static String exChange(String str){
        StringBuffer sb = new StringBuffer();
        if(str!=null){
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
                if(Character.isUpperCase(c)){
                    sb.append(Character.toLowerCase(c));
                }else if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }
            }
        }

        return sb.toString();
    }


}
