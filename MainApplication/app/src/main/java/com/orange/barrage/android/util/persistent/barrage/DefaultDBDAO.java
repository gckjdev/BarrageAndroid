package com.orange.barrage.android.util.persistent.barrage;

import com.orange.barrage.android.util.persistent.LevelDBDAO;

import javax.inject.Singleton;

/**
 * Created by pipi on 15/1/13.
 */

@Singleton
public class DefaultDBDAO extends LevelDBDAO {

    static String DB_NAME = "default_barrage_db.db";

    public DefaultDBDAO() {
        super(DB_NAME);
    }
}
