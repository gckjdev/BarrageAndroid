package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.orange.barrage.android.ui.topic.model.PictureTopicItem;
import com.orange.protocol.message.BarrageProtos;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainWidget extends FrameLayout {


    //private PictureTopicItem mItem;

    //FIXME: change to view
    private FetchableImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews;

    private Context mContext;

    public PictureTopicMainWidget(Context context, AttributeSet set){
        super(context, set);
        this.mContext = context;

        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        this.addView(mSubtitleView, params);

        mImage = new FetchableImageView(context, null);

        this.addView(mImage);
    }
    @Inject
    public PictureTopicMainWidget(Context context){
        this(context, null);
    }

    public void setImangeURL(String url){
        mImage.setImage(url);
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
    }

    public void hideAllBarrageActions(){
        for(View view:mFeedActionViews){
            view.setVisibility(INVISIBLE);
        }
    }

    public void showAllBarrageActions(){
        for(View view:mFeedActionViews){
            view.setVisibility(VISIBLE);
        }
    }

}
