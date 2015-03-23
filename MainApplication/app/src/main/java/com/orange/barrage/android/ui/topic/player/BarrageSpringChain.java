/*
 *  Copyright (c) 2013, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 */

package com.orange.barrage.android.ui.topic.player;

import android.os.Handler;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringConfigRegistry;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * SpringChain is a helper class for creating spring animations with multiple springs in a chain.
 * Chains of springs can be used to create cascading animations that maintain individual physics
 * state for each member of the chain. One spring in the chain is chosen to be the control spring.
 * Springs before and after the control spring in the chain are pulled along by their predecessor.
 * You can change which spring is the control spring at any point by calling
 * {@link com.facebook.rebound.SpringChain#setControlSpringIndex(int)}.
 * <p/>
 * It's copied from SpringChain to configure the different style for barrage for better animation
 */
public class BarrageSpringChain implements SpringListener {

    // The main spring config defines the tension and friction for the control spring. Keeping these
    // values separate allows the behavior of the trailing springs to be different than that of the
    // control point.
    private static final SpringConfig MAIN_SPRING_CONFIG =
            SpringConfig.fromOrigamiTensionAndFriction(20, 3);//(40, 6);
    // The attachment spring config defines the tension and friction for the rest of the springs in
    // the chain.
    private static final SpringConfig ATTACHMENT_SPRING_CONFIG =
            SpringConfig.fromOrigamiTensionAndFriction(3, 20);//(70, 30);

    /**
     * Add these spring configs to the registry to support live tuning through the
     * {@link com.facebook.rebound.ui.SpringConfiguratorView}
     */
    static {
        SpringConfigRegistry registry = SpringConfigRegistry.getInstance();
        registry.addSpringConfig(MAIN_SPRING_CONFIG, "main spring");
        registry.addSpringConfig(ATTACHMENT_SPRING_CONFIG, "attachment spring");
    }

    private SpringConfig mMainSpringConfig = MAIN_SPRING_CONFIG;

    private SpringConfig mAttachmentSpringConfig = ATTACHMENT_SPRING_CONFIG;

    private long mDelayBetweenSpring = 0;

    public long getDelayBetweenSpring() {
        return mDelayBetweenSpring;
    }

    public void setDelayBetweenSpring(long delayBetweenSpring) {
        mDelayBetweenSpring = delayBetweenSpring;
    }

    public SpringConfig getAttachmentSpringConfig() {
        return mAttachmentSpringConfig;
    }

    public SpringConfig getMainSpringConfig() {
        return mMainSpringConfig;
    }

    public void setMainSpringConfig(SpringConfig config) {
        SpringConfigRegistry registry = SpringConfigRegistry.getInstance();
        registry.removeSpringConfig(mMainSpringConfig);
        this.mMainSpringConfig = config;
        registry.addSpringConfig(mMainSpringConfig, "main spring");
    }

    public void setAttachementSpringConfig(SpringConfig config) {
        SpringConfigRegistry registry = SpringConfigRegistry.getInstance();
        registry.removeSpringConfig(mAttachmentSpringConfig);
        this.mAttachmentSpringConfig = config;
        registry.addSpringConfig(mAttachmentSpringConfig, "attachment spring");
    }

    /**
     * Static factor method for creating a new SpringChain.
     *
     * @return the newly created SpringChain
     */
    public static BarrageSpringChain create() {
        return new BarrageSpringChain();
    }

    private final SpringSystem mSpringSystem = SpringSystem.create();
    private final CopyOnWriteArrayList<SpringListener> mListeners =
            new CopyOnWriteArrayList<SpringListener>();
    private final CopyOnWriteArrayList<Spring> mSprings = new CopyOnWriteArrayList<Spring>();
    private int mControlSpringIndex = -1;

    private Handler mHandler = new Handler();

    private CopyOnWriteArrayList<Runnable> mRunnings = new CopyOnWriteArrayList<Runnable>();
    /**
     * Add a spring to the chain that will callback to the provided listener.
     *
     * @param listener the listener to notify for this Spring in the chain
     * @return this SpringChain for chaining
     */
    public BarrageSpringChain addSpring(final SpringListener listener) {
        // We listen to each spring added to the SpringChain and dynamically chain the springs together
        // whenever the control spring state is modified.
        Spring spring = mSpringSystem
                .createSpring()
                .addListener(this)
                .setSpringConfig(getAttachmentSpringConfig());
        mSprings.add(spring);
        mListeners.add(listener);
        return this;
    }

    public void start(){
        //stop all
        stop();

        int size = mSprings.size();

        //find current view already set 1
        int currentIndex = 0;
        for(int i=0;i<size;i++) {
            final int index = i;
            final Spring spring = mSprings.get(index);
            if(spring.getCurrentValue()==1){
                currentIndex++;
            }
        }

        //start from progress not 1, to the end , and set them to 1.
        for(int i=currentIndex;i<size;i++){
            final Spring spring = mSprings.get(i);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    spring.setEndValue(1);
                }
            };
            mRunnings.add(runnable);
            int deltaIndex = i- currentIndex;
            mHandler.postDelayed(runnable, deltaIndex * mDelayBetweenSpring);
        }
    }

    public void stop(){
        for(Runnable runnable:mRunnings) {
            mHandler.removeCallbacks(runnable);
        }
        mRunnings.clear();
    }

    public void moveToEnd(){
        stop();
        List<Spring> springs = mSprings;
        //set all to 0
        for(int i=0;i<springs.size();i++){
            springs.get(i).setCurrentValue(1, true);
        }
    }

    public void moveTo(float progress){
        stop();
        List<Spring> springs = mSprings;
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

    public BarrageSpringChain setCurrentValue(double currentValue){
        int size = mSprings.size();
        for(int i=0;i<size;i++){
            Spring spring = mSprings.get(i);
            spring.setCurrentValue(currentValue);
        }
        return this;
    }

    public BarrageSpringChain setControlSpringIndex(int i) {
        mControlSpringIndex = i;
        Spring controlSpring = mSprings.get(mControlSpringIndex);
        if (controlSpring == null) {
            return null;
        }
//        for (Spring spring : mSpringSystem.getAllSprings()) {
//            spring.setSpringConfig(getAttachmentSpringConfig());
//        }
        getControlSpring().setSpringConfig(getMainSpringConfig());
        return this;
    }

    /**
     * Retrieve the control spring so you can manipulate it to drive the positions of the other
     * springs.
     *
     * @return the control spring.
     */
    public Spring getControlSpring() {
        return mSprings.get(mControlSpringIndex);
    }

    /**
     * Retrieve the list of springs in the chain.
     *
     * @return the list of springs
     */
    public List<Spring> getAllSprings() {
        return mSprings;
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        // Get the control spring index and update the endValue of each spring above and below it in the
        // spring collection triggering a cascading effect.
        int idx = mSprings.indexOf(spring);
        SpringListener listener = mListeners.get(idx);
        listener.onSpringUpdate(spring);
    }

    @Override
    public void onSpringAtRest(Spring spring) {
        int idx = mSprings.indexOf(spring);
        mListeners.get(idx).onSpringAtRest(spring);
    }

    @Override
    public void onSpringActivate(Spring spring) {
        int idx = mSprings.indexOf(spring);
        mListeners.get(idx).onSpringActivate(spring);
    }

    @Override
    public void onSpringEndStateChange(Spring spring) {
        int idx = mSprings.indexOf(spring);
        mListeners.get(idx).onSpringEndStateChange(spring);
    }
}
