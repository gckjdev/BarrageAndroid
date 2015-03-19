package com.orange.barrage.android.user.ui.user_home.user_settings;

import com.orange.barrage.android.misc.activity.AbstractEditTextActivity;

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
        return null;
    }
}
