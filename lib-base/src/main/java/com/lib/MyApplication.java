package com.lib;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.lib.fastkit.utils.system.SystemUtil;
import com.lib.service.InitIntentService;
import com.lib.utls.application_deal.UIUtils;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.OkHttpEngine;

import com.lib.fastkit.utils.log.LogUtil;


import com.lib.utls.bugly.BuglyUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;


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


        BuglyUtil.init(this);
        //内存检测
        initLeakCanary();

        initEasemob(this);
        //X5内核
        initX5();


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


        HttpUtils.init(new OkHttpEngine(applicationInstance));


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
     * 初始化环信
     */
    private boolean isInit = false;

    private void initEasemob(Context mContext) {

        LogUtil.e("环信初始化开始");

        System.out.print("环信初始化开始");
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid, mContext);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(mContext.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }

        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(mContext, initOptions());
        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(SystemUtil.isDebuggable(getApplicationContext()));
        // 设置初始化已经完成
        isInit = true;

        LogUtil.e("环信初始化完毕");

        System.out.print("环信初始化完毕");
    }

    /**
     * SDK初始化的一些配置
     * 关于 EMOptions 可以参考官方的 API 文档
     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
     */
    private EMOptions initOptions() {

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        return options;
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

}