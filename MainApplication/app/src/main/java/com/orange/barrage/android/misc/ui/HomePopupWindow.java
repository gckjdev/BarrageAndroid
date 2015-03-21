package com.orange.barrage.android.misc.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;
import com.orange.barrage.android.util.activity.FloatWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pipi on 15/3/13.
 */
public class HomePopupWindow {

    private FloatWindow mFloatWindow;
    private Context mContext;
    private View mPopWindowItemLine;
    private View mPopWindowItemView;
    private RelativeLayout mRelativeLayout;
    private View mParent;
    private LayoutDrawIconBackground layoutWhileTriangleIcon = new LayoutDrawIconBackground();
    private LayoutDrawIconBackground.Params params = new LayoutDrawIconBackground.Params();

    private String mTag = "";

    public HomePopupWindow( Context context){
        this.mContext = context;
    }


//    public void setBackground(){
//        mRelativeLayout.setBackground(ImageUtil.getWhitTriangleRadioRoundFrectBgDrawle(null , mParent , mRelativeLayout));
//    }


    public void show(View parent , View.OnClickListener l , String tag) {
        boolean is = false;
        if (mFloatWindow == null) {
            is = true;
            mFloatWindow = new FloatWindow(R.layout.view_homepage_pop_view, mContext, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mParent = parent;
        mFloatWindow.show(parent);

        LinearLayout childs = (LinearLayout) (mFloatWindow.getContextView().findViewById(R.id.popLinearLayout));
        mRelativeLayout = (RelativeLayout) mFloatWindow.getContextView().findViewById(R.id.relayout);



          if(!mTag.equals(tag)) {
              mTag = tag;
              mRelativeLayout.setBackground(null);
              layoutWhileTriangleIcon.setParams(params).setWhitTriangleRadioRoundFrectListenerBg(parent, mRelativeLayout);
          }



        if (is && childs != null) {
            params.bgColor = Color.BLACK;
            params.marginRight = 10;
            layoutWhileTriangleIcon.setParams(params).setWhitTriangleRadioRoundFrectListenerBg(parent, mRelativeLayout);
            mTag = tag;
            View v1 = childs.getChildAt(0);
            View v2 = childs.getChildAt(2);
            View v3 = childs.getChildAt(4);
            View v4 = childs.getChildAt(6);


            v1.setTag(1);
            v3.setTag(3);
            v2.setTag(2);
            v4.setTag(4);

            v1.setOnClickListener(l);
            v2.setOnClickListener(l);
            v3.setOnClickListener(l);
            v4.setOnClickListener(l);
            mPopWindowItemLine = childs.getChildAt(1);
            mPopWindowItemView = v2;
        }

        if(tag.equals(HomeActivity.TAB_1_TAG)){
            ItemClose();
        }else if(tag.equals(HomeActivity.TAB_3_TAG)){
           ItemShow();
        }


    }




    private boolean ItemViewisNull(){
        return mPopWindowItemLine == null || mPopWindowItemView == null;
    }

    public void close(){
        if(mFloatWindow != null) mFloatWindow.close();
    }

    public void ItemShow(){
        if(ItemViewisNull()) return;
        mPopWindowItemView.setVisibility(View.VISIBLE);
        mPopWindowItemLine.setVisibility(View.VISIBLE);
    }
    public void ItemClose(){
       if(ItemViewisNull()) return ;
        mPopWindowItemView.setVisibility(View.GONE);
        mPopWindowItemLine.setVisibility(View.GONE);
    }

}


