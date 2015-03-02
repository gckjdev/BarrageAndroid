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

    public BarrageConfigManager(){
        MobclickAgent.updateOnlineConfig(ContextManager.getContext());
        MobclickAgent.setOnlineConfigureListener(new UmengOnlineConfigureListener(){
            @Override
            public void onDataReceived(JSONObject data) {
                // TODO don't know what to do yet, maybe for some urgent notification to UESER?
            }
        });
    }

    public String getString(String key, String defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return value;
    }

    public int getInt(String key, int defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return Integer.parseInt(value.trim());
    }

    public boolean getBoolean(String key, boolean defaultValue){
        String value = MobclickAgent.getConfigParams( ContextManager.getContext(), key );
        if (value == null){
            return defaultValue;
        }

        return (Integer.parseInt(value.trim()) == 1);
    }

}
