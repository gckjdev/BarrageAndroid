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
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainWidget extends FrameLayout {

    private PictureTopicMainInnerWidget mInnerWidget;

    private int mWidgetWidth;
    private int mWidgetHeight;

    public PictureTopicMainWidget(Context context, AttributeSet set) {
        super(context, set);
        mInnerWidget = new PictureTopicMainInnerWidget(context);
        addView(mInnerWidget);
    }

    @Inject
    public PictureTopicMainWidget(Context context) {
        this(context, null);
    }

    public void setMode(PictureTopicMode mode) {
        mInnerWidget.setMode(mode);
    }

    public void setImangeURL(String url) {
        mInnerWidget.setImangeURL(url);
    }

    public void setSubtitle(String title) {
        mInnerWidget.setSubtitle(title);
    }

    public void setBarrageActions(List<BarrageProtos.PBFeedAction> feedActionList) {
        mInnerWidget.setBarrageActions(feedActionList);
    }

    public void hideAllBarrageActions() {
        mInnerWidget.hideAllBarrageActions();
    }

    public void showAllBarrageActions() {
        mInnerWidget.showAllBarrageActions();
    }

    public void play() {
        mInnerWidget.play();
    }

    public void pause() {
        mInnerWidget.pause();
    }

    public void resume() {
        mInnerWidget.resume();
    }

    public void stop() {
        mInnerWidget.stop();
    }

    public void moveTo(float progress) {
        mInnerWidget.moveTo(progress);
    }

    public void setModel(PictureTopicModel model , Activity activity) {
        mInnerWidget.setModel(model, activity);
    }


//    private void startActivity(int x , int y){
//        mInnerWidget.startActivity(x,y);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mInnerWidget.onTouchEvent(event);
    }

    public void setInnerScale(float scale) {
        mInnerWidget.setScaleX(scale);
        mInnerWidget.setScaleX(scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec){
        return mWidgetWidth;
    }

    private int measureHeight(int heightMeasureSpec){
        return mWidgetHeight;
    }

    public void setSize(int width, int height){
        mWidgetWidth = width;
        mWidgetHeight = height;

        float expectWidth = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        float actualWidth = width;

        float expectHeight = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);
        float actualHeight = height;

        float scaleX = actualWidth/expectWidth;
        float scaleY = actualHeight/expectHeight;

        Ln.d("scaleX: %.2f , scaleY: %.2f", scaleX, scaleY);
        mInnerWidget.setScaleX(scaleX);
        mInnerWidget.setScaleY(scaleY);

    }
}
