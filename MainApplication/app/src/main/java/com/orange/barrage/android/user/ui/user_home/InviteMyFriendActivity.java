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
import com.orange.barrage.android.user.ui.view.ActionSheetDialog;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class InviteMyFriendActivity extends BarrageCommonActivity {
    private static final String AVAILINVITENUMBER="avail_number";
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
        for(int i=0;i<20;i++)
        {
            availView();
        }
        for(int i=0;i<20;i++)
        {
            sendView();
        }
    }

    private void availView()
    {
        //取得布局文件
        View view=layoutInflater.inflate(R.layout.activity_invite_my_friend_availitem,null);
        //取得布局文件中的相对布局,就是控件的直接父类
        final RelativeLayout addViewLayout = (RelativeLayout) view.findViewById(R.id.invite_availlayout);
        //addViewLayout.setPadding(0,0,0,3);
        //取得布局文件中的控件
        final TextView avail_number=(TextView)addViewLayout.findViewById(R.id.avail_invitenumber);
        avail_number.setText("1234");
        final ImageView imageView=(ImageView)addViewLayout.findViewById(R.id.avail_inviteimageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(InviteMyFriendActivity.this)
                        .builder()
                        .setTitle("邀请好友")
                        .setCancelabe(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("短信邀请", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //MessageCenter.postInfoMessage("测试菜单成功");
                                //把邀请码传过去,得到邀请码 avail_number.getText().toString();
                                //Activity之间传递信息,如果两个Activtiy之间传递数据不需要返回的话，那么可以用这个方法
                                ActivityIntent.startIntent(InviteMyFriendActivity.this,SmsMessageInviteMyFriendActivity.class,AVAILINVITENUMBER,avail_number.getText().toString());
                        }
                        })
                        .addSheetItem("分享到朋友圈", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                        .addSheetItem("邀请微信好友", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                        .addSheetItem("邀请QQ好友", ActionSheetDialog.SheetItemColor.Blue,new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        }).show();

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
        final  RelativeLayout addViewLayout = (RelativeLayout) view.findViewById(R.id.oversendnumber_layout);
        addViewLayout.setPadding(0,0,0,0);
        //取得布局文件中的控件
        final TextView oversendtext=(TextView)addViewLayout.findViewById(R.id.oversendtext);
        oversendtext.setText("1234");
        TextView oversendname=(TextView)addViewLayout.findViewById(R.id.oversendname);
        oversendname.setText("小文");
        ImageView imageView=(ImageView)addViewLayout.findViewById(R.id.oversendimageview);
        imageView.setImageResource(R.drawable.user_home_more);
        mOversendLayout.addView(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }

}
