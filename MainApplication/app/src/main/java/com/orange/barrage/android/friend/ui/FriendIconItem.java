package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.mission.TagMission;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconItem extends LinearLayout implements View.OnClickListener{

    private UserAvatarView mIconImageView;
    private ImageButton mdeleteIconImageView;
    private TextView mNameTextView;
    private List<UserProtos.PBUser> mPbUsers;
    private UserProtos.PBUser mPbUser;
    private BaseAdapter mAdapter;
    private FriendIconList.OnClickItemListener mOnClickItemListener;
    private int mPostion = 0;

    @Inject

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

    public void clear() {

        post(new Runnable() {
            @Override
            public void run() {
                mIconImageView.setImageBitmap(null);
                mdeleteIconImageView.setVisibility(GONE);
                mNameTextView.setText("");
            }
        });

    }


    public void loadUser(UserProtos.PBUser user  , int type){

        setType(type);
        mPbUser = user;
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
           setHiddenTopDeleteButton();
        }else if(type == FriendIconList.ICON_SHOW_RIGHT_TOP_DELETE){
            setShowDeleteButton();
        }else if(type == FriendIconList.ICON_ORDINARY){
            setHiddenTopDeleteButton();
        }
    }

    public void setShowDeleteButton(){
        mdeleteIconImageView.setVisibility(View.VISIBLE);
        mdeleteIconImageView.setOnClickListener(this);
    }


    public void setHiddenTopDeleteButton(){
        if(mdeleteIconImageView == null) mdeleteIconImageView = (ImageButton) findViewById(R.id.icon_delete_button);
        mdeleteIconImageView.setVisibility(View.GONE);
        //设置TextView为空
        mNameTextView.setText("");
    }


    //删除头像
    private void deleteIcon(View v){


        mOnClickItemListener.onClickItem(mPostion , v, mPbUser , FriendIconList.OnClickItemListener.ICON_TOP_DELETE_BUTTON);

        //删除头像
        if(mAdapter != null && mPbUsers != null){
            mPbUsers.remove(mPbUser);
            mAdapter.notifyDataSetChanged();
        }
    }


    //点击头像事件
    private void onClickIcon(){

    }





    @Override
    public void onClick(View v) {
        if(v == mdeleteIconImageView) deleteIcon(v);
        else if(v == mIconImageView) onClickIcon();
    }

    public void setUser(List<UserProtos.PBUser> pbUsers , BaseAdapter adapter ,FriendIconList.OnClickItemListener l , int postion){
        mPbUsers = pbUsers;
        mAdapter = adapter;
        mOnClickItemListener = l;
        this.mPostion = postion;
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
