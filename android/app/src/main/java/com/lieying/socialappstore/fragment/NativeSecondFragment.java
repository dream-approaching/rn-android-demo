package com.lieying.socialappstore.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lieying.comlib.bean.AppListBean;
import com.lieying.comlib.bean.ExploreBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.activity.AppDetailsActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.DefaultNoMoreViewHolder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: 本地Fragment
 * @Author: liyi
 * @CreateDate: 2019/4/24 0024 17:24
 */
public class NativeSecondFragment extends BaseV4Fragment implements PullToRefreshListener {
    private List<AppListBean> mApplist = new ArrayList<>();
    private PullToRefreshRecyclerView mRecyclerV;
    MyAdapter myAdapter;
    public static NativeSecondFragment newInstance() {
        NativeSecondFragment fragment = new NativeSecondFragment();
        return fragment;
    }
    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fgm_second, viewGroup, false);
    }

    @Override
    public void findView() {
        mRecyclerV = (PullToRefreshRecyclerView)findViewById(R.id.recycler_view);
    }

    @Override
    public void initView() {
        myAdapter = new MyAdapter();
        mRecyclerV.setCanRefresh(true)
                .setCanLoadMore(true)
                .setPullLayoutManager(new LinearLayoutManager(mContext))
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);
    }

    @Override
    public void initData() {
        getAppData(true);
    }

    @Override
    public void initListener() {

    }

    /**
     * @Description:    获取本周排行
     * @Author:         liyi
     */
    private void getAppData(boolean isFresh){
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_id", "1");
        map.put("app_ver", "1");
        map.put("app_ver_code", "1");
        map.put("ch", "1");
        map.put("id" , isFresh || mApplist.size() == 0 ? "" : mApplist.get(mApplist.size() -1).getId());
        map.put("pagesize" , Constants.DEFAULT_PAGE_SIZE+"");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<AppListBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<AppListBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getAppListInfo(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<List<AppListBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<AppListBean>> objectResponseData) {
                if (mRecyclerV.isRefresh()) {
                    mRecyclerV.onPullComplete();
                }
                if (mRecyclerV.isLoadMore()) {
                    mRecyclerV.onLoadMoreComplete();
                }
                if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                    if(isFresh){
                        mApplist.clear();
                        mApplist.addAll(objectResponseData.getData());
                        mApplist.addAll(objectResponseData.getData());
                        mApplist.addAll(objectResponseData.getData());
                        mApplist.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    }else {
                        mApplist.addAll(objectResponseData.getData());
                        int start = mApplist.size() - objectResponseData.getData().size();

                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                        boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                        mRecyclerV.setCanLoadMore(hasMore);
                        myAdapter.setExitsMore(hasMore);
                    }

                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (mRecyclerV.isRefresh()) {
                    mRecyclerV.onPullFail();
                }

                if (mRecyclerV.isLoadMore()) {
                    mRecyclerV.onLoadMoreFail();
                }
                if(isNetWorkError){
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getAppData(true);
    }

    @Override
    public void onLoadMore() {
        getAppData(false);
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final static int VIEW_TYPE_CONTENT = 1;
        private final static int VIEW_TYPE_EMPTY = -1;
        private boolean isExitsMore = true;//是否存在更多
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (viewType == VIEW_TYPE_EMPTY) {
                return new DefaultNoMoreViewHolder(mContext);
            }
            return new MsgHolder(mContext);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MsgHolder) {
                AppListBean bean = mApplist.get(position);
                ((MsgHolder) holder).initData(bean , position);
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                ((DefaultNoMoreViewHolder) holder).setTip("我是有底线的");
            }
        }

        @Override
        public int getItemCount() {
            return !isExitsMore() ? mApplist.size() + 1 : mApplist.size();
        }
        @Override
        public int getItemViewType(int position) {
            if (!isExitsMore() && position == mApplist.size()) {
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
                notifyItemChanged(mApplist.size());
            }
        }
        private class MsgHolder extends RecyclerView.ViewHolder {
            ImageView mIvIcon;
            TextView mTvTitle;
            TextView mTvContent;
            TextView mTvPraiseNum;
            ImageView mIvPraise;
            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_second_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_item_second_logo);
                mTvTitle = itemView.findViewById(R.id.tv_item_second_title);
                mTvContent = itemView.findViewById(R.id.tv_item_second_content);
                mTvPraiseNum = itemView.findViewById(R.id.tv_item_second_praise_num);
                mIvPraise = itemView.findViewById(R.id.iv_item_praise);
            }

            private void initData(final AppListBean bean , final int position) {
                GlideUtils.loadImageForUrl(mContext , mIvIcon , bean.getApp_logo());
                mTvTitle.setText(bean.getApp_name_cn());
                mTvContent.setText(bean.getApp_short_desc());
                mTvPraiseNum.setText(bean.getFabulous());
                mIvPraise.setImageResource(bean.isIs_fabulous()?R.drawable.ic_second_praise_n : R.drawable.ic_second_praise_s);
                mIvPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDetailsActivity.startActivity(mContext , bean.getId());
                    }
                });
            }
        }
    }
}
