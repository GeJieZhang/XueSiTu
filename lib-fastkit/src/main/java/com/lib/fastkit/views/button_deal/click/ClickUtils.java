package com.lib.fastkit.views.button_deal.click;

public class ClickUtils {
    private static long truetimeD = 500;
    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return truetimeD >= timeD;
    }
}
