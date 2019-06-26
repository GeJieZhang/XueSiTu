package com.live.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.live.R;
import com.live.R2;
import com.live.utils.HXChatUtils;
import com.live.utils.hx.HXTextUtils;


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


        if (chatAdapter.getItemCount() > 0) {
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatAdapter.notifyDataSetChanged();

                    if (chatAdapter.getItemCount() > 0) {
                        rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                    }
                }
            });


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

            //messageList.indexOf(messages);
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

            if (position > 0 && position == messageList.size() - 1) {

                LinearLayout linearLayout = holder.getView(R.id.lin_item);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                params.bottomMargin = DisplayUtil.dip2px(getContext(), 40);

            }


            if (message.getType() == EMMessage.Type.TXT) {
                //纯文本

                holder.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
                holder.getView(R.id.f_image).setVisibility(View.GONE);

                TextView textView = holder.getView(R.id.tv_msg);
                Spannable span = HXTextUtils.getSmiledText(getContext(), message);
                textView.setText(span, TextView.BufferType.SPANNABLE);


                if (message.direct() == EMMessage.Direct.SEND) {
                    switch (message.status()) {
                        case SUCCESS:


                            pb.setVisibility(View.GONE);
                            break;
                        case FAIL:
                            pb.setVisibility(View.GONE);

                            iv_err.setVisibility(View.VISIBLE);

                            break;
                        case CREATE:
                            break;
                        case INPROGRESS:
                            break;
                    }


                    Log.e("===", message.status() + "");
                    Log.e("===", message.progress() + "");

                }


                if (message.direct() == EMMessage.Direct.RECEIVE) {

                    pb.setVisibility(View.GONE);

                }
            }


            if (message.getType() == EMMessage.Type.IMAGE) {

                EMImageMessageBody emImageMessageBody = (EMImageMessageBody) message.getBody();
                //纯文本
                holder.getView(R.id.tv_msg).setVisibility(View.GONE);
                holder.getView(R.id.f_image).setVisibility(View.VISIBLE);
                ImageView imageView = holder.getView(R.id.iv_image);

                tv_progress.setText(message.progress() + "%");
                if (message.direct() == EMMessage.Direct.SEND) {


                    switch (message.status()) {
                        case SUCCESS:


                            pb.setVisibility(View.GONE);
                            tv_progress.setVisibility(View.GONE);
                            v_bg.setVisibility(View.GONE);
                            pb.setVisibility(View.GONE);
                            break;
                        case FAIL:
                            pb.setVisibility(View.GONE);
                            tv_progress.setVisibility(View.GONE);
                            v_bg.setVisibility(View.GONE);
                            pb.setVisibility(View.GONE);

                            iv_err.setVisibility(View.VISIBLE);

                            break;
                        case CREATE:
                            break;
                        case INPROGRESS:
                            break;
                    }


                    Glide.with(getContext())
                            .load(emImageMessageBody.getLocalUrl())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(imageView);
                }


                if (message.direct() == EMMessage.Direct.RECEIVE) {


                    pb.setVisibility(View.GONE);
                    tv_progress.setVisibility(View.GONE);
                    v_bg.setVisibility(View.GONE);

                    Glide.with(getContext())
                            .load(emImageMessageBody.getRemoteUrl())
                            .apply(GlideConfig.getRectangleOptions())
                            .into(imageView);
                }
            }


        }


        @Override
        public int getEmptyLayoutId() {
            return R.layout.chat_empty;
        }
    }


    //------------------------------------------------------------------------------EventBus消息

    @Subscriber(tag = EventBusTagUtils.RoomControlFragment)
    public void fromRoomControlFragment(Event event) {
        switch (event.getEventCode()) {
            case 1: {
                String content = (String) event.getData();


                EMMessage message = HXChatUtils.sendMessage(content, chatAdapter, getActivity());


                messageList.add(message);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();

                        if (chatAdapter.getItemCount() > 0) {
                            rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                        }
                    }
                });


                break;
            }
            case 2: {
                String imagePath = (String) event.getData();

                EMMessage message = HXChatUtils.sendImageMessage(imagePath, chatAdapter, getActivity());

                messageList.add(message);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();

                        if (chatAdapter.getItemCount() > 0) {
                            rvChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                        }
                    }
                });
                break;
            }
        }

    }

}
