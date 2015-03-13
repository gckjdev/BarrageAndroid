package com.orange.barrage.android.friend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import roboguice.inject.InjectView;

public class FriendDetailActivity extends BarrageCommonActivity {


    @InjectView(R.id.friend_detail_imageview)
    private ImageView friend_detail_imageView;

    @InjectView(R.id.friend_detail_nick)
    private TextView friend_detail_nick;

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
        String nick=bundle.getString("nick");
        String signature=bundle.getString("signature");
        String location=bundle.getString("location");
        friend_detail_sharephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(FriendDetailActivity.this,"点击了分享",Toast.LENGTH_SHORT).show();
            }
        });
        friend_detail_nick.setText(nick);
        friend_detail_location.setText(location);
        friend_detail_signature.setText(signature);

    }
}
