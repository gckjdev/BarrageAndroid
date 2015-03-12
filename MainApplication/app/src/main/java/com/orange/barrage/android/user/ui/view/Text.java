package com.orange.barrage.android.user.ui.view;

import android.app.Activity;
import android.os.Bundle;

import com.orange.barrage.android.R;

/**
 * Created by youjiannuo on 2015/3/6.
 */
public class Text extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_portrait);

        UserAvatarView mm = (UserAvatarView)findViewById(R.id.view);

        mm.setAvartUrl("http://edu.online.sh.cn/education/gb/content/attachement/jpg/site1/20130712/4487fc9b69f01349f5d444.jpg");

    }
}
