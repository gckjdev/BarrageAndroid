package com.orange.barrage.android.friend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.PhotoAndCamera;
import com.orange.barrage.android.feed.mission.ShowPublishFeedView;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class FriendDetailActivity extends BarrageCommonActivity {


    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    private ShowPublishFeedView mShowPublishFeedView;

    @InjectView(R.id.friend_detail_avatar_view)
    private UserAvatarView userAvatarImageView;

    @InjectView(R.id.friend_detail_nick)
    private TextView nickTextView;

    @InjectView(R.id.friend_detail_signature)
    private TextView signatureTextView;

    @InjectView(R.id.friend_detail_location)
    private TextView locationTextView;

    @InjectView(R.id.friend_detail_sharephoto)
    private Button shareButton;

    @InjectView(R.id.friend_detail_userbackground)
    private LinearLayout mFriendDetailUserBackground;

    UserProtos.PBUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_friend_detail, "详细资料", -1);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Ln.w("FriendDetailActivity show but bundle data null");
            return;
        }

        byte[] userBytes = bundle.getByteArray(BUNDLE_KEY_USER);
        if (userBytes == null) {
            Ln.w("FriendDetailActivity show but bundle user bytes null");
            return;
        }

        try {
            mUser = UserProtos.PBUser.parseFrom(userBytes);
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "FriendDetailActivity parse user byte data, catch exception=" + e.toString());
            return;
        }


        nickTextView.setText(mUser.getNick());

        locationTextView.setText(mUser.getLocation());

        if (!StringUtil.isEmpty(mUser.getSignature())) {
            signatureTextView.setText(mUser.getSignature());
        }

        userAvatarImageView.loadUser(mUser);

    }

    public static void show(UserProtos.PBUser user, Context context) {

        if (user == null) {
            Ln.w("show friend detail but user is null");
            return;
        }

        byte[] userBytes = user.toByteArray();
        if (userBytes == null) {
            Ln.w("show friend detail but userBytes is null");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putByteArray(BUNDLE_KEY_USER, userBytes);

        final Intent intent = new Intent();
        intent.putExtras(bundle);

        intent.setClass(context, FriendDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void onClickCamera(View v) {
        initPublishFeefView();
        MessageCenter.postErrorMessage("niha");
        mShowPublishFeedView.showPublishFeedView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mShowPublishFeedView.getPhotoAndCamera().getPicture(requestCode, resultCode, data, mOnGetPhotoCallbak);

    }

    private PhotoAndCamera.onGetPhotoCallback mOnGetPhotoCallbak = new PhotoAndCamera.onGetPhotoCallback() {
        @Override
        public void onSuccess(Bitmap bitmap) {

        }

        @Override
        public void onErro() {
            MessageCenter.postInfoMessage("获取相片失败");
        }
    };


    private void initPublishFeefView() {
        mShowPublishFeedView = mShowPublishFeedView
                == null ? new ShowPublishFeedView(this) : mShowPublishFeedView;
    }
}
