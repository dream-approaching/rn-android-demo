package com.lieying.petcat.share;

import android.app.Activity;

import com.umeng.socialize.UMShareListener;

/**
 * 定义各平台的分享接口
 */
public interface SocialShareMgr {
    void setUMShareListener(UMShareListener listener);

    /**
     * 分享至微信
     *
     * @param context
     * @param shareContent
     */
    void shareToWX(Activity context, ShareContent shareContent);

    /**
     * 分享至微信朋友圈
     *
     * @param context
     * @param shareContent
     */
    void shareToWXCircle(Activity context, ShareContent shareContent);

    /**
     * 分享至QQ
     *
     * @param context
     * @param shareContent
     */
    void shareToQQ(Activity context, ShareContent shareContent);

    /**
     * 分享至新浪微博
     *
     * @param context
     * @param shareContent
     */
    void shareToSina(Activity context, ShareContent shareContent);

    /**
     * 二维码
     *
     * @param context
     * @param content
     */
    void shareToQqZone(Activity context, ShareContent content);

    /**
     * 分享链接
     *
     * @param context
     * @param content
     */
    void shareCopyUrl(Activity context, ShareContent content);

}
