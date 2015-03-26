package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.MyViewPagerTools;
import com.orange.protocol.message.UserProtos;

import java.util.List;



/**
 * Created by Administrator on 2015/3/24.
 */
public class FriendTagList {




    protected static int mParenctBgColor = Color.WHITE;

    private int mIsHollow = FriendTagView.Params.PARAMS_SOLID;


    ViewPager mViewPager;
    FriendTagView mFriendTabView;
    LinearLayout mMainTagLinear;
    LinearLayout mPonitLayout;


    private TagManager mTagManager;

    private Activity mActivity;

    private FriendTagView.OnClickTabIconItemListener mOnClickTabIconItemListener;
    MyViewPagerTools mViewPagerTools;

    //每一行标签的高度
    private int mHeight_item = 42;
    //标签有多少行
    private int mItemCount = 3;



    public FriendTagList(Activity activity , TagManager tagManager , FriendTagView.OnClickTabIconItemListener l){
        mActivity = activity;
        mTagManager = tagManager;
        mOnClickTabIconItemListener = l;

        mParenctBgColor = activity.getResources().getColor(R.color.b_color_home_page_tab_bg);

        initView();
        mViewPagerTools = new MyViewPagerTools(activity , mViewPager , mPonitLayout);
    }

    public void setViewPagerInvisible(){
        if(mViewPager != null) mViewPager.setVisibility(View.INVISIBLE);
    }


    private void initView(){
        mViewPager = (ViewPager) mActivity.findViewById(R.id.ViewPager);
        mMainTagLinear = (LinearLayout) mActivity.findViewById(R.id.viewpaeLinear);
        mPonitLayout = (LinearLayout) mActivity.findViewById(R.id.tab_icon);
    }

    public void loadLocalTagList(){
        loadLocalTagList(FriendTagView.Params.PARAMS_SOLID);
    }


    /**
     *
     * @param isHollow 是否为空心
     */
    public void loadLocalTagList(int isHollow) {

        mIsHollow = isHollow;

        UserProtos.PBUserTagList tagList = mTagManager.allTags();
        if (tagList == null){
            return;
        }

        List<UserProtos.PBUserTag> list = tagList.getTagsList();
        if (list == null || list.size() == 0){
            mMainTagLinear.setVisibility(View.GONE);
            return;
        }

        mMainTagLinear.setVisibility(View.VISIBLE);
        for(UserProtos.PBUserTag userTag : list){
            addUserTagToTag(userTag);
        }

        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setViewPagerHeight();
                mViewPager.setVisibility(View.VISIBLE);
            }
        }.sendEmptyMessage(0);

    }
    private void addUserTagToTag(UserProtos.PBUserTag userTag ){
        if(userTag == null) return;

        initPager(getParams(userTag), userTag.getTid());
    }


    private void initPager(FriendTagView.Params params , String id){
        getFriendTabView();

        if(!mFriendTabView.addView(params , id)){
            mFriendTabView = null;
            initPager(params , id);
        }
    }

    private FriendTagView getFriendTabView(){
        if(mFriendTabView != null) return mFriendTabView;

        mFriendTabView = new FriendTagView(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mFriendTabView.setLayoutParams(params);
        mViewPagerTools.addView(mFriendTabView);

        mFriendTabView.setOnclickTabItemListener(mOnClickTabIconItemListener);

        return mFriendTabView;
    }


    public void refreshTag(){
        clear();
        loadLocalTagList(mIsHollow);
    }



    /**
     * 注意：标签如何来设置空心边框
     * 这里面主要是把params.baColor的背景颜色设置和父类的View的背景颜色一样，
     * 就会达到空心边框背景的效果
     * @param userTag
     * @return
     */
    private FriendTagView.Params getParams(UserProtos.PBUserTag userTag){
        FriendTagView.Params params = new FriendTagView.Params();



        params.state = mIsHollow;
        params.color = 0XFF7bc567;
        params.title = userTag.getName()+" ( "+userTag.getUsersList().size()+" )";
        if(mIsHollow == FriendTagView.Params.PARAMS_HOLLOW){
            params.textColor = params.color;
        }
        return params;
    }



    public void clear(){
        mFriendTabView = null;
        mViewPagerTools.clearView();
        mViewPagerTools = new MyViewPagerTools(mActivity , mViewPager , mPonitLayout);
    }

    private void setViewPagerHeight(){

        ViewGroup.LayoutParams paramsViewPager = mViewPager.getLayoutParams();
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();

        if(mViewPagerTools.getChildCount() == 1) {
            FriendTagView friendTagView = (FriendTagView) mViewPagerTools.getChildPageView(0);
            paramsViewPager.height = (int)
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeight_item, displayMetrics) * friendTagView.getChildCount();

        }else if (mViewPager.getChildCount() > 1){
            paramsViewPager.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeight_item * mItemCount , displayMetrics);
        }
        ((ViewGroup)mViewPager.getParent()).setLayoutParams(paramsViewPager);
    }



    public void addNewTag(){
        mMainTagLinear.setVisibility(View.VISIBLE);
        UserProtos.PBUserTagList tagList = mTagManager.allTags();
        List<UserProtos.PBUserTag> list = tagList.getTagsList();
        if (list.size() != 0) {

            addUserTagToTag(list.get(list.size() - 1));
        }
        if(mViewPagerTools != null)
            mViewPagerTools.setCurrentLastItem();

    }

    public void refresh(){
        int postion = mViewPagerTools.getViewPostion();
        refreshTag();
        mViewPagerTools.setCurrentItem(postion);
    }

}
