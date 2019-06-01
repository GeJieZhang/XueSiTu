package com.lib.fastkit.db.shared_prefrences;

import android.content.Context;

import com.lib.fastkit.db.shared_prefrences.impl.UserCacheImpl;
import com.lib.fastkit.db.shared_prefrences.interfaces.UserCacheInterface;


/**
 * Desc : 数据管理
 * Author : Lauzy
 * Date : 2018/4/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SharedPreferenceManager {

    private final UserCacheInterface mCacheRepo;

    private volatile static SharedPreferenceManager INSTANCE;

    private SharedPreferenceManager(Context context) {
        mCacheRepo = new UserCacheImpl(context);

    }

    public static SharedPreferenceManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SharedPreferenceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SharedPreferenceManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public UserCacheInterface getUserCache() {
        return mCacheRepo;
    }


}
