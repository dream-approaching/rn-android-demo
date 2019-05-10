package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.AppDetailsBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class AppDetailsActivity extends BaseActivity {
    public static String APP_ID = "app_id";
    ImageView mIVLogo;
    TextView mTVTitle;
    TextView mTvDesc;
    LinearLayout mLLTags;
    LinearLayout mLLBigImags;
    LinearLayout mLLLikes;
    TextView mTVCollection;
    TextView mTVRecommend;
    LinearLayout mLlComments;
    LinearLayout mLlCollections;
    LinearLayout mLlDownLoad;
    AppDetailsBean appDetailsBean;
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
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
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
                    parseData(objectResponseData.getData());
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
     * @param appDetailsBean
     */
    private void parseData(AppDetailsBean appDetailsBean) {
        GlideUtils.loadImageForUrl(mContext, mIVLogo, appDetailsBean.getApp_logo());
        mTVTitle.setText(appDetailsBean.getApp_name_cn());
        mTvDesc.setText(appDetailsBean.getApp_short_desc());
        mTVRecommend.setText(appDetailsBean.getApp_desc());
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
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_app_details_like, mLLLikes, false);
            mLLLikes.addView(linearLayout);
            ImageView imageView = linearLayout.findViewById(R.id.iv_item_like_icon);
            TextView textView = linearLayout.findViewById(R.id.tv_item_like_name);
            GlideUtils.loadImageForUrl(mContext, imageView, likeLists.get(i).getApp_logo());
            textView.setText(likeLists.get(i).getApp_name_cn());
        }

    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_app_details_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ll_app_details_comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "detailChat", new ReactParamsJson.Builder().setContentID(appDetailsBean.getId()).setContentType(Constants.COMMENT_TYPE_APP_DETAILS).getRNParams());
            }
        });

        findViewById(R.id.ll_app_details_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection();
            }
        });

        findViewById(R.id.ll_app_details_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @Description: 收藏
     * @Params: collection   add代表收藏，del代表取消收藏
     * @Author: liyi
     */
    private void collection() {
        String collection = appDetailsBean.isIs_fabulous() ? "del" : "add";
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
                    if (collection.equals("add")) {
                        Drawable drawable= getResources().getDrawable(R.drawable.ic_input_collectios);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        ToastUtil.showToast("搜藏成功");
                        appDetailsBean.setIs_fabulous(true);
                        mTVCollection.setCompoundDrawables(drawable , null, null, null);
                    } else {
                        Drawable drawable= getResources().getDrawable(R.drawable.ic_input_collection_n);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        ToastUtil.showToast("取消收藏成功");
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

}
