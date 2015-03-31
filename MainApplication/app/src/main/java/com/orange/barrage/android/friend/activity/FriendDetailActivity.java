package com.orange.barrage.android.friend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.PhotoAndCamera;
import com.orange.barrage.android.feed.ui.ShowPublishFeedView;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class FriendDetailActivity extends BarrageCommonActivity {

    public static final String mUrlKey = "1";

    private static final String BUNDLE_KEY_USER = "BUNDLE_KEY_USER";
    private ShowPublishFeedView mShowPublishFeedView;

    @InjectView(R.id.friend_detail_avatar_view)
    private UserAvatarView mUserAvatarImageView;

    @InjectView(R.id.friend_detail_nick)
    private TextView mNickTextView;

    @InjectView(R.id.friend_detail_signature)
    private TextView mSignatureTextView;

    @InjectView(R.id.friend_detail_tag)
    private TextView mFriendDetailTag;

    @InjectView(R.id.friend_detail_location)
    private TextView locationTextView;

    @InjectView(R.id.friend_detail_sharephoto)
    private Button shareButton;

    /*@InjectView(R.id.friend_detail_userbackground)
    private LinearLayout mFriendDetailUserBackground;*/

    @InjectView(R.id.user_detail_bg)
    private ImageView mUserDetailBg;

    UserProtos.PBUser mUser;


    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_friend_detail, "详细资料", -1);
        final UserProtos.PBUser user = mUserManager.getUser();


//        new MaterialDialog.Builder(this)
//                .title(R.string.title)
//                .content(R.string.content)
//                .positiveText(R.string.agree)
//                .negativeText(R.string.disagree)
//                .show();

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


        mNickTextView.setText(mUser.getNick());
        if (mUser.hasLocation()) {
            locationTextView.setText(mUser.getLocation());
        } else {
            locationTextView.setText("什么也没有");
        }

        if (!StringUtil.isEmpty(mUser.getSignature())) {
            mSignatureTextView.setText(mUser.getSignature());
        }

        mUserAvatarImageView.loadUser(mUser);
        if (mUser.hasAvatarBg()) {
            Picasso.with(FriendDetailActivity.this)
                    .load(mUser.getAvatarBg().toString())
                    .placeholder(R.drawable.tab_home)
                    .error(R.drawable.tab_friend)
                    .into(mUserDetailBg);
        }

        //点击头像显示
        mUserAvatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(FriendDetailActivity.this,
                        FriendDetailBigImageViewActivity.class, mUrlKey, mUser.getAvatar());
            }
        });
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
        mShowPublishFeedView.showPublishFeedView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mShowPublishFeedView.getPhotoAndCamera().getPicture(requestCode, resultCode, data);

    }

    private PhotoAndCamera.onGetPhotoCallback mOnGetPhotoCallbak = new PhotoAndCamera.onGetPhotoCallback() {
        @Override
        public void onSuccess(Bitmap bitmap) {

        }

        @Override
        public void onError(String reason) {
            MessageCenter.postInfoMessage("获取相片失败");
        }
    };


    private void initPublishFeefView() {
        mShowPublishFeedView = mShowPublishFeedView
                == null ? new ShowPublishFeedView(this, mOnGetPhotoCallbak) : mShowPublishFeedView;
    }
}
