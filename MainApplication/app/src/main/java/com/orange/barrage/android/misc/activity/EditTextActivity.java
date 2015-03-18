package com.orange.barrage.android.misc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class EditTextActivity extends BarrageCommonActivity{

    @InjectView(R.id.clearedittext)
    private EditText mClearEdittext;

    @InjectView(R.id.nickdescription)
    private TextView mNickdescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_edit_text,"",R.string.y_tijiao);
    }

    public static void start(Activity fromActivity,
                             String title,
                             String placeHolder,
                             String tips,
                             EditTextActivityCallback callback){


        ActivityIntent.startIntent(fromActivity, EditTextActivity.class);
}
}
