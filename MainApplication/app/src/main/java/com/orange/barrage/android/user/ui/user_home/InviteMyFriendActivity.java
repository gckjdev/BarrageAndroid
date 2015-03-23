package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;

import roboguice.inject.InjectView;

public class InviteMyFriendActivity extends BarrageCommonActivity {
    @InjectView(R.id.oversend_layout)
    //存放加载可用邀请码布局的视图
    private LinearLayout mOversendLayout;

    // //存放已发放邀请码加载布局的视图
    @InjectView(R.id.avail_linearlayout)
    private LinearLayout mAvailLayout;

    LayoutInflater layoutInflater = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, com.orange.barrage.android.R.layout.activity_invite_my_friend, "邀请好友", R.string.b_complete);
        layoutInflater = LayoutInflater.from(this);
        //测试动态加载
        for(int i=0;i<100;i++)
        {
            availView();
        }
        for(int i=0;i<40;i++)
        {
            sendView();
        }
    }

    private void availView()
    {
        //取得布局文件
        View view=layoutInflater.inflate(R.layout.activity_invite_my_friend_availitem,null);
        //取得布局文件中的相对布局
        RelativeLayout addViewLayout = (RelativeLayout) view.findViewById(R.id.invite_availlayout);
        addViewLayout.setPadding(0,0,0,3);
        //取得布局文件中的控件
        TextView avaail_invitenumber=(TextView)addViewLayout.findViewById(R.id.avail_invitenumber);
        avaail_invitenumber.setText("1234");
        ImageView imageView=(ImageView)addViewLayout.findViewById(R.id.avail_inviteimageview);
        imageView.setImageResource(R.drawable.invitefriend);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageCenter.postInfoMessage("点击成功");
            }
        });
        //承载上面的布局文件的布局
        mAvailLayout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void sendView()
    {
        //取得布局文件
        View view=layoutInflater.inflate(R.layout.activity_invite_my_friend_oversend,null);
        //取得布局文件中的相对布局
        RelativeLayout addViewLayout = (RelativeLayout) view.findViewById(R.id.oversendnumber_layout);
        addViewLayout.setPadding(0,0,0,0);
        //取得布局文件中的控件
        TextView oversendtext=(TextView)addViewLayout.findViewById(R.id.oversendtext);
        oversendtext.setText("1234");
        TextView oversendname=(TextView)addViewLayout.findViewById(R.id.oversendname);
        oversendname.setText("小文");
        ImageView imageView=(ImageView)addViewLayout.findViewById(R.id.oversendimageview);
        imageView.setImageResource(R.drawable.user_home_more);
        mOversendLayout.addView(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }

}
