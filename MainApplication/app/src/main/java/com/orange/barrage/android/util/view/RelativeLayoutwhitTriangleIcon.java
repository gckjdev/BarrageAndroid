package com.orange.barrage.android.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015/3/14.
 */
public class RelativeLayoutwhitTriangleIcon extends RelativeLayout implements LayoutDrawIconBackground.LayoutWhileTriangleIconInterface {




//    private static final int GRIVITY_TOP = 0;
//
//    private static final int GRIVITY_BOTTOM = 1;

    private LayoutDrawIconBackground.Params mPparams;

    public RelativeLayoutwhitTriangleIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(mParams.padding , mParams.padding + mParams.mTopHeight , mParams.padding , mParams.padding);
    }


    @Override
    public void setParams(LayoutDrawIconBackground.Params params){
        if(params == null) return;
        setPadding(params.padding + params.marginLeft, params.padding + params.marginTop, params.padding + params.marginRight, params.padding + params.marginBottopm);
        invalidate();
    }


    @Override
    public void setView( View v ,  LayoutDrawIconBackground.Params params){
        mLayoutWhileTiangIcon.setParcent(v);
        setParams(params);
        getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    @Override
    public void setView(View parcent) {
        setView(parcent , null);
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            setBackground(mLayoutWhileTiangIcon.getWhitTriangleRadioRoundFrectBgDrawle(mLayoutWhileTiangIcon.getParams() , mLayoutWhileTiangIcon.getParcent() , RelativeLayoutwhitTriangleIcon.this));
            RelativeLayoutwhitTriangleIcon.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        Paint p = new Paint();
//        p.setColor(Color.BLACK);
//        p.setAntiAlias(true);// 设置画笔的锯齿效果
//        RectF oval3 = new RectF(0, mParams.mTopHeight, getWidth(), getHeight());// 设置个新的长方形
//        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
//        int h = mParams.mTriangleHeight;
//        float l = getBestX(h + 10);
//        p.setStyle(Paint.Style.FILL);//设置空心
//
//        Path path1=new Path();
//        path1.moveTo(l - h, h );
//        path1.lineTo(l, 0);
//        path1.lineTo( l + h, h);
//        path1.close();//封闭
//        canvas.drawPath(path1, p);

//        setBackground(ImageUtil);
//
//    }

//    private Bitmap draweBitmap(){
//
//        Bitmap bitmap = Bitmap.createBitmap(getWidth() , getHeight() , Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        canvas.drawARGB(0 , 0 , 0 , 0);
//        Rect rectMain = new Rect( 0 , 0 , getWidth() , getHeight());
//
//        Rect rect = new Rect();
//        if(mView == null) return null;
//        mView.getGlobalVisibleRect(rect);
//        int xy[] = SystemUtil.getPhoneScreenWH(getContext());
//
//        int gravity = getBestGravityY(xy , rect);
//        if(gravity == GRIVITY_TOP){
//            DrawTopAndBottom(canvas , mParams.mTopHeight , getHeight() , gravity , paint);
//        }else if(gravity == GRIVITY_BOTTOM){
//            DrawTopAndBottom(canvas , 0 , getHeight() - mParams.mTopHeight , gravity , paint);
//        }
//
//        canvas.drawBitmap(bitmap , rectMain,rectMain,paint);
//        FileUtil.saveBitmapInFile(SystemUtil.getSDCardPath() , "y.png" , bitmap);
//        return bitmap;
//
//    }



//    private void DrawTopAndBottom(Canvas canvas , int rectfStartY , int rectfEndY , int grivity , Paint p ){
//
//
//        p.setColor(mParams.bgColor);
//        p.setAntiAlias(true);// 设置画笔的锯齿效果
//        RectF oval3 = new RectF(0 + mParams.marginLeft, rectfStartY, getWidth() - mParams.marginRight, rectfEndY);// 设置个新的长方形
//        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
//        int h = mParams.mTriangleHeight;
//        float l = getBestX(h + 10);
//
//        p.setStyle(Paint.Style.FILL);//设置空心
//
//        Path path1=new Path();
//        path1.moveTo(l - h, grivity == GRIVITY_TOP ? h : getHeight() );
//        path1.lineTo(l, grivity == GRIVITY_TOP ? 0 : h + getHeight());
//        path1.lineTo( l + h, grivity == GRIVITY_TOP ? h : getHeight());
//        path1.close();//封闭
//        canvas.drawPath(path1, p);
//
//    }


//    private float getBestX(int h){
//        int l = 0;
//        Rect rect = new Rect();
//        if(mView == null) return l;
//        mView.getGlobalVisibleRect(rect);
//
//        int xy[] = SystemUtil.getPhoneScreenWH(getContext());
//        float right = (float) (xy[0] - rect.centerX());
//        float left = (float) (rect.left + mView.getWidth() / 2.0);
//
//        if(right > getWidth() / 2.0 && left > getWidth() / 2.0){
//            return (float) (getWidth() / 2.0);
//        }else if (left <= getWidth()/ 2.0 ) return left <= h ? h : left;
//        else if(right <= getWidth() / 2.0) return  right < h  ? getWidth() - h  : getWidth() - right ;
//
//        return l;
//
//    }

//    private int getBestGravityY(int XY[] , Rect rect){
//
//        float h = 0;
//
//        float top = rect.top;
//        float bottom = XY[1] - rect.bottom;
//
//        if(top > getHeight() && bottom < getHeight() ){
//
//            return GRIVITY_BOTTOM;
//
//        }
//
//        return GRIVITY_TOP;
//
//
//    }





//    public class Params{
//
//        public int marginTop = 0;
//
//        public int marginBottopm = 0;
//
//        public int marginLeft = 0;
//
//        public int marginRight = 0;
//
//        public int bgColor = Color.RED;
//
//        public int mTriangleHeight = 30;
//
//        public int mTopHeight = 20;
//
//    }





}
