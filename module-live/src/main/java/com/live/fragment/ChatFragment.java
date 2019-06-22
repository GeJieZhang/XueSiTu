package com.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.live.R;
import com.live.R2;
import com.live.bean.ChatBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatFragment extends BaseAppFragment {


    @BindView(R2.id.rv_chat)
    RecyclerView rvChat;

    private ChatAdapter chatAdapter;

    private List<ChatBean> chatList = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        chatList.add(new ChatBean("小明同学有话要说?", 0));
        chatList.add(new ChatBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561202333085&di=3c8d7207621db07ae4ca7434429e81f2&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201407%2F24%2F20140724190906_MCkXs.thumb.700_0.jpeg", 1));

        chatAdapter = new ChatAdapter(getContext(), chatList);

        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));

        rvChat.setAdapter(chatAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }


    public class ChatAdapter extends BaseAdapter<ChatBean> {

        public ChatAdapter(Context context, List<ChatBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_chat;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<ChatBean> mData) {

            if (mData.get(position).getType() == 0) {
                //纯文本

                holder.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
                holder.getView(R.id.iv_image).setVisibility(View.GONE);

                holder.setText(R.id.tv_msg, mData.get(position).getContent());

            }


            if (mData.get(position).getType() == 1) {
                //纯文本
                holder.getView(R.id.tv_msg).setVisibility(View.GONE);
                holder.getView(R.id.iv_image).setVisibility(View.VISIBLE);
                ImageView imageView = holder.getView(R.id.iv_image);

                Glide.with(getContext())
                        .load(mData.get(position).getContent())
                        .apply(GlideConfig.getRectangleOptions())
                        .into(imageView);


            }


        }
    }

}
