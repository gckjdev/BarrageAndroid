package com.orange.barrage.android.ui.topic.player;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

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

    private final static int MAIN_SPRING_CONFIG_INDEX = 3;
    private List<FeedActionWidget> mViews;
    private final BarrageSpringChain mSpringChain;
    private Handler mHandler;

    //FIXME: change it later
    private int mParentHeight = 900;

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

    @Override
    public void setBarrageViews(List<FeedActionWidget> views) {
        mViews  = views;
        // Add a spring to the SpringChain to do an entry animation.

        for(int i=0;i<mViews.size();i++){
            final View currentView = mViews.get(i);
            mSpringChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    //FIXME: use top later instead of margin
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)currentView.getLayoutParams();
                    float initY = mParentHeight - layoutParams.topMargin;
                    float translationY = (float)SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, initY, 0);
                    currentView.setTranslationY(translationY);
                }
            });

            mSpringChain.setCurrentValue(0);
        }

    }
}
