package com.lieying.socialappstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.comlib.bean.UserJoinBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.activity.AppDetailsActivity;
import com.lieying.socialappstore.activity.CommonReactActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.DefaultNoMoreViewHolder;

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
public class UserIndexJoinFragment extends BaseV4Fragment implements PullToRefreshListener {
    PullToRefreshRecyclerView refreshRecyclerView;
    private List<UserJoinBean> mApplist = new ArrayList();
    private UserIndexJoinFragment.MyAdapter myAdapter;
    private String userName = "夏冬冬";

    public static UserIndexJoinFragment newInstance() {
        UserIndexJoinFragment fragment = new UserIndexJoinFragment();
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
        getAppData(true);
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
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<UserJoinBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<UserJoinBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getUserJoinInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<UserJoinBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<UserJoinBean>> objectResponseData) {
                if (refreshRecyclerView.isRefresh()) {
                    refreshRecyclerView.onPullComplete();
                }
                if (refreshRecyclerView.isLoadMore()) {
                    refreshRecyclerView.onLoadMoreComplete();
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
                UserJoinBean bean = mApplist.get(position);
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
            ImageView mIvAppLogo;
            TextView mTvShareAppName;
            TextView mTvJoinType;
            ImageView mIvVip;

            private MsgHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_user_join_fg, null));
                mIvIcon = itemView.findViewById(R.id.iv_user_share_head_icon);
                mTvUserName = itemView.findViewById(R.id.tv_user_share_username);
                mTvShareTime = itemView.findViewById(R.id.tv_user_share_time);
                mTvShareContent = itemView.findViewById(R.id.tv_user_share_content);
                mIvVip = itemView.findViewById(R.id.iv_user_share_vip);
                mIvAppLogo = itemView.findViewById(R.id.iv_user_share_app_icon);
                mTvShareAppName = itemView.findViewById(R.id.tv_user_share_app_name);
                mTvJoinType = itemView.findViewById(R.id.tv_user_share_type);
            }

            private void initData(final UserJoinBean bean, final int position) {
                GlideUtils.loadImageForUrl(mContext, mIvIcon, bean.getHead_image());
                mTvUserName.setText(bean.getNick_name());
                mTvShareTime.setText(bean.getTimestr());
                mTvShareContent.setText( bean.getMydata().getMy_content());
                mIvVip.setVisibility(bean.getIs_big_v().equals("1") ? View.GONE : View.
                        VISIBLE);
                GlideUtils.loadImageForUrl(mContext, mIvAppLogo, bean.getMydata().getImg());
                mTvShareAppName.setText(bean.getType().equals("5") ? (Html.fromHtml("<font color='#6a83a2'>"+bean.getMydata().getContent_commit_user()+": </font>"+bean.getMydata().getTitle())) :bean.getMydata().getTitle());
                mTvJoinType.setText(getJoinType(bean.getMytype()));
                itemView.findViewById(R.id.ll_user_share_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (bean.getMydata().getDetail_type()){
                            case Constants.COMMENT_TYPE_TOPIC:
                                CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , new ReactParamsJson.Builder().setContentID(bean.getMydata().getId()).getRNParams());
                                break;
                            case Constants.COMMENT_TYPE_SHUZI:

                                break;
                            case Constants.COMMENT_TYPE_APP:

                                break;
                            case Constants.COMMENT_TYPE_APP_DETAILS:
                                AppDetailsActivity.startActivity(mContext ,bean.getMydata().getId());
                                break;
                        }
                    }
                });
            }

            private String getJoinType(String type) {
                switch (type) {
                    case Constants.JOIN_TYPE_PRAISE:
                        return "点赞了";
                    case Constants.JOIN_TYPE_COMMONTS:
                        return "评论了";
                    case Constants.JOIN_TYPE_COLLECTION:
                        return "收藏了";
                    default:
                        return "收藏了";
                }
            }
        }
    }
}
