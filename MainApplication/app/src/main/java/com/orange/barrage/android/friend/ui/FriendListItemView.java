package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

/**
 * Created by pipi on 15/3/10.
 */
public class FriendListItemView extends LinearLayout{
    private Context mContext;
//    public ImageView mImageView;

    private UserAvatarView mAvatarView;

    public TextView friend_nick;
    public TextView friend_signature;
    private View view;
    //凡是需要用户的时候，就设置这样一个用户变量,这样就以用户为单位进行操作了
    UserProtos.PBUser pbFriend;

    @Inject
    FriendManager mFriendManager;

    public FriendListItemView(Context context) {
        super(context);
        this.mContext = context;

        initView(mContext);
    }

    public void initView(Context context)
    {
        this.mContext = context;
        view = LayoutInflater.from(this.mContext).inflate(R.layout.fragment_friend_home_listitem, this);
    }

    public void setFriend(UserProtos.PBUser friend){
        this.pbFriend = friend;

        mAvatarView = (UserAvatarView)view.findViewById(R.id.friend_avatar_view);
        friend_nick = (TextView)view.findViewById(R.id.friend_nick);
        friend_signature = (TextView)view.findViewById(R.id.friend_signature);

        String nick = pbFriend.getNick();
        String signature = pbFriend.getSignature();

        friend_nick.setText(nick);
        friend_signature.setText(signature);

        mAvatarView.loadUser(pbFriend);
    }
}
