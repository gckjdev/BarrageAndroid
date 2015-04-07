package com.orange.barrage.android.user.ui.user_home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.PhotoAndCamera;
import com.orange.barrage.android.feed.ui.ShowPublishFeedView;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.ActionSheetDialog;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.FloatWindow;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.barrage.android.util.misc.SystemUtil;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Callback;
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

    @InjectView(R.id.friend_home_background)
    private RelativeLayout mFriendHomeBackground;
    @Inject
    UserManager mUserManager;

    private PhotoAndCamera mPhotoAndCamera;
    private PhotoAndCamera.onGetPhotoCallback mGetPhotoCallback;
    private ShowPublishFeedView mShowPublisFeedView;
    private FloatWindow mFloatWindow;

    public static String PHOTOPATH = SystemUtil.getSDCardPath() + "/bbl/";

    public static String PHOTONAME = "you.png";

    public void closePublishFeedView() {
        if (mFloatWindow == null) return;
        mFloatWindow.close();
    }

    private void initPhoto() {
        mPhotoAndCamera = mPhotoAndCamera == null ? new PhotoAndCamera(UserHomeActivity.this) : mPhotoAndCamera;
    }

    public PhotoAndCamera getmPhotoAndCamera() {
        return mPhotoAndCamera;
    }

    private void initPublisFeedView() {
        mShowPublisFeedView = mShowPublisFeedView == null ? new ShowPublishFeedView(this, mGetPhotoCallback) : mShowPublisFeedView;
    }

    //处理照片和拍照的返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPhotoAndCamera != null) {
            mPhotoAndCamera.getPicture(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_home, "我的主页", -1);
        final UserProtos.PBUser user = mUserManager.getUser();
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
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                initPhoto();
                                mPhotoAndCamera.choosePhoto(new PhotoAndCamera.onGetPhotoCallback() {
                                    @Override
                                    public void onSuccess(Bitmap bitmap) {
                                        if (bitmap != null) {
                                            FileUtil.savePhotoToSDCard(bitmap, PHOTOPATH, PHOTONAME);
                                            ActivityIntent.startIntent(UserHomeActivity.this, FeedPublishedWhatchUserHomeImageActivity.class);
                                        }
                                    }

                                    @Override
                                    public void onError(String reason) {
                                        MessageCenter.postInfoMessage("返回照片失败");
                                    }
                                });
                                closePublishFeedView();
                            }
                        })
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                initPhoto();
                                mPhotoAndCamera.takePicture(new PhotoAndCamera.onGetPhotoCallback() {
                                    @Override
                                    public void onSuccess(Bitmap bitmap) {
                                        if (bitmap!=null)
                                        {
                                            FileUtil.savePhotoToSDCard(bitmap, PHOTOPATH, PHOTONAME);
                                            ActivityIntent.startIntent(UserHomeActivity.this, FeedPublishedWhatchUserHomeImageActivity.class);
                                        }
                                    }

                                    @Override
                                    public void onError(String reason) {
                                        MessageCenter.postInfoMessage("返回照片失败");
                                    }
                                });
                                closePublishFeedView();
                            }
                        }).show();
            }
        });
        mFriendHomeview.setText(user.getNick());
        //MessageCenter.postInfoMessage("地址为:"+user.getAvatarBg().toString());
        //取用户头像的时候，必须要用toString()方法
        //个人资料设置界面
        mUserHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this, UserHomeModifyActivity.class);
            }
        });
        //设置界面
        mUserHomeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this, UserHomeSettingActivity.class);
            }
        });
        //邀请好友
        mUserHomeInviteMyfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this, InviteMyFriendActivity.class);
            }
        });
    }

    private boolean is = false;

    private void setColor() {
        Bitmap bitmaphomenick = ImageUtil.getChildBitmap(mFriendHomeview, mFriendHomeBackground);
        mFriendHomeview.setTextColor(ImageUtil.getColorBitmap(bitmaphomenick));
    }

    private void setColorTime() {
        /**
         * 延迟一秒才调用方法
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setColor();
            }
        }, 100);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!is) {
            if (mUserManager.getUser().hasAvatarBg()) {
                Picasso.with(UserHomeActivity.this)
                        .load(mUserManager.getUser().getAvatarBg().toString())
                        .placeholder(R.drawable.tab_home)
                        .error(R.drawable.tab_friend)
                        .into(mUserHomeBg, new Callback() {
                            @Override
                            public void onSuccess() {
                                setColorTime();
                            }

                            @Override
                            public void onError() {
                                setColor();
                            }
                        });
            } else setColor();
            is = true;
        }
    }
}
