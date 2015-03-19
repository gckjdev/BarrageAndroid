package com.orange.barrage.android.user.ui.user_home.user_settings;

import com.orange.barrage.android.misc.activity.AbstractEditTextActivity;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

import roboguice.util.Ln;

public class UserSignatureEditTextActivity extends AbstractEditTextActivity {

    @Override
    public String getTips() {
        return "好签名能让你的精神焕发";
    }

    @Override
    public String getPlaceHolder() {
        return "请输入签名";
    }

    @Override
    public String getTopBarTitle() {
        return "签名设置";
    }

    @Override
    public String onClickSubmit(String newValue) {
        Ln.d("set signature name callback " + newValue);
        mUserMission.updateUserSignature(newValue, new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("签名已更新");
                    finish();
                }
            }
        });
        return null;
    }
}
