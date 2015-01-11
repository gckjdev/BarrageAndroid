package com.orange.barrage.android.util.persistent;

import android.test.AndroidTestCase;

import com.orange.barrage.android.util.ContextManager;

/**
 * Created by Rollin on 2015/1/10.
 */
public class LevelDBTestDAOTest extends AndroidTestCase {

    private static LevelDBTestDAO mLevelDBTestDAO;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextManager.init(getContext());

        if(mLevelDBTestDAO==null) {
            mLevelDBTestDAO = new LevelDBTestDAO();
        }
    }

    public void testSave(){
        String id = "id";
        String value ="saveItem";
        mLevelDBTestDAO.saveText(id, value);

        String result = mLevelDBTestDAO.getText(id);
        assertEquals(value, result);
    }

    public void testDelete(){
        String id="id1";
        String value = "abc";
        mLevelDBTestDAO.saveText(id, value);

        mLevelDBTestDAO.deleteText(id);
        String result = mLevelDBTestDAO.getText(id);
        assertNull("value should be deleted", result);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
