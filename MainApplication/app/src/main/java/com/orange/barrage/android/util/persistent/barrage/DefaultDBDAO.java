package com.orange.barrage.android.util.persistent.barrage;

import com.orange.barrage.android.util.persistent.LevelDBDAO;

import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/1/13.
 */

@ContextSingleton
public class DefaultDBDAO extends LevelDBDAO {

    static String DB_NAME = "default_barrage_db.db";

    public DefaultDBDAO() {
        super(DB_NAME);
    }
}
