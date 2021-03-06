package com.orange.barrage.android.util.persistent;

import com.orange.barrage.android.util.ContextManager;

import java.util.logging.Level;

import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by Rollin on 2015/1/5.
 */
@ContextSingleton
public class SharedPreferenceDAO {
    private static final String DB_SHARED_PREFERENCE="shared_preference";

    private static SharedPreferenceDAO sInstance;

    public static SharedPreferenceDAO getInstance(){
        if(sInstance==null){
            sInstance = new SharedPreferenceDAO();
        }
        return sInstance;
    }

    private LevelDBDAO mLevelDBDAO;

    private SharedPreferenceDAO(){
        mLevelDBDAO = new LevelDBDAO(DB_SHARED_PREFERENCE);
    }

    public byte[] get(String key){
        return mLevelDBDAO.getBytes(key);
    }

    public void put(String key, byte[] value){
        mLevelDBDAO.put(key, value);
    }

    public void delete(String key){
        mLevelDBDAO.delete(key);
    }

    public void destroy(){
        mLevelDBDAO.destroy();
    }
}
