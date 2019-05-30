package com.dayi.fragment.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.dayi.R;
import com.lib.bean.CustomData;

import com.ms.banner.holder.BannerViewHolder;


/**
 * Created by songwenchao
 * on 2018/5/17 0017.
 * <p>
 * 类名
 * 需要 --
 * 可以 --
 */
public class CustomViewHolder2 implements BannerViewHolder<CustomData> {


    private ImageView image1;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item2, null);
        image1 = view.findViewById(R.id.image1);


        return view;
    }

    @Override
    public void onBind(Context context, int position, CustomData data) {
        image1.setImageResource(R.mipmap.banner_1);


       // LogUtil.e(data.getName());
    }
}
