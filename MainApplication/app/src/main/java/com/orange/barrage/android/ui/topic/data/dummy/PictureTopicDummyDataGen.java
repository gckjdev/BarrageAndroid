package com.orange.barrage.android.ui.topic.data.dummy;

import com.orange.barrage.android.ui.topic.data.PictureTopicData;
import com.orange.barrage.android.ui.topic.model.PictureTopicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rollin on 2015/1/1.
 */
public class PictureTopicDummyDataGen {

    public static String[] getUrls(){
        return new String[]
                {
                       "http://b.zol-img.com.cn/soft/6/513/cedxaEtiTOOx.jpg",
                        "http://api.apkbus.com/assets/images/bg_logo.png",
                        "http://www.51ppt.com.cn/Article/Uploadphotos_0708/200604/20064147381446.png",
                };
    }

    public static void init(){
        List<PictureTopicItem> items = new ArrayList<>();

        String[] urls = getUrls();
        long id =1;

        for(int i=0;i<2;i++) {
            for (String url : urls) {
                PictureTopicItem item = new PictureTopicItem(id++, url);
                items.add(item);
            }
        }
        PictureTopicData.getsInstance().setTopics(items);
    }

    public static void loadMore(){
        List<PictureTopicItem> items = PictureTopicData.getsInstance().getTopics();

        String[] urls = getUrls();
        long id =1;

        for(int i=0;i<2;i++) {
            for (String url : urls) {
                PictureTopicItem item = new PictureTopicItem(id++, url);
                items.add(item);
            }
        }
    }
}
