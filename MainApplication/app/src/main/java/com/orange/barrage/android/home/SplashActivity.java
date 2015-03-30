package com.orange.barrage.android.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.orange.barrage.android.R;

import roboguice.inject.InjectView;

public class SplashActivity extends Activity {
    private static final int SPLASH_DISPLAY_TIME = 500;
    private Handler handler;
    @InjectView(R.id.imageview)
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_splash);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,HomePageActivity.class));
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.mainfadein,R.anim.splashfadeout);
            }
        },SPLASH_DISPLAY_TIME);*/

    }
}
