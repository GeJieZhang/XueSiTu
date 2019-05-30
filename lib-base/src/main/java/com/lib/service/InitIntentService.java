package com.lib.service;

import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.MyApplication;
import com.lib.base.R;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.OkHttpEngine;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.utls.application_deal.UIUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by 卖火柴的小女孩 - Jc on 2018/8/8.
 */

public class InitIntentService extends IntentService {

    private static final String ACTION_INIT = "com.lib.service.InitIntentService";

    //是否开启日志
    private boolean isLog = true;
    //是否开启内存检测
    private boolean isLeakCanary = true;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public InitIntentService() {
        super("InitIntentService");
    }

    public static void start(Application application) {
        Intent intent = new Intent(application, InitIntentService.class);
        intent.setAction(ACTION_INIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.startForegroundService(intent);
        } else {
            application.startService(intent);
        }


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && ACTION_INIT.equals(intent.getAction())) {


            //网络请求
            initHttp(MyApplication.getInstance());

            //阿里热修复
            //queryHotFix();

            //腾讯浏览器内核
            initWebViewX5();

            //Timber日志
            initTimber();

            //内存检测
            initLeakCanary(MyApplication.getInstance());
        }
    }


    /**
     * 初始化内存检测
     */
    private void initLeakCanary(Application mApplication) {
        if (isLeakCanary) {


            if (LeakCanary.isInAnalyzerProcess(mApplication)) {
                return;
            }
            LeakCanary.install(mApplication);
        }
    }

    /**
     * 初始化Timber日志
     */
    private void initTimber() {

        if (isLog) {

            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
        }
    }

    /**
     * 初始化腾讯X5浏览器内核
     */
    private void initWebViewX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

                LogUtil.e("x5内核加载成功!");
            }

            @Override
            public void onCoreInitFinished() {


            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    /**
     * 初始化网络请求
     */
    private void initHttp(Application mApplication) {


        HttpUtils.init(new OkHttpEngine(mApplication));


    }


}