package com.orange.barrage.android.user.ui.view;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;

import com.orange.barrage.android.util.imagecdn.ImageDealTools;


/**
 * Created by youjiannuo on 2015/3/9.
 */
public class CommentsView extends LinearLayout {


    private int TAG ;

    //输入文本类型
    public static final int COMMENTS_EDITTEXT = 1;
    //文字类型
    public static final int COMMENETS_TEXTVIEW = 2;


    private  View mView;
    private UserAvatarView mUserAvatarView;
    private TextView mTextView;
    private EditText mEditText;

    private TextView mMainTextView;

    public CommentsView(Context context){
        super(context);
        initView();
    }

    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView(){

        mView = LayoutInflater.from(getContext()).inflate(R.layout.activity_comments_head , this);

        mEditText = (EditText)findViewById(R.id.commentseditText);
        mTextView = (TextView)findViewById(R.id.commentsTextView);
        mUserAvatarView = (UserAvatarView)findViewById(R.id.head);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
//        mUserAvatarView.setLayoutParams(params);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.y_morentouxiang);

//        mUserAvatarView.setImageBitmap(ImageDealTools.getRoundedCornerBitmap(bitmap));

    }

    public void setType(int type){
        setType(type , false);
    }


    /**
     * 设置弹幕的类型
     */
    public void setType(int type ,boolean isFocusable){
        TAG = type;
        if(type == COMMENTS_EDITTEXT) mEditText.setVisibility(View.VISIBLE);
        else {

            mTextView.setFocusable(isFocusable());
            mTextView.setVisibility(View.VISIBLE);
        }
    }


    public String getText(){
        TextView tv = getTextView();
        return tv == null ? "" : tv.getText().toString();

    }

    public void setText(String text){
        TextView tv = getTextView();
        if(tv != null) tv.setText(text);
    }


    private TextView getTextView(){
        return TAG == COMMENETS_TEXTVIEW ? mTextView : mEditText;
    }

    public void setIconUrl(String url){

        if(mUserAvatarView != null)
            mUserAvatarView.setAvartUrl(url);

    }

    public void setIconBitmap(Bitmap bitmap){
        if(mUserAvatarView != null)
            mUserAvatarView.setImageBitmap(bitmap);
    }

    public void setTextColor(int color){
        if(color < 0 ) return ;
        TextView tv = getTextView();
        tv.setTextColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
            params.topMargin = (int) y;
            params.leftMargin = (int) x;
            setLayoutParams(params);
        }
        return super.onTouchEvent(event);
    }
}
