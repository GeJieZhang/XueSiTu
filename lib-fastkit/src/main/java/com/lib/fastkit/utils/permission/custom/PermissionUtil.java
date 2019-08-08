/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.fastkit.utils.permission.custom;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;


/**
 * ================================================
 * 权限请求工具类
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.9">PermissionUtil wiki 官方文档</a>
 * Created by JessYan on 17/10/2016 10:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class PermissionUtil {

    public static final String TAG = "Permission";

    public static RxPermissions rxPermissions;

    public static PermissionUtil permissionUtil;

    private static Activity mactivity;

    public static PermissionUtil getInstance(Activity activity) {

        if (permissionUtil == null) {
            rxPermissions = new RxPermissions(activity);
            permissionUtil = new PermissionUtil();

            mactivity = activity;

        }

        return permissionUtil;

    }

    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param
         */
        void onRequestPermissionFailure();
//
//        /**
//         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
//         *
//         * @param permissions 请求失败的权限名
//         */
//        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }


    public static void requestPermission(RxPermissions rxPermissions, final RequestPermission requestPermission, String... permissions) {
        if (permissions == null || permissions.length == 0) return;
        if (requestPermission == null) return;
        if (rxPermissions == null) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }

        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过,则开始申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new PermissionsSubscriber<List<Permission>>() {
                        @Override
                        public void onNext(@NonNull List<Permission> permissions) {
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        Timber.tag(TAG).d("Request permissions failure");


                                        requestPermission.onRequestPermissionFailure();
                                        onRequestPermissionFailure(Arrays.asList(p.name));
                                        return;
                                    } else {
                                        Timber.tag(TAG).d("Request permissions failure with ask never again");

                                        requestPermission.onRequestPermissionFailure();
                                        onRequestPermissionFailure(Arrays.asList(p.name));
                                        return;
                                    }
                                }
                            }
                            Timber.tag(TAG).d("Request permissions success");
                            requestPermission.onRequestPermissionSuccess();
                        }


                    });
        }

    }


    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(RequestPermission requestPermission) {
        requestPermission(rxPermissions, requestPermission, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    /**
     * 请求直播需要的权限
     *
     * @param requestPermission
     */
    public static void externalZhiBo(RequestPermission requestPermission) {
        requestPermission(rxPermissions, requestPermission, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.MODIFY_AUDIO_SETTINGS
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.CAMERA
                , Manifest.permission.WAKE_LOCK
        );
    }


    /**
     * 支付
     *
     * @param requestPermission
     */
    public static void externalZhiFu(RequestPermission requestPermission) {
        requestPermission(rxPermissions, requestPermission, Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE

        );
    }


    /**
     * 语音
     *
     * @param requestPermission
     */

    public static void externalAudio(RequestPermission requestPermission) {
        requestPermission(rxPermissions, requestPermission, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.MODIFY_AUDIO_SETTINGS
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.CAMERA
                , Manifest.permission.WAKE_LOCK
        );
    }


    private static void onRequestPermissionFailure(List<String> strings) {


        for (String str : strings) {


            switch (str) {
                case "android.permission.WRITE_EXTERNAL_STORAGE": {


                    Toast.makeText(mactivity, "禁止访问存储权限，部分功能将无法使用！", Toast.LENGTH_SHORT).show();


                    break;
                }

                case "android.permission.CAMERA": {


                    Toast.makeText(mactivity, "禁止访问摄像头，部分功能将无法使用！", Toast.LENGTH_SHORT).show();


                    break;
                }

                case "android.permission.RECORD_AUDIO": {


                    Toast.makeText(mactivity, "禁止访问录音，部分功能将无法使用！", Toast.LENGTH_SHORT).show();


                    break;
                }
            }

        }


    }


}

