package com.orange.barrage.android.ui.topic.model;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicItem {


    private long mId;
    private String mPictureUrl;
    private float mCurrentCommentProgress;

    public PictureTopicItem() {
        this(0, null, 0f);
    }

    public PictureTopicItem(long id, String pictureUrl) {
        this(id, pictureUrl, 0);
    }

    public PictureTopicItem(long id, String pictureUrl, float currentCommentProgress) {
        mId = id;
        mPictureUrl = pictureUrl;
        mCurrentCommentProgress = currentCommentProgress;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public float getCurrentCommentProgress() {
        return mCurrentCommentProgress;
    }

    public void setCurrentCommentProgress(float currentCommentProgress) {
        mCurrentCommentProgress = currentCommentProgress;
    }

    public long getId() {
        return mId;
    }
}
