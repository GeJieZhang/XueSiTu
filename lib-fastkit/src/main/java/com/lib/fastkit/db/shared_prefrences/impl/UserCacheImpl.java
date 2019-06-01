package com.lib.fastkit.db.shared_prefrences.impl;

import android.content.Context;

import com.lib.fastkit.db.shared_prefrences.interfaces.UserCacheInterface;


/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class UserCacheImpl extends SharedPreferenceModel implements UserCacheInterface {

    private static final String FILE_NAME = "USER";

    private static final String Key_User_Name = "UserName";

    public UserCacheImpl(Context context) {
        super(context, FILE_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public void setUserName(String name) {

        put(Key_User_Name, name);

    }

    @Override
    public String getUserName() {
        return getString(Key_User_Name);
    }
}
