package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.CollectionBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.DefaultNoMoreViewHolder;
import com.lieying.socialappstore.widget.NiceImageView;
import com.lieying.socialappstore.widget.SwipeItemLayout;
import com.lieying.socialappstore.widget.TitleView;

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


    public static void startActivity(Context context ,String type) {
        Intent intent = new Intent(context, CollectionActivity.class);
        intent.putExtra(KEY_COLLEECTION_TYPE , type);
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
        titleView.setTitle(collectionType.equals(Constants.COLLECTION_TYPE_APP)?"收藏App应用":"收藏文章");
        getCollectionList(true);
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


    private void getCollectionList(boolean isFresh){
        HashMap<String, String> map = new HashMap<>();
        map.put("type" , collectionType);
        map.put("id" , isFresh || list.size() == 0 ? "" : list.get(list.size() -1).getId());
        map.put("pagesize" , Constants.DEFAULT_PAGE_SIZE+"");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<CollectionBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<CollectionBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getCollectionList(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<List<CollectionBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<CollectionBean>> objectResponseData) {
                if (pullToRefreshRecyclerView.isRefresh()) {
                    pullToRefreshRecyclerView.onPullComplete();
                }
                if (pullToRefreshRecyclerView.isLoadMore()) {
                    pullToRefreshRecyclerView.onLoadMoreComplete();
                }
                if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                    if(isFresh){
                        list.clear();
                        list.addAll(objectResponseData.getData());
                        myAdapter.notifyDataSetChanged();
                    }else {
                        list.addAll(objectResponseData.getData());
                        int start = list.size() - objectResponseData.getData().size();
                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().size());
                        boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                        pullToRefreshRecyclerView.setCanLoadMore(hasMore);
                        myAdapter.setExitsMore(hasMore);
                    }

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
                if(isNetWorkError){
                    ToastUtil.showToast("请求失败");
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
                return new DefaultNoMoreViewHolder(mContext);
            }
            if(collectionType.equals(Constants.COLLECTION_TYPE_APP)){
                return new DraftHolder(mContext);
            }else if(collectionType.equals(Constants.COLLECTION_TYPE_APP)){
                return new ArticleHolder(mContext);
            }else {
                return new DraftHolder(mContext);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof DraftHolder) {
                ((DraftHolder) holder).setData(position, list.get(position));
            } if (holder instanceof ArticleHolder) {
                ((DraftHolder) holder).setData(position, list.get(position));
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                ((DefaultNoMoreViewHolder) holder).setTip(mContext.getString(R.string.app_no_more));
            }
        }


        private class DraftHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvContent;
            NiceImageView imageView;
            private DraftHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_collection_activity, null));
                tvTitle = itemView.findViewById(R.id.tv_item_second_title);
                tvContent = itemView.findViewById(R.id.tv_item_second_content);
                imageView = itemView.findViewById(R.id.iv_item_second_logo);
            }

            private void setData(int index, CollectionBean bean) {
                if(collectionType.equals(Constants.COLLECTION_TYPE_ARTICLE)){
                    imageView.setCornerRadius(0);
                }
                tvTitle.setText(bean.getTitle());
                tvContent.setText(bean.getShort_desc());
                GlideUtils.loadImageForUrl(mContext , imageView , bean.getImg());
                setOnDeleteListener(index);
            }


            private void setOnDeleteListener(final int index) {
                itemView.findViewById(R.id.tv_item_collection_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }

        private class ArticleHolder extends RecyclerView.ViewHolder {
            TextView tvContent;
            NiceImageView imageView;
            private ArticleHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_collection_activity_article, null));
                tvContent = itemView.findViewById(R.id.tv_item_second_content);
                imageView = itemView.findViewById(R.id.iv_item_second_logo);
            }

            private void setData(int index, CollectionBean bean) {
                if(collectionType.equals(Constants.COLLECTION_TYPE_ARTICLE)){
                    imageView.setCornerRadius(0);
                }
                tvContent.setText(bean.getTitle());
                GlideUtils.loadImageForUrl(mContext , imageView , bean.getImg());
                setOnDeleteListener(index);
            }


            private void setOnDeleteListener(final int index) {
                itemView.findViewById(R.id.tv_item_collection_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }
}
