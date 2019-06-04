package com.lib.fastkit.utils.fragment_deal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class CustomFragmentUtils {


    /**
     * 添加一个Fragment
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    public static void addFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * 替换Fragment
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    public static void replaceFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {
        if (activity.getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            //弹出tag标记的那层以上的所有fragment
            activity.getSupportFragmentManager().popBackStack(tag, 0);
        }
    }


}
