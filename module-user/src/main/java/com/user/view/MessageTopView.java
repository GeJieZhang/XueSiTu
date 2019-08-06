package com.user.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.user.R;

import java.util.ArrayList;
import java.util.List;

public class MessageTopView extends LinearLayout {


    private Context context;


    private String str[] = {"官方推送", "关联消息", "订单通知", "评论消息"};

    private int iconChecked[] = {R.mipmap.icon_push, R.mipmap.icon_examine, R.mipmap.icon_goods, R.mipmap.icon_comment};
    private int icon[] = {R.mipmap.icon_push_default, R.mipmap.icon_examine_default, R.mipmap.icon_goods_default, R.mipmap.icon_comment_default};
    public static final int GF = 0;
    public static final int GL = 1;
    public static final int DD = 2;
    public static final int PL = 3;

    public MessageTopView(Context context) {
        this(context, null);
    }

    public MessageTopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        initView();

        setChecked(0);
    }


    private List<ImageView> imageList = new ArrayList<>();

    private List<LinearLayout> linearLayoutsList = new ArrayList<>();


    private List<View> viewList = new ArrayList<>();

    private void initView() {
        imageList.clear();

        for (int i = 0; i < str.length; i++) {
            View root = LayoutInflater.from(context).inflate(R.layout.item_top_message, null);

            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            root.setLayoutParams(params);
            TextView tv_title = root.findViewById(R.id.tv_title);
            ImageView iv_image = root.findViewById(R.id.iv_image);
            LinearLayout lin_parent = root.findViewById(R.id.lin_parent);
            View v_point = root.findViewById(R.id.v_point);


            linearLayoutsList.add(lin_parent);
            imageList.add(iv_image);
            viewList.add(v_point);

            tv_title.setText(str[i]);
            iv_image.setImageResource(icon[i]);

            setClickEvent(lin_parent, i);

            addView(root);
        }

    }

    public void setClickEvent(LinearLayout lin_parent, final int i) {


        lin_parent.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                setChecked(i);
                switch (str[i]) {
                    case "官方推送": {

                        if (listener != null) {
                            listener.onClickTS();
                        }


                        break;
                    }

                    case "关联消息": {

                        if (listener != null) {
                            listener.onClickSH();
                        }
                        break;
                    }

                    case "订单通知": {

                        if (listener != null) {
                            listener.onClickDD();
                        }
                        break;
                    }

                    case "评论消息": {

                        if (listener != null) {
                            listener.onClickPL();
                        }
                        break;
                    }
                }
            }


        });

    }


    private void initIconUI() {
        for (int i = 0; i < imageList.size(); i++) {
            imageList.get(i).setImageResource(icon[i]);
        }

    }


    public void showPoint(int i) {
        viewList.get(i).setVisibility(VISIBLE);
    }

    public void hidePoint(int i) {
        viewList.get(i).setVisibility(GONE);
    }


    public void setChecked(int i) {
        initIconUI();
        imageList.get(i).setImageResource(iconChecked[i]);


    }

    private MessageTopViewListener listener;

    public void setMessageTopViewListener(MessageTopViewListener messageTopViewListener) {
        this.listener = messageTopViewListener;

    }

    public interface MessageTopViewListener {

        void onClickTS();

        void onClickSH();

        void onClickDD();

        void onClickPL();
    }


}
