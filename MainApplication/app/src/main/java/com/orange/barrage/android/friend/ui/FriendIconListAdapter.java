package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconListAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserProtos.PBUser> mUsers;
    private Activity mActivity;
    private int mType = FriendIconList.ORDINARY_ICON;
    private FriendIconList.OnClickItemListener mOnClickItemListener;

    public FriendIconListAdapter(Context context ,List<UserProtos.PBUser> users , Activity activity){
        mContext = context;
        mActivity = activity;
        mUsers = users;
    }

    public void setICONType(int type){
        this.mType = type;
    }



    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        FriendIconItem frinedsIconItem = null;

        if(convertView == null){
            frinedsIconItem = new FriendIconItem(mContext);
        }else frinedsIconItem = (FriendIconItem)convertView;

        frinedsIconItem.loadUser((UserProtos.PBUser)getItem(position) , mType);

        frinedsIconItem.setOnClickListener(new View.OnClickListener() {

            private int index = position;

            @Override
            public void onClick(View v) {
                if(mOnClickItemListener != null)
                    mOnClickItemListener.onClickItem(index , v , getItem(index));
            }
        });


        return frinedsIconItem;

    }


    public void setOnClickItemListener(FriendIconList.OnClickItemListener l){
        mOnClickItemListener = l;
    }


}
