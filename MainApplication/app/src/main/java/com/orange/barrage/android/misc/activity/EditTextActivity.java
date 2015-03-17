package com.orange.barrage.android.misc.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.ui.user_home.UserModifyUserForresult;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

public class EditTextActivity extends BarrageCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
    }

    public static void start(Activity fromActivity,
                             String title,
                             String placeHolder,
                             String tips,
                             EditTextActivityCallback callback){

        //

        ActivityIntent.startIntent(fromActivity, EditTextActivity.class);
    }

}
