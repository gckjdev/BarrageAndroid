package com.orange.barrage.android.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orange.barrage.android.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.ui.component.PullDownView;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.util.network.BarrageNetworkClient;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rollin on 2014/12/27.
 */
public class TopicPage extends LinearLayout {

    private static final int WHAT_DID_LOAD_DATA = 0;
    private static final int WHAT_DID_REFRESH = 1;
    private static final int WHAT_DID_MORE = 2;

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        mTopicListView.setAdapter(mAdapter);
        mPullDownView.enableAutoFetchMore(true, 1);
    }

    private BaseAdapter mAdapter;

    private PullDownView mPullDownView;
    private ListView mTopicListView;
    private Handler mUIHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DID_LOAD_DATA:{
                    if(msg.obj != null){
                        List<String> strings = (List<String>) msg.obj;
                        if(!strings.isEmpty()){
                            //mStrings.addAll(strings);
                            PictureTopicDummyDataGen.loadMore();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    // 诉它数据加载完毕;
                    mPullDownView.notifyDidLoad();
                    break;
                }
                case WHAT_DID_REFRESH :{
                    String body = (String) msg.obj;

                    PictureTopicDummyDataGen.init();
                    //mStrings.add(0, body);
                    mAdapter.notifyDataSetChanged();
                    // 告诉它更新完毕
                    mPullDownView.notifyDidRefresh();
                    break;
                }

                case WHAT_DID_MORE:{
                    String body = (String) msg.obj;
                    PictureTopicDummyDataGen.loadMore();
                    //mStrings.add(body);
                    mAdapter.notifyDataSetChanged();
                    // 告诉它获取更多完毕
                    mPullDownView.notifyDidMore();
                    break;
                }
            }

        }

    };

    public void loadData(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> strings = new ArrayList<String>();
                //for (String body : mStringArray) {
                //    strings.add(body);
                //}
                Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
                msg.obj = strings;
                msg.sendToTarget();
            }
        }).start();
    }

    public TopicPage(Context context){
        super(context);

        //LayoutInflater inflater = LayoutInflater.from(context);
        //LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.topic_page, null);

        //addView(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT );
        //mTopicListView =(ListView) findViewById(R.id.topic_list_view);

        mPullDownView = new PullDownView(context);
        addView(mPullDownView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT );
        mTopicListView = mPullDownView.getListView();


        mPullDownView.setOnPullDownListener(new PullDownView.OnPullDownListener(){
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
                        msg.obj = "After refresh " + System.currentTimeMillis();
                        msg.sendToTarget();

//                        BarrageNetworkClient.test();

                        /* user test
                        UserMission.getInstance().regiseterUserByEmail("test@163.com", "password", null, new UserMissionCallback() {

                            @Override
                            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                                Ln.d("test", "regiseterUserByEmail callback invoke test");
                            }
                        });
                        */
                    }
                }).start();
            }

            @Override
            public void onMore() {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
                        msg.obj = "After more " + System.currentTimeMillis();
                        msg.sendToTarget();
                    }
                }).start();
            }
        });

//        BarrageNetworkClient.test();
    }
}
