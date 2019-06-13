package com.lieying.petcat.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lieying.petcat.R;
import com.lieying.petcat.share.ShareContent;
import com.lieying.petcat.share.SocialShareMgr;
import com.lieying.petcat.share.SocialShareMgrImpl;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

public class ShareBoardView extends FrameLayout {
    private SocialShareMgr mSocialShareMgr;
    private ShareContent mShareContentMap;
    private UMShareListener mUMListener;
    private UMShareAPI mUMShareAPI;

    public ShareBoardView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public ShareBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ShareBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View v = inflate(context, R.layout.dialog_share_board_layout, this);
        ShareButtonClickListener clickListener = new ShareButtonClickListener(this);
        findViewById(R.id.we_chat_share).setOnClickListener(clickListener);
        findViewById(R.id.we_chat_friend_share).setOnClickListener(clickListener);
        findViewById(R.id.qq_share).setOnClickListener(clickListener);
        findViewById(R.id.sina_share).setOnClickListener(clickListener);
        findViewById(R.id.qq_zone_share).setOnClickListener(clickListener);
        findViewById(R.id.copy_share).setOnClickListener(clickListener);
        mSocialShareMgr = new SocialShareMgrImpl();
        mUMShareAPI = UMShareAPI.get(context);

    }

    public void setSharePlantform(ShareContent contentMap, UMShareListener listener) {
        mShareContentMap = contentMap;
        mUMListener = listener;
        mSocialShareMgr.setUMShareListener(listener);
    }

    private static class ShareButtonClickListener implements OnClickListener {
        private WeakReference<ShareBoardView> shareBoardRef;

        public ShareButtonClickListener(ShareBoardView shareBoard) {
            this.shareBoardRef = new WeakReference<>(shareBoard);
        }

        @Override
        public void onClick(View v) {
            ShareBoardView shareBoardView = shareBoardRef.get();
            if (null != shareBoardRef) {
                try {
                    share(v, shareBoardView);
                } catch (Exception e) {

                }

            }
        }
    }

    private static void share(View v, ShareBoardView shareBoardView) {
        switch (v.getId()) {
            case R.id.we_chat_share:
                if (shareBoardView.mUMShareAPI.isInstall((Activity) shareBoardView.getContext(), SHARE_MEDIA.WEIXIN)) {
                    shareBoardView.mSocialShareMgr.shareToWX((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                } else {

                }
                break;
            case R.id.we_chat_friend_share:
                shareBoardView.mSocialShareMgr.shareToWXCircle((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                break;
            case R.id.qq_share:
                shareBoardView.mSocialShareMgr.shareToQQ((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                break;
            case R.id.sina_share:
                shareBoardView.mSocialShareMgr.shareToSina((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                break;
            case R.id.qq_zone_share:
                shareBoardView.mSocialShareMgr.shareToQqZone((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                break;
            case R.id.copy_share:
                shareBoardView.mSocialShareMgr.shareCopyUrl((Activity) shareBoardView.getContext(), shareBoardView.mShareContentMap);
                break;
        }
    }
}
