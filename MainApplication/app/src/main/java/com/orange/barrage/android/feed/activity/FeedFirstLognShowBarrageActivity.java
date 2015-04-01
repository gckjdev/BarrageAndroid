package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.orange.barrage.android.R;



/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedFirstLognShowBarrageActivity extends FeedNewMessageBarrageActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.show);
    }

    @Override
     public void initView() {
        super.initView();
        mTimelineItemView.setVisibleAllView();



    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

//    private byte[] getDemoByte(int resid){
////        File
//    }


}
