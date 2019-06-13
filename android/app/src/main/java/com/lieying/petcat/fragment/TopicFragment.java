package com.lieying.petcat.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lieying.comlib.bean.RnCallNativeBean;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.comlib.bean.TopicBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.comlib.pull.DefaultNoMoreViewHolder;
import com.lieying.petcat.widget.NiceImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/26 0026 15:11
 */
public class TopicFragment extends BaseV4Fragment implements PullToRefreshListener {

    private PullToRefreshRecyclerView refreshRecyclerView;
    private MyAdapter myAdapter;
    private List<TopicBean> mApplist = new ArrayList<>();
    private MainReceiver mMainReceiver;
    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) { //刷新主界面三个fragment数据

            String json = intent.getStringExtra(Constants.KEY_FIRSTH_FG_JSON);
            RnCallNativeBean rnCallFirstFragmentBean = GsonUtil.GsonToBean(json , RnCallNativeBean.class);
            TopicBean exploreBean =  mApplist.get(rnCallFirstFragmentBean.getPosition());
            exploreBean.setJoin_people(rnCallFirstFragmentBean.getJoinNum());
            myAdapter.notifyItemRangeChanged(rnCallFirstFragmentBean.getPosition()+1 , 1);
        }
    }
    public static TopicFragment newInstance() {
        TopicFragment fragment = new TopicFragment();
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fgm_topic, viewGroup, false);
    }

    @Override
    public void findView() {
        refreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.rv_fg_topic);
        refreshRecyclerView.setAnimation(null);
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
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_FLAG_TOPIC_FG_FRESH);

        mMainReceiver = new MainReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMainReceiver, filter);
    }

    @Override
    public void initListener() {

    }

    /**
     * @Description: 获取互动话题
     * @Author: liyi
     */
    private void getAppData(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", isFresh || mApplist.size() == 0 ? "" : mApplist.get(mApplist.size() - 1).getId());
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<TopicBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<TopicBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getTopicInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<TopicBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<TopicBean>> objectResponseData) {
                if (refreshRecyclerView.isRefresh()) {
                    refreshRecyclerView.onPullComplete();
                }
                if (refreshRecyclerView.isLoadMore()) {
                    refreshRecyclerView.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    if(isFresh){
                        mApplist.clear();
                        mApplist.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    }else {
                        mApplist.addAll(objectResponseData.getData());
                        int start = mApplist.size() - objectResponseData.getData().size();
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
                TopicBean bean = mApplist.get(position);
                ((MsgHolder) holder).initData(bean, position);
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                if(mApplist.size()<=0 ){
                    ((DefaultNoMoreViewHolder) holder).setTip( "暂无数据");
                }else {
                    ((DefaultNoMoreViewHolder) holder).setTip("已经到底啦");
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
            NiceImageView mIvIcon;
            TextView mTvTitle;
            TextView mTvJoinContent;
            TextView mTvContent;
            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_topic_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_item_topic_imgs);
                mTvTitle = itemView.findViewById(R.id.iv_item_topic_title);
                mTvJoinContent = itemView.findViewById(R.id.tv_item_topic_join);
                mTvContent = itemView.findViewById(R.id.iv_item_topic_content);
            }

            private void initData(final TopicBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getImg());
                mTvTitle.setText(bean.getTitle());
                mTvJoinContent.setText(bean.getJoin_people() + "人已参与");
                mTvContent.setText(bean.getShort_content());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mApplist.get(1).setJoin_people(5);
//                        myAdapter.notifyItemRangeChanged(2 , 1);
                        ReactParamsJson reactParamsJson = new ReactParamsJson.Builder().setContentID(mApplist.get(position).getId()).setPosition(position+"").build();
                        String params = GsonUtil.GsonString(reactParamsJson);
                        CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , params);
                    }
                });
            }
        }
    }
}
