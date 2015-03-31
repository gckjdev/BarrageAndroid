package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.orange.barrage.android.R;

/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedFirstLognShowBarrageActivity extends FeedNewMessageBarrageActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.show);

    }

    @Override
    public void initView() {
        super.initView();
        mTimelineItemView.setVisibleAllView();
    }


}
