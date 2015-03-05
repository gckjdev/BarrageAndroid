package com.orange.barrage.android.user.ui.login;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

public class LoginActivity extends BarrageCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState ,R.layout.activity_login , R.string.y_logn , -1);
    }

}
