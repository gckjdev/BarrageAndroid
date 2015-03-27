package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconListAdapter extends BaseAdapter  {

    private Context mContext;
    private List<UserProtos.PBUser> mUsers;
    private Activity mActivity;
    private int mType = FriendIconList.ICON_ORDINARY;
    private FriendIconList.OnClickItemListener mOnClickItemListener;


    Vector<Vector<String>> mtagIds = new Vector<>();

    public FriendIconListAdapter(Context context ,List<UserProtos.PBUser> users , Activity activity , int type){
        mContext = context;
        mActivity = activity;
        mUsers = users;
        setIconType(type);
    }

    public void setIconType(int type){
        this.mType = type;
        notifyDataSetChanged();
    }

    public void addUser(List<UserProtos.PBUser> pbUserList){
        if(mUsers == null) mUsers = new ArrayList<>();
        if(pbUserList == null) return;

        List<UserProtos.PBUser> pbUsers = new ArrayList<>();

        for(UserProtos.PBUser pbUser : pbUserList){
            //不可以有重复的数据存在
            if(findUser(pbUser) == null){
                pbUsers.add(pbUser);
            }
        }

        mUsers.addAll(pbUsers);
        notifyDataSetChanged();
    }

    public void removewUsers(List<UserProtos.PBUser> pbUsers){
        for(UserProtos.PBUser pbUser : pbUsers){
            removeUser(pbUser);
        }
        notifyDataSetChanged();
    }

    private void removeUser(UserProtos.PBUser pbUser){

        UserProtos.PBUser pb = findUser(pbUser);
        if(pb == null) return;
        mUsers.remove(pb);
    }

    public UserProtos.PBUser findUser(UserProtos.PBUser pbUser){
        for(UserProtos.PBUser pb : mUsers){
            if(pb.getUserId().equals(pbUser.getUserId())){
                return pb;
            }
        }
        return null;
    }


    public void setUser(List<UserProtos.PBUser> pbUserList){
        setUser(pbUserList , mType);
    }


    public void setUser(List<UserProtos.PBUser> pbUserList , int type){
        if(mUsers == null) mUsers = new ArrayList<>();
        mUsers.clear();
        mType = type;
        addUser(pbUserList);
    }



    public List<UserProtos.PBUser> getUsers(){
        return mUsers;
    }

    public int getChildCount(){
        return mUsers == null ? 0 : mUsers.size();
    }




    @Override
    public int getCount() {
        if(mType == FriendIconList.ICON_ADD_AND_DELETE_BUTTON
                || mType == FriendIconList.ICON_HIDDEN_RIGHT_TOP_DELETE
                || mType == FriendIconList.ICON_SHOW_RIGHT_TOP_DELETE){
            return mUsers.size() + 2;
        }else {
            return mUsers.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(position < mUsers.size())
            return mUsers.get(position);
        else return null;
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
        }else{
            frinedsIconItem = (FriendIconItem)convertView;
            //frinedsIconItem.clear();
        }


        UserProtos.PBUser user = (UserProtos.PBUser)getItem(position);
        frinedsIconItem.setUser(mUsers , this , mOnClickItemListener , position);


        if(position >= mUsers.size()){
            int resource = mUsers.size() == position ? R.drawable.x_freinds_list_add : R.drawable.x_friends_list_remove;
            frinedsIconItem.loadResourceImage(resource , mType);
            frinedsIconItem.setHiddenTopDeleteButton();
        }else {
            frinedsIconItem.loadUser( user, mType);

        }
        frinedsIconItem.setOnClickListener(new View.OnClickListener() {

            private int index = position;

            @Override
            public void onClick(View v) {
                int type = FriendIconList.OnClickItemListener.ICON_ORDINARY_BUTTON;
                if(getItem(index) == null){
                    type =  mUsers.size() == index ? FriendIconList.OnClickItemListener.INCO_ADD_BUTTON : FriendIconList.OnClickItemListener.ICON_DELETE_BUTTON;
                }
                dealListener(type , index , v);
            }
        });

        return frinedsIconItem;

    }

    private void dealListener(int type , int index , View v){
        if (mOnClickItemListener == null) return;
        if (type == FriendIconList.OnClickItemListener.ICON_DELETE_BUTTON) {
            if(mType == FriendIconList.ICON_SHOW_RIGHT_TOP_DELETE) {
                setIconType(FriendIconList.ICON_HIDDEN_RIGHT_TOP_DELETE);
            }else setIconType(FriendIconList.ICON_SHOW_RIGHT_TOP_DELETE);
        } else if (type == FriendIconList.OnClickItemListener.INCO_ADD_BUTTON) {
            setIconType(FriendIconList.ICON_HIDDEN_RIGHT_TOP_DELETE);
        }
        mOnClickItemListener.onClickItem(index, v, getItem(index), type);
    }


    public void setOnClickItemListener(FriendIconList.OnClickItemListener l){
        mOnClickItemListener = l;
    }


}
