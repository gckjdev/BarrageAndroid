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

/**
 * Created by pipi on 15/1/6.
 */
public class TimelineFragment extends Fragment {

    private PullToRefreshListView listView;
    private UserTimelineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timeline_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (PullToRefreshListView) getActivity().findViewById(R.id.timeline_listview);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Log.d("TimelineFragment", "onPullDownToRefresh");
                FeedMission.getInstance().getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        listView.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        listView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                Log.d("TimelineFragment", "onPullUpToRefresh");
                FeedMission.getInstance().getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        listView.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        listView.onRefreshComplete();
                    }
                });
            }
        });

        adapter = new UserTimelineAdapter(getActivity());
        listView.setAdapter(adapter);
    }

//    @Override
//    public void onResume(){
////        adapter.notifyDataSetChanged();
//    }

}