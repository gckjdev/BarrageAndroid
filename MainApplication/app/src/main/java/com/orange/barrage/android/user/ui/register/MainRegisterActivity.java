package com.orange.barrage.android.user.ui.register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.login.LoginEditeTextActivtiy;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class MainRegisterActivity extends BarrageCommonActivity {

    @InjectView(R.id.zhuche_other_id)
    LinearLayout mlayout;

    private float mS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_registered, R.string.y_zhuce, -1);
    }

    /**
     * 下拉设置
     */
    public void onClickSF(View v) {

        setAnimation();

//        if (v.getTag() == null || v.getTag().equals("c")) {
//            mlayout.setVisibility(View.VISIBLE);
//            v.setTag("O");
//            ((ImageButton) v).setImageResource(R.drawable.y_zhuce_shouhui);
//        } else {
//            mlayout.setVisibility(View.GONE);
//            v.setTag("c");
//            ((ImageButton) v).setImageResource(R.drawable.y_zhuche_xiala);
//        }

    }

    public void setAnimation1(){
        ScaleAnimation alphaAnimation = new ScaleAnimation(1.0f , 1.0f , 0 , 1.0f);
        //设置动画时间
        alphaAnimation.setDuration(500);
        mlayout.startAnimation(alphaAnimation);
        alphaAnimation.start();
    }


    public void setAnimation() {
//        //初始化
//        Animation alphaAnimation = new TranslateAnimation(0 , 0 , -mS , 0);
//        //设置动画时间
//        alphaAnimation.setDuration(500);
//
//        mlayout.startAnimation(alphaAnimation);

        for(int i = 0 ; i < mlayout.getChildCount() ; i ++)
            setChildAnimation(mlayout.getChildAt(i));

    }


    private void setChildAnimation(View v ){

        Animation alphaAnimation = new TranslateAnimation(0 , 0 , -(v.getHeight() + v.getY()), 0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setStartTime(100);
        v.startAnimation(alphaAnimation);

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mS = mlayout.getHeight();


//        mlayout.layout(-200 , -200 , mlayout.getHeight() , mlayout.getWidth());
    }


    public void onClickQQ(View v) {
    }

    public void onClickXinliang(View v) {
    }

    public void onClickWeixin(View v) {

    }

    public void onClickEmail(View v) {
    }

    public void onClickPhone(View v) {
    }
}
