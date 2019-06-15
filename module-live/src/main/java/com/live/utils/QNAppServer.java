package com.live.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.live.R;
import com.live.bean.UpdateInfo;
import com.live.bean.UserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QNAppServer {
    /**
     * 设置推流画面尺寸，仅用于 Demo 测试，用户可以在创建七牛 APP 时设置该参数
     */
    public static final int STREAMING_WIDTH = 480;
    public static final int STREAMING_HEIGHT = 848;

    public static final String ADMIN_USER = "admin";

    private static final String TAG = "QNAppServer";
    private static final String APP_SERVER_ADDR = "https://api-demo.qnsdk.com";
    public static final String APP_ID = "e9c7uwtct";
    public static final String TEST_MODE_APP_ID = "d8dre8w1p";

    private static class QNAppServerHolder {
        private static final QNAppServer instance = new QNAppServer();
    }

    private QNAppServer() {
    }

    public static QNAppServer getInstance() {
        return QNAppServerHolder.instance;
    }


    private String str1 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:idSQxdXL86rRagHY37khuYoWpk0=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiJ6aGFuZ2ppZSIsImV4cGlyZUF0IjoxNTYwNjc1MjMyLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    private String str2 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:nNYBue5TXCambzmcZ6tNGlyVrhI=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiJ3c3QiLCJleHBpcmVBdCI6MTU2MDY3NTIzMiwicGVybWlzc2lvbiI6InVzZXIifQ==";

    public String requestRoomToken() {

        return str1;
    }




    public UserList getUserList(Context context, String roomName) {
        String url = APP_SERVER_ADDR + "/v1/rtc/users/app/" + getAppId(context) + "/room/" + roomName;
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(new SSLSocketFactoryCompat(), getTrustManager()).build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                UserList userList = new UserList();
                List<UserList.UsersBean> usersBeanList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject user = jsonArray.getJSONObject(i);
                    UserList.UsersBean usersBean = new UserList.UsersBean();
                    usersBean.setUserId(user.getString("userId"));
                    usersBeanList.add(usersBean);
                }
                userList.setUsers(usersBeanList);
                return userList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UpdateInfo getUpdateInfo() {
        String url = APP_SERVER_ADDR + "/v1/upgrade/app?appId=com.qiniu.droid.rtc.demo";

        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(new SSLSocketFactoryCompat(), getTrustManager()).build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.setAppID(jsonObject.getString(Config.APP_ID));
                updateInfo.setVersion(jsonObject.getInt(Config.VERSION));
                updateInfo.setDescription(jsonObject.getString(Config.DESCRIPTION));
                updateInfo.setDownloadURL(jsonObject.getString(Config.DOWNLOAD_URL));
                updateInfo.setCreateTime(jsonObject.getString(Config.CREATE_TIME));
                return updateInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager getTrustManager() {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            for (TrustManager tm : tmf.getTrustManagers()) {
                if (tm instanceof X509TrustManager) {
                    return (X509TrustManager) tm;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // This shall not happen
        return null;
    }

    public static String packageName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
        }
        return "";
    }

    private String getAppId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString(Config.APP_ID, APP_ID);
    }
}
