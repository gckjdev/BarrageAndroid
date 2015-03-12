package com.orange.barrage.android.feed.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制表格
 * @author youjiannuo
 */
public class TableView extends View{

    /**
     * 列数
     */
	private int mCol = 10;
	
	private int mColor = Color.BLACK;
	
	
	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setCol(int col){
		this.mCol = col;
		invalidate();
	}
	
	public void setColor(int color){
		mColor = color;
		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(mColor);
		paint.setStrokeWidth((float)0.5);
		paint.setAntiAlias(true);
		int X = getWidth() / mCol;
		int line = getHeight() / X;		
		paint.setColor(Color.BLACK);
		float []pts = new float[4 * (mCol - 1)];
		for(int i = 0 ;i < mCol - 1  ; i ++){
			pts[i * 4 ] = X * (i + 1);
			pts[i * 4 + 1] = 0;
			pts[i * 4 + 2] = X * (i + 1) ;
			pts[i * 4 + 3] = getHeight();
		}
	    canvas.drawLines(pts, paint);        
	    pts = new float[4 * (line + 1)];
	    for(int i = 0 ;i < line + 1  ; i ++){
	    	pts[i * 4 ] = 0;
	    	pts[i * 4 + 1] = X * (i +1);
	    	pts[i * 4 + 2] =  getWidth();
	    	pts[i * 4 + 3] = X * (i + 1);
	    }
	    canvas.drawLines(pts, paint);        
	}
	
	
	
	
	
}
