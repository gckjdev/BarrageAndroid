package com.orange.barrage.android.ui.topic.model;

import com.orange.protocol.message.BarrageProtos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rollin on 2015/1/25.
 */
public class PictureTopicModel implements Serializable{

    private String mImageUrl;
    private String mSubtitleText;
    private BarrageProtos.PBFeed mFeed;

    private List<BarrageProtos.PBFeedAction> mFeedActionLis;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getSubtitleText() {
        return mSubtitleText;
    }

    public void setSubtitleText(String subtitleText) {
        mSubtitleText = subtitleText;
    }

    public List<BarrageProtos.PBFeedAction> getFeedActionLis() {
        return mFeedActionLis;
    }

    public void setFeedActionLis(List<BarrageProtos.PBFeedAction> feedActionLis) {
        mFeedActionLis = feedActionLis;
    }

    public BarrageProtos.PBFeed getFeed() {
        return mFeed;
    }

    public void setFeed(BarrageProtos.PBFeed feed) {
        mFeed = feed;
    }
}
