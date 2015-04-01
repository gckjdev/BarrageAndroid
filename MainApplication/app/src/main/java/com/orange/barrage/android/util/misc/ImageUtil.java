/**
 * @title ImageUtil.java
 * @package com.orange.common.android.utils
 * @description
 * @author liuxiaokun
 * @update 2012-12-27 下午7:23:51
 * @version V1.0
 */
package com.orange.barrage.android.util.misc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.orange.barrage.android.util.ContextManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import roboguice.util.Ln;


public class ImageUtil {
    public static Drawable getBitmapChangeDrawable(Bitmap bm) {
        if (bm == null) return null;
        return new BitmapDrawable(ContextManager.getContext().getResources(), bm);
    }


    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        if (width <= height) {
            canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), (rect.width() / 2) - 3, paint);
        } else {
            canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), (rect.height() / 2) - 3, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static boolean saveImageToGallery(Bitmap bitmap, Context context) {
        boolean flag = false;
        String result = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
        if (result != null) {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public static byte[] imageToJpeg(Bitmap bitmap, float quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, (int) (quality * 100), stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            Ln.e(e, "<imageToJpeg> but catch exception" + e.toString());
        }

        return byteArray;
    }


    //获取之类所在父类的背景图片
    public static Bitmap getChildBitmap(View child, View parcent) {
        float XY[] = {0, 0};
        getChildFromParencetXY(child, parcent, XY);

        Bitmap bitmap = getViewBitmapCache(parcent);
        if (bitmap == null) return null;

        return Bitmap.createBitmap(bitmap, (int) XY[0], (int) XY[1], child.getWidth(), child.getHeight());
    }


    private static void getChildFromParencetXY(View child, View parcent, float XY[]) {

        boolean is = false;

        if (child.getParent() == parcent) {
            is = true;
        }

        XY[0] += child.getX();
        XY[1] += child.getY();

        if (is) return;
        else getChildFromParencetXY((View) child.getParent(), parcent, XY);
    }


    //获取某一个Vie的截图
    //该方法不可以放在Oncreate里面执行，否则获取不到
    public static Bitmap getViewBitmapCache(View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getWidth(), v.getHeight());
        v.buildDrawingCache();
        return v.getDrawingCache();
    }


    /**
     * 通过图片可以获取那个颜色显示的最清楚，
     *
     * @param bm
     * @return 白色或者黑色
     */

    public static int getColorBitmap(Bitmap bm){

        FileUtil.savePhotoToSDCard(bm, SystemUtil.getSDCardPath(), "you.png");

        int bw[] = {0, 0};
        for (int i = 0; i < bm.getHeight(); i++) {
            for (int j = 0; j < bm.getWidth(); j++)
                getColor(bm.getPixel(j, i), bw);
        }
        //Ln.e("sdhsds:"+bw[0]+"   "+bw[1]);
        return bw[0] < bw[1] ? Color.BLACK : Color.WHITE;
    }


    private static void getColor(int color, int bw[]) {

        int red = Color.red(color);
        int bule = Color.blue(color);
        int green = Color.green(color);

        int gray = (int) (red * 0.3 + green * 0.59 + bule * 0.11);
        if (gray < 127.5f)
            bw[0] += 1;
        else
            bw[1] += 1;

    }


}
