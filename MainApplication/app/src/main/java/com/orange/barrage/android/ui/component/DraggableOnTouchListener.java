package com.orange.barrage.android.ui.component;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/25.
 */
public class DraggableOnTouchListener implements View.OnTouchListener {
    private final int STATE_IDEAL = 0;
    private final int STATE_DRAGGING = 1;

    private int mState = STATE_IDEAL;

    private float mLastX,mLastY;
    private int[] location = new int[2];
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        boolean handled = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //TODO: handle multi finger
                mState = STATE_DRAGGING;
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mState = STATE_IDEAL;
                break;

            case MotionEvent.ACTION_MOVE:
                switch (mState){
                    case STATE_DRAGGING:
                        //FIXME: how to handle this widget?
                        ViewParent parent = view.getParent();
                        float parentTop = 0;
                        if(parent instanceof View){
                            parentTop = ((View) parent).getTop();
                        }
                        view.setY(event.getRawY() - parentTop);
                        view.setX(event.getRawX());

                        break;

                    default:
                        break;
                }
                break;
        }
        return handled;
    }
}
