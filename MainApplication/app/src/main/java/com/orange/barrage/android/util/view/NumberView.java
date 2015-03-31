package com.orange.barrage.android.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.AttributeSet;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.util.misc.ImageUtil;

/**
 * Created by Administrator on 2015/3/30.
 */
public class NumberView extends TextView {

    private Params mParams = new Params();

    private Params mParamsCace;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setParams(Params params){
        if(!mParams.text.equals(params.text)) {
            setTextViewParams(params);
        }
        if(mParams.textSize != mParams.textSize){
            setTextSize(params.textSize);
        }
        if(mParams.textColor != mParams.textColor){
            setTextColor(params.textColor);
        }
        if(mParams.bgColor != params.bgColor)
            invalidate();

        initParams(params);

    }

    private void setTextViewParams(Params params){
        String text = " "+params.text+" ";

        setText(text);
        Paint paint = getPaint();
        float width = paint.measureText(text);
        setLayoutParams((int)width);

        Bitmap bitmap = new LayoutDrawIconBackground().getCirclrBg(params , (int)width , (int)width);
        setBackground(ImageUtil.getBitmapChangeDrawable(bitmap));
    }

    private void setLayoutParams(int width){
        ViewGroup.LayoutParams params = getLayoutParams();

        if(params instanceof RelativeLayout.LayoutParams){
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) params;
            param.leftMargin = (int) (-1 * width / 1.2);
            param.topMargin = (int) (-1 * width / 3.6);
            param.height = width;
            setLayoutParams(param);
        }else if(params instanceof  LinearLayout.LayoutParams){
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) params;
            param.leftMargin = (int) (-1 * width / 1.2);
            param.topMargin = -1 * width / 3;
            param.height = width;
            setLayoutParams(param);

        }else if(params instanceof FrameLayout.LayoutParams){
            FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) params;
            param.leftMargin = (int) (-1 * width / 1.2);
            param.topMargin = -1 * width / 3;
            param.height = width;
            setLayoutParams(param);
        }

    }

    private void initParams(Params params){
        mParamsCace = params;
        mParams.bgColor = params.bgColor;
        mParams.text = params.text;
        mParams.textColor = params.textColor;
        mParams.textSize = params.textSize;
    }

    public Params getParams(){
        return mParamsCace == null ? Params.Build() : mParams;
    }







    public static class Params extends LayoutDrawIconBackground.Params{

        public String text = "";


        public static Params Build(){
            return new Params();
        }

    }



}
