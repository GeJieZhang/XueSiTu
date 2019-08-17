package com.live.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.lib.app.ApiUtils;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.json_deal.lib_mgson.MGson;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.pop.SmallZoomImagePopupUtils;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.bean.control.IMBean;

import com.live.utils.socket.IMSocketUtils;


import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class ChatFragment extends BaseAppFragment {


    @BindView(R2.id.rv_chat)
    RecyclerView rvChat;
    private ChatAdapter chatAdapter;

    private LinearLayoutManager linearLayoutManager;
    private IMBean imBean;

    private String roomName;

    private String userName;

    private String userPhone;

    private String userIcon;
    private Gson gson;
    //发送接收消息(所有人)
    public static final int ACTION_TYPE1 = 1;
    //学生申请语音(老师)
    public static final int ACTION_TYPE2 = 2;
    //学生申请视频(老师)
    public static final int ACTION_TYPE3 = 3;
    //老师对申请进行操作(语音，视频)(指定学生)
    public static final int ACTION_TYPE4 = 4;
    //学生加入(所有人)
    public static final int ACTION_TYPE5 = 5;
    //学生离开(所有人)
    public static final int ACTION_TYPE6 = 6;
    //直播间用户列表(所有人)
    public static final int ACTION_TYPE7 = 7;
    //学生取消视频，语音(系统)
    public static final int ACTION_TYPE8 = 8;
    //更新聊天室用户信息(所有人)
    public static final int ACTION_TYPE9 = 9;
    //手动获取直播间列表(系统)
    public static final int ACTION_TYPE10 = 10;

    //老师关闭学生的语音、视频(返回9、11)
    public static final int ACTION_TYPE11 = 11;

    //老师下课
    public static final int ACTION_TYPE12 = 12;

    //文字
    public static final int MESSAGE_TYPE1 = 1;
    //图片
    public static final int MESSAGE_TYPE2 = 2;

    @SuppressLint("ValidFragment")
    public ChatFragment(String roomName) {

        this.roomName = roomName;
    }


    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        userName = SharedPreferenceManager.getInstance(getActivity()).getUserCache().getUserName();
        userIcon = SharedPreferenceManager.getInstance(getActivity()).getUserCache().getUserHeadUrl();

        userPhone = SharedPreferenceManager.getInstance(getActivity()).getUserCache().getUserPhone();
        chatAdapter = new ChatAdapter(getContext(), MainRoomActivity.messageList);
        gson = new Gson();
        linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setAdapter(chatAdapter);


        if (chatAdapter.getItemCount() > 0) {
            rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        }


        showLog("===============监听长连接");

        MainRoomActivity.imSocketUtils.setIMSocketListener(imSocketListener);

        requestUserList();
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }


    //-----------------------------------------------------------------------------消息监听
    IMSocketUtils.IMSocketListener imSocketListener = new IMSocketUtils.IMSocketListener() {
        @Override
        public void onOpen() {

        }

        @Override
        public void onMessage(String json) {

            try {
                imBean = MGson.newGson().fromJson(json, IMBean.class);
            } catch (Exception e) {

                showLog(e.getMessage());

            }


            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    dealMessage();
                }
            });


        }


        @Override
        public void onClosed() {

        }

        @Override
        public void onClosing() {

        }

        @Override
        public void onFailure() {

        }
    };

    private void dealMessage() {
        int type = imBean.getActionType();
        switch (type) {
            case ACTION_TYPE1: {
                //发送接收消息


                MainRoomActivity.messageList.add(imBean);
                notifyAdapter();


                break;
            }

            case ACTION_TYPE2: {
                //学生申请语音


                MainRoomActivity.roomControlFragment.addRequest(imBean);
                MainRoomActivity.roomControlFragment.showRequestPopu();


                break;
            }

            case ACTION_TYPE3: {
                //学生申请视频

                MainRoomActivity.roomControlFragment.addRequest(imBean);
                MainRoomActivity.roomControlFragment.showRequestPopu();


                break;
            }
            case ACTION_TYPE4: {
                //老师对申请进行操作(语音，视频)

                int type4 = imBean.getObject().getType();

                if (type4 == 2) {
                    //语音

                    String action = imBean.getObject().getAction();

                    if (action.equals("1")) {
                        //同意
                        showToast("申请成功！");

                        if (listener != null) {
                            listener.onRequestVoiceSuccess();
                        }


                    }
                    if (action.equals("0")) {
                        //不同意
                        showToast("申请失败！");


                    }

                }

                if (type4 == 3) {
                    //视频
                    String action = imBean.getObject().getAction();

                    if (action.equals("1")) {
                        //同意
                        showToast("申请成功！");
                        if (listener != null) {
                            listener.onRequestCameraSuccess();
                        }

                    }
                    if (action.equals("0")) {
                        //不同意
                        showToast("申请失败！");
                    }
                }

                break;
            }

            case ACTION_TYPE5: {
                //学生加入

                if (listener != null) {
                    listener.onUserJoinRoom(imBean.getObject());
                }


                break;
            }

            case ACTION_TYPE6: {
                //学生离开
                if (listener != null) {
                    listener.onUserLeftRoom(imBean.getObject());
                }

                break;
            }

            case ACTION_TYPE7: {
                //直播间用户列表


                if (listener != null) {
                    listener.onGetUserList(imBean.getObject().getUserList());
                }


                break;
            }

            case ACTION_TYPE8: {
                //学生取消视频，语音


                break;
            }


            case ACTION_TYPE9: {
                //更新聊天室

                if (listener != null) {
                    listener.onUpdateUserInfo(imBean.getObject());
                }


                break;
            }
            case ACTION_TYPE11: {
                //更新指定学生的麦克风、摄像头状态

                if (listener != null) {
                    listener.onUpdateStudentVoiceCameraInfo(imBean.getObject());
                }


                break;
            }

        }
    }

    private void notifyAdapter() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter.notifyDataSetChanged();

                if (chatAdapter.getItemCount() > 0) {
                    rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                }
            }
        });
    }


    //-----------------------------------------------------------------------------适配器

    public class ChatAdapter extends BaseAdapter<IMBean> {

        public ChatAdapter(Context context, List<IMBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_chat;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, int position, List<IMBean> mData) {


            final ImageView iv_image = holder.getView(R.id.iv_image);

            ImageView iv_head = holder.getView(R.id.iv_head);


            final IMBean.ObjectBean objectBean = mData.get(position).getObject();
            int messageType = objectBean.getType();
            if (messageType == MESSAGE_TYPE1) {
                //纯文本
                holder.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
                holder.getView(R.id.f_image).setVisibility(View.GONE);
                TextView textView = holder.getView(R.id.tv_msg);
                textView.setText(objectBean.getMessage());

            }


            if (messageType == MESSAGE_TYPE2) {
                holder.getView(R.id.tv_msg).setVisibility(View.GONE);
                holder.getView(R.id.f_image).setVisibility(View.VISIBLE);
                String path = mData.get(position).getObject().getMessage();

                View v_bg = holder.getView(R.id.v_bg);

                TextView tv_progress = holder.getView(R.id.tv_progress);

                if (uploadMap.get(path) == null) {

                    v_bg.setVisibility(View.GONE);
                    tv_progress.setVisibility(View.GONE);

                    Glide.with(getContext())
                            .load(objectBean.getMessage())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(iv_image);

                } else {


                    v_bg.setVisibility(View.VISIBLE);
                    tv_progress.setVisibility(View.VISIBLE);
                    uploadMap.put(objectBean.getMessage(), tv_progress);
                    Glide.with(getContext())
                            .load(objectBean.getMessage())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(iv_image);
                    //图片正在上传中

                }


//                for (Map.Entry<String, String> entry : uploadMap.entrySet()) {
//                    String mapKey = entry.getKey();
//                    String mapValue = entry.getValue();
//
//                }


                iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (objectBean.getMessage() != null && !objectBean.getMessage().equals("")) {
                            SmallZoomImagePopupUtils smallZoomImagePopupUtils = new SmallZoomImagePopupUtils(getContext());

                            smallZoomImagePopupUtils.setZoomImage(objectBean.getMessage());
                            smallZoomImagePopupUtils.showAnswerPopuPopu(iv_image);
                        }

                    }
                });


            }

            holder.setText(R.id.tv_userName, objectBean.getUserName());


            Glide.with(getContext())
                    .load(objectBean.getUserIcon())
                    .apply(GlideConfig.getCircleOptions())
                    .into(iv_head);


            /**
             * 设置最后一条消息距离底部的距离
             */
            LinearLayout linearLayout = holder.getView(R.id.lin_item);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            if (position > 0 && position == MainRoomActivity.messageList.size() - 1)

            {


                params.bottomMargin = DisplayUtil.dip2px(getContext(), 40);

            } else

            {

                params.bottomMargin = DisplayUtil.dip2px(getContext(), 0);
            }

        }


        @Override
        public int getEmptyLayoutId() {
            return R.layout.chat_empty;
        }

    }


    //------------------------------------------------------------------------------发送消息


    private Map<String, TextView> uploadMap = new HashMap<>();


    /**
     * 发送图片消息
     *
     * @param compressPath
     */
    public void sendImageMessage(String compressPath) {
        /**
         * 发送一条假的消息先占个位置
         */

        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE1);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setMessageId(TimeUtils.getNowTimestamp() + "");
        objectBean.setUserIcon(userIcon);
        objectBean.setRoomName(roomName);
        objectBean.setType(MESSAGE_TYPE2);
        objectBean.setMessage(compressPath);
        objectBean.setUserName(userName);
        imBean.setObject(objectBean);
        MainRoomActivity.messageList.add(imBean);


        uploadMap.put(compressPath, new TextView(getActivity()));
        notifyAdapter();

        requestUploadImage(compressPath);
    }


    /**
     * 发送文字消息
     *
     * @param content
     */
    public void sendTextMessage(String content) {


        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE1);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setMessageId(TimeUtils.getNowTimestamp() + "");
        objectBean.setUserIcon(userIcon);
        objectBean.setRoomName(roomName);
        objectBean.setType(MESSAGE_TYPE1);
        objectBean.setMessage(content);
        objectBean.setUserName(userName);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }


    //-----------------------------------------------------------------------------长连接控制


    /**
     * 请求用户列表
     */
    public void requestUserList() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE10);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setRoomName(roomName);
        objectBean.setUserPhone(userPhone);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);


        MainRoomActivity.imSocketUtils.sendMessage(json);
    }

    /**
     * 学生请求打开麦克风
     */
    public void requestOpenVoice() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE2);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setActionTag(TimeUtils.getNowTimestamp() + "");
        objectBean.setUserName(userName);
        objectBean.setUserIcon(userIcon);
        objectBean.setRoomName(roomName);
        objectBean.setUserPhone(userPhone);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);


        MainRoomActivity.imSocketUtils.sendMessage(json);
    }


    /**
     * 学生关闭麦克风
     */
    public void requestCloseVoice() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE8);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setUserName(userName);
        objectBean.setRoomName(roomName);
        objectBean.setUserPhone(userPhone);
        objectBean.setType(2);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);


        MainRoomActivity.imSocketUtils.sendMessage(json);
    }

    /**
     * 学生请求打开摄像头
     */
    public void requestOpenCamera() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE3);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setActionTag(TimeUtils.getNowTimestamp() + "");
        objectBean.setUserName(userName);
        objectBean.setUserIcon(userIcon);
        objectBean.setRoomName(roomName);
        objectBean.setUserPhone(userPhone);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }


    /**
     * 学生关闭麦克风
     */
    public void requestClocseCamera() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE8);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setUserName(userName);
        objectBean.setRoomName(roomName);
        objectBean.setUserPhone(userPhone);
        objectBean.setType(3);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);


        MainRoomActivity.imSocketUtils.sendMessage(json);
    }

    /**
     * 老师处理是否打开麦克风
     */
    public void requestBackOpenVoice(boolean b, String studentPhone) {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE4);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();

        objectBean.setUserPhone(studentPhone);
        objectBean.setType(2);
        if (b) {
            objectBean.setAction("1");
        } else {
            objectBean.setAction("0");
        }

        objectBean.setRoomName(roomName);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }

    /**
     * 老师处理是否打开摄像头
     */
    public void requestBackOpenCamera(boolean b, String studentPhone) {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE4);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();

        objectBean.setUserPhone(studentPhone);
        objectBean.setType(3);
        if (b) {
            objectBean.setAction("1");
        } else {
            objectBean.setAction("0");
        }

        objectBean.setRoomName(roomName);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }


    /**
     * @param b            true打开false关闭
     * @param studentPhone
     * @param type         2麦克风 3视频
     */
    public void requestCloseOpenCameraVoice(boolean b, String studentPhone, int type) {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE11);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();

        objectBean.setUserPhone(studentPhone);
        objectBean.setType(type);
        if (b) {
            objectBean.setAction("1");
        } else {
            objectBean.setAction("0");
        }

        objectBean.setRoomName(roomName);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }


    /**
     * 老师下课
     */
    public void requestOverClass() {
        IMBean imBean = new IMBean();
        imBean.setActionType(ACTION_TYPE12);
        IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
        objectBean.setRoomName(roomName);
        imBean.setObject(objectBean);
        String json = MGson.newGson().toJson(imBean);
        showLog(json);
        MainRoomActivity.imSocketUtils.sendMessage(json);
    }

    /**
     * 上传图片
     *
     * @param compressPath
     */
    private void requestUploadImage(final String compressPath) {


        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

                TextView tv_progress = uploadMap.get(compressPath);

                if (tv_progress != null) {
                    tv_progress.setText(progress + "%");
                }


            }

            @Override
            public void onSuccess(String s) {


                String urlHead = SharedPreferenceManager.getInstance(getContext()).getUserCache().getQiNiuUrl();

                IMBean imBean = new IMBean();
                imBean.setActionType(ACTION_TYPE1);
                IMBean.ObjectBean objectBean = new IMBean.ObjectBean();
                objectBean.setMessageId(TimeUtils.getNowTimestamp() + "");
                objectBean.setUserIcon(userIcon);
                objectBean.setRoomName(roomName);
                objectBean.setType(MESSAGE_TYPE2);
                objectBean.setMessage(urlHead + s);
                objectBean.setUserName(userName);
                imBean.setObject(objectBean);
                String json = MGson.newGson().toJson(imBean);
                showLog(json);
                MainRoomActivity.imSocketUtils.sendMessage(json);
                uploadMap.remove(compressPath);

                notifyAdapter();


            }

            @Override
            public void onError(String e) {


            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(getActivity()).getUserCache().getQiNiuToken());


    }

    private ChatFragmentListener listener;

    public void setChatFragmentListener(ChatFragmentListener chatFragmentListener) {

        this.listener = chatFragmentListener;

    }

    public interface ChatFragmentListener {
        void onRequestVoiceSuccess();

        void onRequestCameraSuccess();


        void onGetUserList(List<IMBean.UserListBean> userList);


        void onUserJoinRoom(IMBean.ObjectBean user);

        void onUserLeftRoom(IMBean.ObjectBean user);

        void onUpdateUserInfo(IMBean.ObjectBean user);

        void onUpdateStudentVoiceCameraInfo(IMBean.ObjectBean user);

    }

}
