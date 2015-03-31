package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.misc.SystemUtil;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/17.
 */
public class FriendTagView extends LinearLayout implements View.OnClickListener  {


    public ChildLayoutInfo mLayoutInfo;

    public float mTextViewHeight =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 30 , getResources().getDisplayMetrics());

    private FriendTagItemView mModleTextView;

    private OnClickTabIconItemListener mOnClickTabIconItemListener;

    private List<FriendParams> mParamsLists = new ArrayList<>();


    private int b_friend_marginLeft = 14;

    public FriendTagView(Context context) {
        super(context);
        initView();
    }

    public FriendTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


   private void initView(){
       setOrientation(VERTICAL);

   }


    private void initLayoutInfo(){
        if(mLayoutInfo == null){
            mLayoutInfo = new ChildLayoutInfo();
            mLayoutInfo.startView();
        }
    }


    private void initModleTextView(Params params){
        if(mModleTextView != null){
            mModleTextView.setText(params.title);
        }

        mModleTextView = FriendTagItemView.CreateFriendItemView(getContext() , params);
        addView(mModleTextView);
    }


    public boolean addView(final Params params , String tagId ) {

        if(params == null){
            throw new NullPointerException("Params is not null");
        }
        if(params.getHaveUse()){
            throw  new NullPointerException("params only use once");
        }

        initLayoutInfo();
        initModleTextView(params);
        boolean is = AddTextViewToLinear(mModleTextView, params ,  tagId);
        if(is){
            FriendParams friendParams = new FriendParams();
            friendParams.params = params;
            friendParams.tagId = tagId;
            mParamsLists.add(friendParams);
        }

        return is;

    }



    private boolean AddTextViewToLinear(FriendTagItemView friendTagItemView , Params params , String tabId ){

        int width = (int) (mTextViewHeight +friendTagItemView.getTextViewTextwidth() );
        removeView(friendTagItemView);

        LinearLayout linearLayout = mLayoutInfo.getLinearLayout(width);
        if(linearLayout == null) return false;
        if(linearLayout.getParent() == null)
            addView(linearLayout , mLayoutInfo.getLayoutParams());

        FriendTagItemView ft = FriendTagItemView.CreateFriendItemView(getContext() , params);

        TextView tv = ft.getTextView();
        tv.setTag(tabId);
        tv.setOnClickListener(this);
        linearLayout.addView(ft);
        params.setHaveUse();
        return true;
    }

    public void setOnclickTabItemListener(OnClickTabIconItemListener l){
        mOnClickTabIconItemListener = l;
    }


    @Override
    public void onClick(View v) {

        if (mOnClickTabIconItemListener != null) {
            FriendTagItemView friendTagItemView =
                    v.getParent() == null ? null :
                            v.getParent().getParent() == null ? null :
                                    v.getParent().getParent() instanceof FriendTagItemView ? (FriendTagItemView) (v.getParent().getParent()) : null;
            mOnClickTabIconItemListener.onClickItem(v.getTag() != null ? v.getTag().toString() : "", friendTagItemView, this);
        }
    }

    class ChildLayoutInfo{

        private int mWidth = -1;
        private LinearLayout mLayout;
        private int postion = -1;

        //获取添加的布局
        public LinearLayout getLinearLayout(int width){
//            if(postion >= layout.length)  return layout[layout.length - 1];

           if(isNext(mLayout , width)){
               mLayout = initView();
           }

            return mLayout;
        }

        private boolean isNext(LinearLayout linearLayout , int w){

            int n = linearLayout.getChildCount();
            if( n == 0) return false;

            int width = (int) getDp(b_friend_marginLeft);

            width = width * (n);

            for (int i = 0 ; i < n ; i ++){
//                TextView textView = (TextView)linearLayout.getChildAt(i);
//                Paint paint = textView.getPaint();
//                width += (paint.measureText(textView.getText().toString())+ mTextViewHeight);
                width += ((FriendTagItemView)linearLayout.getChildAt(i)).getTextViewTextwidth() +mTextViewHeight;
            }
            Ln.e("count:"+linearLayout.getChildCount());
            Ln.e("postion:"+postion);
            Ln.e("width:"+width);
            Ln.e("TextView:"+w);
            Ln.e("mWidth:"+mWidth);
            Ln.e(""+(w > mWidth - width));
            return w > mWidth - width;
        }


        public void startView(){
            initView();

            FriendTagView.this.addView(mLayout , getLayoutParams());

            mWidth = (int) (SystemUtil.getPhoneScreenWH(getContext())[0] - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 18 ,  getResources().getDisplayMetrics()) * 2);

        }


        private LinearLayout initView(){
            postion ++;
            if(postion >= 3) return null;

            mLayout = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.view_friends_tab , null);
            return  mLayout;
        }


        public LayoutParams getLayoutParams(){

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 30 , displayMetrics));

            params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 18 , displayMetrics);
            params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 18 , displayMetrics);
            params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 12 , displayMetrics);

            return params;
        }

    }


    private float getDp(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , dp , getResources().getDisplayMetrics());
    }




    public static class Params extends LayoutDrawIconBackground.Params {

//        private int color[] = {0X7bc567,0Xecbc25,0Xc792e0,0X6bb5e5,0Xed6e74,0X7997f2,0Xf68a54};

        public static final int PARAMS_HOLLOW = 1;

        public static final  int PARAMS_SOLID = 2;


        public String title;

        public int state = -1;//目前是什么样的状态 空心或者实现


        public int getBorderColor(){
           return borderColor == Integer.MAX_VALUE ? color : borderColor;
        }

        public int getTextColor(){
            if(state == PARAMS_HOLLOW){
                //设置为空心
                textColor = color;
            }else{
                textColor = Color.WHITE;
            }
            return textColor;
        }

        public int getBgColor() {

            if(state == PARAMS_HOLLOW){
                //设置为空心
                bgColor = FriendTagList.mParenctBgColor;
            }else{
                //实心
                bgColor = color;
            }

            return bgColor;
        }
    }

    public interface OnClickTabIconItemListener {

        public void onClickItem(String tagId , FriendTagItemView friendTagItemView , FriendTagView friendTagView);

    }

    class FriendParams{

        Params params;
        String tagId ;

    }



}
