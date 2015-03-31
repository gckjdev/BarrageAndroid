package com.orange.barrage.android.feed.ui;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.FloatWindow;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class ShowPublishFeedView implements View.OnClickListener {

    private Activity mActivity;

    private FloatWindow mWin;

    private ImageButton mPhotoButton;
    private ImageButton mCameraButton;
    private PhotoAndCamera mPhotoAndCamera;
    private PhotoAndCamera.onGetPhotoCallback mGetPhotoCallback;

    public ShowPublishFeedView(Activity activity,PhotoAndCamera.onGetPhotoCallback getPhotoCallback){
        this.mActivity = activity;
        this.mGetPhotoCallback = getPhotoCallback;
    }

    public void showPublishFeedView(){
        initFloatWindow();

        mWin.show();

        View v = mWin.getContextView();
        mPhotoButton = mPhotoButton != null ? mPhotoButton : (ImageButton)v.findViewById(R.id.potoButton);
        mCameraButton = mCameraButton != null ? mCameraButton : (ImageButton)v.findViewById(R.id.camareButton);
        mPhotoButton.setOnClickListener(this);
        mCameraButton.setOnClickListener(this);
    }

    public void closePublishFeedView(){
        if(mWin == null) return;
        mWin.close();
    }

    private boolean initFloatWindow(){

        boolean is;

        mWin = (is = mWin != null) ? mWin :
                new FloatWindow(R.layout.activity_pop_photo_and_camera , mActivity);

        return is;
    }

    private void initPhoto(){
        mPhotoAndCamera =
                mPhotoAndCamera == null ? new PhotoAndCamera(mActivity) : mPhotoAndCamera;
    }

    public PhotoAndCamera getPhotoAndCamera(){
        return mPhotoAndCamera;
    }

    @Override
    public void onClick(View v) {
        initPhoto();
        if(v == mCameraButton){
            mPhotoAndCamera.takePicture(mGetPhotoCallback);
        }else if(v == mPhotoButton){
            mPhotoAndCamera.choosePhoto(mGetPhotoCallback);
        }
        closePublishFeedView();
    }
}
