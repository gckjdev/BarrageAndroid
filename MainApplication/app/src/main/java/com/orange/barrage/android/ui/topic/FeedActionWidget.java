package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

/**
 * Created by Rollin on 2015/1/12.
 */
public class  FeedActionWidget extends LinearLayout {

    private ImageView mAvatarView;
    private TextView mContent;

    private BarrageProtos.PBFeedAction mFeedAction;
    private Context mContext;

    public FeedActionWidget(Context context) {
        this(context,false);
    }

    public FeedActionWidget(Context context, boolean editable) {
        super(context);
        mContext = context;
        setOrientation(HORIZONTAL);

        mAvatarView = new ImageView(context);
        int iconSize = (int)getResources().getDimension(R.dimen.b_icon_size_44);
        LayoutParams avatarParams = new LayoutParams(iconSize, iconSize);
        addView(mAvatarView, avatarParams);

        if(editable){
            mContent = new EditText(context);
        }else{
            mContent = new TextView(context);
        }
        LayoutParams contentParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mContent, contentParams);
    }

    public void setFeedAction(BarrageProtos.PBFeedAction feedAction){
        mFeedAction = feedAction;
        setAvadar(mFeedAction.getAvatar());
        setContent(mFeedAction.getText());
    }

    public BarrageProtos.PBFeedAction getFeedAction() {
        return mFeedAction;
    }

    public void setAvadar(String url){
        if (StringUtil.isEmpty(url)){
//            Picasso.with(mContext).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(mAvatarView);
        }
        else{
            Picasso.with(mContext).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(mAvatarView);
        }
    }

    public void setContent(String content){
        mContent.setText(content);
    }

    public String getContent(){
        return mContent.getText() == null?"":mContent.getText().toString();
    }
}
