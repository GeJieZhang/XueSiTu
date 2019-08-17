package com.lib.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lib.base.BuildConfig;
import com.lib.base.R;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.ui.activity.kit.BaseActivity;


import timber.log.Timber;

/**
 * 继承BaseActivity
 * 方便日后切换使用其他的通用Activity
 */
public abstract class BaseAppActivity extends BaseActivity {

    protected abstract void onCreateView();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

//    @Override
//    protected int setStatusBarColor() {
//        return R.color.colorPrimaryDark;
//    }

}
