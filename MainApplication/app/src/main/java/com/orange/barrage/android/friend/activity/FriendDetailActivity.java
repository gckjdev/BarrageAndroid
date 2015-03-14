package com.orange.barrage.android.friend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class FriendDetailActivity extends BarrageCommonActivity {


    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    @InjectView(R.id.friend_detail_avatar_view)
    private UserAvatarView friend_detail_imageView;

    @InjectView(R.id.friend_detail_nick)
    private TextView nickTextView;

    @InjectView(R.id.friend_detail_signature)
    private TextView friend_detail_signature;

    @InjectView(R.id.friend_detail_location)
    private TextView friend_detail_location;

    @InjectView(R.id.friend_detail_sharephoto)
    private Button friend_detail_sharephoto;

    UserProtos.PBUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_friend_detail, "详细资料", -1);

        Bundle bundle=getIntent().getExtras();
        if (bundle == null){
            Ln.w("FriendDetailActivity show but bundle data null");
            return;
        }

        byte[] userBytes = bundle.getByteArray(BUNDLE_KEY_USER);
        if (userBytes == null){
            Ln.w("FriendDetailActivity show but bundle user bytes null");
            return;
        }

        try {
            mUser = UserProtos.PBUser.parseFrom(userBytes);
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "FriendDetailActivity parse user byte data, catch exception="+e.toString());
            return;
        }

        friend_detail_sharephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(FriendDetailActivity.this,"点击了分享",Toast.LENGTH_SHORT).show();
            }
        });

        nickTextView.setText(mUser.getNick());

        friend_detail_location.setText(mUser.getLocation());

        if (!StringUtil.isEmpty(mUser.getSignature())) {
            friend_detail_signature.setText(mUser.getSignature());
        }

        friend_detail_imageView.loadUser(mUser);

    }

    public static void show(UserProtos.PBUser user, Context context){

        if (user == null){
            Ln.w("show friend detail but user is null");
            return;
        }

        byte[] userBytes = user.toByteArray();
        if (userBytes == null){
            Ln.w("show friend detail but userBytes is null");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putByteArray(BUNDLE_KEY_USER, userBytes);

        final Intent intent=new Intent();
        intent.putExtras(bundle);

        intent.setClass(context, FriendDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
