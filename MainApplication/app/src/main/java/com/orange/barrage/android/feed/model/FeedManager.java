package com.orange.barrage.android.feed.model;

import com.orange.barrage.android.util.persistent.LevelDBDAO;
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

    private static final String FEED_CACHED_DB_NAME = "feed_db.db";
    LevelDBDAO mFeedDB = new LevelDBDAO(FEED_CACHED_DB_NAME);

    private List<BarrageProtos.PBFeed> userTimelineList = Collections.emptyList();

    public List<BarrageProtos.PBFeed> getUserTimeline(){
        return userTimelineList;
    }

    public void setUserTimelineList(List<BarrageProtos.PBFeed> feedList){
        userTimelineList = feedList;
    }

    public void storeUserTimeline(List<BarrageProtos.PBFeed> list) {
        // TODO save into local cache
        if (list == null){
            userTimelineList = Collections.emptyList();
        }
        else {
            userTimelineList = list;
        }
    }
}
