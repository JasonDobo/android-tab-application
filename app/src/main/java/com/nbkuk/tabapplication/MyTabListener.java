package com.nbkuk.tabapplication;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by jason.dobo on 12/01/2015.
 */
public class MyTabListener implements ActionBar.TabListener {
    Fragment fragment;

    public MyTabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.container, fragment);
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // nothing done here
    }
}
