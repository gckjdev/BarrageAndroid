package com.orange.barrage.android.util.persistent;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.orange.barrage.android.util.ContextManager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rollin on 2015/1/5.
 */
public class LevelDBDAO {

    private String mDBName;

    private DB mLevelDB;

    public LevelDBDAO(String dbName){
        File dbFile = new File(ContextManager.getContext().getCacheDir(), dbName);
        mLevelDB = new DB(dbFile);
        mLevelDB.open();
    }

    public void destroy(){
        mLevelDB.close();
    }

    public byte[] get(String key){
        return mLevelDB.get(bytes(key));
    }

    public void put(String key, byte[] value){
        mLevelDB.put(bytes(key), value);
    }

    public void delete(String key){
        mLevelDB.delete(bytes(key));
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

    private byte[] bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
