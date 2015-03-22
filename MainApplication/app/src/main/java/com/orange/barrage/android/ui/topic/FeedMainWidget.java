package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.util.view.ScalableFrameLayout;
import com.orange.protocol.message.BarrageProtos;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2014/11/30.
 */
public class FeedMainWidget extends ScalableFrameLayout<FeedMainInnerWidget> {

    public FeedMainWidget(Context context, AttributeSet set) {
        super(context, set);
    }

    @Inject
    public FeedMainWidget(Context context) {
        super(context);
    }

    public void setMode(FeedWidgetMode mode) {
        mInnerView.setMode(mode);
    }

    public void setImageURL(String url) {
        mInnerView.setImageURL(url);
    }

    public void setSubtitle(String title) {
        mInnerView.setSubtitle(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList) {
        mInnerView.setBarrageActions(feedActionList);
    }

    public void hideAllBarrageActions() {
        mInnerView.hideAllBarrageActions();
    }

    public void showAllBarrageActions() {
        mInnerView.showAllBarrageActions();
    }

    public void play() {
        mInnerView.play();
    }

    public void pause() {
        mInnerView.pause();
    }

    public void resume() {
        mInnerView.resume();
    }

    public void stop() {
        mInnerView.stop();
    }

    public void moveTo(float progress) {
        mInnerView.moveTo(progress);
    }

    public void setModel(PictureTopicModel model) {
        mInnerView.setModel(model);
    }

    public PictureTopicModel getModel(){
        return mInnerView.getModel();
    }

    @Override
    protected FeedMainInnerWidget createInnerView(Context context) {
        return new FeedMainInnerWidget(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mInnerView.onTouchEvent(event);
    }

    @Override
    protected float getExpectedHeight() {
        return getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);
    }

    @Override
    protected float getExpectedWidth() {
        return getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
    }
}
