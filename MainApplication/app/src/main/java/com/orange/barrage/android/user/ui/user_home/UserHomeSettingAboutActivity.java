package com.orange.barrage.android.user.ui.user_home;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class UserHomeSettingAboutActivity extends BarrageCommonActivity {
    @InjectView(R.id.current_version)
    private TextView mCurrentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_setting_about,"关于",-1);
        mCurrentVersion.setText("当前版本为 "+getVersion());
    }

    //获取当前版本号码
    private String getVersion()
    {
        PackageManager packageManager=this.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        }catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
