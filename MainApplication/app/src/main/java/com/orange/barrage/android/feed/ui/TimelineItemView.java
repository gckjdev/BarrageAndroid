package com.orange.barrage.android.feed.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.protocol.message.BarrageProtos;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/7.
 */
@Deprecated
public class TimelineItemView extends LinearLayout {

    private Context mContext;
    private ImageView barrageView;

    public TimelineItemView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public void initView(Context c){
        this.mContext = c;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.view_timeline_list_item, null);
        barrageView = (ImageView) view.findViewById(R.id.timeline_item_barage_image);
//        text_id = (TextView) view.findViewById(R.id.text_id);
//        text_info = (TextView) view.findViewById(R.id.text_info);
//        text_url = (TextView) view.findViewById(R.id.text_url);

        if (view == null || barrageView == null){
            Ln.e("view is null!!!");
            return;
        }

        addView(view);
    }


    public void setModel(BarrageProtos.PBFeed feed) {
        // TODO
        barrageView.setImageResource(R.drawable.tab_home);
    }
}
