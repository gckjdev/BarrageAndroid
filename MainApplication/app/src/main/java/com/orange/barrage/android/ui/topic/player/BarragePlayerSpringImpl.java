package com.orange.barrage.android.ui.topic.player;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.orange.barrage.android.ui.topic.BarragePlayer;
import com.orange.barrage.android.ui.topic.FeedActionWidget;

import java.util.List;

/**
 * Created by Rollin on 2015/1/17.
 */
public class BarragePlayerSpringImpl implements BarragePlayer {

    private List<FeedActionWidget> mViews;
    private final BarrageSpringChain mSpringChain;
    private Handler mHandler;

    public BarragePlayerSpringImpl(){
        mSpringChain = BarrageSpringChain.create();
        mSpringChain.setDelayBetweenSpring(1000);
        mHandler = new Handler();
    }

    @Override
    public void play() {
        playFrom(0);
    }

    @Override
    public void playFrom(int index) {
        //mSpringChain.setCurrentValue(index);
        moveTo(index);
        int mainIndex = 3;
        for(int i=0;i<mainIndex;i++){
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
        //FIXME: should move to BarrageSpringChain.
        List<Spring> springs = mSpringChain.getAllSprings();
        //set all to 0
        for(int i=0;i<springs.size();i++){
            springs.get(i).setCurrentValue(0, true);
        }

        if(progress>0){
            //start to move before progress.
            int currentIndex = (int)progress;
            for(int i=0;i<currentIndex;i++){
                springs.get(i).setCurrentValue(1, true);
            }

            float currentSpringProgress = progress - (float)currentIndex;
            springs.get(currentIndex).setCurrentValue(currentSpringProgress);
        }
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

    private int mParentHeight = 900;

    @Override
    public void setBarrageViews(List<FeedActionWidget> views) {
        mViews  = views;
        // Add a spring to the SpringChain to do an entry animation.

        for(int i=0;i<mViews.size();i++){
            final View currentView = mViews.get(i);
            mSpringChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    //float val = (float) spring.getCurrentValue();
                    float translationY = (float)SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, mParentHeight, 0);
                    currentView.setTranslationY(translationY);
                }
            });

            mSpringChain.setCurrentValue(0);
        }

    }
}
