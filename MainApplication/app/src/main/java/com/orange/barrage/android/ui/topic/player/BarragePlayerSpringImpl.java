package com.orange.barrage.android.ui.topic.player;

import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringUtil;
import com.orange.barrage.android.ui.topic.BarragePlayer;
import com.orange.barrage.android.ui.topic.FeedActionWidget;

import java.util.List;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/17.
 */
public class BarragePlayerSpringImpl implements BarragePlayer {

    private final static int MAIN_SPRING_CONFIG_INDEX = 3;
    private List<FeedActionWidget> mViews;
    private BarrageSpringChain mSpringChain;

    private int mParentHeight = 860;

    private final int mDelayBetweenSpring = 800;

    public BarragePlayerSpringImpl(){
    }

    private void reset() {
        mSpringChain = BarrageSpringChain.create();
        mSpringChain.setDelayBetweenSpring(mDelayBetweenSpring);
    }

    @Override
    public void play() {
        playFrom(0);
    }

    @Override
    public void playFrom(int index) {
        moveTo(index);
        int mainIndex = MAIN_SPRING_CONFIG_INDEX;
        for(int i=0;i<mainIndex && i<mSpringChain.getAllSprings().size();i++){
            mSpringChain.setControlSpringIndex(i);
        }
        mSpringChain.start();
    }

    @Override
    public void pause() {
        mSpringChain.stop();
    }

    @Override
    public void resume() {
        mSpringChain.start();
    }

    @Override
    public void stop() {
        mSpringChain.stop();
    }

    @Override
    public void moveTo(float progress) {
        mSpringChain.moveTo(progress);
    }

    @Override
    public void hideAllBarrage() {
        for(View view:mViews){
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showAllBarrage() {
        for(View view:mViews){
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setParentHeight(int parentHeight) {
        mParentHeight = parentHeight;
    }

    @Override
    public void setBarrageViews(List<FeedActionWidget> views) {
        mViews  = views;
        reset();

        // Add a spring to the SpringChain to do an entry animation.
        for(int i=0;i<mViews.size();i++){
            final View currentView = mViews.get(i);
            mSpringChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    int topMargin = currentView.getTop();
                    float initY = mParentHeight - topMargin;
                    float translationY = (float)SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, initY, 0);
                    Ln.v("initY: %.2f, translationY: %.2f", initY, translationY);
                    currentView.setTranslationY(translationY);
                }
            });
        }
        mSpringChain.setCurrentValue(0);
    }
}
