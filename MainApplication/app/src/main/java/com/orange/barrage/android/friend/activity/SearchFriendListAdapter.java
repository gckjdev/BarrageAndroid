package com.orange.barrage.android.friend.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.orange.barrage.android.friend.ui.FriendListAdapter;
import com.orange.barrage.android.friend.ui.FriendListItemView;
import com.orange.protocol.message.UserProtos;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pipi on 15/3/12.
 */
public class SearchFriendListAdapter extends FriendListAdapter {

    private List<UserProtos.PBUser> mFriendList = Collections.emptyList();

    @Inject
    public SearchFriendListAdapter(Context context) {
        super(context);
    }

    public void setFriendList(List<UserProtos.PBUser> list){
        this.mFriendList = list;
    }

    public UserProtos.PBUser getFriend(int position){
        if (mFriendList == null){
            return null;
        }

        return mFriendList.get(position);
    }

    @Override
    public int getCount() {
        if (mFriendList == null){
            return 0;
        }

        return mFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return getFriend(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FriendListItemView view = null;
        if (convertView == null){
            view = new FriendListItemView(mContext);
        }
        else{
            view = (FriendListItemView)convertView;
        }

        final UserProtos.PBUser friend = getFriend(position);
        view.setFriend(friend);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailActivity.show(friend, mContext);
            }
        });

        return view;
    }
}
