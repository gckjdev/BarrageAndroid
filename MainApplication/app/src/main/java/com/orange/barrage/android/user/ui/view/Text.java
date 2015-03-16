package com.orange.barrage.android.user.ui.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.FloatWindow;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;
import com.orange.barrage.android.util.view.RelativeLayoutwhitTriangleIcon;

/**
 * Created by youjiannuo on 2015/3/6.
 */
public class Text extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_portrait);

        MoveViewParentRelativity moveViewParentRelativity = (MoveViewParentRelativity) findViewById(R.id.mm);

        ImageView mUserAvatraView = new ImageView(this);

        mUserAvatraView.setImageResource(R.drawable.ic_launcher);

        moveViewParentRelativity.addView(mUserAvatraView , 100 , 100 , 50 , 50);


//        mm.setAvartUrl("http://edu.online.sh.cn/education/gb/content/attachement/jpg/site1/20130712/4487fc9b69f01349f5d444.jpg");

    }




}
