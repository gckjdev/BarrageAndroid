package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;

/**
 * Created by Administrator on 2015/3/25.
 */
public class FriendTagItemView extends LinearLayout {


    private float mTextViewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
    private int b_friend_marginLeft = 14;

    private FriendTagView.Params mParams;

    public FriendTagItemView(Context context) {
        super(context);
        initView();
    }

    public FriendTagItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendTagItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView() {
        addView(getTagItemView());
    }


    public View getTagItemView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.view_friend_tag_item, null);
    }


    public void setTextViewAndLayoutParams(FriendTagView.Params param) {
        if (param == null) param = new FriendTagView.Params();
        mParams = param;

        TextView textView = (TextView) findViewById(R.id.nameTextView);
        setParams(param , false);

        Paint paint = textView.getPaint();
        textView.setPadding((int) (mTextViewHeight / 2), 0, (int) (mTextViewHeight / 2), 0);
        LayoutParams params = new LayoutParams((int) (mTextViewHeight + paint.measureText(textView.getText().toString())), (int) mTextViewHeight);

        params.rightMargin = (int) getDp(b_friend_marginLeft);

        textView.setLayoutParams(params);
        //设置背景图片
        setBackgroundListenerLayout(param);
    }


    //设置背景颜色
    public void setBackgroundListenerLayout(final FriendTagView.Params param) {
        mParams = param;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setBackground(param);
            }
        });


    }

    public void setBackground(FriendTagView.Params params) {
        new LayoutDrawIconBackground().setTwoSemicircleRectang(getTextView(), params);
    }

    public void setParams(FriendTagView.Params params ){
        setParams(params , true);
    }

    /**
     *
     * @param params
     * @param isRefreshBackgournd 是否需要刷新背景图片
     */
    public void setParams(FriendTagView.Params params, boolean isRefreshBackgournd) {
        if (params == null) return;

        mParams = mParams == params ? mParams : params;

        TextView textView = getTextView();
        textView.setText(params.title);
        textView.setTextColor(params.getTextColor());
        textView.setTextSize(params.textSize);
        textView.setLines(1);

        //刷新背景图片
        if (isRefreshBackgournd)
            setBackground(params);
    }

    public void setText(String title) {
        TextView textView = getTextView();
        textView.setText(title);
    }

    private float getDp(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public FriendTagView.Params getParams() {
        return mParams == null ? new FriendTagView.Params() : mParams;
    }


    public float getTextViewTextwidth() {
        TextView textView = getTextView();
        return textView.getPaint().measureText(textView.getText().toString());
    }

    public TextView getTextView() {
        return (TextView) findViewById(R.id.nameTextView);
    }

    public static FriendTagItemView CreateFriendItemView(Context context, FriendTagView.Params params) {
        FriendTagItemView friendTagItemView = new FriendTagItemView(context);

        friendTagItemView.setTextViewAndLayoutParams(params);

        return friendTagItemView;
    }

}
