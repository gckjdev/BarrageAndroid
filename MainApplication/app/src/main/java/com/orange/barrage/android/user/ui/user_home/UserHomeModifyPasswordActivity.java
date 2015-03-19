package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.view.InputEditText;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class UserHomeModifyPasswordActivity extends BarrageCommonActivity {
    @InjectView(R.id.input_new_password)
    private InputEditText mInputNewPassword;

    @InjectView(R.id.input_confirm_password)
    private InputEditText mInoutConfirmPassword;

    @InjectView(R.id.submit_password)
    private Button mSubmitPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify_password,"修改密码",-1);
        mSubmitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
