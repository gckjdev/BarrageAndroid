package com.orange.barrage.android.feed.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleColorView  extends ImageView{

	private int mColor = Color.BLACK;
	private int mRadius = 28;
	
	public CircleColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setColor(int color){
		mColor = color;
		invalidate();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	
		Paint paint  = new Paint();
		
		paint.setAntiAlias(true);
		paint.setColor(mColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, paint);
	}
	
	
}
