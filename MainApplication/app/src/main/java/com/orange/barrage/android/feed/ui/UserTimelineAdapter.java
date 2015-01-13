package com.orange.barrage.android.feed.ui;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.data.PictureTopicData;
import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.model.PictureTopicItem;
import com.orange.barrage.android.ui.topic.PictureTopicContainer;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/1.
 */
public class UserTimelineAdapter extends BaseAdapter{

    private Context mContext;
    private Fragment mFragment;

    @Inject
    private FeedManager mFeedManager;

//    @Inject
//    public UserTimelineAdapter(Context context){
//        super();
//        this.mContext = context;
//    }

    public UserTimelineAdapter(Context context, TimelineFragment fragment) {
        super();
        this.mContext = context;
        this.mFragment = fragment;
    }

    @Override
    public int getCount() {
        return mFeedManager.getUserTimeline().size();
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
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.timeline_list_item, null);
        }

        BarrageProtos.PBFeed feed = mFeedManager.getUserTimeline().get(position);

        TextView userNickTextView = (TextView)convertView.findViewById(R.id.timeline_item_user_nick);
        userNickTextView.setText(feed.getCreateUser().getNick());

        TextView dateTextView = (TextView)convertView.findViewById(R.id.timeline_item_date);
        dateTextView.setText(DateUtil.dateFormatToString(feed.getDate(), mContext));

        ImageView barrageView = (ImageView) convertView.findViewById(R.id.timeline_item_barage_image);
        barrageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goto reply view
                Ln.d("click image view, go to reply");

                ReplyFeedFragment replyFeedFragment = new ReplyFeedFragment();

                FragmentTransaction transaction = mFragment.getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_home_container, replyFeedFragment);
                transaction.commit();
                mFragment.getChildFragmentManager().executePendingTransactions();

            }
        });

        Picasso.with(mContext)
                .load(feed.getImage())
                .placeholder(R.drawable.tab_home)           // default
                .error(R.drawable.tab_friend)               // error loading
                .into(barrageView);

        return convertView;
    }
}
