package com.orange.barrage.android.util.config;

import com.orange.barrage.android.util.ContextManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;

import org.json.JSONObject;

import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/3/2.
 */

@ContextSingleton
public class BarrageConfigManager {

    // TODO change dynamically while get updated
    public static String TRAFFIC_SERVER_URL = getString("TRAFFIC_SERVER_URL", "http://112.74.107.152:8100/?");

    public BarrageConfigManager(){
        MobclickAgent.updateOnlineConfig(ContextManager.getContext());
        MobclickAgent.setOnlineConfigureListener(new UmengOnlineConfigureListener(){
            @Override
            public void onDataReceived(JSONObject data) {
                // TODO don't know what to do yet, maybe for some urgent notification to UESER?
            }
        });
    }

    public static String getString(String key, String defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return value;
    }

    public static int getInt(String key, int defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return Integer.parseInt(value.trim());
    }

    public static boolean getBoolean(String key, boolean defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return (Integer.parseInt(value.trim()) == 1);
    }

}
