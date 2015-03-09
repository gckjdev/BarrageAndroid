package com.orange.barrage.android.feed.activity;

import android.os.Bundle;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class CommentActivity extends BarrageCommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_comments ,R.string.b_comment , R.string.b_send);
        setNavigationBackgroundChangeOtherType();
    }
}
