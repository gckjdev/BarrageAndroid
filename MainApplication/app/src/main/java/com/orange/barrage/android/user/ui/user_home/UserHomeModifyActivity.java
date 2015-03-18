package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.user_home.user_settings.UserNickEditTextActivity;
import com.orange.barrage.android.user.ui.user_home.user_settings.UserSignatureEditTextActivity;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeModifyActivity extends BarrageCommonActivity {
    @InjectView(R.id.nick_layout)
    private RelativeLayout mNickLayout;

    @InjectView(R.id.signature_layout)
    private RelativeLayout mSignatureLayout;

    @InjectView(R.id.user_nick_layout)
    private LinearLayout mLayoutModifyNick;

    @InjectView(R.id.user_avatarview)
    private UserAvatarView userAvatarImageView;

    @InjectView(R.id.user_modify_nick)
    private TextView mUserModifyNick;

    @InjectView(R.id.user_modify_signature)
    private TextView mUserModifySignature;

    @InjectView(R.id.user_location)
    private TextView mUserLocation;

    @InjectView(R.id.user_modify_gender)
    private TextView mUserModifyGender;

    @Inject
    UserManager mUserManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify,"个人资料",-1);


        //用户只有get方法和has方法，没有set方法
        UserProtos.PBUser user = mUserManager.getUser();

        userAvatarImageView.loadUser(user);
        mUserModifyNick.setText(user.getNick());
        mUserModifySignature.setText(user.getSignature());

        if (TextUtils.isEmpty(user.getLocation())){
            mUserLocation.setText("什么也没有");
        }
        else{
            mUserLocation.setText(user.getLocation());
        }

        if (user.getGender()){
            mUserModifyGender.setText("男");
        }
        else{
            mUserModifyGender.setText("女");
        }

        //修改昵称
        mNickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserNickEditTextActivity.start(UserHomeModifyActivity.this,
                        UserNickEditTextActivity.class,
                        mUserManager.getUser().getNick());
            }
        });

        //修改签名
        mSignatureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSignatureEditTextActivity.start(UserHomeModifyActivity.this,
                        UserSignatureEditTextActivity.class,
                        mUserManager.getUser().getSignature());
            }
        });
        // TODO set data here
    }
}
