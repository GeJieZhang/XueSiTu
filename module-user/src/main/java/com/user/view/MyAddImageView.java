package com.user.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lib.utls.glide.GlideConfig;
import com.luck.picture.lib.tools.ScreenUtils;
import com.umeng.commonsdk.debug.I;
import com.user.R;

import java.util.zip.Inflater;

public class MyAddImageView extends LinearLayout {

    private Context context;
    private View root;

    public MyAddImageView(Context context) {
        this(context, null);
    }

    public MyAddImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAddImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        initView();
    }


    private CardView card;

    private ImageView iv_bg, iv_delete, iv_add;
    private LinearLayout lin_parent;

    private void initView() {
        root = LayoutInflater.from(context).inflate(R.layout.include_add_image, this);
        lin_parent = root.findViewById(R.id.lin_parent);

        int margin = ScreenUtils.dip2px(context, 16);

        int screenWidth = ScreenUtils.getScreenWidth(getContext()) - margin * 2;

        int width = screenWidth / 3;

        LinearLayout.LayoutParams params = (LayoutParams) lin_parent.getLayoutParams();
        params.width = width;
        lin_parent.setLayoutParams(params);


        card = root.findViewById(R.id.card);
        iv_add = root.findViewById(R.id.iv_add);
        iv_bg = root.findViewById(R.id.iv_bg);
        iv_delete = root.findViewById(R.id.iv_delete);
        iv_delete.setVisibility(INVISIBLE);
        card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickBg();
                }


            }
        });
        iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDelete();
                }
            }
        });

    }


    public void setImageUrl(String url) {

        if (url != null) {
            Glide.with(context)
                    .load(url)
                    .apply(GlideConfig.getRectangleOptions())
                    .into(iv_bg);
            iv_delete.setVisibility(VISIBLE);
            iv_bg.setVisibility(VISIBLE);

            iv_add.setVisibility(INVISIBLE);
        } else {
            iv_bg.setVisibility(INVISIBLE);
            iv_add.setVisibility(VISIBLE);
            iv_delete.setVisibility(INVISIBLE);

        }


    }


    private MyAddImageViewListener listener;

    public void setMyAddImageViewListener(MyAddImageViewListener myAddImageViewListener) {

        this.listener = myAddImageViewListener;

    }

    public interface MyAddImageViewListener {

        void onClickBg();

        void onClickDelete();

    }


}
