package com.orange.barrage.android.friend.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;

import roboguice.inject.InjectView;

public class OptionFeedBackActivity extends BarrageCommonActivity {
    @InjectView(R.id.feedback_edittext)
    private EditText mFeedbackEdittext;

    @InjectView(R.id.option_contact)
    private EditText mFeedbackContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_option_feed_back, "意见反馈", R.string.option_feedback);
        initTopBar();


    }
    private void initTopBar() {

        // TODO set right button text
//        mTopBarView.setRightButton();

        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交的事件响应
                //MessageCenter.postInfoMessage("已经提交啦");
                Checktext();
                //finish();
            }
        });
    }

    private void Checktext()
    {
        if (TextUtils.isEmpty(mFeedbackEdittext.getText().toString()))
        {
            MessageCenter.postErrorMessage("反馈信息不能为空");
            return;
        }
    }
}
