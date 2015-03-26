package com.orange.barrage.android.friend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectView;

public class FriendDetailBigImageViewActivity extends BarrageCommonActivity {
    //private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";

    @InjectView(R.id.friend_detail_bigview_avatar_view)
    private ImageView mUserAvatarBigView;
    /*private UserAvatarView mUserAvatarBigView;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail_big_image_view);
        String url = getIntentString(FriendDetailActivity.mUrlKey);
        if (url!=null || url.length()!=0)
        {
            Picasso.with(FriendDetailBigImageViewActivity.this)
                    .load(url)
                    .into(mUserAvatarBigView);
        }
        mUserAvatarBigView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserAvatarBigView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        /*mUserAvatarBigView.setAvatUrl(url);*/
    }
}
