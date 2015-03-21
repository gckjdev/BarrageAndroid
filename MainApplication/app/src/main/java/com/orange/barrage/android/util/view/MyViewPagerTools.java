package com.orange.barrage.android.util.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.MessageCenter;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;


/**
 * Created by Administrator on 2015/3/18.
 */
public class MyViewPagerTools implements ViewPager.OnPageChangeListener {

    private Adapter mAdapter = null;
    private ViewPager mViewPage;
    private ViewGroup mTab_Icon;
    private List<View> mViewList = new ArrayList<>();

    private int mPosition = 0;
    private Context mContext;
    private boolean isInit = true;



    public MyViewPagerTools(Activity activity , int layoutId , int  pointId){
        this( activity,(ViewPager) activity.findViewById(layoutId) ,  (ViewGroup)activity.findViewById(layoutId));
    }

    public  MyViewPagerTools( Context context , ViewPager viewPage , ViewGroup pointIcon){
        setParams(context , viewPage , pointIcon);

    }

    public MyViewPagerTools(){

    }


    public void setParams(Context context , ViewPager viewPage , ViewGroup pointIcon){
        this.mViewPage = viewPage;
        this.mTab_Icon = pointIcon;
        this.mContext = context;
        mViewPage.setOnPageChangeListener(this);
    }


    public void setInitTabpointIcon(){
        isInit = true;
    }

    public void addView(View v){

        if(v == null) return;

        mViewList.add(v);
        addTabIcon(1);

        initAdapter();
    }


    public View getChildlastView(){
        if(mViewList.size() == 0) return null;
        return mViewList.get(mViewList.size() - 1);
    }

    public View getChildPageView(int postion){
        if(postion < mViewList.size()) return mViewList.get(postion);
        return null;
    }

    public int getChildCount(){
        return mViewList.size();
    }

    public void addViews(View[] views){
        if(views == null)  return ;


       for(View v : views){
           mViewList.add(v);
       }
        addTabIcon(views.length);
       initAdapter();
    }


    private void addTabIcon(int count){

        if(mViewList.size() <= 1) return;
        if(mViewList.size() == 2){
            count = 2;
        }

        for(int i = 0 ;  i < count ; i ++)
            mTab_Icon.addView(getImageView());

       setTabIconLocation(mPosition);

    }

    public void clearView(){
        if(mViewPage != null) mViewPage.removeAllViews();
        if(mTab_Icon != null) mTab_Icon.removeAllViews();
    }


    private void setTabIconLocation(int postion){

       setTabPointBg(postion - 1,R.drawable.x_homepage_3_tab_bg_circle_white);
       setTabPointBg(postion + 1,R.drawable.x_homepage_3_tab_bg_circle_white);
       setTabPointBg(postion , R.drawable.x_homepage_3_tab_bg_circle_red);

    }

    private void setTabPointBg(int postion , int drawId){

        if(postion < mViewList.size() && postion >= 0){

            mTab_Icon.getChildAt(postion).setBackgroundResource(drawId);

        }


    }



    private ImageView getImageView(){
        ImageView imageView = new ImageView(mContext);
        int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 7 , imageView.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width , width);
        params.leftMargin = width;
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.x_homepage_3_tab_bg_circle_white);
        return imageView;
    }

    private void initAdapter(){
        if(mAdapter == null){
            mAdapter = new Adapter();
            mViewPage.setAdapter(mAdapter);
        }else
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Ln.e("state多少的是多少:"+position);
        if(isInit){
            isInit = false;
            setTabIconLocation(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        setTabIconLocation(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class Adapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

             return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return mViewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                    Object object) {
            container.removeView(mViewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

            @Override
            public CharSequence getPageTitle(int position) {

                return "";
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));

                return mViewList.get(position);
            }

    }


}
