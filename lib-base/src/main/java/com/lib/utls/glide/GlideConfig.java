package com.lib.utls.glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lib.MyApplication;
import com.lib.base.R;

public class GlideConfig {

    /**
     * 圆形图片
     *
     * @return
     */
    public static RequestOptions getCircleOptions() {


        RequestOptions options = new RequestOptions()
                .bitmapTransform(new CircleCrop())
                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }

    /**
     * 圆角图片
     *
     * @param size
     * @return
     */
    public static RequestOptions getRoundOptions(int size) {
        RequestOptions options = new RequestOptions()

                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .bitmapTransform(new RoundedCorners(size))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }
    /**
     * 圆角视频
     *
     * @param size
     * @return
     */
    public static RequestOptions getRoundVdieoOptions(int size) {
        RequestOptions options = new RequestOptions()
                .frame(4000000)
                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .bitmapTransform(new RoundedCorners(size))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }

    /**
     * 方形图片
     *
     * @return
     */
    public static RequestOptions getRectangleOptions() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }

}
