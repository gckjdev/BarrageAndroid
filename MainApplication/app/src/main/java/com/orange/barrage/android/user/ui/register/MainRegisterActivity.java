package com.orange.barrage.android.user.ui.register;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class MainRegisterActivity extends BarrageCommonActivity {





    @InjectView(R.id.zhuche_other_id)
    LinearLayout mlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_registered, R.string.y_zhuce, -1);
    }

    /**
     * 下拉设置
     */
    public void onClickSF(View v) {
        MessageCenter.postErrorMessage("wo ");
        if (v.getTag() == null || v.getTag().equals("c")) {
            mlayout.setVisibility(View.VISIBLE);
            v.setTag("O");
            ((ImageButton) v).setImageResource(R.drawable.y_zhuce_shouhui);
            showMenuAnimation();
        } else {
            v.setTag("c");
            ((ImageButton) v).setImageResource(R.drawable.y_zhuche_xiala);
            closeMenuAnimation();
        }

    }


    private void closeMenuAnimation() {

        for (int i = 0; i < mlayout.getChildCount(); i++) {
            closeChildAnimation(mlayout.getChildAt(i), i);
        }

    }


    private void showMenuAnimation() {

        int j = 0;
        for (int i = mlayout.getChildCount() - 1; i >= 0; i--) {
            showChildAnimation(mlayout.getChildAt(i), j++);
        }

    }

    private void closeChildAnimation(View v, int i) {


        Animation alphaAnimation = new TranslateAnimation(0, 0, 0, -(v.getHeight() + v.getY()));
        alphaAnimation.setFillAfter(true);
        setChildAnimation(alphaAnimation, i, v);

        if (i == mlayout.getChildCount() - 1) {
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mlayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    private void showChildAnimation(View v, int i) {

        Animation alphaAnimation = new TranslateAnimation(0, 0, -(v.getHeight() + v.getY()), 0);
        setChildAnimation(alphaAnimation, i, v);
    }

    void setChildAnimation(Animation alphaAnimation, int i, View v) {
        if (v.getVisibility() == View.GONE) return;
        alphaAnimation.setDuration(500);
        alphaAnimation.setStartOffset(100 * i);
        v.startAnimation(alphaAnimation);
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
