package com.orange.barrage.android.util.imagecdn;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 *
 * Image deal tools
 * Created by youjiannuo on 2015/3/6.
 */
public class ImageDealTools {


    /**
     * 将一张图片变成圆形
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() );
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getHeight() / 2 > bitmap.getWidth()/2 ? bitmap.getWidth()/2 : bitmap.getHeight() / 2 ;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);


//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2 , bitmap.getHeight() / 2 , roundPx , paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void DrawRoundStroke(Canvas canvas , int width , int height , int strokeColor ){

        //画边框
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(strokeColor);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        float x = width / 2;
        float y = height / 2;
        canvas.drawCircle( x , y ,( x > y ? y : x) - 1  , paint );
    }


    /**
     * 获取一个圆
     * @return
     */
    public static Bitmap getBitmapCircle(int color , int radius){
        Bitmap bitmap = Bitmap.createBitmap(radius * 2 , radius * 2 , Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        Canvas canvas = new Canvas(bitmap);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); //因为我们先画了图所以DST_IN
        canvas.drawCircle(radius, radius, radius, paint); //r=radius, 圆心(r,r)

        return bitmap;
    }



}
