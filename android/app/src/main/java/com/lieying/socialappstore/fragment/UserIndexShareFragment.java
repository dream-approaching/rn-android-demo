package com.lieying.socialappstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieying.comlib.bean.TopicBean;
import com.lieying.comlib.bean.UserShareBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.DefaultNoMoreViewHolder;
import com.lieying.socialappstore.widget.NiceImageView;

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
    private List<UserShareBean> mApplist = new ArrayList();
    private UserIndexShareFragment.MyAdapter myAdapter;
    public static UserIndexShareFragment newInstance() {
        UserIndexShareFragment fragment = new UserIndexShareFragment();
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
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
        map.put("id", isFresh || mApplist.size() == 0 ? "" : mApplist.get(mApplist.size() - 1).getId());
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("othermobilephone", "18503068868");
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
                        mApplist.clear();
                        mApplist.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    }else {
                        mApplist.addAll(objectResponseData.getData());
                        int start = mApplist.size() - objectResponseData.getData().size();

                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                        boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                        refreshRecyclerView.setCanLoadMore(hasMore);
                        myAdapter.setExitsMore(hasMore);
                    }

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
                UserShareBean bean = mApplist.get(position);
                ((MyAdapter.MsgHolder) holder).initData(bean, position);
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
            TextView mTvUserName;
            TextView mTvShareTime;
            TextView mTvShareContent;
            NiceImageView mIvAppLogo;
            TextView mTvShareAppName;
            TextView mTvPraise;
            TextView mTvComments;
            TextView mTvShare;
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
                mTvShare = itemView.findViewById(R.id.tv_item_share_commons);
            }

            private void initData(final UserShareBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getHead_image());
                mTvUserName.setText(bean.getNick_name());
                mTvShareTime.setText(bean.getTimestr());
                String[] sourceStrArray = bean.getLabel().split(",");
                StringBuffer label = new StringBuffer();
                for(int i = 0 ; i < sourceStrArray.length ; i++){
                    label.append("<font color='#707070'>#"+sourceStrArray[i]+"   </font>");
                }
                mTvShareContent.setText(Html.fromHtml(label+"   "+bean.getContent()));
                mTvPraise.setText(bean.getFabulous());
                mTvComments.setText(bean.getComment_num());
                GlideUtils.loadImageForUrl(mContext, mIvAppLogo, bean.getAppdata().getApp_logo());
                mTvShareAppName.setText(bean.getAppdata().getApp_short_desc());
                mTvShare.setText(bean.getForward_num());
            }
        }
    }
}
