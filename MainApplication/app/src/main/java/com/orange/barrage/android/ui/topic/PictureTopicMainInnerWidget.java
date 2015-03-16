package com.orange.barrage.android.ui.topic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.activity.FeedCommentActivity;
import com.orange.barrage.android.feed.activity.FeedReplyActivity;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.ui.topic.player.BarragePlayerSpringImpl;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.view.ViewConstants;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainInnerWidget extends FrameLayout {

    private ImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews;
    private BarragePlayer mBarragePlayer;
    private PictureTopicModel mModel;
    private Context mContext;
    private PictureTopicMode mMode;
    private Activity mActivity;

    public  static String mKey = "1";

    public PictureTopicMainInnerWidget(Context context,AttributeSet set, PictureTopicContainer container) {
        this(context, set);
    }

    public PictureTopicMainInnerWidget(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addView(mSubtitleView, params);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImage = new ImageView(context);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(mImage, imageParams);

        //TODO: can be change to a factory
        mBarragePlayer = new BarragePlayerSpringImpl();
        mMode = PictureTopicMode.LIST;

        float width = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        float height = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);

        LayoutParams widgetLayoutParams = new LayoutParams((int)width, (int)height);
        setLayoutParams(widgetLayoutParams);
    }

    @Inject
    public PictureTopicMainInnerWidget(Context context) {
        this(context, null);
    }

    public void setMode(PictureTopicMode mode) {
        mMode = mode;
    }

    public void setImangeURL(String url) {
        Picasso.with(mContext).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(mImage);
    }

    public void setSubtitle(String title) {
        mSubtitleView.setText(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList) {
        if (mFeedActionViews != null) {
            for (View view : mFeedActionViews) {
                removeView(view);
            }
        }
        mFeedActionViews = Lists.newArrayList();
        for (BarrageProtos.PBFeedAction action : feedActionList) {
            FeedActionWidget actionWidget = new FeedActionWidget(mContext);
            actionWidget.setFeedAction(action);
            mFeedActionViews.add(actionWidget);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //FIXME: use top later instead of margin
//            params.leftMargin = (int) (action.getPosX() * getScaleX());
//            params.topMargin = (int) (action.getPosY() * getScaleY()) ;

            params.leftMargin = (int) action.getPosX();
            params.topMargin = (int) action.getPosY();

            //actionWidget.setTop((int)action.getPosX());
            //actionWidget.setLeft((int)action.getPosY());
            addView(actionWidget, params);
        }

        mBarragePlayer.setBarrageViews(mFeedActionViews);
    }

    public void hideAllBarrageActions() {
        mBarragePlayer.hideAllBarrage();
    }

    public void showAllBarrageActions() {
        mBarragePlayer.showAllBarrage();
    }

    public void play() {
        mBarragePlayer.play();
    }

    public void pause() {
        mBarragePlayer.pause();
    }

    public void resume() {
        mBarragePlayer.resume();
    }

    public void stop() {
        mBarragePlayer.stop();
    }

    public void moveTo(float progress) {
        mBarragePlayer.moveTo(progress);
    }

    public void setModel(PictureTopicModel model , Activity activity) {
        mActivity = activity;
        mModel = model;
        setSubtitle(model.getSubtitleText());
        setImangeURL(model.getImageUrl());
        setBarrageActions(model.getFeedActionLis());
    }


    private void startActivity(int x , int y){

        Info info  = new Info(x , y , mModel.getFeed().toByteArray());
        ActivityIntent.startIntent(mActivity, FeedCommentActivity.class, mKey, info);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            startActivity((int)event.getX() , (int)event.getY());
        }

        return true;
    }

    public static class Info implements Parcelable {

        public int XY[] = new int[2];
        public byte[] b ;

        public Info(int x , int y , byte[] b){
            XY[0] = x;
            XY[1] = y;
            this.b = b;
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel source) {
                byte[] bs = source.createByteArray();
                source.readByteArray(bs);

                int[] XYs = source.createIntArray();
                source.readIntArray(XYs);
                PictureTopicMainInnerWidget.Info info = new PictureTopicMainInnerWidget.Info(XYs[0],XYs[1],bs);
                return info;
//                return null;
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[0];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByteArray(b);
            dest.writeIntArray(XY);
        }
    }

}
