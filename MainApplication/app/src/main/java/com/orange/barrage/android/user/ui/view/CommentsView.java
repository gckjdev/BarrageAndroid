package com.orange.barrage.android.user.ui.view;

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
import com.orange.barrage.android.user.model.UserManager;

import com.orange.barrage.android.util.view.LayoutDrawIconBackground;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import roboguice.util.Ln;


/**
 * Created by youjiannuo on 2015/3/9.
 */
public class CommentsView extends LinearLayout {


    private int TAG ;

    //输入文本类型
    public static final int COMMENTS_EDITTEXT = 1;
    //文字类型
    public static final int COMMENETS_TEXTVIEW = 2;

    Map<String , Integer > mMapsHeight = new HashMap<String , Integer>();



    private View mView;
    private UserAvatarView mUserAvatarView;
    private TextView mTextView;
    private EditText mEditText;

    private TextView mMainTextView;

    private int mColorbg = Color.BLACK;
    private float mAlphabg = 0.4f;

    @Inject
    UserManager mUserManager;

    @Inject
    public CommentsView(Context context){
        super(context);
        initView();
    }

    @Inject
    public CommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView(){

//        LayoutWhileTriangleIcon

        mView = LayoutInflater.from(getContext()).inflate(R.layout.activity_comments_head , this);

        mEditText = (EditText)findViewById(R.id.commentseditText);
        mTextView = (TextView)findViewById(R.id.commentsTextView);
        mUserAvatarView = (UserAvatarView)findViewById(R.id.head);

//        mUserAvatarView.loadUser(mUserManager.getUser());

//        LayoutDrawIconBackground layoutDrawIconBackground = new LayoutDrawIconBackground();


        LayoutDrawIconBackground.Params params = getParams();

        new LayoutDrawIconBackground().setSemicircleRectangleBg(mEditText, params, false);
        new LayoutDrawIconBackground().setSemicircleRectangleBg(mTextView, params, true);


//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
//        mUserAvatarView.setLayoutParams(params);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.y_morentouxiang);

//        mUserAvatarView.setImageBitmap(ImageDealTools.getRoundedCornerBitmap(bitmap));


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

    public void setTextColor(int color ){
        TextView tv = getTextView();
        tv.setTextColor( color);
    }


}
