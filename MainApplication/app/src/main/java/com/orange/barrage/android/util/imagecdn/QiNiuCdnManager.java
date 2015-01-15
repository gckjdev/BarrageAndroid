package com.orange.barrage.android.util.imagecdn;

import javax.inject.Singleton;

/**
 * Created by pipi on 15/1/6.
 */

@Singleton
public class QiNiuCdnManager {

    public String getToken(){
        // TODO online config later
        return "PGXicdkeGqQHXTBCV-qKbMaQj6aFWwM3yS1qcKKF:AeIaOS9MyyZv7Rq1i2lQ32UGMZc=:eyJzY29wZSI6Imdja2pkZXYiLCJkZWFkbGluZSI6Mjg2NzcyMzQ2Nn0=";
    }

    public String getUrl(String key){
        return "http://gckjdev.qiniudn.com/".concat(key);
    }


}
