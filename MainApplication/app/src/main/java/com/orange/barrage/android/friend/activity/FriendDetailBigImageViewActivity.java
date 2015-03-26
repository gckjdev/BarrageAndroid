package com.orange.barrage.android.friend.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                    .error(R.drawable.image_default)
                    .placeholder(R.drawable.image_default)
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
                new AlertDialog.Builder(FriendDetailBigImageViewActivity.this)
                        .setTitle("提示").setMessage("需要保存图片到相册吗")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            }
        });
        /*mUserAvatarBigView.setAvatUrl(url);*/
    }
}
