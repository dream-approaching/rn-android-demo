package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.CollectionBean;
import com.lieying.comlib.bean.RnCallNativeBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.comlib.utils.ScreenUtils;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;
import com.lieying.petcat.bean.ReactParamsJson;
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
import com.lieying.petcat.widget.SwipeItemLayout;
import com.lieying.petcat.widget.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class CollectionActivity extends BaseActivity implements PullToRefreshListener {
    private static String KEY_COLLEECTION_TYPE = "collecttion_type";
    PullToRefreshRecyclerView pullToRefreshRecyclerView;
    List<CollectionBean> list = new ArrayList<>();
    MyAdapter myAdapter;
    TitleView titleView;
    private String collectionType = Constants.COLLECTION_TYPE_APP;


    public static void startActivity(Context context, String type) {
        Intent intent = new Intent(context, CollectionActivity.class);
        intent.putExtra(KEY_COLLEECTION_TYPE, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_collection);
    }

    @Override
    public void findView() {
        titleView = findViewById(R.id.view_title);
        pullToRefreshRecyclerView = findViewById(R.id.recycler_view);
        pullToRefreshRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        pullToRefreshRecyclerView.setCanRefresh(true)
                .setPullLayoutManager(new LinearLayoutManager(mContext))
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);

    }

    @Override
    public void initData() {
        collectionType = getIntent().getStringExtra(KEY_COLLEECTION_TYPE);
        titleView.setTitle(getTitle(collectionType));
        getCollectionList(true);
    }

    private String getTitle(String type) {
        switch (type) {
            case Constants.COLLECTION_TYPE_APP:
                return "收藏App应用";
            case Constants.COLLECTION_TYPE_ARTICLE:
                return "收藏文章";
            case Constants.COLLECTION_TYPE_TOPIC:
                return "收藏话题";
            default:
                return "收藏App应用";
        }
    }

    @Override
    public void initListener() {
        titleView.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getCollectionList(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        map.put("favorites_type", collectionType);
        map.put("id", isFresh || list.size() == 0 ? "" : list.get(list.size() - 1).getId());
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<CollectionBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<CollectionBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getCollectionList(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<CollectionBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<CollectionBean>> objectResponseData) {
                if (pullToRefreshRecyclerView.isRefresh()) {
                    pullToRefreshRecyclerView.onPullComplete();
                }
                if (pullToRefreshRecyclerView.isLoadMore()) {
                    pullToRefreshRecyclerView.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    if (isFresh) {
                        list.clear();
                        list.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    } else {
                        list.addAll(objectResponseData.getData());
                        int start = list.size() - objectResponseData.getData().size();
                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                    }
                    boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                    pullToRefreshRecyclerView.setCanLoadMore(hasMore);
                    myAdapter.setExitsMore(hasMore);
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (pullToRefreshRecyclerView.isRefresh()) {
                    pullToRefreshRecyclerView.onPullFail();
                }
                if (pullToRefreshRecyclerView.isLoadMore()) {
                    pullToRefreshRecyclerView.onLoadMoreFail();
                }
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getCollectionList(true);
    }

    @Override
    public void onLoadMore() {
        getCollectionList(false);
    }


    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int VIEW_TYPE_CONTENT = 1;
        private final static int VIEW_TYPE_EMPTY = -1;

        private boolean isExitsMore = true;//是否存在更多

        public void setExitsMore(boolean exitsMore) {
            if (isExitsMore != exitsMore) {
                isExitsMore = exitsMore;
                notifyItemChanged(list.size());
            }
        }

        public boolean isExitsMore() {
            return isExitsMore;
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

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_EMPTY) {
                if (list.size() == 0) {
                    return new DefaultNoMoreViewHolder(mContext, "", ScreenUtils.dip2px(mContext, 151));
                }
                return new DefaultNoMoreViewHolder(mContext);
            }
            if (collectionType.equals(Constants.COLLECTION_TYPE_APP)) {
                return new AppHolder(mContext);
            } else {
                return new ArticleHolder(mContext);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof AppHolder) {
                ((AppHolder) holder).setData(position, list.get(position));
            }
            if (holder instanceof ArticleHolder) {
                ((ArticleHolder) holder).setData(position, list.get(position));
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                if (list.size() <= 0) {
                    ((DefaultNoMoreViewHolder) holder).setTip("还没有任何" + getTitle(collectionType));
                } else {
                    ((DefaultNoMoreViewHolder) holder).setTip("没有更多数据了");
                }
            }
        }


        private class AppHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvContent;
            NiceImageView imageView;
            RelativeLayout mRlLayout;

            private AppHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_collection_activity, null));
                tvTitle = itemView.findViewById(R.id.tv_item_second_title);
                tvContent = itemView.findViewById(R.id.tv_item_second_content);
                imageView = itemView.findViewById(R.id.iv_item_second_logo);
                mRlLayout = itemView.findViewById(R.id.rl_item_app_collection);
            }

            private void setData(int index, CollectionBean bean) {
                if (collectionType.equals(Constants.COLLECTION_TYPE_ARTICLE)) {
                    imageView.setCornerRadius(0);
                }
                tvTitle.setText(bean.getTitle());
                tvContent.setText(bean.getShort_desc());
                GlideUtils.loadImageForUrl(mContext, imageView, bean.getImg());

                setOnDeleteListener(index);
                mRlLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDetailsActivity.startActivity(mContext, bean.getId());
                    }
                });
            }


            private void setOnDeleteListener(final int index) {
                itemView.findViewById(R.id.tv_item_collection_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constants.NEED_REFREASH_SENCOND_FG = true;
                        collection(list.get(index));
                        list.remove(index);
                        notifyItemRemoved(index + 1);
                        notifyItemRangeChanged(index + 1, list.size() - index);
                    }
                });
            }
        }

        private class ArticleHolder extends RecyclerView.ViewHolder {
            TextView tvContent;
            NiceImageView imageView;
            RelativeLayout mRlLayout;

            private ArticleHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_collection_activity_article, null));
                tvContent = itemView.findViewById(R.id.tv_item_second_content);
                imageView = itemView.findViewById(R.id.iv_item_second_logo);
                mRlLayout = itemView.findViewById(R.id.rl_item_article_collection);
            }

            private void setData(int index, CollectionBean bean) {
                if (collectionType.equals(Constants.COLLECTION_TYPE_ARTICLE)) {
                    imageView.setCornerRadius(0);
                }
                tvContent.setText(bean.getTitle());
                GlideUtils.loadImageForUrl(mContext, imageView, bean.getImg());
                setOnDeleteListener(index);
                mRlLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (bean.getType()) {
                            case Constants.COLLECTION_TYPE_ARTICLE:
                                CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "detailWebview", new ReactParamsJson.Builder().setWebUrl(bean.getUrl_content()).getRNParams());
                                break;
                            case Constants.COLLECTION_TYPE_TOPIC:
                                CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "detailChat", new ReactParamsJson.Builder().setContentID(bean.getId()).getRNParams());
                                break;
                            case Constants.COLLECTION_TYPE_APP:
                                CommonReactActivity.startActivity(mContext, "MyReactNativeAppthree", "detailWebview", new ReactParamsJson.Builder().setWebUrl(bean.getUrl_content()).getRNParams());
                                break;
                            case Constants.COLLECTION_TYPE_APP_DETAILS:
                                AppDetailsActivity.startActivity(mContext, bean.getId());
                                break;
                        }
                    }
                });
            }


            private void setOnDeleteListener(final int index) {
                itemView.findViewById(R.id.tv_item_collection_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RnCallNativeBean rnCallNativeBean = new RnCallNativeBean();
                        rnCallNativeBean.setId(list.get(index).getId());
                        rnCallNativeBean.setCollection(false);
                        Intent intent = new Intent(Constants.BROADCAST_FLAG_FIRST_FG_FRESH_BY_ID);
                        intent.putExtra(Constants.KEY_FIRSTH_FG_JSON, GsonUtil.GsonString(rnCallNativeBean));
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                        collection(list.get(index));
                        list.remove(index);
                        notifyItemRemoved(index + 1);
                        notifyItemRangeChanged(index + 1, list.size() - index);
                    }
                });
            }
        }

        private void collection(CollectionBean collectionBean) {
            HashMap<String, String> map = new HashMap<>();
            map.put("opt", Constants.OPT_TO_USER_CANCLE_FOLLOW);
            map.put("info_id", collectionBean.getId());
            map.put("type", collectionBean.getType());
            map.put("mobilephone", UserManager.getCurrentUser().getPhone());
            RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                @Override
                public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                    return RetrofitUtils.getInstance(mContext).getApiService().collectionContent(ReqBody.getReqString(map));
                }
            }, new BaseObserver<ResponseData<Object>>() {
                @Override
                protected void onSuccees(ResponseData<Object> objectResponseData) {
                    if (objectResponseData.getStatus() == 0) {
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
