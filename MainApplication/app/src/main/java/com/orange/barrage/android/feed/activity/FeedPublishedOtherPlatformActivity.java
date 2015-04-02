package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.ui.topic.FeedMainWidget;
import com.orange.barrage.android.ui.topic.FeedWidgetMode;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.CommonProtos;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FeedPublishedOtherPlatformActivity extends BarrageCommonActivity {

    @InjectView(R.id.feed_main_widget)
    FeedMainWidget mFeedMainWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_feed_publish_other_platform, R.string.b_share_main, R.string.b_grid);
        initView();

        FeedModel model = initData();
        mFeedMainWidget.setMode(FeedWidgetMode.SHARE);
        mFeedMainWidget.setModel(model);

        mFeedMainWidget.getInnerView().moveToEnd();
        mFeedMainWidget.getInnerView().showAllBarrageActions();
    }

    private void initView() {
//        mTopBarView.setNavigationBackgroundChangeOtherType();

        mFeedMainWidget.initActualWidth(ScreenUtil.getWidthPixels());
    }

    //FIXME: Rollin, duplicate codes in CommentActivity
    private FeedModel initData() {
        BarrageProtos.PBFeed feed = null;
        try {
            feed = BarrageProtos.PBFeed.parseFrom(getIntentByteArrays(HomeActivity.KEYSBYTE));
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "init feed comment data, parse data exception=" + e.toString());
        }

        if (feed == null) {
            return null;
        }

        FeedModel model = new FeedModel();
        model.setFeed(feed);
        return model;
    }


    private void getPBPoint(int resid){

        byte[] bytes = FileUtil.getResourceSmallFilebytes(resid , this);
        try {
            CommonProtos.PBPoint pbPoint = CommonProtos.PBPoint.parseFrom(bytes);
//            pbPoint

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }




    @Override
    public void onClickRight(View v) {
        if (mFeedMainWidget == null) return;
        mFeedMainWidget.getInnerView().showOrCloseBarrageGridView();
    }

    //分享到微信朋友圈
    public void onClickweixinFriend(View v) {

    }

    //分享到微信好友
    public void onClickweixin(View v) {

    }

    //分享到新浪
    public void onClickXiangLiang(View v) {

    }

    //分享到QQ空间
    public void onClickQQZone(View v) {

    }

}
