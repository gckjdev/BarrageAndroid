package com.orange.barrage.android.util.persistent;

import android.graphics.Color;
import android.test.AndroidTestCase;

import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.CompressColorUtil;

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

    public void testConvertColorWhite(){
        int color = Color.WHITE;
        int barrage = CompressColorUtil.toBarrageColor(color);
        int android = CompressColorUtil.toAndroidColor(barrage);
        assertEquals(color, android);
    }

    public void testConvertColorRed(){
        int color = Color.RED;
        int barrage = CompressColorUtil.toBarrageColor(color);
        int android = CompressColorUtil.toAndroidColor(barrage);
        assertEquals(color, android);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
