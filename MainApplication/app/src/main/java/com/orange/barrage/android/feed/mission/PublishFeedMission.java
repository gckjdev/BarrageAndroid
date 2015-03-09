package com.orange.barrage.android.feed.mission;

import com.orange.barrage.android.util.model.CommonMission;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/3/6.
 */

@ContextSingleton
public class PublishFeedMission extends CommonMission {

    @Inject
    FeedMission mFeedMission;

    public void showPublishFeedView(){
        // TODO
    }
}
