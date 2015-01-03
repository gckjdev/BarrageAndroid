package com.orange.barrage.android.data;

import com.orange.barrage.android.model.PictureTopicItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rollin on 2015/1/1.
 */
public class PictureTopicData {

    private static PictureTopicData sInstance = new PictureTopicData();

    public static PictureTopicData getsInstance(){
        return sInstance;
    }

    public List<PictureTopicItem> getTopics() {
        return mTopics;
    }

    public void setTopics(List<PictureTopicItem> topic) {
        this.mTopics =  topic;
    }

    List<PictureTopicItem> mTopics = new ArrayList<>();
}
