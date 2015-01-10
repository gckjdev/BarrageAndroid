package com.orange.barrage.android.util.persistent;

import android.test.AndroidTestCase;

import com.litl.leveldb.DB;
import com.orange.barrage.android.util.ContextManager;

import java.io.File;

/**
 * Created by Rollin on 2015/1/10.
 */
public class SharedPreferenceDAOTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextManager.init(getContext());
    }

    public void testPut(){
        byte[] value = "abc".getBytes();
        SharedPreferenceDAO.getInstance().put("key1", value);

        byte[] result = SharedPreferenceDAO.getInstance().get("key1");
        String resultStr = new String(result);
        assertEquals("abc", resultStr);
    }

    public void testDelete(){
        byte[] value = "abc".getBytes();
        SharedPreferenceDAO.getInstance().put("key1", value);

        SharedPreferenceDAO.getInstance().delete("key1");

        byte[] result = SharedPreferenceDAO.getInstance().get("key1");
        assertNull(result);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
