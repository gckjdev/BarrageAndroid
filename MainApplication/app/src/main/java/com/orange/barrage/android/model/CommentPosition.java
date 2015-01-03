package com.orange.barrage.android.model;

/**
 * Created by Rollin on 2014/12/1.
 */
public class CommentPosition {

    private int mXPos;
    private int mYPos;

    private String mComment;
    private User mUser;

    public int getmXPos() {
        return mXPos;
    }

    public void setmXPos(int mXPos) {
        this.mXPos = mXPos;
    }

    public int getmYPos() {
        return mYPos;
    }

    public void setmYPos(int mYPos) {
        this.mYPos = mYPos;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
