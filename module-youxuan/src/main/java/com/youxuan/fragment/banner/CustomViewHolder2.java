package com.youxuan.fragment.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lib.base.R;
import com.lib.bean.CustomData;
import com.lib.utls.glide.GlideConfig;
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


        Glide.with(context)
                .load(data.getUrl())
                .apply(GlideConfig.getRoundOptions(10))
                .into(image1);
    }
}
