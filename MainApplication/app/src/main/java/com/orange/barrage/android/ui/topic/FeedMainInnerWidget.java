package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.StartActivityFeedCommentEvent;
import com.orange.barrage.android.feed.ui.view.BarrageGridView;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.ui.topic.player.BarragePlayerSpringImpl;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;
import com.orange.protocol.message.BarrageProtos;
import com.squareup.picasso.Picasso;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Rollin on 2014/11/30.
 */
public class FeedMainInnerWidget extends FrameLayout {

    private ImageView mImage;
    private TextView mSubtitleView;
    private List<FeedActionWidget> mFeedActionViews = new ArrayList<>();
    private BarragePlayer mBarragePlayer;
    private PictureTopicModel mModel;
    private Context mContext;
    private FeedWidgetMode mMode;

    private BarrageGridView mGridView;

    public MoveViewParentRelativity getMoveView() {
        return mMoveView;
    }

    public BarrageGridView getGridView() {
        return mGridView;
    }

    private MoveViewParentRelativity mMoveView;

    //FIXME: there's a typo here, and why need another model, reuse mMode:List and comment
    private Modle mModle = new Modle();

    public FeedMainInnerWidget(Context context, AttributeSet set, PictureTopicContainer container) {
        this(context, set);
    }

    public FeedMainInnerWidget(Context context, AttributeSet set) {
        super(context, set);

        initViews(context);
    }

    protected void initViews(Context context) {
        this.mContext = context;
        mSubtitleView = new TextView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        this.addView(mSubtitleView, params);

        LayoutParams matchParentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImage = new ImageView(context);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(mImage, matchParentParams);

        float width = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        float height = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);

        //grid view
        mGridView = new BarrageGridView(mContext);
        mGridView.setVisibility(View.GONE);
        this.addView(mGridView, matchParentParams);

        //move view
        mMoveView = new MoveViewParentRelativity(mContext);
        this.addView(mMoveView, matchParentParams);

        LayoutParams widgetLayoutParams = new LayoutParams((int) width, (int) height);
        setLayoutParams(widgetLayoutParams);


        mMode = FeedWidgetMode.LIST;
        //
        BarragePlayerSpringImpl barragePlayerSpring = new BarragePlayerSpringImpl();
        barragePlayerSpring.setParentHeight((int) height);
        mBarragePlayer = barragePlayerSpring;
    }

    @Inject
    public FeedMainInnerWidget(Context context) {
        this(context, null);
    }

    public void setMode(FeedWidgetMode mode) {

        if (mFeedActionViews != null) {
            for (FeedActionWidget actionWidget : mFeedActionViews) {
                //from
                switch (mMode) {
                    case COMMENT:
                    case LIST:
                        switch (mode) {
                            case SHARE:
                                //list to share
                                removeView(actionWidget);
                                BarrageProtos.PBFeedAction action = actionWidget.getFeedAction();

                                int left = (int) action.getPosX();
                                int top = (int) action.getPosY();
                                mMoveView.addView(actionWidget, left, top);
                                break;
                            default:
                                break;
                        }
                        break;

                    case SHARE:
                        switch (mode) {
                            case COMMENT:
                            case LIST:
                                //share to list
                                mMoveView.removeView(actionWidget);
                                BarrageProtos.PBFeedAction action = actionWidget.getFeedAction();

                                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = (int) action.getPosX();
                                params.topMargin = (int) action.getPosY();
                                addView(actionWidget, params);
                                break;
                            default:
                                break;
                        }
                        break;
                }
            }
        }
        mMode = mode;
    }

    public void setImageURL(String url) {
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
            actionWidget.setType(FeedActionWidget.COMMENETS_TEXTVIEW);
            actionWidget.setFeedAction(action);
            mFeedActionViews.add(actionWidget);

            switch (mMode) {
                case COMMENT:
                case LIST: {
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int) action.getPosX();
                    params.topMargin = (int) action.getPosY();
                    addView(actionWidget, params);
                    break;
                }
                case SHARE: {
                    int left = (int) action.getPosX();
                    int top = (int) action.getPosY();
                    mMoveView.addView(actionWidget, left, top);
                    break;
                }
            }
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

    public void moveToEnd() {
        mBarragePlayer.moveToEnd();
    }

    public void setModel(PictureTopicModel model) {
        mModel = model;
        setSubtitle(model.getSubtitleText());
        setImageURL(model.getImageUrl());
        setBarrageActions(model.getFeedActionLis());
    }

    public PictureTopicModel getModel() {
        return mModel;
    }

    private void startActivityFeedCommentEvent(int x, int y) {
        StartActivityFeedCommentEvent event = new StartActivityFeedCommentEvent();
        event.setByteArray(mModel.getFeed().toByteArray());
        event.setPos(new int[]{x, y});
        EventBus.getDefault().post(event);
    }

    //FIXME: why not use click action here.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mModle.clear();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mModle.is = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!mModle.is) {
                startActivityFeedCommentEvent((int) event.getX(), (int) event.getY());
                mModle.clear();
            }
        }

        return true;
    }

    class Modle {
        public boolean is = false;

        public void clear() {
            is = false;
        }

    }

}
