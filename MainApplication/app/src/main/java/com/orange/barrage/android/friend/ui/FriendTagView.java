package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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

    private TextView mModleTextView;

    private OnClickTabIconItemListener mOnClickTabIconItemListener;

    private List<FriendParams> mParamsLists = new ArrayList<>();

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

        mModleTextView = initTextView(params , this);

    }


    public boolean addView(final Params params , String tagId){

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

//    private int i = 0;

    private TextView initTextView(Params param , LinearLayout viewGroup){


        TextView textView =new TextView(getContext());
        textView.setText(param.title);
        textView.setTextColor(param.textColor);
        textView.setTextSize(param.textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setLines(1);
        textView.setVisibility(View.INVISIBLE);
        textView.setPaddingRelative((int)(mTextViewHeight / 2) , 0 , (int)(mTextViewHeight / 2) , 0);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) mTextViewHeight);

        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.b_frined_marginright);
        viewGroup.addView(textView, params);
        return textView;
    }

    private boolean AddTextViewToLinear(TextView textView , Params params , String tabId){
        Paint paint = textView.getPaint();
        int width = (int) (mTextViewHeight +paint.measureText(textView.getText().toString()) );
        removeView(textView);
        LinearLayout linearLayout = mLayoutInfo.getLinearLayout(width);
        if(linearLayout == null) return false;
        if(linearLayout.getParent() == null)
            addView(linearLayout , mLayoutInfo.getLayoutParams());


        TextView tv = initTextView(params , linearLayout);
        Ln.e(params.title);
        //绘制背影图片
        new LayoutDrawIconBackground().setTwoSemicircleRectang(tv , params);
        tv.setVisibility(View.VISIBLE);

        tv.setTag(tabId);
        tv.setOnClickListener(this);

        return true;
    }

    public void setOnclickTabItemListener(OnClickTabIconItemListener l){
        mOnClickTabIconItemListener = l;
    }


    @Override
    public void onClick(View v) {

        if(mOnClickTabIconItemListener != null)
            mOnClickTabIconItemListener.onClickItem(v.getTag() != null ? v.toString() : "" ,v , this);
    }

    class ChildLayoutInfo{

        private int mWidth = -1;
        private LinearLayout mLayout;
        private int postion = -1;

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

            int width = getResources().getDimensionPixelSize(R.dimen.b_frined_marginright);

            width = width * n;

            for (int i = 0 ; i < n ; i ++){
                TextView textView = (TextView)linearLayout.getChildAt(i);
                Paint paint = textView.getPaint();
                width += (paint.measureText(textView.getText().toString())+ mTextViewHeight);
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



//    public void removeView(String tagId){
//        FriendParams params = findParams(tagId);
//        mParamsLists.remove(params);
//        setRefresh();
//    }
//
//    private FriendParams findParams(String tagId){
//        for(FriendParams param : mParamsLists){
//            if(param.tagId.equals(tagId)){
//               return param;
//            }
//        }
//    }
//
//    public void AlterTag(String tagId , FriendParams paramNew){
//        FriendParams paramOld = findParams(tagId);
//
//        paramOld.params = paramNew.params;
//
//    }



    //刷新
    public void setRefresh(){
        //移除所以控件
        removeAllViews();
        for(FriendParams param : mParamsLists){
            addView(param.params , param.tagId);
        }

    }



    public static class Params extends LayoutDrawIconBackground.Params {

//        private int color[] = {0X7bc567,0Xecbc25,0Xc792e0,0X6bb5e5,0Xed6e74,0X7997f2,0Xf68a54};

        public String title;
        public int textColor = Color.WHITE;
        public int textSize = 13;

    }

    public interface OnClickTabIconItemListener {

        public void onClickItem(String tagId , View v , FriendTagView friendTagView);

    }

    class FriendParams{

        Params params;
        String tagId ;

    }



}