package com.orange.barrage.android.feed.ui;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
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

//        if(convertView == null){
//            convertView = new PictureTopicContainer(this.mContext);
//        }
//
//        PictureTopicContainer container = (PictureTopicContainer) convertView;
//        BarrageProtos.PBFeed feed = mFeedManager.getUserTimeline().get(position);
//
//        PictureTopicModel model = new PictureTopicModel();
//        model.setFeed(feed);
//        container.setModel(model);
        if (convertView == null){
            convertView = new TimelineItemView(mContext);
        }

        BarrageProtos.PBFeed feed = mFeedManager.getUserTimeline().get(position);
        PictureTopicModel model = new PictureTopicModel();
        model.setFeed(feed);
        ((TimelineItemView)convertView).setModel(model , mFragment.getActivity());
//
//        TextView userNickTextView = (TextView)convertView.findViewById(R.id.timeline_item_user_nick);
//        userNickTextView.setText(feed.getCreateUser().getNick());
//
//        TextView dateTextView = (TextView)convertView.findViewById(R.id.timeline_item_date);
//        dateTextView.setText(DateUtil.dateFormatToString(feed.getDate(), mContext));
//
//        ImageView barrageView = (ImageView) convertView.findViewById(R.id.timeline_item_barage_image);
//        barrageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // goto reply view
//                Ln.d("click image view, go to reply");
//
//                ReplyFeedFragment replyFeedFragment = new ReplyFeedFragment();
//
//                FragmentTransaction transaction = mFragment.getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.timeline_fragment, replyFeedFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//                mFragment.getChildFragmentManager().executePendingTransactions();
//
//            }
//        });
//
//        Picasso.with(mContext)
//                .load(feed.getImage())
//                .placeholder(R.drawable.tab_home)           // default
//                .error(R.drawable.tab_friend)               // error loading
//                .into(barrageView);

        return convertView;
    }
}
