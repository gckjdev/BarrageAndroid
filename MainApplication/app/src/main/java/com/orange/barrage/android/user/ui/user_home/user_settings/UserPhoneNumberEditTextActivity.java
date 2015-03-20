package com.orange.barrage.android.user.ui.user_home.user_settings;

import com.orange.barrage.android.misc.activity.AbstractEditTextActivity;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

public class UserPhoneNumberEditTextActivity extends AbstractEditTextActivity {

    @Override
    public String getTips() {
        return "请输入11位正确的手机号码";
    }

    @Override
    public String getPlaceHolder() {
        return "请输入手机号码";
    }

    @Override
    public String getTopBarTitle() {
        return "手机号码设置";
    }

    @Override
    public String onClickSubmit(String newValue) {
        mUserMission.updateUserPhoneNumber(newValue,new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("手机号码已经更新");
                    finish();
                }
            }
        });
        return null;
    }
}
