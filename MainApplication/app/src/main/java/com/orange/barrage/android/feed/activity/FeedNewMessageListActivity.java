package com.orange.barrage.android.feed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import javax.inject.Inject;

import roboguice.inject.InjectView;

/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedNewMessageListActivity extends BarrageCommonActivity{

    @InjectView(R.id.listView1)
    private GridView mGridView;

    private LayoutInflater mLayoutInflater;

    @Inject
    FeedMission mFeedMision;

    @Inject
    FeedManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.view_timeline_list_item , R.string.newmessage,-1);
        initView();
    }

    private void initView(){
        mLayoutInflater = LayoutInflater.from(this);
    }


    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            if(convertView == null){
                v = mLayoutInflater.inflate(R.layout.new_message_item , null);
            }else v = convertView;



            return v;
        }
    }


}
