package com.orange.barrage.android.util.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.orange.barrage.android.friend.ui.FriendTagView;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.barrage.android.util.misc.SystemUtil;

import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/14.
 */
public  class LayoutDrawIconBackground {

    private static final int GRIVITY_TOP = 0;

    private static final int GRIVITY_BOTTOM = 1;

    private View mParcent;
    private Params mParams;

    private EditText mEditText;

    public void setWhitTriangleRadioRoundFrectListenerBg(final View Parcent, final View child){


        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Parcent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setWhitTrianleRadioRoundFrectBg(Parcent ,child);

            }
        });
    }


    public void setWhitTrianleRadioRoundFrectBg(View parcent , View child){
        mParams = mParams != null ? mParams : new Params();
        child.setPadding(mParams.padding + mParams.marginLeft, mParams.padding + mParams.marginTop + mParams.mTopHeight, mParams.padding + mParams.marginRight, mParams.padding + mParams.marginBottopm);
        child.setBackground(ImageUtil.getBitmapChangeDrawable(getWhitTriangleRadioRoundFrectBgBitmap(getParams(), parcent, child)));
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

    public LayoutDrawIconBackground setParams(Params params){
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

//        FileUtil.saveBitmapInFile(SystemUtil.getSDCardPath() , "i.png" , bitmap);

        return bitmap;

    }


    /**
     *
     * @param canvas
     * @param rectfStartY 界面开始 y
     * @param rectfEndY 界面End y
     * @param grivity
     * @param p
     * @param params
     * @param child
     * @param parcent
     */
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









    public void setSemicircleRectangleBg(View v , Params params ){
        setSemicircleRectangleBg(v ,params, true);
    }

    public void setSemicircleRectangleBg(final View v ,  final Params params, final boolean isMove){

        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Bitmap bitmap = drawSemicircleRectang(v, params.getColor());
                v.setBackground(ImageUtil.getBitmapChangeDrawable(bitmap));
                if(isMove) {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    if(v instanceof EditText){
                        mEditText = ((EditText)v);
                        mEditText.addTextChangedListener(mTextWatcher);
                    }

                }
            }
        });

    }


    private TextWatcher mTextWatcher =  new TextWatcher() {

        private  int width = -1;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(width == - 1){
                width = (int) (mEditText.getWidth() - mEditText.getPaint().measureText(s.toString()));
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Paint p = mEditText.getPaint();
            float f = p.measureText(s.toString());
//                                int width = (int) (f + SystemUtil.dipTOpx(39));
            int width = (int) (f + this.width);
            ViewGroup.LayoutParams params = mEditText.getLayoutParams();
            params.width = width;
            mEditText.setLayoutParams(params);
        }
    };


    public void setTwoSemicircleRectangListener(final View v, final FriendTagView.Params params){

        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                setTwoSemicircleRectang(v , params);
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

    }

    public void setTwoSemicircleRectang(View v , FriendTagView.Params params){
        Drawable drawable = ImageUtil.getBitmapChangeDrawable(drawTwoSemicircleRectang(v, params));
        if(drawable != null)
            v.setBackground(drawable);
    }

    /**
     * 绘制两边都是半圆的矩形
     * @param v
     * @param params
     * @return
     */
    public Bitmap drawTwoSemicircleRectang(View v, FriendTagView.Params params){

        if(v.getHeight() <= 0 || v.getWidth() <= 0) return null;
        Rect mainRect = new Rect(0 , 0 , v.getWidth() , v.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth() , v.getHeight() , Bitmap.Config.ARGB_8888);
        Canvas canvas = getCanvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        DrawTwosemicirclerectangle(canvas , params , v , paint);

        canvas.drawBitmap(bitmap , mainRect , mainRect , paint);

        return bitmap;

    }



    private Bitmap drawSemicircleRectang(View v , int color){
        if(v.getWidth() == 0 || v.getHeight() == 0) return null;

        Bitmap bitmap = Bitmap.createBitmap(v.getWidth() , v.getHeight() , Bitmap.Config.ARGB_8888);


        Paint paint = getPaint(color , Paint.Style.FILL);
        Canvas canvas = getCanvas(bitmap);

        Rect mainRect = new Rect(0 , 0 , v.getWidth() , v.getHeight());

        Rect rect = new Rect(0 , 0 , v.getWidth() - v.getHeight() /2 , v.getHeight());

        //绘画矩形
        canvas.drawRect(rect , paint);

        RectF rectF = new RectF((float) (v.getWidth() - v.getHeight() + 1), 0 , v.getWidth() , v.getHeight());

        canvas.drawArc(rectF , 270 , 180 , true , paint);

        canvas.drawBitmap(bitmap , mainRect , mainRect , paint);

        return bitmap;
    }




    public  void DrawTwosemicirclerectangle(Canvas canvas , FriendTagView.Params params, View v , Paint p) {
        if (v == null || v.getWidth() == 0 || v.getHeight() == 0) return;
        if (params == null) params = new FriendTagView.Params();


        p.setStyle(Paint.Style.FILL);

        //绘制边框
        if (params.getBorderColor() != Integer.MAX_VALUE) {
            p.setColor(params.getBorderColor());
            drawTwosemicirclerectangleRun(canvas, p, 0, 0, v.getWidth(), v.getHeight(), v.getHeight() / 2);
        }


        //绘制背景
        if(params.getBgColor() != Integer.MAX_VALUE) {
            p.setColor(params.getBgColor() );
            drawTwosemicirclerectangleRun(canvas, p, 2, 2, v.getWidth() - 2, v.getHeight() - 2, v.getHeight() / 2 - 1);
        }

    }


    private void drawTwosemicirclerectangleRun(Canvas canvas , Paint paint , int startX , int startY , int stopX , int stopY , float raudio){

        //绘制矩形
        Rect rect = new Rect(((int)(raudio +startX)) - 1  , startY , ((int)(stopX - raudio)) , stopY);
        canvas.drawRect(rect, paint);

        RectF rectF = new RectF(stopX - raudio * 2  , startY , stopX , stopY);
        //绘制连个半圆
        canvas.drawArc(rectF , 270 , 180 , true , paint);
        rectF = new RectF(startX , startY , raudio * 2  , raudio * 2 );
        canvas.drawArc(rectF , 90 , 180 , true , paint);
    }

    private Paint getPaint(int color , Paint.Style flag){

        Paint paint = new Paint();
        paint.setStyle(flag);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }


    private Canvas getCanvas(Bitmap bitmap ){
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0 , 0, 0 ,0 );
        return canvas;
    }


    public Bitmap getCircleBg(Params params , View v){
        return getCirclrBg(params , v.getWidth() , v.getHeight());
    }

    public Bitmap getCirclrBg(Params params , int width ,  int height){
        mParams = params;
        Paint paint = getPaint(params.bgColor , Paint.Style.FILL);
        Bitmap bitmap = getNewBitmap(height , width);
        Canvas canvas = getCanvas(bitmap);
        canvas.drawARGB(0 , 0 , 0 ,0);
        drawCircle(canvas , paint , height , width);
        Rect rect = new Rect(0 , 0 , width , height);

        canvas.drawBitmap(bitmap , rect , rect , paint);
        return bitmap;
    }

    public void drawCircle(Canvas canvas ,Paint paint , int height , int width){
        paint.setColor(mParams.bgColor);
        float x = (float) (width / 2.0);
        float y = (float) (height / 2.0);
        float radio = x > y ? y : x;

        canvas.drawCircle(x , y , radio ,paint);
    }



    public Bitmap getNewBitmap(int height , int width){
        return Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
    }


    public static class Params{

        private boolean is = false;

        //是否有使用了
        public boolean isHaveUse = false;

        public int marginTop = 0;

        public int marginBottopm = 0;

        public int marginLeft = 0;

        public int marginRight = 0;

        public int bgColor = Integer.MAX_VALUE;

        public int borderColor = Integer.MAX_VALUE;

        public int color = Color.WHITE;

        public float alpha = 1.0f;

        public int mTriangleHeight = 20;

        public int mTopHeight = 20;

        public int padding = 10;



        public int textColor = Color.WHITE;
        public int textSize = 13;

        public int getColor(){
            int alphas = (int)(255 * alpha);
            return Color.argb(alphas , Color.red(bgColor) , Color.green(bgColor) , Color.blue(bgColor));

        }

//        public int getBgColor(){
//            return bgColor;
//        }
//
//        public int getBorderColor(){
//            return borderColor == Integer.MAX_VALUE ? bgColor : borderColor;
//        }

        public void setHaveUse(){
            if(!is){
                isHaveUse = true;
                is = true;
            }
        }

        public boolean getHaveUse(){
            return isHaveUse;
        }

    }

    public interface LayoutWhileTriangleIconInterface {

        public Params mParams = new Params();

        public LayoutDrawIconBackground mLayoutWhileTiangIcon = new LayoutDrawIconBackground();

        public void setParams(Params params);

        public void setView(View parcent , Params params);

        public void setView(View parcent);

    }


}
