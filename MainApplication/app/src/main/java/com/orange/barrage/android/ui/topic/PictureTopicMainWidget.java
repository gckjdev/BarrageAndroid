package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.topic.model.PictureTopicItem;
import com.orange.barrage.android.ui.topic.player.BarragePlayerSpringImpl;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainWidget extends FrameLayout {


    //private PictureTopicItem mItem;

    //FIXME: change to view
    private ImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews;
    private BarragePlayer mBarragePlayer;

    private Context mContext;

    public PictureTopicMainWidget(Context context, AttributeSet set){
        super(context, set);
        this.mContext = context;

        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        this.addView(mSubtitleView, params);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImage = new ImageView(context);
        this.addView(mImage, imageParams);

        //FIXME: can be change to a factory
        mBarragePlayer = new BarragePlayerSpringImpl();
    }
    @Inject
    public PictureTopicMainWidget(Context context){
        this(context, null);
    }

    public void setImangeURL(String url){
        Picasso.with(mContext).load(url).into(mImage);
    }

    public void setSubtitle(String title){
        mSubtitleView.setText(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList){

        if(mFeedActionViews!=null) {
            for (View view : mFeedActionViews) {
                removeView(view);
            }
        }
        mFeedActionViews = Lists.newArrayList();
        for(BarrageProtos.PBFeedAction action :feedActionList){
            FeedActionWidget actionWidget = new FeedActionWidget(mContext);
            actionWidget.setFeedAction(action);
            mFeedActionViews.add(actionWidget);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int)action.getPosX();
            params.topMargin  =(int) action.getPosY();

            addView(actionWidget, params);
        }

        mBarragePlayer.setBarrageViews(mFeedActionViews);
    }

    public void hideAllBarrageActions(){
        mBarragePlayer.hideAllBarrage();
    }

    public void showAllBarrageActions(){
        mBarragePlayer.showAllBarrage();
    }

    public void play(){
        mBarragePlayer.play();
    }

    public void pause(){
        mBarragePlayer.pause();
    }

    public void resume(){
        mBarragePlayer.resume();
    }

    public void stop(){
        mBarragePlayer.stop();
    }

    public void moveTo(float progress){
        mBarragePlayer.moveTo(progress);
    }
}
