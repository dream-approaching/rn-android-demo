package com.lieying.petcat.share;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;

import com.lieying.petcat.R;
import com.lieying.petcat.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class SocialShareMgrImpl implements SocialShareMgr {
    private UMShareListener mUMShareListener;

    @Override
    public void setUMShareListener(UMShareListener mOnShareResultListener) {
        this.mUMShareListener = mOnShareResultListener;
        if (null == mUMShareListener) {
            initShareListener();
        }

    }

    private void initShareListener() {
        mUMShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        };
    }

    @Override
    public void shareToWX(Activity context, ShareContent shareContent) {
        UMImage urlImage = new UMImage(context, shareContent.getImgUrl());
        String shareUrl = shareContent.getUrl();
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareContent.getTitle());//标题
        web.setThumb(urlImage);  //缩略图
        web.setDescription(shareContent.getContent());//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(mUMShareListener)
                .withText(shareContent.getContent())
                .withMedia(web)
                .share();
    }

    @Override
    public void shareToWXCircle(Activity context, ShareContent shareContent) {
        UMImage urlImage = new UMImage(context, shareContent.getImgUrl());
        String shareUrl = shareContent.getUrl();
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareContent.getTitle());//标题
        web.setThumb(urlImage);  //缩略图
        web.setDescription(shareContent.getContent());//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(mUMShareListener)
                .withText(shareContent.getContent())
                .withMedia(web)
                .share();
    }

    @Override
    public void shareToQQ(Activity context, ShareContent shareContent) {
        UMImage urlImage = new UMImage(context, shareContent.getImgUrl());
        String shareUrl = shareContent.getUrl();
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareContent.getTitle());//标题
        web.setThumb(urlImage);  //缩略图
        web.setDescription(shareContent.getContent());//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.QQ).setCallback(mUMShareListener)
                .withText(shareContent.getContent())
                .withMedia(web)
                .share();
    }

    @Override
    public void shareToSina(Activity context, ShareContent shareContent) {
        UMImage urlImage = new UMImage(context, shareContent.getImgUrl());
        String shareUrl = shareContent.getUrl();
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareContent.getTitle());//标题
        web.setThumb(urlImage);  //缩略图
        web.setDescription(shareContent.getContent());//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.SINA).setCallback(mUMShareListener)
                .withText(shareContent.getContent())
                .withMedia(web)
                .share();
    }

    @Override
    public void shareToQqZone(Activity context, ShareContent shareContent) {
        UMImage urlImage = new UMImage(context, shareContent.getImgUrl());
        String shareUrl = shareContent.getUrl();
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(shareContent.getTitle());//标题
        web.setThumb(urlImage);  //缩略图
        web.setDescription(shareContent.getContent());//描述
        new ShareAction(context).setPlatform(SHARE_MEDIA.QZONE).setCallback(mUMShareListener)
                .withText(shareContent.getContent())
                .withMedia(web)
                .share();
    }

    @Override
    public void shareCopyUrl(Activity context, ShareContent content) {
        String copyStr = content.getUrl();
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(this::showCopyResault);
        cm.setText(copyStr);

    }

    private void showCopyResault() {
        ToastUtil.showToast(R.string.string_copy_url_ok);
    }
}
