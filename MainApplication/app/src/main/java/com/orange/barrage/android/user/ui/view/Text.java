package com.orange.barrage.android.user.ui.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;

import com.orange.barrage.android.R;

/**
 * Created by youjiannuo on 2015/3/6.
 */

import java.util.List;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Text extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initView();
    }

    private void initView() {
//        ImageView imageView = getImageView();
//
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
//
//        linearLayout.addView(getImageView());

    }




    private ImageView getImageView(){
        ImageView imageView = new ImageView(this);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, imageView.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20 , 20);
        params.leftMargin = 100;

        Drawable drawable = getResources().getDrawable(R.drawable.x_homepage_3_tab_bg_circle_red);
        imageView.setImageDrawable(drawable);
        imageView.setLayoutParams(params);
        return imageView;
    }

}

