package com.orange.barrage.android.friend.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectView;

public class FriendDetailBigImageViewActivity extends BarrageCommonActivity {
    //private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    private String mUrl;
    @InjectView(R.id.friend_detail_bigview_avatar_view)
    private ImageView mUserAvatarBigView;


    /*private UserAvatarView mUserAvatarBigView;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail_big_image_view);
        mUrl = getIntentString(FriendDetailActivity.mUrlKey);
        if (mUrl != null && mUrl.trim().length() != 0) {
            Picasso.with(FriendDetailBigImageViewActivity.this)
                    .load(mUrl)
                    .error(R.drawable.image_default)
                    .placeholder(R.drawable.image_default)
                    .into(mUserAvatarBigView);
        } else {
            mUserAvatarBigView.setImageResource(R.drawable.y_morentouxiang);
        }
        mUserAvatarBigView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserAvatarBigView.setDrawingCacheEnabled(true);

        mUserAvatarBigView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showRemindboxAlertDialog(new String[]{"取消", "确定"}, "提醒", "确定需要保存图片吗", -1);
                return true;
            }
        });

    }

    //凡是用了那个对话框的点击按钮的时候调用的方法
    @Override
    public void onRemindItemClick(int position) {
        super.onRemindItemClick(position);
        if (position == RemindboxAlertDialog.RIGHTBUTTON) {
            //将一张ImageView图片保存为在SD卡上存储的Bitmap类型的图片
            Bitmap bitmap = Bitmap.createBitmap(mUserAvatarBigView.getDrawingCache());
            FileUtil.savePhotoToSDCard(bitmap, Environment.getExternalStorageDirectory() + "/Orange/barrage", FileUtil.getFromURLToFileName(mUrl));
            MessageCenter.postInfoMessage("保存图片到手机相册成功");
        }
        if (position == RemindboxAlertDialog.LEFTBUTTON) {
            return;
        }
    }
}
