package com.orange.barrage.android.ui.topic.model;

import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Rollin on 2015/1/25.
 */
public class FeedModel implements Serializable{

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
        mFeedActionLis = new ArrayList<>(feedActionLis);
    }

    public BarrageProtos.PBFeed getFeed() {
        return mFeed;
    }

    public int getIntDate(){
        return mFeed.getDate();
    }

    public List<UserProtos.PBUser> getToUsers(){
        return mFeed.getToUsersList();
    }

    public void setFeed(BarrageProtos.PBFeed feed) {
        mFeed = feed;
        setSubtitleText(feed.getText());
        setImageUrl(feed.getImage());
        setFeedActionLis(feed.getActionsList());
        feed.getActionsList();
    }
}
