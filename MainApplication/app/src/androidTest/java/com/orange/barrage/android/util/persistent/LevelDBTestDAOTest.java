package com.orange.barrage.android.util.persistent;

import android.test.AndroidTestCase;

import com.orange.barrage.android.util.ContextManager;

/**
 * Created by Rollin on 2015/1/10.
 */
public class LevelDBTestDAOTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextManager.init(getContext());
    }

    public void testSave(){
        String id = "id";
        String value ="saveItem";
        LevelDBTestDAO.getInstance().saveText(id, value);

        String result = LevelDBTestDAO.getInstance().getText(id);
        assertEquals(value, result);
    }

    public void testDelete(){
        String id="id1";
        String value = "abc";
        LevelDBTestDAO.getInstance().saveText(id, value);

        LevelDBTestDAO.getInstance().deleteText(id);
        String result = LevelDBTestDAO.getInstance().getText(id);
        assertNull("value should be deleted",result);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
