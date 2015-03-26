package com.orange.barrage.android.user.ui.user_home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class UserHomeSettingAboutActivity extends BarrageCommonActivity {
    @InjectView(R.id.current_version)
    private TextView mCurrentVersion;

    @InjectView(R.id.checkupdate)
    private Button mChechUpdate;

    @InjectView(R.id.help)
    private Button mHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_setting_about,"关于",-1);
        mCurrentVersion.setText("当前版本为 "+getVersion());
        mChechUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                Uri uri=Uri.parse("mailto:123@126.com");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    //获取当前版本号
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
