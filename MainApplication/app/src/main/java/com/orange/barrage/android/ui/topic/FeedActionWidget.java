package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.orange.barrage.android.R;
import com.orange.protocol.message.BarrageProtos;

/**
 * Created by Rollin on 2015/1/12.
 */
public class  FeedActionWidget extends LinearLayout {

    private ImageView mAvatarView;
    private TextView mContent;


    private BarrageProtos.PBFeedAction mFeedAction;

    public FeedActionWidget(Context context) {
        super(context);
        setOrientation(HORIZONTAL);

        mAvatarView = new ImageView(context);
        LayoutParams avatarParams = new LayoutParams(60, 60);
        addView(mAvatarView, avatarParams);

        mContent = new TextView(context);
        LayoutParams contentParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        //FIXME:
        mAvatarView.setImageDrawable(this.getResources().getDrawable(R.drawable.btn_home));
    }

    public void setContent(String content){
        mContent.setText(content);
    }
}