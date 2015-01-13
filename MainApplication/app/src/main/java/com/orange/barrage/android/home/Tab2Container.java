package com.orange.barrage.android.home;

/**
 * Created by pipi on 15/1/6.
 */
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.TimelineFragment;

import roboguice.util.Ln;

public class Tab2Container extends HomeContainerFragment {

    private boolean isViewInited = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Ln.d("Tab2 onCreateView");
        return inflater.inflate(R.layout.fragment_home_container, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isViewInited) {
            isViewInited = true;
            initView();
        }
    }

    private void initView() {
        // TODO set fragment here
        replaceFragment(new TestFragment(), false);
    }

}
