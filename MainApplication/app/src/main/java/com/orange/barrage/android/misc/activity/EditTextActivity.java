package com.orange.barrage.android.misc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class EditTextActivity extends BarrageCommonActivity{

    @InjectView(R.id.clearedittext)
    private EditText mClearEdittext;

    @InjectView(R.id.nickdescription)
    private TextView mNickdescription;
    //获取本地用户只需要两步,一步是取得用户管理，第二是getUser
    @Inject
    UserManager mUserManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_edit_text,"",R.string.y_tijiao);
        UserProtos.PBUser user = mUserManager.getUser();
        mClearEdittext.setText(user.getNick());
    }

    public static void start(Activity fromActivity,
                             String title,
                             String placeHolder,
                             String tips,
                             EditTextActivityCallback callback){


        ActivityIntent.startIntent(fromActivity, EditTextActivity.class);
}
}
