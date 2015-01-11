package com.orange.barrage.android.util.persistent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.litl.leveldb.DB;
import com.orange.barrage.android.util.ContextManager;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Rollin on 2015/1/5.
 */
@Singleton
public class LevelDBTestDAO {

    private String TABLE_NAME = "level_test";

    private LevelDBDAO mLevelDBDAO;

    public LevelDBTestDAO(){
        //init dao
        mLevelDBDAO = new LevelDBDAO(TABLE_NAME);
    }

    public void saveText(String id, String text){
        mLevelDBDAO.put(id, text.getBytes());
    }

    public String getText(String id){
        byte[] bytes = mLevelDBDAO.get(id);
        if(bytes==null){
            return null;
        }
        return new String(bytes);
    }

    public void deleteText(String id){
        mLevelDBDAO.delete(id);
    }

    public void destroy(){
        mLevelDBDAO.destroy();
    }
}
