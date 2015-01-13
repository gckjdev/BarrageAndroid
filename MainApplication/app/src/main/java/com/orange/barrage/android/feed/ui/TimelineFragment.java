package com.orange.barrage.android.feed.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
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

    UserTimelineAdapter mAdapter;

    @Inject
    FeedMission mFeedMission;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timeline_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //listView = (PullToRefreshListView) getActivity().findViewById(R.id.timeline_listview);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Ln.d("onPullDownToRefresh");
                mFeedMission.getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        mListView.onRefreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        mListView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Ln.d("onPullUpToRefresh");
                mFeedMission.getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        mListView.onRefreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        mListView.onRefreshComplete();
                    }
                });
            }
        });

        mAdapter = new UserTimelineAdapter(getActivity(), this);
        mListView.setAdapter(mAdapter);
    }

//    @Override
//    public void onResume(){
////        adapter.notifyDataSetChanged();
//    }

}