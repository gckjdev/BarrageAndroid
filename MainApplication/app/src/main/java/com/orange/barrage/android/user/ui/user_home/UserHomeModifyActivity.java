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
    @InjectView(R.id.NickLayout)
    private RelativeLayout mNickLayout;

    @InjectView(R.id.Signaturelayout)
    private RelativeLayout mSignaturelayout;

    @InjectView(R.id.usermodifyNicker)
    private LinearLayout mLayoutmodifyNicker;

    @InjectView(R.id.usermodifyView)
    private UserAvatarView userAvatarImageView;

    @InjectView(R.id.usermodifyNick)
    private TextView mUsermodifyNick;

    @InjectView(R.id.usermodifySignature)
    private TextView mUsermodifySignature;

    @InjectView(R.id.usermodifyLocation)
    private TextView mUsermodifyLocation;

    @InjectView(R.id.usermodifySexual)
    private TextView mUsermodifySexual;
    @Inject
    UserManager mUserManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify,"个人资料",-1);


        //用户只有get方法和has方法，没有set方法
        UserProtos.PBUser user = mUserManager.getUser();

        userAvatarImageView.loadUser(user);
        mUsermodifyNick.setText(user.getNick());
        mUsermodifySignature.setText(user.getSignature());

        if (TextUtils.isEmpty(user.getLocation())){
            mUsermodifyLocation.setText("什么也没有");
        }
        else{
            mUsermodifyLocation.setText(user.getLocation());
        }

        if (user.getGender()){
            mUsermodifySexual.setText("男");
        }
        else{
            mUsermodifySexual.setText("女");
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
        mSignaturelayout.setOnClickListener(new View.OnClickListener() {
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
