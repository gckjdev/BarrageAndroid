package com.orange.barrage.android.user.model;

import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.barrage.android.util.persistent.SharedPreferenceDAO;
import com.orange.barrage.android.util.persistent.barrage.DefaultDBDAO;

import javax.inject.Singleton;

/**
 * Created by pipi on 15/3/2.
 */

@Singleton
public class InviteCodeManager {

    DefaultDBDAO mDefaultDBDAO;

    private static final String CURRENT_INVITE_CODE = "CURRENT_INVITE_CODE";

    public void setCurrentInviteCode(String code){
        if (StringUtil.isEmpty(code)){
            return;
        }

        mDefaultDBDAO.put(CURRENT_INVITE_CODE, code);
    }

    public String getCurrentInviteCode(){
        return mDefaultDBDAO.getString(CURRENT_INVITE_CODE);
    }

    public void clearCurrentInviteCode(){
        mDefaultDBDAO.delete(CURRENT_INVITE_CODE);
    }
}
