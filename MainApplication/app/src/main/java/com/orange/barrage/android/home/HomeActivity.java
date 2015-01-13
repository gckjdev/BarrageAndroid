package com.orange.barrage.android.home;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.ContextManager;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.activity.RoboFragmentActivity;

public class HomeActivity extends RoboFragmentActivity {

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";
    private static final String TAB_3_TAG = "tab_3";

    private FragmentTabHost mTabHost;

    @Inject
    UserMission mUserMission;

    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.tab_select,"首页",R.drawable.tab_select),Tab1Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.tab_select,"发表",R.drawable.tab_select),Tab2Container.class,null);
        mTabHost.addTab(setIndicator(HomeActivity.this,mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.tab_select,"好友",R.drawable.tab_select),Tab3Container.class,null);


    }

    @Override
    public void onStart(){
        super.onStart();

        if (!mUserManager.hasUser()) {
            String email = String.format("test%d@163.com", ((long)Math.random()) % 99999);
            mUserMission.regiseterUserByEmail(email, "password", null, new UserMissionCallback() {
                @Override
                public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();

        if (currentTabTag.equals(TAB_1_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_1_TAG)).popFragment();
        } else if (currentTabTag.equals(TAB_2_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_2_TAG)).popFragment();
        }
        else if (currentTabTag.equals(TAB_3_TAG)) {
            isPopFragment = ((HomeContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_3_TAG)).popFragment();
        }

        if (!isPopFragment) {
            finish();
        }
    }

    private TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec,
                                 int resid, String string, int genresIcon) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_item, null);
        v.setBackgroundResource(resid);
        TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
        ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);

        tv.setText(string);
        img.setBackgroundResource(genresIcon);
        return spec.setIndicator(v);
    }
}
