package com.orange.barrage.android.misc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/18.
 */
public abstract class AbstractEditTextActivity extends BarrageCommonActivity {


    @InjectView(R.id.clearedittext)
    private EditText mClearEdittext;

    @InjectView(R.id.nickdescription)
    private TextView mNickdescription;

    private static String BUNDLE_KEY_INIT_VALUE = "BUNDLE_KEY_INIT_VALUE";

    private String mInitValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_edit_text,"",R.string.y_tijiao);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            Ln.w("EditTextActivity show but bundle data null");
            return;
        }

        mInitValue = bundle.getString(BUNDLE_KEY_INIT_VALUE);

        mTopBarView.setTitleText(getTopBarTitle());

        mClearEdittext.setText(mInitValue);
        mClearEdittext.setHint(getPlaceHolder());

        // TODO set tips

        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit();
            }
        });
    }

    public static void start(Activity fromActivity,
                             AbstractEditTextActivity toActivity,
                             String initValue){

        Bundle bundle = new Bundle();

        bundle.putString(BUNDLE_KEY_INIT_VALUE, initValue);

        final Intent intent = new Intent();
        intent.putExtras(bundle);

        intent.setClass(fromActivity.getApplicationContext(), toActivity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fromActivity.getApplicationContext().startActivity(intent);

    }

    public abstract String getTips();
    public abstract String getPlaceHolder();
    public abstract String getTopBarTitle();
    public abstract String onClickSubmit();

}
