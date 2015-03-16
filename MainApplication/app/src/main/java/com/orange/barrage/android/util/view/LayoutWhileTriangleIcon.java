package com.orange.barrage.android.util.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewTreeObserver;

import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.barrage.android.util.misc.SystemUtil;

/**
 * Created by Administrator on 2015/3/14.
 */
public  class LayoutWhileTriangleIcon  {

    private static final int GRIVITY_TOP = 0;

    private static final int GRIVITY_BOTTOM = 1;

    private View mParcent;
    private Params mParams;



    public void setWhitTriangleRadioRoundFrectBg(final View Parcent , final View child){


        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mParams = mParams != null ? mParams : new Params();
                child.setPadding(mParams.padding + mParams.marginLeft, mParams.padding + mParams.marginTop +mParams.mTopHeight, mParams.padding + mParams.marginRight, mParams.padding + mParams.marginBottopm);
                child.setBackground(ImageUtil.getBitmapChangeDrawable(getWhitTriangleRadioRoundFrectBgBitmap(getParams(), Parcent, child)));
                Parcent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }



    public  Drawable getWhitTriangleRadioRoundFrectBgDrawle(Params params , View parcent , View child){
        return ImageUtil.getBitmapChangeDrawable(getWhitTriangleRadioRoundFrectBgBitmap(params, parcent, child));
    }

    public void setParcent(View parcent){
        mParcent = parcent;
    }
    public View getParcent(){
        return mParcent;
    }

    public LayoutWhileTriangleIcon setParams(Params params){
        mParams = params;
        return this;
    }

    public Params getParams(){
        return mParams;
    }

    public  Bitmap getWhitTriangleRadioRoundFrectBgBitmap(Params params , View parcent , View child){
        if(params == null) params = new Params();
        Bitmap bitmap = Bitmap.createBitmap(child.getWidth() , child.getHeight() , Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        canvas.drawARGB(0 , 0 , 0 , 0);
        Rect rectMain = new Rect( 0 , 0 , child.getWidth() , child.getHeight());

        Rect rect = new Rect();
        if(parcent == null) return null;
        parcent.getGlobalVisibleRect(rect);
        int xy[] = SystemUtil.getPhoneScreenWH(ContextManager.getContext());

        int gravity = getBestGravityY(xy , rect , child);
        if(gravity == GRIVITY_TOP){
            DrawTopAndBottom(canvas , params.mTopHeight , child.getHeight() , gravity , paint , params , child , parcent);
        }else if(gravity == GRIVITY_BOTTOM){
            DrawTopAndBottom(canvas , 0 , child.getHeight() - params.mTopHeight , gravity , paint, params , child, parcent);
        }

        canvas.drawBitmap(bitmap , rectMain,rectMain,paint);

        FileUtil.saveBitmapInFile(SystemUtil.getSDCardPath() , "i.png" , bitmap);

        return bitmap;

    }



    private  void DrawTopAndBottom(Canvas canvas , int rectfStartY , int rectfEndY , int grivity , Paint p , Params params , View child , View parcent){


        p.setColor(params.bgColor);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
        RectF oval3 = new RectF(0 + params.marginLeft, rectfStartY, child.getWidth() - params.marginRight, rectfEndY);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 12, 7, p);//第二个参数是x半径，第三个参数是y半径
        int h = params.mTriangleHeight;
        float l = getBestX(h + 15 , parcent , child);

        p.setStyle(Paint.Style.FILL);//设置空心

        Path path1=new Path();
        path1.moveTo(l - h, grivity == GRIVITY_TOP ? h : child.getHeight() );
        path1.lineTo(l, grivity == GRIVITY_TOP ? 0 : h + child.getHeight());
        path1.lineTo( l + h, grivity == GRIVITY_TOP ? h : child.getHeight());
        path1.close();//封闭
        canvas.drawPath(path1, p);

    }


    private  float getBestX(int h , View parcent , View child){
        int l = 0;
        Rect rect = new Rect();
        if(parcent == null) return l;
        parcent.getGlobalVisibleRect(rect);

        int xy[] = SystemUtil.getPhoneScreenWH(ContextManager.getContext());
        float right = (float) (xy[0] - rect.centerX());
        float left = (float) (rect.left + parcent.getWidth() / 2.0);

        if(right > child.getWidth() / 2.0 && left > child.getWidth() / 2.0){
            return (float) (child.getWidth() / 2.0);
        }else if (left <= child.getWidth()/ 2.0 ) return left <= h ? h : left;
        else if(right <= child.getWidth() / 2.0) return  right < h  ? child.getWidth() - h  : child.getWidth() - right ;

        return l;

    }

    private  int  getBestGravityY(int XY[] , Rect rect , View child){

        float h = 0;

        float top = rect.top;
        float bottom = XY[1] - rect.bottom;

        if(top > child.getHeight() && bottom < child.getHeight() ){

            return GRIVITY_BOTTOM;

        }

        return GRIVITY_TOP;


    }




    public static class Params{

        public int marginTop = 0;

        public int marginBottopm = 0;

        public int marginLeft = 0;

        public int marginRight = 0;

        public int bgColor = Color.WHITE;

        public int mTriangleHeight = 30;

        public int mTopHeight = 20;

        public int padding = 10;
    }

    public interface LayoutWhileTriangleIconInterface {

        public Params mParams = new Params();

        public LayoutWhileTriangleIcon mLayoutWhileTiangIcon = new LayoutWhileTriangleIcon();

        public void setParams(Params params);

        public void setView(View parcent , Params params);

        public void setView(View parcent);

    }



}
