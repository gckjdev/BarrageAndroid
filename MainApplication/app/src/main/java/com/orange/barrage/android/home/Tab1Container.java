package com.orange.barrage.android.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.TimelineFragment;
import com.orange.barrage.android.util.activity.MessageCenter;

import roboguice.util.Ln;

public class Tab1Container extends HomeContainerFragment {

    private boolean isViewInited = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Ln.d("Tab1 onCreateView");
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
        replaceFragment(new TimelineFragment(), false);

    }

}
