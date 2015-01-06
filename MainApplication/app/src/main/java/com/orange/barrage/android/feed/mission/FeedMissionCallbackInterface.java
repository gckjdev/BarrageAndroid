package com.orange.barrage.android.feed.mission;

import com.orange.protocol.message.BarrageProtos;

import java.util.List;

/**
 * Created by pipi on 15/1/6.
 */
public interface FeedMissionCallbackInterface {

    public void FeedMissionCallback(String id, List<BarrageProtos.PBFeed> list, int errorCode);
}
