package com.lieying.petcat.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieying.comlib.bean.AppListBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.AppDetailsActivity;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.activity.LoginActivity;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.comlib.pull.DefaultNoMoreViewHolder;

import java.lang.ref.WeakReference;
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
public class SecondFragment extends BaseV4Fragment implements PullToRefreshListener {
    private List<AppListBean> mApplist = new ArrayList<>();
    private PullToRefreshRecyclerView mRecyclerV;
    MyAdapter myAdapter;
    private Handler myHandler;

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fgm_second, viewGroup, false);
    }

    @Override
    public void findView() {
        mRecyclerV = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public void initView() {
        myAdapter = new MyAdapter();
        mRecyclerV.setCanRefresh(false)
                .setCanLoadMore(false)
                .setPullLayoutManager(new LinearLayoutManager(mContext))
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);
    }

    @Override
    public void initData() {
        myHandler = new MyHandler(this);
        getAppData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constants.NEED_REFREASH_SENCOND_FG){
            getAppData(true);
            Constants.NEED_REFREASH_SENCOND_FG = false;
        }
    }

    @Override
    public void initListener() {

    }

    /**
     * @Description: 获取本周排行
     * @Author: liyi
     */
    private void getAppData(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<AppListBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<AppListBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getAppListInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<AppListBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<AppListBean>> objectResponseData) {
                if (mRecyclerV.isRefresh()) {
                    mRecyclerV.onPullComplete();
                }
                if (mRecyclerV.isLoadMore()) {
                    mRecyclerV.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    if (isFresh) {
                        mApplist.clear();
                        mApplist.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    } else {
                        mApplist.addAll(objectResponseData.getData());
                        int start = mApplist.size() - objectResponseData.getData().size();
                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                    }
                }
//                boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                mRecyclerV.setCanLoadMore(false);
                myAdapter.setExitsMore(false);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (mRecyclerV.isRefresh()) {
                    mRecyclerV.onPullFail();
                }

                if (mRecyclerV.isLoadMore()) {
                    mRecyclerV.onLoadMoreFail();
                }
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
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
            return new MsgHolder(mContext);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MsgHolder) {
                ((MsgHolder) holder).initData(mApplist.get(position), position);
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                if (mApplist.size() <= 0) {
                    ((DefaultNoMoreViewHolder) holder).setTip("暂无数据");
                } else {
                    ((DefaultNoMoreViewHolder) holder).setTip("本周就到这，下周待续");
                }

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
            LinearLayout mLlPraise;
            TextView mTvSeeTest;
            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_second_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_item_second_logo);
                mTvTitle = itemView.findViewById(R.id.tv_item_second_title);
                mTvContent = itemView.findViewById(R.id.tv_item_second_content);
                mTvPraiseNum = itemView.findViewById(R.id.tv_item_second_praise_num);
                mIvPraise = itemView.findViewById(R.id.iv_item_praise);
                mLlPraise = itemView.findViewById(R.id.ll_item_praise);
                mTvSeeTest = itemView.findViewById(R.id.tv_item_second_test);
            }

            private void initData(final AppListBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getApp_logo());
                if(TextUtils.isEmpty(bean.getUrl_content())){
                    mTvSeeTest.setVisibility(View.GONE);
                }else{
                    mTvSeeTest.setVisibility(View.VISIBLE);
                    mTvSeeTest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" , "detailWebview" ,new ReactParamsJson.Builder().setWebUrl(bean.getUrl_content()).setPosition(position+"").getRNParams());
                        }
                    });
                }
                mTvTitle.setText(bean.getApp_name_cn());
                mTvContent.setText(bean.getApp_short_desc());
                mTvPraiseNum.setText(bean.getFavorites());
                mIvPraise.setImageResource(bean.isIs_fabulous() ? R.drawable.ic_second_praise_s : R.drawable.ic_second_praise_n);
                mTvPraiseNum.setTextColor( bean.isIs_fabulous() ? getResources().getColor(R.color.color_fb716b) : getResources().getColor(R.color.color_8f8f8f) );
                mLlPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UserManager.getCurrentUser().getUserinfo() == null) {
                            LoginActivity.startActivity(mContext);
                            ToastUtil.showToast("请先登陆账号");
                            return;
                        }
                        String collection = bean.isIs_fabulous() ? Constants.OPT_TO_USER_CANCLE_FOLLOW : Constants.OPT_TO_USER_FOLLOW;
                        if (collection.equals(Constants.OPT_TO_USER_FOLLOW)) {
                            mApplist.get(position).setIs_fabulous(true);
                            int count = Integer.parseInt(mApplist.get(position).getFavorites()) + 1;
                            mApplist.get(position).setFavorites(count + "");
                        } else {
                            int count = Integer.parseInt(mApplist.get(position).getFavorites()) - 1;
                            mApplist.get(position).setFavorites(count + "");
                            mApplist.get(position).setIs_fabulous(false);
                        }
                        notifyItemChanged(position + 1);
                        sendCollection(4 + "", collection, bean.getId());
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        AppDetailsActivity.startActivity(mContext, bean.getId());
                        Intent intent = new Intent(mContext, AppDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(AppDetailsActivity.APP_ID, bean.getId());
                        Pair<View, String> pair = new Pair<View, String>(mIvIcon,"appIcon");
                        ActivityOptionsCompat optionsCompat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);

                        mContext.startActivity(intent ,optionsCompat.toBundle());
                    }
                });
            }
        }
    }

    private void sendCollection(String type, String collection, String info_id) {
        Message message = Message.obtain();
        message.what = Integer.parseInt(info_id);
        Bundle bundle = new Bundle();
        bundle.putString("opt", collection);
        bundle.putString("type", type);
        bundle.putString("info_id", info_id);
        message.setData(bundle);
        myHandler.removeMessages(message.what);
        myHandler.sendMessageDelayed(message, Constants.PRAISE_DELAY_MILES);
    }

    private static class MyHandler extends Handler {
        WeakReference<SecondFragment> mWeakReference;

        public MyHandler(SecondFragment activity) {
            mWeakReference = new WeakReference<SecondFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            HashMap<String, String> map = new HashMap<>();
            map.put("type", bundle.getString("type"));
            map.put("opt", bundle.getString("opt"));
            map.put("info_id", bundle.getString("info_id"));
            map.put("mobilephone", UserManager.getCurrentUser().getPhone());
            RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                @Override
                public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                    return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().collectionContent(ReqBody.getReqString(map));
                }
            }, new BaseObserver<ResponseData<Object>>() {
                @Override
                protected void onSuccees(ResponseData<Object> objectResponseData) {
                    if (objectResponseData.getStatus() == 0) {

                    } else {
                        ToastUtil.showToast("操作失败" + objectResponseData.getMsg());
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

}
