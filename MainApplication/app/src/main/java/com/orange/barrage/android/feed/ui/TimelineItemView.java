package com.orange.barrage.android.feed.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/7.
 */
public class TimelineItemView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private ImageView barrageView;

    private ImageButton mShareButton;

    private ImageButton mPlayerButton;


    public TimelineItemView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public void initView(Context c){
        this.mContext = c;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.view_timeline_list_item, this);
        barrageView = (ImageView) view.findViewById(R.id.timeline_item_barage_image);
        mShareButton = (ImageButton)view.findViewById(R.id.shareButton);
        mPlayerButton = (ImageButton)view.findViewById(R.id.playerButton);

        mPlayerButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);


    }


    public void setModel(PictureTopicModel model) {
        // TODO
        barrageView.setImageResource(R.drawable.tab_home);

        ((TextView)findViewById(R.id.titleText)).setText(model.getSubtitleText()+"djsfjsdfs");

        Picasso.with(mContext).load(model.getImageUrl()).
                placeholder(R.drawable.tab_home).
                error(R.drawable.tab_friend).into(barrageView);

        // TODO show date
        int createDate = model.getFeed().getDate();

        
    }




    /**
     * 分享东西
     * @param v
     */
    public void onClickShare(View v){


    }

    /**
     * 播放
     *
     */
    public void onClickPlayer(View v){



    }

    @Override
    public void onClick(View v) {
        if(mShareButton == v)  onClickShare(v);
        else if (mPlayerButton == v) onClickPlayer(this);

    }
}
