package com.orange.barrage.android.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.ActionImageCaptureEvent;
import com.orange.barrage.android.event.ActionPickEvent;
import com.orange.barrage.android.event.StartActivityFeedCommentEvent;
import com.orange.barrage.android.event.StartActivityFeedPublishedOtherPlatformEvent;
import com.orange.barrage.android.feed.activity.FeedCommentActivity;
import com.orange.barrage.android.feed.activity.FeedPublishedActivity;
import com.orange.barrage.android.feed.activity.FeedPublishedOtherPlatform;
import com.orange.barrage.android.feed.mission.PhotoAndCamera;
import com.orange.barrage.android.feed.mission.ShowPublishFeedView;
import com.orange.barrage.android.friend.activity.FriendTabDetailInfoAndCreateAndAlterActivity;
import com.orange.barrage.android.friend.activity.OptionFeedBackActivity;
import com.orange.barrage.android.friend.activity.RequestAddFriendActivity;
import com.orange.barrage.android.misc.ui.HomePopupWindow;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.user_home.UserHomeActivity;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonFragmentActivity;
import com.orange.barrage.android.util.activity.RequestCodes;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.SystemUtil;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import roboguice.util.Ln;

public class HomeActivity extends BarrageCommonFragmentActivity implements View.OnClickListener {

    //这里才是主页面，就是那三个tab页面
    public static final String TAB_1_TAG = "tab_1";
    public static final String TAB_2_TAG = "tab_2";
    public static final String TAB_3_TAG = "tab_3";

    private FragmentTabHost mTabHost;

    private HomePopupWindow mHomePopupWindow ;

    private String mTag = TAB_1_TAG;

    @Inject
    UserMission    mUserMission;

    @Inject
    UserManager mUserManager;

    public static String PHOTOPATH = SystemUtil.getSDCardPath()+"/bbl/";

    public static String PHOTONAME = "you.png";

    private ShowPublishFeedView mShowPublisFeedView;

    private Tab3Container.OnFragmentCommunicationListener mOnFragmentCommunicationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_home,R.string.y_shouyue,R.drawable.y_more_and_more);
        initView();
        initTopBar();
    }

    private void initTopBar(){
        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRight(v);
            }
        });
    }

    private void initView() {

      //  MobclickAgent.updateOnlineConfig(this);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);



        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.x_shouye,"首页",R.drawable.tab_select),Tab1Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.x_all_alph,"",R.drawable.tab_select),Tab2Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.x_haoyou,"好友",R.drawable.tab_select),Tab3Container.class,null);




        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mTag = tabId;
               changeTitleText();
            }
        });


    }

    private void changeTitleText(){
        int stringId = R.string.y_shouyue;
        if(mTag.equals(TAB_3_TAG)){
            stringId = R.string.y_haiyou;
        }
        mTopBarView.setTitleText(stringId);
    }

    public void onCLickCamer(View v){
        initPublisFeedView();
        mShowPublisFeedView.showPublishFeedView();
    }




    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();

        if (currentTabTag.equals(TAB_1_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_1_TAG)).popFragment();
        } else if (currentTabTag.equals(TAB_2_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_2_TAG)).popFragment();
        }
        else if (currentTabTag.equals(TAB_3_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_3_TAG)).popFragment();
        }

        if (!isPopFragment) {
            finish();
        }
    }

    private TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec,
                                 int resid, String string, int genresIcon) {
        View v = null;
        if(resid == R.drawable.x_fabiao) {
            v = LayoutInflater.from(ctx).inflate(R.layout.view_tab_item_center, null);

        }
        else{
            v = LayoutInflater.from(ctx).inflate(R.layout.view_tab_item, null);
            TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
            tv.setText(string);
        }

        ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);

        img.setImageResource(resid);
        return spec.setIndicator(v);
    }


    public void setOnFramgeListener(Tab3Container.OnFragmentCommunicationListener l ){
        mOnFragmentCommunicationListener = l;
    }




    private void initPublisFeedView(){

        mShowPublisFeedView =
                mShowPublisFeedView == null ? new ShowPublishFeedView(this) : mShowPublisFeedView;

    }


    public void onEvent(ActionPickEvent event){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(event.getType());
        ActivityCompat.startActivityForResult(this, intent, RequestCodes.FEED_CREATE_PICK_IMAGE, null);

    }

    public void onEvent(ActionImageCaptureEvent event){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityCompat.startActivityForResult(this, intent, RequestCodes.FEED_CREATE_TAKEN_PICTURE, null);
    }

    public final  static String KEYSBYTE = "1";
    public final static String KEYSSCREENXY = "2";


    public void onEvent(StartActivityFeedCommentEvent event) {
        Intent intent = new Intent(this, FeedCommentActivity.class);
        intent.putExtra(KEYSBYTE , event.getByteArray());
        intent.putExtra(KEYSSCREENXY , event.getPos());
        ActivityIntent.startIntent(this, intent );
    }

    public void onEvent(StartActivityFeedPublishedOtherPlatformEvent event){
        ActivityIntent.startIntent(this, FeedPublishedOtherPlatform.class );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == 0X12){
            mOnFragmentCommunicationListener.onListener(null);
        }else if(mShowPublisFeedView != null) {
            PhotoAndCamera photoAndCamera =  mShowPublisFeedView.getPhotoAndCamera();
            if(photoAndCamera != null)
                photoAndCamera.getPicture(requestCode, resultCode, data, l);
        }
    }

    private PhotoAndCamera.onGetPhotoCallback l = new PhotoAndCamera.onGetPhotoCallback() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            if(bitmap != null) {
                FileUtil.savePhotoToSDCard(bitmap, PHOTOPATH, PHOTONAME);
                ActivityIntent.startIntent(HomeActivity.this, FeedPublishedActivity.class);
            }
        }

        @Override
        public void onErro() {

        }
    };






    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            Ln.e(e, "error while save photo from camera");
        }
    }

    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
       // MobclickAgent.onPause(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        if(mHomePopupWindow != null) mHomePopupWindow.setBackground();

    }

    @Override
    public void onClickRight(View v) {
//        ToolTipRelativeLayout toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipRelativeLayout);
//
//        ToolTip toolTip = new ToolTip()
//                .withText("A beauti\n\n\n\n\nful View")
//                .withColor(Color.RED)
//                .withShadow()
//                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
//
//        toolTipRelativeLayout.showToolTipForView(toolTip, v);

        if(mHomePopupWindow == null)
            mHomePopupWindow = new HomePopupWindow(this);
        mHomePopupWindow.show(v , this , mTag);

    }


    @Override
    public void onClick(View v) {
        if(v.getTag() == null)return;
        int position = (int)v.getTag();
        if(position == 1){
            //添加好友
            ActivityIntent.startIntent(this , RequestAddFriendActivity.class);
        }else if(position == 2){
            //添加标签
            ActivityIntent.startForResult(this , FriendTabDetailInfoAndCreateAndAlterActivity.class ,
                    FriendTabDetailInfoAndCreateAndAlterActivity.TABSTATEKEY,
                    FriendTabDetailInfoAndCreateAndAlterActivity.CREATE_STATE , 0X11);

        }else if(position == 3){
            //个人资料
            ActivityIntent.startIntent(this, UserHomeActivity.class);

        }else if(position == 4){
            //意见反馈
            ActivityIntent.startIntent(this, OptionFeedBackActivity.class);
        }

        if (mHomePopupWindow!=null)
            mHomePopupWindow.close();

    }



}
