package com.orange.barrage.android.ui.component;

import android.view.MotionEvent;
import android.view.View;

import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/25.
 */
public class DraggableOnTouchListener implements View.OnTouchListener {
    private final int STATE_IDEAL = 0;
    private final int STATE_DRAGGING = 1;

    private int mState = STATE_IDEAL;

    private float mLastX,mLastY;

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
                        view.setY(event.getRawY() - view.getTop());
                        view.setX(event.getRawX() - view.getLeft());
                        Ln.d("view x : %.2f, y: %.2f", view.getX(), view.getY());
                        break;

                    default:
                        break;
                }
                break;
        }
        return handled;
    }
}
