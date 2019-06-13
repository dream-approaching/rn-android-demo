package com.lieying.petcat.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lieying.petcat.R;

import java.io.File;

public class GlideUtils {
    /**
     *  加载矩形图片文件到 imageview上
     * @param context 上下文
     * @param imageView  要加载到那个控件上
     * @param url  图片的url地址
     */
    public static void loadImageForUrl(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.default_pic)
                .error(R.drawable.bg_default)
                .priority(Priority.HIGH);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageForUrl(Context context, ImageView imageView, String url , boolean isLow) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.default_pic)
                .error(R.drawable.bg_default)
                .priority(isLow ? Priority.LOW : Priority.HIGH);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     *  加载矩形图片文件到 imageview上
     * @param context 上下文
     * @param imageView  要加载到那个控件上
     * @param url  图片的url地址
     */
    public static void loadImageForUrl(Context context, ImageView imageView, String url , int default_pic) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.default_pic)
                .error(default_pic)
                .priority(Priority.HIGH);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     *  加载图片文件到 imageview上
     * @param context 上下文
     * @param imageView  要加载到那个控件上
     * @param file  图片文件
     */
    public static void loadImageForFile(Context context, ImageView imageView, File file) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.default_pic)
                .error(R.drawable.bg_default)                 //出错显示的图片
                .priority(Priority.HIGH)                     //优先级高
                .diskCacheStrategy(DiskCacheStrategy.NONE);  //禁止磁盘缓存

        Glide.with(context)
                .load(file)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }
    /**
     *加载成圆形图片
     * @param context 上下文
     * @param imageView  要加载到那个控件上
     * @param url  图片的 url
     */
    public static void loadCircleImageForUrl(Context context, ImageView imageView, String url){
        RequestOptions options = new RequestOptions()
//                .centerCrop()
                .placeholder(R.drawable.bg_default)
                .error(R.drawable.bg_default)
                .circleCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(url).apply(options)
                .into(imageView);
    }


    /**
     *
     * @param context 上下文
     * @param imageView  要加载到那个控件上
     * @param url  图片的 url
     */
    public static void loadCircleImageForUrl(Context context,int defalut, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
//                .centerCrop()
                .placeholder(defalut)
                .error(defalut)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .circleCrop()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }
}
