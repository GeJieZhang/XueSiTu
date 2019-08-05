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
    private static final String Key_Is_Login = "IsLogin";
    private static final String Key_Token = "Token";
    private static final String Key_Identity = "Identity";
    private static final String Key_Url = "Url";
    private static final String Key_Phone = "Phone";
    private static final String Key_Qi_Niu_Token = "QiNiuToken";
    private static final String Key_Qi_Niu_Url = "QiNiuUrl";
    private static final String Key_You_Meng_Token = "YouMengToken";

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

    @Override
    public void setIsLogin(boolean b) {
        put(Key_Is_Login, b);
    }

    @Override
    public boolean getIsLogin() {
        return getBoolean(Key_Is_Login);
    }

    @Override
    public void setUserToken(String token) {
        put(Key_Token, token);
    }

    @Override
    public String getUserToken() {
        return getString(Key_Token);
    }

    @Override
    public void setUserIdentity(String identity) {


        put(Key_Identity, identity);
    }

    @Override
    public String getUserIdentity() {
        return getString(Key_Identity);
    }

    @Override
    public void setUserHeadUrl(String url) {
        put(Key_Url, url);
    }

    @Override
    public String getUserHeadUrl() {
        return getString(Key_Url);
    }

    @Override
    public void setUserPhone(String phone) {
        put(Key_Phone, phone);
    }

    @Override
    public String getUserPhone() {
        return getString(Key_Phone);
    }

    @Override
    public void setQiNiuToken(String phone) {
        put(Key_Qi_Niu_Token, phone);
    }

    @Override
    public String getQiNiuToken() {
        return getString(Key_Qi_Niu_Token);
    }

    @Override
    public void setQiNiuUrl(String url) {
        put(Key_Qi_Niu_Url, url);
    }

    @Override
    public String getQiNiuUrl() {
        return getString(Key_Qi_Niu_Url);
    }

    @Override
    public void setYouMengPushToken(String token) {
        put(Key_You_Meng_Token, token);
    }

    @Override
    public String getYouMengPushToken() {
        return getString(Key_You_Meng_Token);
    }
}
