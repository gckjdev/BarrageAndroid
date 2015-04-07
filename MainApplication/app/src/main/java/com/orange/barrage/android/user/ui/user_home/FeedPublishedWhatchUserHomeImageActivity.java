package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.activity.FeedPublishedWhatchImageActivity;

public class FeedPublishedWhatchUserHomeImageActivity extends FeedPublishedWhatchImageActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //更改用户背景的时候或许需要另一个页面了
        mTopBarView.setTitleText("设置用户头像");
        mTopBarView.setRightButton(R.string.b_OK);
    }

    @Override
    public void onClickRight(View v) {
        finish();
    }
}
