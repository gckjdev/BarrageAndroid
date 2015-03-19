package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.protocol.message.UserProtos;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconItem extends LinearLayout implements View.OnClickListener{

    private UserAvatarView mIconImageView;
    private ImageButton mdeleteIconImageView;
    private TextView mNameTextView;


    public FriendIconItem(Context context) {
        super(context);
        initView();
    }

    public FriendIconItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendIconItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_friends_icon_and_delete , this);

        mIconImageView = (UserAvatarView) v.findViewById(R.id.iconUserAvatarView);
        mdeleteIconImageView = (ImageButton) v.findViewById(R.id.icon_delete_button);
        mNameTextView = (TextView) v.findViewById(R.id.icon_name_text);

    }



    public void loadUser(UserProtos.PBUser user  , int type){

        setType(type);

        if(mIconImageView != null) mIconImageView.loadUser(user);
        if(mNameTextView != null) mNameTextView.setText(user.getNick());
    }

    public void loadResourceImage(int resourceId , int type){
        setType(type);
        if(mIconImageView != null) mIconImageView.setImageResource(resourceId);
    }

    /**
     * 设置头像列表的类型
     */
    private void setType(int type){
        if(mdeleteIconImageView == null) return;

        if(type == FriendIconList.ICON_HIDDEN_RIGHT_TOP_DELETE){
           setHiddenDeleteButton();
        }else if(type == FriendIconList.ICON_SHOW_RIGHT_TOP_DELETE){
            setShowDeleteButton();
        }else if(type == FriendIconList.ICON_ORDINARY){
            setHiddenDeleteButton();
        }
    }

    public void setShowDeleteButton(){
        mdeleteIconImageView.setVisibility(View.VISIBLE);
        mdeleteIconImageView.setOnClickListener(this);
    }


    public void setHiddenDeleteButton(){
        if(mdeleteIconImageView == null) mdeleteIconImageView = (ImageButton) findViewById(R.id.icon_delete_button);
        mdeleteIconImageView.setVisibility(View.GONE);

    }


    private void deleteIcon(){
        //删除头像

    }


    //点击头像事件
    private void onClickIcon(){

    }





    @Override
    public void onClick(View v) {
        if(v == mdeleteIconImageView) deleteIcon();
        else if(v == mIconImageView) onClickIcon();
    }




//    public class FriendIconInfo {
//
//        private String mIconUrl;
//
//        private Uri mIncoUri;
//
//        private String mName;
//
//        public String getName(){return mName;}
//        public Uri getIconUri(){return mIncoUri;}
//        public String getIconUrl(){return mIconUrl;}
//
//        public void setName(String name){
//            mName = name;
//        }
//
//        public void setmIconUrl(String url){
//            mIconUrl = url;
//        }
//
//        public void setmInconUri(Uri uri){
//            mIncoUri = uri;
//        }
//    }


}
