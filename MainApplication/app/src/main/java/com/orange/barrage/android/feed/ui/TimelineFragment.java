package com.orange.barrage.android.feed.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.activity.FeedFirstLognShowBarrageActivity;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.protocol.message.BarrageProtos;

import java.util.List;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/6.
 */
public class TimelineFragment extends RoboFragment {

    @InjectView(R.id.timeline_listview)
    PullToRefreshListView mListView;

    @Inject
    UserTimelineAdapter mAdapter;

    @Inject
    FeedMission mFeedMission;
    private View v ;

    @InjectView(R.id.showFriendButton)
    Button mShowFriendButton;

    @InjectView(R.id.notdateshow)
    LinearLayout mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return v = inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        listView = (PullToRefreshListView) getActivity().findViewById(R.id.timeline_listview);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Ln.d("onPullDownToRefresh");
                loadTimeline();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Ln.d("onPullUpToRefresh");
                mFeedMission.getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        mListView.onRefreshComplete();
                        mAdapter.notifyDataSetChanged();
                        showNotMessage();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        mListView.onRefreshComplete();
                        showNotMessage();
                    }
                });
            }
        });

        //启动滚动条
        ((HomeActivity)getActivity()).showTopProgress();

        mAdapter.setFragment(this);

//        EventBus.getDefault().register(this);

        mListView.setAdapter(mAdapter);

        loadTimeline();

        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
               v.setVisibility(View.VISIBLE);
            }
        }.sendEmptyMessage(0);


        mShowFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转

                ActivityIntent.startIntent(getActivity() , FeedFirstLognShowBarrageActivity.class);

            }
        });


        getActivity().findViewById(R.id.showFriendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(getActivity() , FeedFirstLognShowBarrageActivity.class);
            }
        });


    }

    private void loadTimeline() {

        mFeedMission.getTimelineFeed(new FeedMissionCallbackInterface() {
            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                mListView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
                showNotMessage();
            }

            @Override
            public void handleFailure(int errorCode) {
                mListView.onRefreshComplete();
                showNotMessage();
            }
        });

    }

    private void showNotMessage(){
        ((HomeActivity)getActivity()).dismissTopProgress();
        if(mAdapter.getCount() != 0){
            mLayout.setVisibility(View.GONE);
        }else {
            mLayout.setVisibility(View.VISIBLE);
        }
    }

//    @Override
//    public void onResume(){
////        adapter.notifyDataSetChanged();
//    }

}