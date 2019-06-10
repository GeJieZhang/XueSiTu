package com.xuesitu.bugly;

import android.content.Context;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.xuesitu.activity.MainActivity;

public class BuglyUtil {

    /**
     * 初始化SDK
     */
    public static void init(Context context) {
        // true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        Beta.autoCheckUpgrade = false;
        // 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Bugly.init(context, "8299478ab4", true);
    }

    /**
     * 静默检查更新，并弹窗
     */
    public static void checkUpdate() {
        /**
         * @param isManual  用户手动点击检查，非用户点击操作请传false
         * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
         */
        Beta.checkUpgrade(false, false);
    }
}