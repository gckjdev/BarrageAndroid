package com.orange.barrage.android.feed.ui;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;
import com.orange.protocol.message.BarrageProtos;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2015/1/1.
 */
public class UserTimelineAdapter extends BaseAdapter{

    private Context mContext;
    private Fragment mFragment;



    @Inject
    private FeedManager mFeedManager;

    @Inject
    public UserTimelineAdapter(Context context){
        super();
        this.mContext = context;
    }



    public void setFragment(Fragment fragment){
        this.mFragment = fragment;

    }

//    public UserTimelineAdapter(Context context, TimelineFragment fragment) {
//        super();
//        this.mContext = context;
//        this.mFragment = fragment;
//    }

    @Override
    public int getCount() {
        List list = mFeedManager.getUserTimeline();
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return mFeedManager.getUserTimeline().get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = new TimelineItemView(mContext);
        }

        BarrageProtos.PBFeed feed = mFeedManager.getUserTimeline().get(position);
        FeedModel model = new FeedModel();
        model.setFeed(feed);

        //alter youjiannuo @time 2015/4/3
        //为了绘制弹幕的背景颜色，改变观察者的方式
        ((TimelineItemView)convertView).setModel(model , LayoutDrawIconBackground.SCROOL_DRAWBACKGROUND);


        return convertView;
    }
}
