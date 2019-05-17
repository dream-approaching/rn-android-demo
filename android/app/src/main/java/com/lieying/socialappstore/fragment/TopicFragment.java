package com.lieying.socialappstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.comlib.bean.TopicBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.activity.CommonReactActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.GsonUtil;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.DefaultNoMoreViewHolder;

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
                    ((DefaultNoMoreViewHolder) holder).setTip("我是有底线的");
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
            TextView mTvJoinContent;

            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_topic_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_item_topic_imgs);
                mTvTitle = itemView.findViewById(R.id.iv_item_topic_title);
                mTvJoinContent = itemView.findViewById(R.id.tv_item_topic_join);
            }

            private void initData(final TopicBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getImg());
                mTvTitle.setText(bean.getTitle());
                mTvJoinContent.setText(bean.getJoin_people() + "人已参与");
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReactParamsJson reactParamsJson = new ReactParamsJson.Builder().setContentID(mApplist.get(position).getId()).build();
                        String params = GsonUtil.GsonString(reactParamsJson);
                        CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , params);
                    }
                });
            }
        }
    }
}
