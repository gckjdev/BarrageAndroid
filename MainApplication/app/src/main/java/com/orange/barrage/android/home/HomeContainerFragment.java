package com.orange.barrage.android.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.orange.barrage.android.R;

import roboguice.fragment.RoboFragment;
import roboguice.util.Ln;

//import android.support.v4.app.FragmentTransaction;

public class HomeContainerFragment extends RoboFragment {

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        Ln.e("pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }
}
