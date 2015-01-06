/**  
        * @title DateUtil.java  
        * @package com.orange.common.android.utils  
        * @description   
        * @author liuxiaokun  
        * @update 2013-1-9 下午4:58:51  
        * @version V1.0  
 */
package com.orange.barrage.android.util.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.util.Log;


public class DateUtil
{

	private static final String TAG = "DateUtil";

	public static String dateFormatToString(int date,Context context){
		String dateString = "";
		long currentTime = System.currentTimeMillis()/1000;
		int dateTime = (int)(currentTime-date)/60;
		if (date == 0)
		{
			return dateString;
		}
		if(dateTime>4320){
			dateString = getDateString(date);
		}
		else if(dateTime>1440&&dateTime<4320){
			dateString = dateTime/1440+"天前"; //context.getString(R.string.day_before);
		}else if (dateTime>60&&dateTime<1440) {
			dateString = dateTime/60+"小时前"; //context.getString(R.string.hour_before);
		}else {
			dateString = (dateTime/60<1?1:dateTime/60) +"分钟前"; //context.getString(R.string.minute_before);
		}
		return dateString;
	}
	
	
	public static String getDateString(long time) {
		Calendar date = Calendar.getInstance();
		time = time*1000;
		//long targetTime = time - TimeZone.getDefault().getRawOffset();
		date.setTimeInMillis(time);
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString =dateformat.format(date.getTime());
		return dateString;
	}

    public static String CHINA_TIMEZONE = "GMT+0800";

    // format example "dd/MM/yyyy-hh:mm:ss"
    public static String dateToStringByFormat(Date date, String format, boolean isChinaTimeZone) {
        if (date == null || format == null)
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if (isChinaTimeZone){
            dateFormat.setTimeZone(TimeZone.getTimeZone(CHINA_TIMEZONE));
        }
        else {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return dateFormat.format(date);
    }

    public static int getNowTime() {
        return (int)(System.currentTimeMillis()/1000);
    }
}
