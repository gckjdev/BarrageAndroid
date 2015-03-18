package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.StartActivityFeedCommentEvent;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.ui.topic.player.BarragePlayerSpringImpl;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainInnerWidget extends FrameLayout {

    private ImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews;
    private BarragePlayer mBarragePlayer;
    private PictureTopicModel mModel;
    private Context mContext;
    private PictureTopicMode mMode;

    //FIXME: there's a typo here, and why need another model, reuse mMode:List and comment
    private Modle mModle = new Modle();

    public PictureTopicMainInnerWidget(Context context,AttributeSet set, PictureTopicContainer container) {
        this(context, set);
    }

    public PictureTopicMainInnerWidget(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addView(mSubtitleView, params);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImage = new ImageView(context);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(mImage, imageParams);
        mMode = PictureTopicMode.LIST;

        float width = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        float height = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);

        BarragePlayerSpringImpl barragePlayerSpring = new BarragePlayerSpringImpl();
        barragePlayerSpring.setParentHeight((int)height);
        mBarragePlayer = barragePlayerSpring;

        LayoutParams widgetLayoutParams = new LayoutParams((int)width, (int)height);
        setLayoutParams(widgetLayoutParams);
    }

    @Inject
    public PictureTopicMainInnerWidget(Context context) {
        this(context, null);
    }

    public void setMode(PictureTopicMode mode) {
        mMode = mode;
    }

    public void setImangeURL(String url) {
        Picasso.with(mContext).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(mImage);
    }

    public void setSubtitle(String title) {
        mSubtitleView.setText(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList) {
        if (mFeedActionViews != null) {
            for (View view : mFeedActionViews) {
                removeView(view);
            }
        }
        mFeedActionViews = Lists.newArrayList();
        for (BarrageProtos.PBFeedAction action : feedActionList) {
            FeedActionWidget actionWidget = new FeedActionWidget(mContext);
            actionWidget.setFeedAction(action);
            mFeedActionViews.add(actionWidget);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) action.getPosX();
            params.topMargin = (int) action.getPosY();

            addView(actionWidget, params);
        }

        mBarragePlayer.setBarrageViews(mFeedActionViews);
    }

    public void hideAllBarrageActions() {
        mBarragePlayer.hideAllBarrage();
    }

    public void showAllBarrageActions() {
        mBarragePlayer.showAllBarrage();
    }

    public void play() {
        mBarragePlayer.play();
    }

    public void pause() {
        mBarragePlayer.pause();
    }

    public void resume() {
        mBarragePlayer.resume();
    }

    public void stop() {
        mBarragePlayer.stop();
    }

    public void moveTo(float progress) {
        mBarragePlayer.moveTo(progress);
    }

    public void setModel(PictureTopicModel model) {
        mModel = model;
        setSubtitle(model.getSubtitleText());
        setImangeURL(model.getImageUrl());
        setBarrageActions(model.getFeedActionLis());
    }


    private void startActivityFeedCommentEvent(int x, int y){
        StartActivityFeedCommentEvent event = new StartActivityFeedCommentEvent();
        event.setByteArray( mModel.getFeed().toByteArray());
        event.setPos(new int[]{x, y});
        EventBus.getDefault().post(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mModle.clear();
        }else
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            mModle.is = true;
        }else if( event.getAction() == MotionEvent.ACTION_UP){
            if(!mModle.is){
                startActivityFeedCommentEvent((int) event.getX(), (int) event.getY());
                mModle.clear();
            }
        }

        return true;
    }

    class Modle{
        public boolean is = false;

        public void clear(){
            is = false;
        }

    }

}
