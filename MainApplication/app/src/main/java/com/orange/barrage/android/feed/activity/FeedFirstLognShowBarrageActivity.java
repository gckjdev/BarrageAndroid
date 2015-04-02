package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.TimelineItemView;
import com.orange.barrage.android.ui.topic.FeedLoadingPhotoListener;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.CommonProtos;


import java.util.Date;

import roboguice.util.Ln;


/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedFirstLognShowBarrageActivity extends FeedNewMessageBarrageActivity
                        implements TimelineItemView.onTouchTimelineItemViewListener , FeedLoadingPhotoListener.OnLoadPhotoListener{


    private int mDemo[] = {R.raw.demo1,R.raw.demo2,R.raw.demo3,R.raw.demo4,R.raw.demo5,R.raw.demo6,R.raw.demo7,
            R.raw.demo8,R.raw.demo9,R.raw.demo10,R.raw.demo11,R.raw.demo12,R.raw.demo13,};
    private int mPostion = 0;

    private boolean isShowToast = false;
    private long mPostionTime = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.show);
    }

    @Override
     public void initView() {
        super.initView();
        mTimelineItemView.setVisibleAllView();
        mTimelineItemView.setOnTimelineItemViewTouchListener(this);
        mTimelineItemView.setOnFeedLoadPhotoListener(this);
        changeBarrager();

    }


    private void changeBarrager(){
        mTimelineItemView.clearAllOnFeedLoadPhotoListener();
        Message message = new Message();
        message.obj = mDemo[mPostion >= mDemo.length ? ((mPostion = 0) == 0? mPostion ++: 0 ) : mPostion ++];
        handler.sendMessage(message);
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte[] bs = FileUtil.getResourceSmallFilebytes((Integer) msg.obj, FeedFirstLognShowBarrageActivity.this);

            try {
                BarrageProtos.PBFeed mFeed = BarrageProtos.PBFeed.parseFrom(bs);
                FeedModel feedModel = new FeedModel();
                feedModel.setFeed(mFeed);
                mTimelineItemView.setModel(feedModel);

            } catch (InvalidProtocolBufferException e) {
                Ln.d(e);
            }

        }
    };

    private boolean isNext(){
        boolean is = true;
        long time = new Date().getTime();
        if(time - mPostionTime < 2000){
            if(isShowToast) {
                MessageCenter.postInfoMessage("你点得太快了，人家受不了了");
                isShowToast = false;
            }
            is = false;
        }
        mPostionTime = time;
        return is;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_DOWN &&isNext() ) {
            mTimelineItemView.stopPlayer();
            changeBarrager();
            isShowToast = true;
        }
        return false;
    }

    @Override
    public boolean onTimelineItemTouch(MotionEvent event) {

        onTouchEvent(event);

        return true;
    }


    @Override
    public void onFinish() {
        mTimelineItemView.onClickPlayer(null);
    }
}
