package com.orange.barrage.android.util.persistent;

import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.misc.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/3/3.
 *
 * Store User Related Data (In the future, if you switch the UserId,
 * Then you can switch the user DATA quickly!
 *
 */

@ContextSingleton
public class ShareUserDBDAO {

    final ConcurrentHashMap<String, LevelDBDAO> dbMap = new ConcurrentHashMap<>();

    @Inject
    UserManager mUserManager;

    public LevelDBDAO getDB(){
        String userId = mUserManager.getUserId();
        if (StringUtil.isEmpty(userId)){
            // user not exist, return null
            return null;
        }

        String dbName = String.format("user_db_%s.db", userId);
        if (dbMap.containsKey(dbName)){
            return dbMap.get(dbName);
        }
        else{
            LevelDBDAO db = new LevelDBDAO(dbName);
            dbMap.put(dbName, db);
            return db;
        }
    }
}
