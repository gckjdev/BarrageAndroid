package com.orange.barrage.android.util.persistent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.orange.barrage.android.util.ContextManager;

import im.amomo.leveldb.DBFactory;
import im.amomo.leveldb.LevelDB;

/**
 * Created by Rollin on 2015/1/5.
 */
//FIXME: remove later
public class LevelDBTestDAO {

    private static String TAG = "LevelDBTestDAO";

    public void performShaedPreferences(int n) {
        long t0 = System.currentTimeMillis();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ContextManager.getContext());

        for (int i = 0; i < n; i++) {
            long value = sharedPrefs.getLong("foo", 0L);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putLong("foo", value + 1L);
            editor.commit();
        }

        Log.d(TAG, "SharedPreferences open, (get, put)*" + n + ": " + (System.currentTimeMillis() - t0) + "ms\n");
    }

    public void performLevelDB(int n) {
        long t0 = System.currentTimeMillis();

        byte[] value = "abc".getBytes();
        LevelDBDAO db = LevelDBDAO.getInstance();

        for (int i = 0; i < n; i++) {
            if (db.exists("foo")) {
                value = db.get("foo");
            } else {
                db.put("foo", value);
            }
        }

        Log.d(TAG,"(get, put)*" + n + ": " + (System.currentTimeMillis() - t0) + "ms\n");
    }
}
