package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.misc.FileUtil;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class FeedPublishedActivity extends BarrageCommonActivity {

    @InjectView(R.id.imageview)
    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_published_edit , R.string.b_share_main , -1 );
        initView();
    }



    private void initView(){

        mImage.setImageBitmap(FileUtil.getPhotoFromSDCard(HomeActivity.PHOTOPATH, HomeActivity.PHOTONAME));

    }


}
