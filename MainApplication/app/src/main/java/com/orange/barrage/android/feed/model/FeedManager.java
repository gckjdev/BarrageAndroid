package com.orange.barrage.android.feed.model;

import com.orange.protocol.message.BarrageProtos;

import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/1/6.
 */
@Singleton
public class FeedManager {

    private List<BarrageProtos.PBFeed> userTimelineList = Collections.emptyList();

    public List<BarrageProtos.PBFeed> getUserTimeline(){
        return userTimelineList;
    }

    public void setUserTimelineList(List<BarrageProtos.PBFeed> feedList){
        userTimelineList = feedList;
    }

    public void storeUserTimeline(List<BarrageProtos.PBFeed> list) {
        // TODO save into local cache
        userTimelineList = list;
    }
}
