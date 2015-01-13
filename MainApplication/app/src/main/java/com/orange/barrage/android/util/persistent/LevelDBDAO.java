package com.orange.barrage.android.util.persistent;

import android.util.Log;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.GeneratedMessage;
import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.orange.barrage.android.util.ContextManager;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit.converter.ConversionException;
import roboguice.util.Ln;

/**
 * Created by Rollin on 2015/1/5.
 */
public class LevelDBDAO {

    private static final String TAG = LevelDBDAO.class.getName();
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

    public byte[] getBytes(String key){
        return mLevelDB.get(bytes(key));
    }

    public String getString(String key){
        byte[] bytes = mLevelDB.get(bytes(key));
        if (bytes == null){
            return null;
        }

        String str = null;
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Ln.e(e, "getString but catch exception="+e.toString());
        }
        return str;
    }

    public void put(String key, String value){
        byte[] bytes = new byte[0];
        try {
            bytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Ln.e(e, "putString but catch exception="+e.toString());
        }
        mLevelDB.put(bytes(key), bytes);
    }

    // save PB
    public void put(String key, GeneratedMessage pbMessage){
        if (pbMessage == null){
            Ln.w("put pb message but pb is null");
            return;
        }

        byte[] bytes = pbMessage.toByteArray();
        if (bytes == null){
            Ln.w("put pb message but pb to bytes is null");
            return;
        }

        mLevelDB.put(bytes(key), bytes);
    }

    // get PB
    public<T extends GeneratedMessage> T getPB(String key, Class<T> c){

        byte[] bytes = getBytes(key);
        if (bytes == null){
            return null;
        }

        if (!AbstractMessageLite.class.isAssignableFrom(c)) {
            Ln.e("get protocol buffer data but incorrect class"+c.getName());
            return null;
        }

        try {
            Method parseFrom = c.getMethod("parseFrom", byte[].class);
            return (T)parseFrom.invoke(null, bytes);
        } catch (Exception e) {
            Ln.e(e, "get protocol buffer data but catch exception="+e.toString());
        }

        return null;
    }

    public void put(String key, byte[] value){
        mLevelDB.put(bytes(key), value);
    }

    public void delete(String key){
        mLevelDB.delete(bytes(key));
    }

    public boolean exists(String key){
        return getBytes(key) != null;
    }

    public List<byte[]> list(){
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
            Ln.e(e, "convert to bytes but catch exception="+e.toString());
            return null;
        }
    }
}
