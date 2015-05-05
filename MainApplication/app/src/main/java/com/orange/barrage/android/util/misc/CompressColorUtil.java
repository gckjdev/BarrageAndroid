package com.orange.barrage.android.util.misc;

import android.graphics.Color;

import roboguice.util.Ln;

public class CompressColorUtil {

    public static int toBarrageColor(int androidColor){

        float alpha = Color.alpha(androidColor);
        float red = Color.red(androidColor);
        float green = Color.green(androidColor);
        float blue = Color.blue(androidColor);
        int barrageColor = (int)compressColor8WithRed((int)red, (int)green, (int)blue, (int)alpha);
        //alter youjiannuo time 2015/4/7
        d(red , green , blue , alpha);
        return barrageColor;
    }




    public static int toAndroidColor(int barrageColor){

        float alpha = getAlphaFromColor8(getUnsignedInt(barrageColor));
        float red = getRedFromColor8(getUnsignedInt(barrageColor));
        float green = getGreenFromColor8(getUnsignedInt(barrageColor));
        float blue = getBlueFromColor8(getUnsignedInt(barrageColor));
        //alter youjiannuo time 2015/4/7
        d(red , green , blue , alpha);
        return Color.argb((int)alpha, (int)red, (int)green, (int)blue);

    }


    static void d(float red , float green , float blue , float alpha){
        Ln.d(" red("+red+") green("+green+") blue("+blue+") alpha("+alpha+")");
    }

	private static int getInt(float f){
		float value = f * 255f;
		return (int)value;
	}

    public static long getUnsignedInt (int data){     //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFFl;
    }

	public static int compressColor8WithInt(int red, int green, int blue, int alpha){
		int ret = (alpha) +
				((blue) << 8) +
				((green) << 16) +
				((red) << 24);
		return ret;
	}

	public static long compressColor8WithRed(int red, int green, int blue, int alpha){
		long ret = (long)(alpha) +
				((long)(blue) << 8) +
				((long)(green) << 16) +
				((long)(red) << 24);
		return ret;
	}
	// new compress, with 8 bits for alpha
	public static long compressColor8WithRed(float red, float green, float blue, float alpha){
		long ret = (long)(alpha * 255.0f) +
	                     ((long)(blue * 255.0f) << 8) +
	                     ((long)(green * 255.0f) << 16) +
	                     ((long)(red * 255.0f) << 24);
	    return ret;
	}
	
	// old compress, with 6 bits for alpha
	public static long compressColor6WithRed(float red, float green, float blue, float alpha){
		long ret = (long)(alpha * 63.0f) +
	                     ((long)(blue * 255.0f) << 6) +
	                     ((long)(green * 255.0f) << 14) +
	                     ((long)(red * 255.0f) << 22);
	    return ret;
	}

	public static float getRedFromColor8(long longColor){
		return ((longColor >> 24) % (1<<8)); // / 255.0f;
	}

	public static float getGreenFromColor8(long longColor){
		return  ((longColor >> 16) % (1<<8)); // / 255.0f;
	}
	
	public static float getBlueFromColor8(long longColor){
		return ((longColor >> 8) % (1<<8)); // / 255.0f;
	}
	
	public static float getAlphaFromColor8(long longColor){
		return (longColor % (1<<8)); // / 255.0f;
	}	
	
	public static float getRedFromColor6(long longColor){
		return ((longColor >> 22) % (1<<8)) / 255.0f;
	}

	public static float getGreenFromColor6(long longColor){
		return  ((longColor >> 14) % (1<<8)) / 255.0f;
	}
	
	public static float getBlueFromColor6(long longColor){
		return ((longColor >> 6) % (1<<8)) / 255.0f;
	}
	
	public static float getAlphaFromColor6(long longColor){
		return (longColor % (1<<6)) / 63.0f;
	}		
}
