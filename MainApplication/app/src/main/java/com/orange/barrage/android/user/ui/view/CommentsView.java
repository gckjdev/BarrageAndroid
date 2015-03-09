package com.orange.barrage.android.user.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.topic.model.User;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class CommentsView extends ViewGroup {


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



    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void initView(){

        mView = LayoutInflater.from(getContext()).inflate(R.layout.activity_comments_head , this);

        mEditText = (EditText)findViewById(R.id.commentseditText);
        mTextView = (TextView)findViewById(R.id.commentsTextView);
        mUserAvatarView = (UserAvatarView)findViewById(R.id.head);
    }


    /**
     * 设置弹幕的类型
     */
    public void setType(int type){
        TAG = type;
        if(type == COMMENTS_EDITTEXT) mEditText.setVisibility(View.VISIBLE);
        else mTextView.setVisibility(View.VISIBLE);
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
            mUserAvatarView.setImageBitmap(url);

    }

    public void setIconBitmap(Bitmap bitmap){
        if(mUserAvatarView != null)
            mUserAvatarView.setImageBitmap(bitmap);
    }

    public void setTextColor(int color){
        TextView tv = getTextView();
        tv.setTextColor(color);
    }

}
