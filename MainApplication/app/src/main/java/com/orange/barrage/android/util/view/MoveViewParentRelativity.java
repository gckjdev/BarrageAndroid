package com.orange.barrage.android.util.view;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import com.orange.barrage.android.R;
import com.squareup.picasso.Picasso;

public class MoveViewParentRelativity extends RelativeLayout implements OnTouchListener  {

	private MoveInfo mMoveInfo;

    public MoveViewParentRelativity(Context context) {
        this(context, null);
    }

	public MoveViewParentRelativity(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMoveInfo = new MoveInfo();
	}

    public void addView(View v , LayoutParams params){
        addView(v , params ,true);
    }

    public void addView(View v , int l ,int t,LayoutParams params ){
        addView(v , l , t , params , true);
    }

    public void addView(View v , int l ,int t){
        addView(v , l ,t , true);
    }
	
	public void addView(View v , int l ,int t , int w,int h){
		addView(v , l , t , w, h , true);
	}


    /**
     *
     * @param v
     * @param l
     * @param t
     * @param params
     * @param isMove 是否可以被移动
     */
    public void addView(final View v , final int l ,final int t ,final LayoutParams params , boolean isMove){
        if(params == null) return;
        addView(v, params , isMove);

        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MoveInfo moveInfo = new MoveInfo();
                moveInfo.v = v;
                params.leftMargin = moveInfo.getLeft(l);
                params.topMargin = moveInfo.getTop(t);
                v.setLayoutParams(params);
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }


    public void addView(View v , LayoutParams params , boolean isMove){
        setOnTouchListener(v , v , isMove);
        super.addView(v , params);
    }

    /**
     *
     * @param v
     * @param l
     * @param t
     * @param w
     * @param h
     * @param isMove 是否可以被移动
     */
    public void addView(View v , int l,int t , int w ,int h , boolean isMove){
        LayoutParams params = new LayoutParams(w , h);
        addView(v , l , t ,params , isMove);
    }

    public void addView(View v , int l ,int t , boolean isMove){
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
        addView(v , l ,t , params , isMove);
    }




	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

//
        if(ev.getAction() == MotionEvent.ACTION_UP ){
            mMoveInfo.clear();
        }
		if(mMoveInfo.v == null) {
            return super.onInterceptTouchEvent(ev);
        }
		else return true;
	}


    private void setOnTouchListener(View parent , View parents , boolean is ){
        if(parent == null) return;
        if(parent instanceof LinearLayout){
            for(int i = 0 ; i <((LinearLayout)parent).getChildCount() ; i ++){
                setOnTouchListener(((LinearLayout)parent).getChildAt(i),parents , is);
            }
        }else if(parent instanceof RelativeLayout){
            for(int i = 0 ; i <((RelativeLayout)parent).getChildCount() ; i ++){
                setOnTouchListener(((RelativeLayout)parent).getChildAt(i),parents , is);
            }
        }else if(parent instanceof FrameLayout){
            for(int i = 0 ; i <((FrameLayout)parent).getChildCount() ; i ++){
                setOnTouchListener(((FrameLayout)parent).getChildAt(i),parents , is);
            }
        }else if(! (parent instanceof EditText)){

            StayViewInfo stayViewInfo = new StayViewInfo(parents , is);

            parent.setOnTouchListener(this);
            parent.setTag(stayViewInfo);
        }
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(mMoveInfo == null) return super.onTouchEvent(event);

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            final int x = (int)event.getX();
            final int y = (int)event.getY();
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    startMove(x- mMoveInfo.childX , y - mMoveInfo.childY);
                }
            }.sendEmptyMessage(0);
		}else if(event.getAction() == MotionEvent.ACTION_UP ||event.getAction() == MotionEvent.ACTION_CANCEL){

            mMoveInfo.clear();
		}

        return super.onTouchEvent(event);
	}





    private void startMove(float l ,float t){
        if( mMoveInfo.v == null && !mMoveInfo.isMove()) return;
        int X = mMoveInfo.getLeft(l);
        int Y = mMoveInfo.getTop(t);
        mMoveInfo.v.layout(X , Y  , X + mMoveInfo.v.getWidth() , Y + mMoveInfo.v.getHeight());
    }


    class StayViewInfo{
        public  View v;

        public int action;

        private boolean isMove;

        public StayViewInfo(View v , boolean isMove){
            this.v = v;
            setIsMove(isMove);
        }

        public void setIsMove(boolean is){
            this.isMove = is;
        }

        public boolean getIsMove(){
            return isMove;
        }
    }

    public int getMoveingViewX(){
        if(mMoveInfo == null) return 0;
        return mMoveInfo.MovewL;

    }

    public int getMoveingViewY(){
        if(mMoveInfo == null) return 0;
        return  mMoveInfo.MovewT;
    }



	class MoveInfo{
		
		public View v;

        public int MovewL;
        public int MovewT;
        public float childX = 0;
        public float childY = 0;

        public int getLeft(float x){
           MovewL = getbestParams((int)x , v .getWidth() , MoveViewParentRelativity.this.getWidth());
           return MovewL;
        }

        public int getTop(float y){
            MovewT = getbestParams((int)y , v .getHeight() , MoveViewParentRelativity.this.getHeight());
            return MovewT;
        }

        private int getbestParams(int xy , int child ,  int parent){
            return ((xy +child) < parent)? (xy < 0 ? 0 : xy) : parent - child;
        }

		public boolean isMove(){
			return (v != null);
		}

        public void clear(){
            if(v != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.leftMargin = MovewL;
                params.topMargin = MovewT;
                v.setLayoutParams(params);
            }
            childX = 0;
            childY = 0;
            v = null;
        }
		
	}
	public class LayoutParams extends RelativeLayout.LayoutParams{

		public LayoutParams(int w, int h) {
			super(w, h);
			// TODO Auto-generated constructor stub
		}
	}

    private int i = 0;
	@Override
     public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub


        if(event.getAction() == MotionEvent.ACTION_DOWN) {

            StayViewInfo stayViewInfo = (StayViewInfo)v.getTag();

            if(stayViewInfo.getIsMove()) {
                mMoveInfo.v = stayViewInfo.v;
                mMoveInfo.childY = event.getY();
                mMoveInfo.childX = event.getX();
            }
        }
        return true;
    }

//    public void setImageUrl(String url){
//        ImageView veiw = (ImageView) findViewById(R.id.CommentImageView);
//        if(veiw == null) return;
//        Picasso.with(getContext()).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(veiw);
//    }

}
