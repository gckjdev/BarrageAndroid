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

    private final int radius;
    private final int margin;  // dp

    private final int borderColor = Color.WHITE;

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {


        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle((source.getWidth() - margin)/2, (source.getHeight() - margin)/2, radius-2, paint);

        if (source != output) {
            source.recycle();
        }

        Paint paint1 = new Paint();
        paint1.setColor(borderColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(margin);
        canvas.drawCircle((source.getWidth() - margin)/2, (source.getHeight() - margin)/2, radius-2, paint1);


        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
