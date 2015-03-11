package com.orange.barrage.android.util.misc.image;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by pipi on 15/3/6.
 */
public class RoundedTransformation implements com.squareup.picasso.Transformation {

    /*
            Picasso.with(mContext)
                .load(url)
                .transform(new RoundedTransformation(50, 4))
                .resize(100, 100)
                .placeholder(R.drawable.tab_home)
                .error(R.drawable.tab_friend)
                .into(this, null);

     */

    private final int borderColor = Color.WHITE;
    private int borderWidth = 4;

    // borderWidth is the board in dp
    public RoundedTransformation(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    @Override
    public Bitmap transform(final Bitmap source) {


        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle((width - borderWidth)/2, (height - borderWidth)/2, width/2-borderWidth, paint);

        if (source != output) {
            source.recycle();
        }

        Paint paint1 = new Paint();
        paint1.setColor(borderColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(borderWidth);
        canvas.drawCircle((width - borderWidth)/2, (height - borderWidth)/2, width/2-borderWidth, paint1);
//        canvas.drawCircle((source.getWidth() - margin)/2, (source.getHeight() - margin)/2, radius-margin, paint1);

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
