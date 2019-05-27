package com.lib.app;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;



/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class FragmentUtils {

    public static Fragment getDayiFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.Dayi_DayiFragment).navigation();
        return fragment;
    }

    public static Fragment getFengXiangFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.YouXuan_FengXiangFragment).navigation();
        return fragment;
    }

    public static Fragment getJianKeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.YouXuan_JianKeFragment).navigation();
        return fragment;
    }

    public static Fragment getYouXuanFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.YouXuan_YouXuanFragment).navigation();
        return fragment;
    }

    public static Fragment getSiXueFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.YouXuan_SiXueFragment).navigation();
        return fragment;
    }

    public static Fragment getSchoolFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPathUtils.YouXuan_SchoolFragment).navigation();
        return fragment;
    }
}
