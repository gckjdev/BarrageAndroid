package com.orange.barrage.android.friend.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

public class RequestAddFriendActivity extends BarrageCommonActivity {
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //调用父类的super.onCreate()方法
        super.onCreate(savedInstanceState,R.layout.activity_request_add_friend,"添加好友",-1);
        //setContentView(R.layout.activity_request_add_friend);
        mRelativeLayout=(RelativeLayout)findViewById(R.id.add_friend_layout);
        mTextView=(TextView)findViewById(R.id.activity_request_add_friend);
        mTextView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(RequestAddFriendActivity.this,SearchUserActivity.class);
                //startActivity(new Intent(RequestAddFriendActivity.this,SearchUserActivity.class));
            }
        });
    }

    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);

    }

    @Override
    public void onClickFinish(View v) {
        super.onClickFinish(v);
    }
}
