package com.lib.fastkit.ui.base.control;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Activity管理类 
 *  
 */  
public class ActivityCollector {
  
    private static Stack<Activity> activityStack;
  
    private static ActivityCollector instance;
  
    private ActivityCollector() {
    }  
  
    /** 
     * 单一实例 
     */  
    public static ActivityCollector getInstance() {
        if (instance == null) {  
            instance = new ActivityCollector();
        }  
        return instance;  
    }  
  
    /** 
     * 添加Activity到堆栈 
     */  
    public  void addActivity(Activity activity) {
        if (activityStack == null) {  
            activityStack = new Stack<Activity>();  
        }  
        activityStack.add(activity);  
    }  
  
    /** 
     * 获取当前Activity（堆栈中最后一个压入的） 
     */  
    public Activity currentActivity() {  
        Activity activity = activityStack.lastElement();  
        return activity;  
    }  
  
    /** 
     * 结束当前Activity（堆栈中最后一个压入的） 
     */  
    public void finishActivity() {  
        Activity activity = activityStack.lastElement();  
        finishActivity(activity);  
    }  
  
    /** 
     * 结束指定的Activity 
     */  
    public void finishActivity(Activity activity) {  
        if (activity != null) {  
            activityStack.remove(activity);  
            activity.finish();  
            activity = null;  
        }  
    }  
  
    /** 
     * 结束指定类名的Activity 
     */  
    public void finishActivity(Class<?> cls) {  
        for (Activity activity : activityStack) {  
            if (activity.getClass().equals(cls)) {  
                finishActivity(activity);  
            }  
        }  
    }  
  
    /** 
     * 结束所有Activity 
     */  
    public void finishAllActivity() {  
        for (int i = 0, size = activityStack.size(); i < size; i++) {  
            if (null != activityStack.get(i)) {  
                activityStack.get(i).finish();  
            }  
        }  
        activityStack.clear();  
    }  
  
    /** 
     * 退出应用程序 
     */  
    @SuppressWarnings("deprecation")  
    public void AppExit(Context context) {
        try {  
            finishAllActivity();  
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());  
            System.exit(0);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
} 