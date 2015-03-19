package com.orange.barrage.android.user.ui.user_home.user_settings;

import com.orange.barrage.android.misc.activity.AbstractEditTextActivity;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/18.
 */
public class UserNickEditTextActivity extends AbstractEditTextActivity {

    @Override
    public String getTips() {
        return "好名字能让你的朋友尽快记住你";
    }

    @Override
    public String getPlaceHolder() {
        return "请输入昵称";
    }

    @Override
    public String getTopBarTitle() {
        return "昵称设置";
    }

    @Override
    public String onClickSubmit(String newValue) {
        Ln.d("set nick name callback " + newValue);
        mUserMission.updateUserNick(newValue, new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("昵称已更新");
                    finish();
                }
            }
        });
        return null;
    }
}
