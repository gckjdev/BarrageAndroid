package com.orange.barrage.android.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.misc.ScreenUtil;

import roboguice.util.Ln;

/**
 * It's a scalable FrameLayout.
 * Provide actual and expected height and width, the inner view will be scale from expected size
 * to actual size automatically.
 *
 * Created by Rollin on 2015/3/16.
 *
 * @see com.orange.barrage.android.ui.topic.PictureTopicMainWidget
 */
public abstract class ScalableFrameLayout<T extends FrameLayout> extends FrameLayout {

    protected T mInnerView;

    private int mActualWidth;
    private int mActualHeight;

    public ScalableFrameLayout(Context context) {
        this(context, null);
    }

    public ScalableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInnerView = createInnerView(context);
        addView(mInnerView);
    }

    /**
     * Return initialize inner view to scale
     **/
    protected abstract T createInnerView(Context context);

    /**
     * Return initialize inner view's expected height, in px usually
     **/
    protected abstract float getExpectedHeight();

    /**
     * Return initialize inner view's expected width, in px usually
     **/
    protected abstract float getExpectedWidth();

    public void initActualWidth(float actualWidth ){
        float expectWidth = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        //float actualWidth = ScreenUtil.getWidthPixels();

        float expectHeight = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);
        float factor = actualWidth/expectWidth;
        float actualHeight = expectHeight * factor;

        setSize((int)actualWidth, (int)actualHeight);
    }
    /**
     * Set actual width and height for layout
     **/
    public void setSize(int width, int height){
        mActualWidth = width;
        mActualHeight = height;

        float expectWidth = getExpectedWidth();
        float actualWidth = width;

        float expectHeight = getExpectedHeight();
        float actualHeight = height;

        float scaleX = actualWidth/expectWidth;
        float scaleY = actualHeight/expectHeight;

        Ln.d("scaleX: %.2f , scaleY: %.2f", scaleX, scaleY);
        getInnerView().setPivotX(0);
        getInnerView().setPivotY(0);

        getInnerView().setScaleX(scaleX);
        getInnerView().setScaleY(scaleY);
    }

     public T getInnerView(){
         return mInnerView;
     }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec){
        return mActualWidth;
    }

    private int measureHeight(int heightMeasureSpec){
        return mActualHeight;
    }

}
