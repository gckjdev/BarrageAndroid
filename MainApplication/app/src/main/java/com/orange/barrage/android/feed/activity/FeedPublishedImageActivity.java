package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.friend.ui.FriendTagList;
import com.orange.barrage.android.friend.ui.FriendTagView;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/24.
 */
public class FeedPublishedImageActivity extends BarrageCommonActivity
                                                implements FriendTagView.OnClickTabIconItemListener,FriendIconList.OnClickItemListener {

    @Inject
    TagManager mTagManager;

    @Inject
    FriendManager mFriendManager;

    @Inject
    UserManager mUserManger;

    @InjectView(R.id.friendIconList)
    FriendIconList mFriendIconList;

    @InjectView(R.id.chengyuan)
    TextView mMenberTextView;


    private FriendTagList mFriendTagList;

    private int mNum = 0;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle, R.layout.activity_publishimage, R.string.b_share_to_who, R.string.b_OK);

        initView();
    }

    private void initView() {
        mFriendTagList = new FriendTagList(this, mTagManager, this);
        UserProtos.PBUser pbUser = mUserManger.getUser();
        if (pbUser == null) {
            mFriendIconList.setUser(pbUser, this, FriendIconList.ICON_ADD_AND_DELETE_BUTTON);
            setMenBerTextView(1);
        }
        //注册监听
        mFriendIconList.setOnClickItemListener(this);

        mFriendTagList = new FriendTagList(this, mTagManager, this);

        mFriendTagList.loadLocalTagList();

    }


    public void setMenBerTextView(int num) {
        mNum += num;
        mMenberTextView.setText("意见选择的成员(" + num + ")");

    }


    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);



    }


    @Override
    public void onClickItem(String tagId, View v, FriendTagView friendTagView) {
        //点击标签
        UserProtos.PBUserTag pbUserTag = mTagManager.getTagById(tagId);

        if (pbUserTag == null) return;
        setMenBerTextView(pbUserTag.getUsersList().size());
        mFriendIconList.addUsers(pbUserTag.getUsersList());

    }

    @Override
    public void onClickItem(int postion, View view, Object data, int iconType) {
        //点击头像
//        if(iconType == )
    }



}
