
package com.lib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.alibaba.android.arouter.launcher.ARouter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lib.app.EventBusTagUtils;
import com.lib.base.R;
import com.lib.bean.Event;
import com.lib.bean.PushBean;
import com.lib.bean.PushDetailBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.json_deal.lib_mgson.MGson;
import com.lib.fastkit.utils.system.SystemUtil;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.service.InitIntentService;
import com.lib.utls.application_deal.UIUtils;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.OkHttpEngine;

import com.lib.fastkit.utils.log.LogUtil;


import com.lib.utls.bugly.BuglyUtil;
import com.lib.utls.pop.PushPopupUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;


import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

public class MyApplication extends Application {

    //==============================================================================================
    //====================================================================================系统开关====
    //==============================================================================================
    private static MyApplication applicationInstance;
    //是否开启日志
    private boolean isLog = true;
    //是否开启内存检测
    private boolean isLeakCanary = true;


    private String TAG = "=======推送";

    public static String url = "http://192.168.0.117:8081/api";
    public static String urlHtml = "http://192.168.0.101:8080/";


    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;

        //组件化开发
        initRouter();
        //网络请求
        initHttp();
        //Timber日志
        initTimber();

        //X5内核
        initX5();

        BuglyUtil.init(this);
        //内存检测
        //initLeakCanary();


        initYouMeng();


    }


    /**
     * 初始化友盟
     */
    private void initYouMeng() {

        /**
         * 友盟(分享，推送)
         */
        UMConfigure.init(this, "5d3e56863fc195e682000cb2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "97110bcafcc2b47a2377bb4390bac6b7");
        /**
         * 小米，华为，魅族，Oppo,Vivo(推送)
         */
        MiPushRegistar.register(this, "5d3e56863fc195e682000cb2", "5d3e56863fc195e682000cb2");


        /**
         * QQ,WeiXing,微博(分享)
         */
        PlatformConfig.setWeixin("wx2747c1e8b040c4d1", "d07fa072e26e1e8c32701e8759fea609");
        PlatformConfig.setSinaWeibo("2489690608", "c54ead966302cf43d9242dcdcd6f8c34", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1105126608", "I7rMc2xTW9BwP9OD");

        initPush();


    }


    /**
     * 初始化推送
     */
    private void initPush() {


        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.e(TAG, "注册成功：deviceToken：-------->  " + deviceToken);

                SharedPreferenceManager.getInstance(MyApplication.this).getUserCache().setYouMengPushToken(deviceToken);


            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {


                String json = new Gson().toJson(msg);
                Log.e(TAG, "收到消息default:" + json);


                try {
                    PushBean pushBean = MGson.newGson().fromJson(json, PushBean.class);
                    String jsonTicker = pushBean.getA().getNameValuePairs().getBody().getNameValuePairs().getTicker();

                    PushDetailBean pushDetailBean = MGson.newGson().fromJson(jsonTicker, PushDetailBean.class);

                    if (pushDetailBean.getType() == 1) {
                        //答题类推送

                        EventBus.getDefault().post(new Event<>(1, pushDetailBean), EventBusTagUtils.MyApplication);


                    } else if (pushDetailBean.getType() == 2) {
                        //答题类推送

                        EventBus.getDefault().post(new Event<>(2, pushDetailBean), EventBusTagUtils.MyApplication);


                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }


                return super.getNotification(context, msg);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Log.e(TAG, "click");
            }

        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    private void initX5() {
        Intent intent = new Intent(this, InitIntentService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }


    }

    /**
     * 初始化内存检测
     */
    private void initLeakCanary() {
        if (isLeakCanary) {


            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    /**
     * 初始化Timber日志
     */
    private void initTimber() {

        if (isLog) {

            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(SystemUtil.isDebuggable(getApplicationContext()));
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


                LogUtil.e("初始化腾讯X5浏览器内核完成!");
            }

            @Override
            public void onCoreInitFinished() {


            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    /**
     * 查询HotFix补丁
     */
    private void queryHotFix() {
        //SophixManager.getInstance().queryAndLoadNewPatch();
    }

    /**
     * 得到Application对象
     *
     * @return
     */
    public static Application getInstance() {
        return applicationInstance;
    }

    /**
     * 初始化网络请求
     */
    private void initHttp() {


        HttpUtils.init(new OkHttpEngine(applicationInstance), url);


    }


    /**
     * 初始化阿里组件化开发
     *
     * @param
     */
    private void initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (UIUtils.isApkInDebug(applicationInstance)) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(applicationInstance);

    }

    /**
     * 初始化MultiDex
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);

        // initHotFix();
    }

    /**
     * 阿里热修复初始化
     */
//    private void initHotFix() {
//        String appVersion = "1.0.0";
//        try {
//            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            appVersion = "1.0.0";
//        }
//
//
//        Timber.d("版本号=" + appVersion);
//        SophixManager.getInstance().setContext(this)
//                .setAppVersion(appVersion)
//                .setAesKey(null)
//                .setEnableDebug(true)
//                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                    @Override
//                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
//                        // 补丁加载回调通知
//                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
//                            // 表明补丁加载成功
//                            // Toast.makeText(getApplicationContext(), "补丁加载成功,以为您修复Bug", Toast.LENGTH_SHORT).show();
//
//                            Timber.d("补丁加载成功" + "Code=" + code);
//
//                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
//                            //Toast.makeText(getApplicationContext(), "补丁生效需要重启", Toast.LENGTH_SHORT).show();
//                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
//                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
//
//                        } else if (code == PatchStatus.CODE_DOWNLOAD_SUCCESS) {
//                            Timber.d("补丁加载成功" + "Code=" + code);
//                        } else {
//
//                            Timber.d("补丁加载成功" + "Code=" + code);
//                        }
//
//                    }
//
//
//                }).initialize();
//
//    }

    /**
     * 阿里热修复提示框
     */
//    private void showFixDialog() {
//        new MaterialDialog.Builder(applicationInstance)
//                .title("补丁修复")
//                .content("请重新启动应用")
//                .positiveText("确定")
//                .negativeText("取消")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                        // TODO
//                        restartApp();
//
//                    }
//                })
//
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                        // TODO
//
//                    }
//                })
//                .build()
//                .show();
//    }

    /**
     * 重启应用
     */
    public void restartApp() {
        final Intent intent = applicationInstance.getPackageManager()
                .getLaunchIntentForPackage(applicationInstance.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationInstance.startActivity(intent);
    }


    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid, Context mContext) {
        String processName = null;
        ActivityManager activityManager =
                (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info =
                    (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }


    /**
     * 查看当前再栈顶的Activity
     */
    public void getTopStackActivity() {

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.e("current", "pkg:" + cn.getPackageName());
        Log.e("currentclass", "cls:" + cn.getClassName());
        if (!cn.getClassName().equals("当前显示的class名")) {
            //跳转操作
        } else {
            //不跳转
        }
    }

}