package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.feed.activity.FeedReplyActivity;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.ui.topic.player.BarragePlayerSpringImpl;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainWidget extends FrameLayout {

    private PictureTopicModel mModel;

    private ImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews;
    private BarragePlayer mBarragePlayer;

    private Context mContext;
    private PictureTopicMode mMode;

    public PictureTopicMainWidget(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
        mModel = new PictureTopicModel();

        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addView(mSubtitleView, params);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImage = new ImageView(context);
        this.addView(mImage, imageParams);

        //FIXME: can be change to a factory
        mBarragePlayer = new BarragePlayerSpringImpl();
        mMode = PictureTopicMode.LIST;

        mImage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                switch (mMode) {
                    case LIST: {
                        Intent intent = new Intent(mContext, FeedReplyActivity.class);
                        intent.putExtra("model", mModel);
                        mContext.startActivity(intent);
                        break;
                    }
                    case COMMENT:
                        break;
                }
            }
        });
    }

    @Inject
    public PictureTopicMainWidget(Context context) {
        this(context, null);
    }

    public void setMode(PictureTopicMode mode) {
        mMode = mode;
    }

    public void setImangeURL(String url) {
        Picasso.with(mContext).load(url).into(mImage);
        mModel.setImageUrl(url);
    }

    public void setSubtitle(String title) {
        mSubtitleView.setText(title);
        mModel.setSubtitleText(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList) {
        mModel.setFeedActionLis(feedActionList);

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

            //FIXME: use top later instead of margin
            params.leftMargin = (int) action.getPosX();
            params.topMargin = (int) action.getPosY();
            //actionWidget.setTop((int)action.getPosX());
            //actionWidget.setLeft((int)action.getPosY());
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
}
