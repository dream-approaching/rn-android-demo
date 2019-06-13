package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.AppDetailsBean;
import com.lieying.comlib.bean.DownBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseLoadingActivity;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.service.UpdateService;
import com.lieying.petcat.share.ShareContent;
import com.lieying.petcat.utils.DialogUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.ToastUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class AppDetailsActivity extends BaseLoadingActivity {
    public static String APP_ID = "app_id";
    ImageView mIVLogo;
    TextView mTVTitle;
    TextView mTvDesc;
    LinearLayout mLLTags;
    LinearLayout mLLBigImags;
    LinearLayout mLLLikes;
    TextView mTVCollection;
    TextView mTVRecommend;
    AppDetailsBean appDetailsBean;
    TextView mTvCommentsCount;
    ImageView mIvShare;
    private int[] bgs = {R.drawable.bg_app_tag_one, R.drawable.bg_app_tag_two, R.drawable.bg_app_tag_three};

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, AppDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(APP_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_app_details);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    public void findView() {
        mIVLogo = findViewById(R.id.iv_app_details_logo);
        mTVTitle = findViewById(R.id.tv_app_details_title);
        mTvDesc = findViewById(R.id.tv_app_details_describe);
        mLLTags = findViewById(R.id.ll_app_details_tag_list);
        mLLBigImags = findViewById(R.id.ll_app_details_big_imgs_list);
        mLLLikes = findViewById(R.id.ll_app_details_like_list);
        mTVCollection = findViewById(R.id.tv_app_details_collection);
        mTVRecommend = findViewById(R.id.tv_app_details_recommend);
        mTvCommentsCount = findViewById(R.id.tv_app_details_comments_counts);
        mIvShare = findViewById(R.id.iv_app_details_share);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        showLoading();
        String id = getIntent().getStringExtra(APP_ID);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<AppDetailsBean>>>() {
            @Override
            public ObservableSource<ResponseData<AppDetailsBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getAppDetailsInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<AppDetailsBean>>() {
            @Override
            protected void onSuccees(ResponseData<AppDetailsBean> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    appDetailsBean = objectResponseData.getData();
                    parseData();
                    dissMissDialog();
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                dissMissDialog();
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    private void parseData() {
        GlideUtils.loadImageForUrl(mContext, mIVLogo, appDetailsBean.getApp_logo());
        mTVTitle.setText(appDetailsBean.getApp_name_cn());
        mTvDesc.setText(appDetailsBean.getApp_short_desc());
        mTVRecommend.setText(appDetailsBean.getApp_desc());
        mTvCommentsCount.setText(appDetailsBean.getComment_num());
        Drawable drawable;
        if (appDetailsBean.isIs_fabulous()) {
            drawable = getResources().getDrawable(R.drawable.ic_input_collectios);
        } else {
            drawable = getResources().getDrawable(R.drawable.ic_input_collection_n);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTVCollection.setCompoundDrawables(drawable, null, null, null);
        //解析标签数组
        String tag = appDetailsBean.getDevelop_classfy();
        String[] sourceStrArray = tag.split(",");
        int tagRightMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.task_item_tag_margin);
        for (int i = 0; i < sourceStrArray.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_layout_app_tag, null);
            mLLTags.addView(textView);
            textView.setBackgroundResource(bgs[i]);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.rightMargin = tagRightMargin;
            textView.setLayoutParams(params);
            textView.setText("#" + sourceStrArray[i]);
        }
        //解析截图数组
        String imgs = appDetailsBean.getApp_screenshot();
        String[] imgsArray = imgs.split(",");
        for (int i = 0; i < imgsArray.length; i++) {
            ImageView textView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_app_details_pic, mLLBigImags, false);
            mLLBigImags.addView(textView);
            GlideUtils.loadImageForUrl(mContext, textView, imgsArray[i]);
        }

        //解析可能喜欢列表
        List<AppDetailsBean.LikedataBean> likeLists = appDetailsBean.getLikedata();
        for (int i = 0; i < likeLists.size(); i++) {
            int finalI = i;
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_app_details_like, mLLLikes, false);
            mLLLikes.addView(linearLayout);
            ImageView imageView = linearLayout.findViewById(R.id.iv_item_like_icon);
            linearLayout.findViewById(R.id.tv_item_like_download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(appDetailsBean.getApp_down_url())) {
                        ToastUtil.showToast("下载地址不存在");
                        return;
                    }
                    DownBean downBean = new DownBean();
                    downBean.setName(likeLists.get(finalI).getApp_name_cn());
                    downBean.setDownPath(likeLists.get(finalI).getApp_down_url());
                    String[] name = likeLists.get(finalI).getApp_down_url().split("/");
                    downBean.setFileName(name[name.length - 1]);
                    startFxService(downBean);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDetailsActivity.startActivity(mContext, likeLists.get(finalI).getId());
                }
            });
            TextView textView = linearLayout.findViewById(R.id.tv_item_like_name);
            GlideUtils.loadImageForUrl(mContext, imageView, likeLists.get(i).getApp_logo());
            textView.setText(likeLists.get(i).getApp_name_cn());
        }

    }

    @Override
    public void initListener() {
        mIvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareContent shareContent = new ShareContent();
                shareContent.setContent("晚上睡不着白天起不来？试试这款减压放松神器吧");
                shareContent.setUrl("http://api.content.lynav.com/h5/21.html");
                shareContent.setImgUrl("http://api.appstore.lynav.com/uploads_cms_images/20.png");
                shareContent.setTitle("晚上睡不着白天起不来？");
                DialogUtils.showShareDialog(AppDetailsActivity.this , shareContent , new UMShareListener() {
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
                });
            }
        });
        findViewById(R.id.iv_app_details_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ll_app_details_comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance().NoUserLogin()){
                    LoginActivity.startActivity(mContext);
                    return;
                }
                CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "comment", new ReactParamsJson.Builder().setContentID(appDetailsBean.getId()).setContentType(Constants.COMMENT_TYPE_APP_DETAILS).getRNParams());
            }
        });

        findViewById(R.id.ll_app_details_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance().NoUserLogin()){
                    LoginActivity.startActivity(mContext);
                    return;
                }
                Constants.NEED_REFREASH_SENCOND_FG = true;
                collection();
            }
        });

        findViewById(R.id.ll_app_details_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(appDetailsBean.getApp_down_url())) {
                    ToastUtil.showToast("下载地址不存在");
                    return;
                }
                startFxService();
            }
        });
    }

    /**
     * @Description: 收藏
     * @Params: collection   add代表收藏，del代表取消收藏
     * @Author: liyi
     */
    private void collection() {
        String collection = appDetailsBean.isIs_fabulous() ? Constants.OPT_TO_USER_CANCLE_FOLLOW :Constants.OPT_TO_USER_FOLLOW;
        HashMap<String, String> map = new HashMap<>();
        map.put("type", Constants.COLLECTION_TYPE_APP_DETAILS);
        map.put("opt", collection);
        map.put("info_id", appDetailsBean.getId());
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().collectionContent(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {

                if (objectResponseData.getStatus() == 0) {
                    if (collection.equals(Constants.OPT_TO_USER_FOLLOW)) {
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_input_collectios);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        appDetailsBean.setIs_fabulous(true);
                        mTVCollection.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_input_collection_n);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        mTVCollection.setCompoundDrawables(drawable, null, null, null);
                        appDetailsBean.setIs_fabulous(false);
                    }
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

    /**
     * 启动下载服务
     */
    private void startFxService() {
        if (TextUtils.isEmpty(appDetailsBean.getApp_down_url())) {
            ToastUtil.showToast("下载地址为空");
            return;
        }
        Intent intent = new Intent(this, UpdateService.class);
        DownBean downBean = new DownBean();
        downBean.setName(appDetailsBean.getApp_name_cn());
        downBean.setDownPath(appDetailsBean.getApp_down_url());
        String[] name = appDetailsBean.getApp_down_url().split("/");
        downBean.setFileName(name[name.length - 1]);
        intent.putExtra("appURL", downBean);
        this.startService(intent);
    }

    /**
     * 启动下载服务
     */
    private void startFxService(DownBean downBean) {
        if (TextUtils.isEmpty(downBean.getDownPath())) {
            ToastUtil.showToast("下载地址为空");
            return;
        }
        Intent intent = new Intent(this, UpdateService.class);
        intent.putExtra("appURL", downBean);
        this.startService(intent);
    }

}
