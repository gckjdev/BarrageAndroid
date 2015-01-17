package com.orange.barrage.android.ui.topic.data.dummy;

import com.orange.barrage.android.ui.topic.data.PictureTopicData;
import com.orange.barrage.android.ui.topic.model.PictureTopicItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Rollin on 2015/1/1.
 */
public class PictureTopicDummyDataGen {

    private static Random sRandom = new Random();
    private static String[] sAvatarData = new String[]{
            "http://b.hiphotos.baidu.com/image/pic/item/cb8065380cd79123a63d9773ae345982b3b780d3.jpg"
            ,"http://g.hiphotos.baidu.com/image/pic/item/d009b3de9c82d1581a5fa3b7830a19d8bd3e42d3.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/1b4c510fd9f9d72a37ded01bd72a2834349bbb7a.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/a8014c086e061d9534e9dd3e78f40ad162d9ca0d.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=c6ec3adc16ce36d3bd0484300af33a24/ae51f3deb48f8c5409b7032939292df5e0fe7f54.jpg"
    };

    private static String[] sImange = new String[]
            {
                    "http://t11.baidu.com/it/u=3293027587,4105458926,298,426,1,95,1,13917669069935645991&fm=89",
                    "http://d.hiphotos.baidu.com/image/w%3D310/sign=6f7a098fc8ef76093c0b9f9e1edca301/5d6034a85edf8db1a89d71760a23dd54564e7474.jpg",
                    "http://c.hiphotos.baidu.com/image/w%3D310/sign=b4bc7e61d309b3deebbfe269fcbe6cd3/9345d688d43f879446309127d01b0ef41ad53ae8.jpg",
                    "http://d.hiphotos.baidu.com/image/w%3D310/sign=a22f50ded243ad4ba62e40c1b2025a89/8601a18b87d6277fe5339f432b381f30e924fcac.jpg",
                    "http://c.hiphotos.baidu.com/image/w%3D310/sign=44a82601aeaf2eddd4f14fe8bd110102/8cb1cb1349540923eff53b799058d109b3de491c.jpg",
                    "http://b.hiphotos.baidu.com/image/w%3D310/sign=4d73b518e8f81a4c2632eac8e72b6029/caef76094b36acaf6f9d91907fd98d1001e99c58.jpg"
            };

    private static String[] sFeedActions = new String[]{
    "美女",
            "漂亮",
            "感觉自己萌萌哒",
            "为什么我没有男朋友",
            "好想有个可靠的肩膀",
            "大家住手",
    "让我来"

    };

    public static String getAvatar(){
        int index = sRandom.nextInt(sAvatarData.length);
        return sAvatarData[index];
    }

    public static String getImange(){
        int index = sRandom.nextInt(sImange.length);
        return sImange[index];
    }

    public static String getFeedActionText(){
        return sFeedActions[sRandom.nextInt(sFeedActions.length)];
    }

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
