package com.lib.utls.glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lib.MyApplication;
import com.lib.base.R;

public class GlideConfig {


    public static RequestOptions getCircleOptions() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .transform(new GlideCircleTransform(MyApplication.getInstance()))
                //.override(400,400)	//指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                //.fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                //.circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                //.skipMemoryCache(true)	//跳过内存缓存
                //.diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
                //.diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
                //.diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
                //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }

    public static RequestOptions getRectangleOptions() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                //.transform(new GlideCircleTransform(MyApplication.getInstance()))
                //.override(400,400)	//指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                //.fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                //.circleCrop()//指定图片的缩放类型为centerCrop （圆形）不能和transform一起使用
                //.skipMemoryCache(true)	//跳过内存缓存
                //.diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
                //.diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
                //.diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
                //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)    //智能缓存
                ;

        return options;
    }

}
