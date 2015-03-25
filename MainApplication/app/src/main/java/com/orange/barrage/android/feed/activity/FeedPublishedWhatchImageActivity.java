package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.misc.FileUtil;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class FeedPublishedWhatchImageActivity extends BarrageCommonActivity {

    @InjectView(R.id.imageview)
    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_published_whatch, R.string.b_share_main , R.string.b_next );
        initView();
    }



    private void initView(){

        mImage.setImageBitmap(FileUtil.getPhotoFromSDCard(HomeActivity.PHOTOPATH, HomeActivity.PHOTONAME));

    }

    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
    }
}
