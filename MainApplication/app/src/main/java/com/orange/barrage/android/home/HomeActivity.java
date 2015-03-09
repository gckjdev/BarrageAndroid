package com.orange.barrage.android.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.orange.barrage.android.event.ActionImageCaptureEvent;
import com.orange.barrage.android.event.ActionPickEvent;
import com.orange.barrage.android.feed.activity.FeedPublishedActivity;
import com.orange.barrage.android.feed.mission.PhotoAndCamera;
import com.orange.barrage.android.feed.mission.ShowPublishFeedView;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.File.FileTools;
import com.orange.barrage.android.util.System.SystemTools;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.FramgActivity;
import com.orange.barrage.android.util.activity.RequestCodes;
import com.orange.barrage.android.R;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import roboguice.util.Ln;

public class HomeActivity extends FramgActivity {

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";
    private static final String TAB_3_TAG = "tab_3";

    private FragmentTabHost mTabHost;

    /*当前显示界面的View*/
    private View mCurrentView;


    @Inject
    UserMission mUserMission;

    @Inject
    UserManager mUserManager;

    public static String PHOTOPATH = SystemTools.getSDCardPath()+"/bbl/";
    public static String PHOTONAME = "you.png";

    private ShowPublishFeedView mShowPublisFeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_home,R.string.y_shouyue,R.drawable.y_more_and_more_press);

        initView();
    }

    private void initView() {

      //  MobclickAgent.updateOnlineConfig(this);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);



        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.x_shouye,"首页",R.drawable.tab_select),Tab1Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.x_fabiao,"",R.drawable.tab_select),Tab2Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.x_haoyou,"好友",R.drawable.tab_select),Tab3Container.class,null);


        mCurrentView = mTabHost.getCurrentView();



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       mShowPublisFeedView.getPhotoAndCamera().getPicture(requestCode,resultCode,data, l);

    }

    private PhotoAndCamera.onGetPhotoCallback l = new PhotoAndCamera.onGetPhotoCallback() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            if(bitmap != null) {
                FileTools.savePhotoToSDCard(bitmap , PHOTOPATH , PHOTONAME);
                ActivityIntent.startIntent(HomeActivity.this, FeedPublishedActivity.class);
            }
        }

        @Override
        public void onErro() {

        }
    };



//    public void a(){
//
//
//        Bitmap photo =null;
//        String picturePath = null;
//        if( requestCode == RequestCodes.FEED_CREATE_PICK_IMAGE) {
//            Uri imageUri = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(imageUri,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            if(picturePath.endsWith("jpg") || picturePath.endsWith("png")){
//                try {
//                    //FIXME to big for intent, exceeds 1MB
//                    //photo =
//                    BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                }catch (FileNotFoundException e){
//                    Ln.e(e, "error while loading picture");
//                }
//            }
//        } else if (requestCode == RequestCodes.FEED_CREATE_TAKEN_PICTURE) {
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                photo = (Bitmap) bundle.get("data");
//                picturePath = getCacheDir() + "/temp.jpg";
//                if(photo!=null){
//                    saveImage(photo, picturePath);
//                }
//            }
//        }else  if(requestCode==RequestCodes.FEED_CREATE_REQUEST_CODE){
//            if(resultCode==Activity.RESULT_FIRST_USER){
//                if(data != null) {
//                    ToastUtil.showToastMessage(this,"Create Feed Successfully", Toast.LENGTH_SHORT);
//                }
//            }
//        }
//
//        //convert to path
//        if(photo != null || picturePath !=null){
//            Intent intent = new Intent(ContextManager.getContext(), FeedCreateActivity.class);
//            //intent.putExtra("photo", photo);
//            intent.putExtra("path", picturePath);
//            startActivityForResult(intent, RequestCodes.FEED_CREATE_REQUEST_CODE);
//        }
//
//    }


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
}
