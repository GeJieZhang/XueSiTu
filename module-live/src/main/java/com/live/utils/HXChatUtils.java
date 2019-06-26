package com.live.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMChatRoomManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.live.R;
import com.live.fragment.ChatFragment;
import com.live.utils.config.ChatConfig;

import java.util.ArrayList;
import java.util.List;

import static com.hyphenate.chat.EMClient.TAG;

public class HXChatUtils {


    /**
     * 登录方法
     */
    public static void signIn(String username, String password, final Activity activity) {

        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // 加载所有会话到内存
                        EMClient.getInstance().chatManager().loadAllConversations();
                        // 加载所有群组到内存，如果使用了群组的话
                        EMClient.getInstance().groupManager().loadAllGroups();
                        // 登录成功跳转界面

                        //Toast.makeText(activity, "登录环信成功!", Toast.LENGTH_LONG).show();
                        Log.e("======", "登录环信成功!");
                        joinChatRoom();

                    }
                });
            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                        /**
                         * 关于错误码可以参考官方api详细说明
                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                         */
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(activity,
                                        "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(activity,
                                        "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(activity,
                                        "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(activity,
                                        "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG)
                                        .show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(activity,
                                        "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(activity,
                                        "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(activity,
                                        "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG)
                                        .show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(activity,
                                        "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(activity,
                                        "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(activity,
                                        "ml_sign_in_failed code: " + i + ", message:" + s,
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 退出登录
     */
    public static void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    public static void joinChatRoom() {


        EMClient.getInstance().chatroomManager().joinChatRoom(ChatConfig.RoomId, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(EMChatRoom value) {
                EMLog.d(TAG, "join room success");

                Log.e("======", "加入房间成功!");
            }

            @Override
            public void onError(final int error, String errorMsg) {
                EMLog.d(TAG, "join room failure : " + error);
                Log.e("======", "加入房间失败!");
            }
        });
    }

    public static void leaveChatRoom() {
        EMClient.getInstance().chatroomManager().leaveChatRoom(ChatConfig.RoomId);
    }


    public static List<EMMessage> getMessgeHistory() {
        EMConversation conversations = EMClient.getInstance().chatManager().getConversation(ChatConfig.RoomId);

        List<EMMessage> messageList = conversations.getAllMessages();

        return messageList;
    }

    public static EMMessage sendMessage(String content, final ChatFragment.ChatAdapter chatAdapter, final Activity context) {


        EMMessage message = EMMessage.createTxtSendMessage(content, ChatConfig.RoomId);

        message.setChatType(EMMessage.ChatType.GroupChat);





        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });
                Log.e("===","1消息发送成功");
            }

            @Override
            public void onError(int i, String s) {

                Log.e("===","1消息发送失败");

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {




                Log.e("===","1消息进度：" + i);


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });


            }
        });
        EMClient.getInstance().chatManager().sendMessage(message);
        return message;
    }

    public static EMMessage sendImageMessage(String imagePath, final ChatFragment.ChatAdapter chatAdapter, final Activity context) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, ChatConfig.RoomId);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.GroupChat);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });
                Log.e("===","1消息发送成功");
            }

            @Override
            public void onError(int i, String s) {

                Log.e("===","1消息发送失败");

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {




                Log.e("===","1消息进度：" + i);


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });


            }
        });




        EMClient.getInstance().chatManager().sendMessage(message);

        return message;
    }


}
