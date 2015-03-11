package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.friend.activity.RequestAddFriendActivity;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

/**
 * Created by pipi on 15/3/10.
 */
public class FriendListAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater layoutInflater;

    @Inject
    FriendManager mFriendManager;

    public final class ListItemView
    {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
    }

    @Inject
    public FriendListAdapter(Context context)
    {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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

    //这个方法是返回每一项的位置
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
        FriendListItemView view =null;
        if (convertView == null)
        {
            view = new FriendListItemView(context);
            //下面这里可以设置监听，利用回调接口
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
               context.startActivity(new Intent(context, RequestAddFriendActivity.class));
            }
        });
        return view;
        //这里改动的
/*
else
        {
            listItemView=(ListItemView)convertView.getTag();
        }
       listItemView.imageView.setBackgroundResource((Integer)listItems.get(position).get("image"));

        UserProtos.PBUser friend = mFriendManager.getFriendList().get(position);

        String nick = friend.getNick();
        String signature = friend.getSignature();

        listItemView.textView1.setText(nick);
        listItemView.textView2.setText(signature);
 */




    }
}
