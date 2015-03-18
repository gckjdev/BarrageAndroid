package com.orange.barrage.android.misc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class EditTextActivity extends BarrageCommonActivity{

    @InjectView(R.id.clearedittext)
    private EditText mClearEdittext;

    @InjectView(R.id.nickdescription)
    private TextView mNickdescription;

    private static String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    private static String BUNDLE_KEY_INIT_VALUE = "BUNDLE_KEY_INIT_VALUE";
    private static String BUNDLE_KEY_PLACE_HOLDER = "BUNDLE_KEY_PLACE_HOLDER";
    private static String BUNDLE_KEY_TIPS = "BUNDLE_KEY_TIPS";
    private static String BUNDLE_KEY_CALLBACK = "BUNDLE_KEY_CALLBACK";

    private String mTitle;
    private String mInitValue;
    private String mPlaceHolder;
    private String mTips;
    private EditTextActivityCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_edit_text,"",R.string.y_tijiao);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            Ln.w("EditTextActivity show but bundle data null");
            return;
        }

        mTips = bundle.getString(BUNDLE_KEY_TIPS);
        mPlaceHolder = bundle.getString(BUNDLE_KEY_PLACE_HOLDER);
        mTitle = bundle.getString(BUNDLE_KEY_TITLE);
        mInitValue = bundle.getString(BUNDLE_KEY_INIT_VALUE);
        mCallback = (EditTextActivityCallback) bundle.getSerializable(BUNDLE_KEY_CALLBACK);

        mTopBarView.setTitleText(mTitle);

        // TODO set view info
        mClearEdittext.setText(mInitValue);
        mClearEdittext.setHint(mPlaceHolder);
    }

    public static void start(Activity fromActivity,
                             String title,
                             String placeHolder,
                             String tips,
                             String currentValue,
                             EditTextActivityCallback callback){

        Bundle bundle = new Bundle();

        bundle.putString(BUNDLE_KEY_TITLE, title);
        bundle.putString(BUNDLE_KEY_PLACE_HOLDER, placeHolder);
        bundle.putString(BUNDLE_KEY_TIPS, tips);
        bundle.putString(BUNDLE_KEY_INIT_VALUE, currentValue);

        final Intent intent = new Intent();
        intent.putExtras(bundle);

        intent.setClass(fromActivity.getApplicationContext(), EditTextActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fromActivity.getApplicationContext().startActivity(intent);

    }
}
