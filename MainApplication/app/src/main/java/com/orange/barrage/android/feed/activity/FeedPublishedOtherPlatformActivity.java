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
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.CommonProtos;

import java.util.List;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FeedPublishedOtherPlatformActivity extends BarrageCommonActivity {

    @InjectView(R.id.feed_main_widget)
    FeedMainWidget mFeedMainWidget;

    private FeedModel model;

    private float mWidth;

    int mLayouts[] = {R.raw.layout1 , R.raw.layout2,R.raw.layout3,R.raw.layout4,R.raw.layout5,R.raw.layout6,R.raw.layout7,R.raw.layout8,R.raw.layout9,R.raw.layout10};
    int mPostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_feed_publish_other_platform, R.string.b_share_main, R.string.b_grid);

        mWidth =  mFeedMainWidget.getInnerView().getGridViewWidth();
        FeedModel model = initData();
        mFeedMainWidget.setMode(FeedWidgetMode.SHARE);
        mFeedMainWidget.setModel(model , LayoutDrawIconBackground.LAYOUT_DRAWBAKGROUND);

        mFeedMainWidget.getInnerView().moveToEnd();
        mFeedMainWidget.getInnerView().showAllBarrageActions();
    }

    @Override
    protected void initView() {
        super.initView();

        mFeedMainWidget.initActualWidth(ScreenUtil.getWidthPixels());
    }

    private FeedModel initData(){
        return initData(R.raw.layout1);
    }

    //FIXME: Rollin, duplicate codes in CommentActivity
    private FeedModel initData(int resid) {
        BarrageProtos.PBFeed feed = null;
        try {
            feed = BarrageProtos.PBFeed.parseFrom(getIntentByteArrays(HomeActivity.KEYSBYTE));
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "init feed comment data, parse data exception=" + e.toString());
        }

        if (feed == null) {
            return null;
        }

        model = new FeedModel();
        model.setFeed(feed);

        changeModel( resid , model.getFeedActionLis());
        return model;
    }

    public void changeLayout(View v){
        if(mPostion >= mLayouts.length ) mPostion = 0;
        changAddress(mLayouts[mPostion++]);
    }


    private void changAddress(int resid){
        changeModel(resid , model.getFeedActionLis());
    }


    private void changeModel(int resid, List<BarrageProtos.PBFeedAction> feedActionWidgets){

        CommonProtos.PBPointList pbPointList = getPBPoint(resid);
        if(pbPointList == null) return;

        int n = feedActionWidgets.size() < pbPointList.getPointCount()?feedActionWidgets.size() : pbPointList.getPointCount();

        for(int i = 0 ; i < n ; i ++){
            feedActionWidgets.set( i , changePPFeedAction(feedActionWidgets.get(i) , pbPointList.getPoint(i)));
        }
    }

    private BarrageProtos.PBFeedAction changePPFeedAction(BarrageProtos.PBFeedAction feedAction , CommonProtos.PBPoint pbPoint){

        BarrageProtos.PBFeedAction.Builder builder =  BarrageProtos.PBFeedAction.newBuilder(feedAction);

        builder.setPosY(pbPoint.getPoxY() * mWidth);
        builder.setPosX(pbPoint.getPosX() * mWidth);

        return builder.build();
    }

    private CommonProtos.PBPointList getPBPoint(int resid){
        if(resid < 0) return null;
        byte[] bytes = FileUtil.getResourceSmallFilebytes(resid , this);

        try {
            CommonProtos.PBPointList pbPointList = CommonProtos.PBPointList.parseFrom(bytes);
            return pbPointList;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return null;
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
