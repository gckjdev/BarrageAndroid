package com.orange.barrage.android.util.view;


import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

public class MoveViewParentRelativity extends RelativeLayout implements OnTouchListener  {

	private MoveInfo mMoveInfo;
	
	public MoveViewParentRelativity(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
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
    public void addView(View v , int l ,int t ,LayoutParams params , boolean isMove){
        if(params == null) return;
        params.leftMargin = l;
        params.topMargin = t;
        addView(v, params , isMove);
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
		if(mMoveInfo.v == null)
			return super.onInterceptTouchEvent(ev);
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
            startMove(event.getX() - mMoveInfo.childX , event.getY() - mMoveInfo.childY);
		}else if(event.getAction() == MotionEvent.ACTION_UP ||event.getAction() == MotionEvent.ACTION_CANCEL){
                mMoveInfo.clear();
		}

        return super.onTouchEvent(event);
	}

	
	class Asy extends AsyncTask<Float, Void, RelativeLayout.LayoutParams>{

		@Override
		protected RelativeLayout.LayoutParams doInBackground(Float... params) {
			// TODO Auto-generated method stub
			return getMoveLayoutPrams(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(RelativeLayout.LayoutParams result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}

    private void startMove(float l ,float t){
        RelativeLayout.LayoutParams result = getMoveLayoutPrams(l , t);
        if(result == null || mMoveInfo.v == null) return;
        mMoveInfo.v.setLayoutParams(result);
    }


	private RelativeLayout.LayoutParams getMoveLayoutPrams(float l ,float t){
		if(mMoveInfo == null || !mMoveInfo.isMove()) return null;
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mMoveInfo.v.getLayoutParams();
		params.leftMargin = mMoveInfo.getLeft(l);
		params.topMargin = mMoveInfo.getTop(t);
		
		return params;
	}

    class StayViewInfo{
        public  View v;


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
	
	class MoveInfo{
		
		public View v;


        public float childX = 0;
        public float childY = 0;


        public int getLeft(float x){
            int l = (int)((x + v.getWidth() < MoveViewParentRelativity.this.getWidth()) ?
                    x : MoveViewParentRelativity.this.getWidth() - v.getWidth());


           return l ;
        }

        public int getTop(float y){
            return (int)(y + v.getHeight() < MoveViewParentRelativity.this.getHeight() ? y : MoveViewParentRelativity.this.getHeight() - v.getHeight());
        }

		public boolean isMove(){
			return (v != null);
		}

        public void clear(){
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




}
