package com.lib.fastkit.db.shared_prefrences.interfaces;

/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface UserCacheInterface extends BaseCacheInterface {

    void setUserName(String name);

    String getUserName();


    void setIsLogin(boolean b);

    boolean getIsLogin();
}
