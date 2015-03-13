package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.friend.activity.FriendDetailActivity;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

/**
 * Created by pipi on 15/3/10.
 */
public class FriendListAdapter extends BaseAdapter {

    protected Context mContext;

    @Inject
    FriendManager mFriendManager;

    @Inject
    public FriendListAdapter(Context context)
    {
        super();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        UserProtos.PBUserFriendList userFriendList = mFriendManager.getAllList();
        if (userFriendList == null){
            return 0;
        }

        int friendsCount = userFriendList.getFriendsCount();
        int requestFriendsCount = userFriendList.getRequestFriendsCount();
        return friendsCount; // + requestFriendsCount;
    }

    @Override
    public Object getItem(int position) {
        return mFriendManager.getFriendList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendListItemView view = null;
        if (convertView == null){
            view = new FriendListItemView(mContext);
        }
        else{
            view = (FriendListItemView)convertView;
        }

        //每一项的位置，然后设置
        UserProtos.PBUser friend = mFriendManager.getFriendList().get(position);
        view.setFriend(friend);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FriendDetailActivity.class));
            }
        });
        return view;
    }
}
