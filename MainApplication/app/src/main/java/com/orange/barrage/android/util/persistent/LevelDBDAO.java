package com.orange.barrage.android.util.persistent;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.orange.barrage.android.util.ContextManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rollin on 2015/1/5.
 */
public class LevelDBDAO {

    private static final String DB_NAME_PREFIX = "/leveldb/";
    private String mDBName;

    private DB mLevelDB;

    public LevelDBDAO(String dbName){
        File dbFile = new File(DB_NAME_PREFIX+mDBName);
        mLevelDB = new DB(dbFile);
    }

    public void destroy(){
        mLevelDB.close();
        mLevelDB = null;
    }

    public byte[] get(String key){
        return mLevelDB.get(key.getBytes());
    }

    public void put(String key, byte[] value){
        mLevelDB.put(key.getBytes(), value);
    }

    public void delete(String key){
        mLevelDB.delete(key.getBytes());
    }

    public boolean exists(String key){
        return get(key) != null;
    }

    public List<Byte[]> list(){
        List list = new ArrayList();
        Iterator iterator = mLevelDB.iterator();
        for(iterator.seekToFirst();iterator.isValid();iterator.next()){
            list.add(iterator.getValue());
        }
        return list;
    }
}
