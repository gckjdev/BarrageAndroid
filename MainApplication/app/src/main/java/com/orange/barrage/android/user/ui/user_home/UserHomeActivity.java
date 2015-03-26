package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.ActionSheetDialog;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeActivity extends BarrageCommonActivity {
    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    @InjectView(R.id.user_home_layout)
    private RelativeLayout mUserHomeView;

    @InjectView(R.id.user_home_myfriend_layout)
    private LinearLayout mUserHomeMyfriend;

    @InjectView(R.id.user_home_invitemyfriend)
    private LinearLayout mUserHomeInviteMyfriend;

    @InjectView(R.id.user_home_mytag)
    private LinearLayout mUsrHomeMyTag;

    @InjectView(R.id.user_home_setting)
    private LinearLayout mUserHomeSetting;

    @InjectView(R.id.friend_home_avatarview)
    private UserAvatarView userAvatarImageView;

    @InjectView(R.id.friend_home_nick)
    private TextView mFriendHomeview;

    @InjectView(R.id.userhome_bg)
    private ImageView mUserHomeBg;
    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home,"我的主页",-1);
        UserProtos.PBUser user = mUserManager.getUser();
        userAvatarImageView.loadUser(user);
        userAvatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageCenter.postInfoMessage("测试");
                new ActionSheetDialog(UserHomeActivity.this)
                        .builder()
                        .setTitle("请选择")
                        .setCancelabe(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //MessageCenter.postInfoMessage("测试菜单成功");
                            }
                        })
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        }).show();
            }
        });
        mFriendHomeview.setText(user.getNick());
        //MessageCenter.postInfoMessage("地址为:"+user.getAvatarBg().toString());
        //取用户头像的时候，必须要用toString()方法
        if (user.hasAvatarBg())
        {
            Picasso.with(UserHomeActivity.this)
                    .load(user.getAvatarBg().toString())
                    .placeholder(R.drawable.tab_home)
                    .error(R.drawable.tab_friend)
                    .into(mUserHomeBg);
        }
        //个人资料设置界面
        mUserHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this,UserHomeModifyActivity.class);
            }
        });
        //设置界面
        mUserHomeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this,UserHomeSettingActivity.class);
            }
        });
        //邀请好友
        mUserHomeInviteMyfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this,InviteMyFriendActivity.class);
            }
        });
    }
}
