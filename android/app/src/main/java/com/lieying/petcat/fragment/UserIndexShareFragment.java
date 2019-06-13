package com.lieying.petcat.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.lieying.comlib.bean.NativeCallRnBean;
import com.lieying.comlib.bean.UserShareBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.AppDetailsActivity;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.share.ShareContent;
import com.lieying.petcat.utils.DialogUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.comlib.pull.DefaultNoMoreViewHolder;
import com.lieying.petcat.widget.NiceImageView;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: 个人主页他人分享
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 10:16
 */
public class UserIndexShareFragment extends BaseV4Fragment implements PullToRefreshListener {
    PullToRefreshRecyclerView refreshRecyclerView;
    protected static final String ARG_PARAM_PHONE = "phone";
    private List<UserShareBean> list = new ArrayList();
    private UserIndexShareFragment.MyAdapter myAdapter;
    private String phone;
    private Drawable praise_n ;
    private Drawable praise_s ;

    public static UserIndexShareFragment newInstance(String phone) {
        UserIndexShareFragment fragment = new UserIndexShareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle args = getArguments();
        phone = (String) args.get(ARG_PARAM_PHONE);
        return layoutInflater.inflate(R.layout.fgm_user_index_share, viewGroup, false);
    }

    @Override
    public void findView() {
        refreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.rv_fg_user_share);
    }

    @Override
    public void initView() {
        myAdapter = new MyAdapter();
        refreshRecyclerView.setCanRefresh(true)
                .setCanLoadMore(false)
                .setPullLayoutManager(new LinearLayoutManager(mContext))
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);
    }

    @Override
    public void initData() {
        getAppData(true);
        praise_n = getResources().getDrawable(R.drawable.ic_praise_n);
        praise_s = getResources().getDrawable(R.drawable.ic_like_o);
        praise_n.setBounds(0, 0, praise_n.getMinimumWidth(), praise_n.getMinimumHeight());
        praise_s.setBounds(0, 0, praise_n.getMinimumWidth(), praise_n.getMinimumHeight());
    }

    @Override
    public void initListener() {

    }

    /**
     * @Description: 获取用户分享动态
     * @Author: liyi
     */
    private void getAppData(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", isFresh || list.size() == 0 ? "" : list.get(list.size() - 1).getId());
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("othermobilephone", phone);
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<UserShareBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<UserShareBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getUserShareInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<UserShareBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<UserShareBean>> objectResponseData) {
                if (refreshRecyclerView.isRefresh()) {
                    refreshRecyclerView.onPullComplete();
                }
                if (refreshRecyclerView.isLoadMore()) {
                    refreshRecyclerView.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    if(isFresh){
                        list.clear();
                        list.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    }else {
                        list.addAll(objectResponseData.getData());
                        int start = list.size() - objectResponseData.getData().size();
                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                    }
                    boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                    refreshRecyclerView.setCanLoadMore(hasMore);
                    myAdapter.setExitsMore(hasMore);
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (refreshRecyclerView.isRefresh()) {
                    refreshRecyclerView.onPullFail();
                }

                if (refreshRecyclerView.isLoadMore()) {
                    refreshRecyclerView.onLoadMoreFail();
                }
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }
    public void setCanRefresh(boolean refresh){
        if(refreshRecyclerView!=null)
            refreshRecyclerView.setCanRefresh(refresh);
    }


    @Override
    public void onRefresh() {
        getAppData(true);
    }

    @Override
    public void onLoadMore() {
        getAppData(false);
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int VIEW_TYPE_CONTENT = 1;
        private final static int VIEW_TYPE_EMPTY = -1;
        private boolean isExitsMore = true;//是否存在更多

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (viewType == VIEW_TYPE_EMPTY) {
                return new DefaultNoMoreViewHolder(mContext);
            }
            return new MyAdapter.MsgHolder(mContext);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyAdapter.MsgHolder) {
                UserShareBean bean = list.get(position);
                ((MyAdapter.MsgHolder) holder).initData(bean, position);
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                ((DefaultNoMoreViewHolder) holder).setTip("已经到底啦");
            }
        }

        @Override
        public int getItemCount() {
            return !isExitsMore() ? list.size() + 1 : list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (!isExitsMore() && position == list.size()) {
                return VIEW_TYPE_EMPTY;
            } else {
                return VIEW_TYPE_CONTENT;
            }
        }

        public boolean isExitsMore() {
            return isExitsMore;
        }

        public void setExitsMore(boolean exitsMore) {
            if (isExitsMore != exitsMore) {
                isExitsMore = exitsMore;
                notifyItemChanged(list.size());
            }
        }

        private class MsgHolder extends RecyclerView.ViewHolder {
            ImageView mIvIcon;
            TextView mTvUserName;
            TextView mTvShareTime;
            TextView mTvShareContent;
            NiceImageView mIvAppLogo;
            TextView mTvShareAppName;
            TextView mTvPraise;
            TextView mTvComments;
            ImageView mTvShare;
            TextView mTvDelete ;
            LinearLayout mLlApp;
            ImageView mIvVip;
            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_user_share_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_user_share_head_icon);
                mTvUserName = itemView.findViewById(R.id.tv_user_share_username);
                mTvShareTime = itemView.findViewById(R.id.tv_user_share_time);
                mTvShareContent = itemView.findViewById(R.id.tv_user_share_content);
                mIvAppLogo = itemView.findViewById(R.id.iv_user_share_app_icon);
                mTvShareAppName = itemView.findViewById(R.id.tv_user_share_app_name);
                mTvPraise = itemView.findViewById(R.id.tv_item_share_praise);
                mTvComments = itemView.findViewById(R.id.tv_item_share_commons);
                mTvShare = itemView.findViewById(R.id.iv_tv_item_share_share);
                mTvDelete = itemView.findViewById(R.id.tv_item_my_share_delete);
                mLlApp = itemView.findViewById(R.id.ll_user_share_app);
                mIvVip = itemView.findViewById(R.id.iv_user_share_vip);
            }

            private void initData(final UserShareBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getHead_image());
                mIvVip.setVisibility(bean.getIs_big_v().equals("1") ? View.GONE : View.
                        VISIBLE);
                mTvDelete.setVisibility(phone.equals(UserManager.getCurrentUser().getPhone()) ? View.VISIBLE : View.GONE);
                mTvUserName.setText(bean.getNick_name());
                mTvShareTime.setText(bean.getTimestr());
                String[] sourceStrArray = bean.getLabel().split(",");
                StringBuffer label = new StringBuffer();
                for(int i = 0 ; i < sourceStrArray.length ; i++){
                    label.append("<font color='#707070'>#"+sourceStrArray[i]+"   </font>");
                }
                mTvShareContent.setText(Html.fromHtml(label+"   "+bean.getContent()));
                mTvPraise.setText(bean.getFabulous());
                mTvPraise.setCompoundDrawables(bean.isIs_fabulous()? praise_n : praise_s, null , null , null);
                mTvComments.setText(bean.getComment_num());
                GlideUtils.loadImageForUrl(mContext, mIvAppLogo, bean.getAppdata().getApp_logo());
                mTvShareAppName.setText(bean.getAppdata().getApp_name_cn());
                mTvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareContent shareContent = new ShareContent();
                        shareContent.setContent("晚上睡不着白天起不来？试试这款减压放松神器吧");
                        shareContent.setUrl("http://api.content.lynav.com/h5/21.html");
                        shareContent.setImgUrl("http://api.appstore.lynav.com/uploads_cms_images/20.png");
                        shareContent.setTitle("晚上睡不着白天起不来？");
                        DialogUtils.showShareDialog(mContext , shareContent , new UMShareListener() {
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
                mTvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDeleteDialog(mContext, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteShare(bean.getId());
                                list.remove(position);
                                notifyItemRemoved(position + 1);
                                notifyItemRangeChanged(position + 1, list.size() - position);

                                NativeCallRnBean nativeCallRnBean = new NativeCallRnBean();
                                nativeCallRnBean.setOption(Constants.OPTION_DELETE_X_SHARE);
                                nativeCallRnBean.setContentId(bean.getId());
                                MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                        .emit(Constants.NATIVE_CALL_RN_PARAMS, GsonUtil.GsonString(nativeCallRnBean));
                            }
                        });
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "xFriendDetail", new ReactParamsJson.Builder().setContentID(bean.getId()).setContentType(Constants.COMMENT_TYPE_APP_DETAILS).getRNParams());
                    }
                });

                mLlApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDetailsActivity.startActivity(mContext , bean.getAppdata().getId());
                    }
                });

                mTvPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String collection = bean.isIs_fabulous() ?  Constants.OPT_TO_USER_FOLLOW : Constants.OPT_TO_USER_CANCLE_FOLLOW ;
                        follow(bean.getId() , collection);
                        if (collection.equals(Constants.OPT_TO_USER_FOLLOW)) {
                            list.get(position).setIs_fabulous(false);
                            int count = Integer.parseInt(list.get(position).getFabulous()) + 1;
                            list.get(position).setFabulous(count + "");
                        } else {
                            int count = Integer.parseInt(list.get(position).getFabulous()) - 1;
                            list.get(position).setFabulous(count + "");
                            list.get(position).setIs_fabulous(true);
                        }
                        notifyItemChanged(position + 1);
                        NativeCallRnBean nativeCallRnBean = new NativeCallRnBean();
                        nativeCallRnBean.setOption(Constants.OPTION_FABULOUS_X_SHARE);
                        nativeCallRnBean.setContentId(bean.getId());
                        nativeCallRnBean.setFabulousCount(list.get(position).getFabulous());
                        nativeCallRnBean.setFabulous(list.get(position).isIs_fabulous());
                        MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit(Constants.NATIVE_CALL_RN_PARAMS, GsonUtil.GsonString(nativeCallRnBean));
                    }
                });
                mTvComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "xFriendDetail", new ReactParamsJson.Builder().setContentID(bean.getId()).setContentType(Constants.COMMENT_TYPE_APP_DETAILS).getRNParams());
                    }
                });
            }

            private void follow(String id , String opt ){
                HashMap<String, String> map = new HashMap<>();
                map.put("mobilephone", UserManager.getCurrentUser().getPhone());
                map.put("opt", opt);
                map.put("type" , 5+"");
                map.put("id", id);
                RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                    @Override
                    public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                        return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().setFabulous(ReqBody.getReqString(map));
                    }
                } , new BaseObserver<ResponseData<Object>>() {
                    @Override
                    protected void onSuccees(ResponseData<Object> objectResponseData) {
                        if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {
                        if(isNetWorkError){
                            ToastUtil.showToast(getString(R.string.string_request_fails));
                        }
                    }
                });
            }
            private void deleteShare(String id ){
                HashMap<String, String> map = new HashMap<>();
                map.put("mobilephone", UserManager.getCurrentUser().getPhone());
                map.put("id", id);
                RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                    @Override
                    public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                        return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().delShare(ReqBody.getReqString(map));
                    }
                } , new BaseObserver<ResponseData<Object>>() {
                    @Override
                    protected void onSuccees(ResponseData<Object> objectResponseData) {
                        if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {
                        if(isNetWorkError){
                            ToastUtil.showToast(getString(R.string.string_request_fails));
                        }
                    }
                });

            }
        }
    }
}
