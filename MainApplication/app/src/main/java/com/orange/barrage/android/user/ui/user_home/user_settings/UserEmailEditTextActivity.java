package com.orange.barrage.android.user.ui.user_home.user_settings;

import com.orange.barrage.android.misc.activity.AbstractEditTextActivity;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

import roboguice.util.Ln;

public class UserEmailEditTextActivity extends AbstractEditTextActivity {


    @Override
    public String getTips() {
        return "好的邮箱能让你的朋友能尽快联系到你";
    }

    @Override
    public String getPlaceHolder() {
        return "请输入邮箱";
    }

    @Override
    public String getTopBarTitle() {
        return "邮箱设置";
    }

    @Override
    public String onClickSubmit(String newValue) {
        Ln.d("set email name callback " + newValue);
        mUserMission.updateUserEmail(newValue, new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("邮箱已更新");
                    finish();
                }
            }
        });
        return null;
    }
}
