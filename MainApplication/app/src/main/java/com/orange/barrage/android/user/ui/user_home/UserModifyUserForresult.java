package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;

import roboguice.inject.InjectView;

public class UserModifyUserForresult extends BarrageCommonActivity {
    @InjectView(R.id.clearedittext)
    private EditText mClearEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.layout.activity_user_modify_user_forresult, "修改个人昵称",R.string.y_tijiao);
        //mTopBarView=new TopBarView(this);
        initTopBar();
    }

    private void initTopBar()
    {
        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里是提交按钮的事件触发者
                if (TextUtils.isEmpty(mClearEditText.getText().toString()))
                {
                    MessageCenter.postErrorMessage("个人昵称不能为空");
                }
                IntentData();
            }
        });
    }
    private void IntentData()
    {

    }
}
