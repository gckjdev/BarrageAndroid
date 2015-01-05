package com.orange.barrage.android.util.persistent;

import com.orange.barrage.android.util.ContextManager;

import im.amomo.leveldb.DBFactory;
import im.amomo.leveldb.LevelDB;

/**
 * Created by Rollin on 2015/1/5.
 */
public class LevelDBDAO {

    private static final String DB_NAME = "barrage";

    private static LevelDBDAO sInstance = new LevelDBDAO();

    private LevelDB mLevelDB;

    public static LevelDBDAO getInstance(){
        return sInstance;
    }

    public void init(){
        mLevelDB = DBFactory.open(ContextManager.getContext(), DB_NAME);
    }

    public void destroy(){
        mLevelDB.close();
        mLevelDB = null;
    }

    public byte[] get(String key){
        return mLevelDB.get(key);
    }

    public void put(String key, byte[] value){
        mLevelDB.put(key, value);
    }

    public boolean exists(String key){
        return mLevelDB.exists(key);
    }
}
