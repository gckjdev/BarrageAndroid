package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.orange.protocol.message.BarrageProtos;

/**
 * Created by Rollin on 2015/1/12.
 */
public class FeedActionWidget extends FrameLayout {

    private FetchableImageView mAvatarView;
    private TextView mContent;


    private BarrageProtos.PBFeedAction mFeedAction;

    public FeedActionWidget(Context context) {
        super(context);

        mAvatarView = new FetchableImageView(context, null);
        LayoutParams avatarParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.LEFT);
        addView(mAvatarView, avatarParams);

        mContent = new TextView(context);
        LayoutParams contentParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
        //FIXME: text aligment
        addView(mContent, contentParams);
    }

    public void setFeedAction(BarrageProtos.PBFeedAction feedAction){
        mFeedAction = feedAction;
        setAvadar(mFeedAction.getAvatar());
        setContent(mFeedAction.getText());
    }

    public BarrageProtos.PBFeedAction getFeedAction() {
        return mFeedAction;
    }

    public void setAvadar(String url){
        mAvatarView.setImage(url);
    }

    public void setContent(String content){
        mContent.setText(content);
    }
}
