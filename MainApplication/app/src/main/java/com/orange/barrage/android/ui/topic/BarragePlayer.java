package com.orange.barrage.android.ui.topic;

import java.util.List;

/**
 * Created by Rollin on 2015/1/17.
 */
public interface BarragePlayer {

    void play();

    void playFrom(int index);

    void pause();

    void resume();

    void stop();

    void moveTo(float progress);

    void hideAllBarrage();

    void showAllBarrage();

    void setBarrageViews(List<FeedActionWidget> views);
}
