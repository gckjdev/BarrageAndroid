package com.orange.barrage.android.event;

/**
 * Created by Rollin on 2015/3/16.
 */
public class StartActivityFeedCommentEvent {

    private byte[] mByteArray;
    private int[] mPos;

    public int[] getPos() {
        return mPos;
    }

    public void setPos(int[] pos) {
        mPos = pos;
    }

    public byte[] getByteArray() {
        return mByteArray;
    }

    public void setByteArray(byte[] byteArray) {
        mByteArray = byteArray;
    }

}
