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


    @InjectView(R.id.input_edittext)
    private EditText mEditText;

    @InjectView(R.id.nick_or_signature_description)
    private TextView mNickdescription;

    private static String BUNDLE_KEY_INIT_VALUE = "BUNDLE_KEY_INIT_VALUE";

    protected String mInitValue;

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

        mEditText.setText(mInitValue);
        mEditText.setHint(getPlaceHolder());
        mNickdescription.setText(getTips());

        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit(mEditText.getText().toString());
            }
        });
    }

    public static void start(Activity fromActivity,
                             Class<? extends AbstractEditTextActivity> toActivity,
                             String initValue){

        Bundle bundle = new Bundle();

        bundle.putString(BUNDLE_KEY_INIT_VALUE, initValue);

        final Intent intent = new Intent();
        intent.putExtras(bundle);

        intent.setClass(fromActivity.getApplicationContext(), toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fromActivity.getApplicationContext().startActivity(intent);

    }

    public abstract String getTips();
    public abstract String getPlaceHolder();
    public abstract String getTopBarTitle();
    public abstract String onClickSubmit(String newValue);

}
