/**  
        * @title ImageUtil.java  
        * @package com.orange.common.android.utils  
        * @description   
        * @author liuxiaokun  
        * @update 2012-12-27 下午7:23:51  
        * @version V1.0  
 */
package com.orange.barrage.android.util.misc;

import android.R.integer;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.activity.MessageCenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import roboguice.util.Ln;


public class ImageUtil
{



    public static Drawable getBitmapChangeDrawable(Bitmap bm){
        if(bm == null) return null;
        return new BitmapDrawable(ContextManager.getContext().getResources(), bm);
    }


    public static Bitmap getRoundBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();            
            Bitmap output = Bitmap.createBitmap(width,height, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;   
            final Paint paint = new Paint();   
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());          
            paint.setAntiAlias(true);   
            canvas.drawARGB(0, 0, 0, 0);   
            paint.setColor(color);   
            if (width<=height)
			{
            	canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(),(rect.width()/2)-3, paint);
			}else {
				canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(),(rect.height()/2)-3, paint);
			}
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));   
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
    }
    
    
    public static boolean saveImageToGallery(Bitmap bitmap,Context context)
	{
    	boolean flag = false;
    	String result = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
		if (result!=null)
		{
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, 
					Uri.parse("file://"+ Environment.getExternalStorageDirectory()))); 
			flag = true;
		}else {
			flag = false;
		}		
		return flag;
	}

    public static byte[] imageToJpeg(Bitmap bitmap, float quality)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, (int)(quality*100), stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            Ln.e(e, "<imageToJpeg> but catch exception" + e.toString());
        }

        return byteArray;
    }













}
