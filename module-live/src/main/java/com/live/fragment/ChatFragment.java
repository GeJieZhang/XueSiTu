package com.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.live.R;
import com.live.R2;
import com.live.utils.HXChatUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatFragment extends BaseAppFragment {


    @BindView(R2.id.rv_chat)
    RecyclerView rvChat;

    private ChatAdapter chatAdapter;

    //private List<ChatBean> chatList = new ArrayList<>();
    List<EMMessage> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        chatAdapter = new ChatAdapter(getContext(), messageList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setAdapter(chatAdapter);


        if (chatAdapter.getItemCount() > 2) {
            rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    //-----------------------------------------------------------------------------消息监听


    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息


            showLog("收到消息");
            messageList.addAll(messages);

            chatAdapter.notifyDataSetChanged();

            // EMImageMessageBody messageBody= (EMImageMessageBody) messages.get(0).getBody();

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息


            showLog("收到透传消息");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            showLog("收到已读回执");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执

            showLog("收到已送达回执");
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回

            showLog("消息被撤回");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            showLog("消息状态变动");

        }
    };


    //-----------------------------------------------------------------------------适配器

    public class ChatAdapter extends BaseAdapter<EMMessage> {

        public ChatAdapter(Context context, List<EMMessage> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_chat;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, int position, List<EMMessage> mData) {

            final ProgressBar pb = holder.getView(R.id.pb);
            final TextView tv_progress = holder.getView(R.id.tv_progress);
            final View v_bg = holder.getView(R.id.v_bg);

            final ImageView iv_err = holder.getView(R.id.iv_err);
            EMMessage message = mData.get(position);


            holder.setText(R.id.tv_name, message.getFrom());
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    pb.setVisibility(View.GONE);
                    tv_progress.setVisibility(View.GONE);
                    v_bg.setVisibility(View.GONE);

                    showLog("消息发送成功");
                }

                @Override
                public void onError(int i, String s) {
                    tv_progress.setVisibility(View.GONE);
                    iv_err.setVisibility(View.VISIBLE);
                    showLog("消息发送失败");
                }

                @Override
                public void onProgress(int i, String s) {


                    tv_progress.setText(s + "%");

                    showLog("消息进度：" + i);

                }
            });


            if (message.getType() == EMMessage.Type.TXT) {
                //纯文本

                holder.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
                holder.getView(R.id.f_image).setVisibility(View.GONE);

                holder.setText(R.id.tv_msg, message.getBody().toString());

                if (message.direct() == EMMessage.Direct.SEND) {


                }


                if (message.direct() == EMMessage.Direct.RECEIVE) {

                }
            }


            if (message.getType() == EMMessage.Type.IMAGE) {

                EMImageMessageBody emImageMessageBody = (EMImageMessageBody) message.getBody();
                //纯文本
                holder.getView(R.id.tv_msg).setVisibility(View.GONE);
                holder.getView(R.id.f_image).setVisibility(View.VISIBLE);
                ImageView imageView = holder.getView(R.id.iv_image);


                if (message.direct() == EMMessage.Direct.SEND) {
                    Glide.with(getContext())
                            .load(emImageMessageBody.getLocalUrl())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(imageView);
                }


                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    Glide.with(getContext())
                            .load(emImageMessageBody.getRemoteUrl())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(imageView);
                }
            }


        }
    }


    //------------------------------------------------------------------------------EventBus消息

    @Subscriber(tag = EventBusTagUtils.RoomControlFragment)
    public void fromRoomControlFragment(Event event) {
        switch (event.getEventCode()) {
            case 1: {
                String content = (String) event.getData();


                EMMessage message = HXChatUtils.sendMessage(content);

                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {


                        showLog("1消息发送成功");
                    }

                    @Override
                    public void onError(int i, String s) {

                        showLog("1消息发送失败");
                    }

                    @Override
                    public void onProgress(int i, String s) {




                        showLog("1消息进度：" + i);

                    }
                });

                messageList.add(message);
                chatAdapter.notifyDataSetChanged();

                break;
            }
            case 2: {
                String imagePath = (String) event.getData();

                EMMessage message = HXChatUtils.sendImageMessage(imagePath);

                messageList.add(message);
                chatAdapter.notifyDataSetChanged();
                break;
            }
        }

    }

}
