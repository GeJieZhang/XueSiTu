package com.lib.fastkit.utils.fragment_deal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentCustomUtils {


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
//    public static void replaceFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {
//        if (activity.getSupportFragmentManager().findFragmentByTag(tag) == null) {
//            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(containerId, fragment, tag);
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            fragmentTransaction.addToBackStack(tag);
//            fragmentTransaction.commit();
//        } else {
//            //弹出tag标记的那层以上的所有fragment
//            activity.getSupportFragmentManager().popBackStack(tag, 0);
//        }
//    }
    public static void replaceFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {


        if (activity.getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(containerId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerId, activity.getSupportFragmentManager().findFragmentByTag(tag), tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * 横竖屏切换设置Fragment
     *
     * @param activity
     * @param containerId
     * @param fragment
     * @param tag
     */
    public static void setFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {

        if (activity.getSupportFragmentManager().findFragmentByTag(tag) != null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.commit();

    }


    /**
     * @param activity
     * @param fragment 实列对象
     * @param tag      唯一标识
     */
    public static void removeFragment(AppCompatActivity activity, Fragment fragment, String tag) {
        if (activity.getSupportFragmentManager().findFragmentByTag(tag) != null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);

            fragmentTransaction.commit();

        }
    }


}
