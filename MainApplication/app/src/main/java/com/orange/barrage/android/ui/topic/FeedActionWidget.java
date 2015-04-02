package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Callback;

/**
 * Action widget for feed
 */
public class FeedActionWidget extends LinearLayout {

    //输入文本类型
    public static final int COMMENTS_EDITTEXT = 1;
    //文字类型
    public static final int COMMENETS_TEXTVIEW = 2;

    //it's type, default in text view
    private int TAG = COMMENETS_TEXTVIEW;

    private View mView;
    private UserAvatarView mUserAvatarView;
    private TextView mTextView;
    private EditText mEditText;
    private BarrageProtos.PBFeedAction mFeedAction;

    private TextView mEditextRight;
    private TextView mTextRight;


    private int mColorbg = Color.BLACK;
    private float mAlphabg = 0.4f;

    public FeedActionWidget(Context context){
        this(context, null);
    }

    public FeedActionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_feed_action_widget, this);

        mEditText = (EditText)findViewById(R.id.commentseditText);
        mTextView = (TextView)findViewById(R.id.commentsTextView);
        mUserAvatarView = (UserAvatarView)findViewById(R.id.head);
        mEditextRight = (TextView) findViewById(R.id.commentseditTextRight);
        mTextRight = (TextView) findViewById(R.id.commentsTextViewRight);

        LayoutDrawIconBackground.Params params = getParams();


        new LayoutDrawIconBackground().setSemicircleRectangleBg(mEditText, params, false);

        new LayoutDrawIconBackground().setSemicircleRectangleBg(mTextView, params, true);



    }


    private LayoutDrawIconBackground.Params getParams(){
        LayoutDrawIconBackground.Params params = new LayoutDrawIconBackground.Params();
        params.bgColor = mColorbg;
        params.alpha = mAlphabg;

        return params;
    }

    public void setType(int type){
        setType(type , false);
    }

    /**
     * 设置弹幕的类型
     */
    public void setType(int type ,boolean isFocusable){
        TAG = type;
        if(type == COMMENTS_EDITTEXT) {
            mTextView.setVisibility(View.GONE);
            mTextRight.setVisibility(View.GONE);

            mEditText.setVisibility(View.VISIBLE);
        } else {
            mEditText.setVisibility(View.GONE);
            mEditextRight.setVisibility(View.GONE);

            mTextView.setFocusable(isFocusable);
            mTextView.setVisibility(View.VISIBLE);
//            mTextRight.setVisibility(View.VISIBLE);
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
        setIconUrl(url , null);
    }

    public void setIconUrl(String url ,Callback callback){
        if (mUserAvatarView != null) {
            mUserAvatarView.setAvartUrl(url , callback);
        }
    }

    public void setIconBitmap(Bitmap bitmap){
        if(mUserAvatarView != null){
            mUserAvatarView.setImageBitmap(bitmap);
        }
    }

    public void setUser(UserProtos.PBUser user){
        mUserAvatarView.loadUser(user);
    }

    public BarrageProtos.PBFeedAction getFeedAction() {
        return mFeedAction;
    }

    public void setFeedAction(BarrageProtos.PBFeedAction feedAction){
        setFeedAction(feedAction , null);
    }

    public void setFeedAction(BarrageProtos.PBFeedAction feedAction , Callback callback){
        mFeedAction = feedAction;
        setIconUrl(feedAction.getAvatar() , callback);
//        UserProtos.PBUser user = feedAction.getUser();
//        setUser(user);
        setText(feedAction.getText());

        // FIXME incorrect color, need conversion
        setTextColor(feedAction.getColor());
    }

    public void setTextColor(int color ){
        TextView tv = getTextView();
        tv.setTextColor( color);
    }
}

