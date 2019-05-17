package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.OtherUserBean;
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

public class FollowOrFansActivity extends BaseActivity implements PullToRefreshListener {
    private static String KEY_USER_TYPE = "user_type";
    List<OtherUserBean> list = new ArrayList<>();
    private String userTypeFans = Constants.USER_TYPE_FANS;
    private MyAdapter myAdapter;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    TitleView titleView;
    private boolean isFans;
    public static void startActivity(Context context ,String type) {
        Intent intent = new Intent(context, FollowOrFansActivity.class);
        intent.putExtra(KEY_USER_TYPE , type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
          ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_follow_or_fans);
    }

    @Override
    public void findView() {
        pullToRefreshRecyclerView = findViewById(R.id.recycler_view);
        titleView = findViewById(R.id.title_fans_title);
    }

    @Override
    public void initView() {
        myAdapter = new MyAdapter();
        pullToRefreshRecyclerView.setCanRefresh(true)
                .setPullLayoutManager(new LinearLayoutManager(mContext))
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);
        pullToRefreshRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    @Override
    public void initData() {
        if( getIntent().getStringExtra(KEY_USER_TYPE)!=null){
            userTypeFans = getIntent().getStringExtra(KEY_USER_TYPE);
        }
        if(userTypeFans.equals(Constants.USER_TYPE_FANS)){
            titleView.setTitle("我的粉丝");
            isFans = true;
        }else {
            titleView.setTitle("我的关注");
        }
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

    @Override
    public void onRefresh() {
        getCollectionList(true);
    }

    @Override
    public void onLoadMore() {
        getCollectionList(false);
    }

    private void getCollectionList(boolean isFresh){
        HashMap<String, String> map = new HashMap<>();
        map.put("type" , userTypeFans);
        map.put("follow_type" , (list.size()>0 ? list.get(list.size()-1).getFollow_type():"2"));
//        map.put("id" , isFresh || list.size() == 0 ? "" : list.get(list.size() -1).get());
        map.put("pagesize" , Constants.DEFAULT_PAGE_SIZE+"");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<OtherUserBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<OtherUserBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getFollowOrFansList(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<List<OtherUserBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<OtherUserBean>> objectResponseData) {
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
                    }
                }
                boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                pullToRefreshRecyclerView.setCanLoadMore(hasMore);
                myAdapter.setExitsMore(hasMore);
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
                return new FansHolder(mContext);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FansHolder) {
                ((FansHolder) holder).setData(position, list.get(position));
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                if(list.size()<=0 ){
                    ((DefaultNoMoreViewHolder) holder).setTip(isFans? "暂无粉丝" : "还没有关注的人");
                }else {
                    ((DefaultNoMoreViewHolder) holder).setTip("没有更多了");
                }

            }
        }



        private class FansHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvContent;
            TextView tvFansCount;
            NiceImageView imageView;
            ImageView mIvStutas;
            TextView mTvDelete;
            TextView mTvShield;
            private FansHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_follow_or_fans_activity, null));
                tvTitle = itemView.findViewById(R.id.tv_item_fans_title);
                tvContent = itemView.findViewById(R.id.tv_item_fans_content);
                imageView = itemView.findViewById(R.id.iv_item_fans_head_icon);
                tvFansCount = itemView.findViewById(R.id.tv_item_fans_fans);
                mIvStutas = itemView.findViewById(R.id.tv_item_fans_follow_stutas);
                mTvDelete = itemView.findViewById(R.id.tv_item_fans_follow_delete);
                mTvShield = itemView.findViewById(R.id.tv_item_fans_follow_shield);
            }

            private void setData(int index, OtherUserBean bean) {
                if(!isFans){
                    mTvShield.setVisibility(View.GONE);
                }
                tvTitle.setText(bean.getNick_name());
                tvContent.setText(bean.getProfile());
                tvFansCount.setText("粉丝"+bean.getCnt());
                GlideUtils.loadImageForUrl(mContext, imageView, bean.getHead_image());
                setOnDeleteListener(index);
                mIvStutas.setImageResource(bean.getFollow_type().equals("1") ? R.drawable.ic_stutas_follow : R.drawable.ic_stutas_follow_each);
            }


            private void setOnDeleteListener(final int index) {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserIndexActivity.startActivity(mContext , list.get(index).getMobilephone());
                    }
                });

                mTvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete("del" , index);
                    }
                });
                mTvShield.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete("shield" ,index);
                    }
                });
            }
            private void delete(String type , int position){
                HashMap<String, String> map = new HashMap<>();
                map.put("access_token", UserManager.getCurrentUser().getAccessToken());
                map.put("opt" , type);
                map.put("mobilephone", UserManager.getCurrentUser().getPhone());
                map.put("fun_mobilephone", list.get(position).getMobilephone());
                RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                    @Override
                    public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                        return RetrofitUtils.getInstance(mContext).getApiService().deleteFans(ReqBody.getReqString(map));
                    }
                }, new BaseObserver<ResponseData<Object>>() {
                    @Override
                    protected void onSuccees(ResponseData<Object> objectResponseData) {
                        if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                            list.remove(position);
                            notifyItemRemoved(position + 1);
                            notifyItemRangeChanged(position + 1, list.size() - position);
                        } else {
                            ToastUtil.showToast("操作失败:"+objectResponseData.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) {
                        if (isNetWorkError) {
                            ToastUtil.showToast("网络层错误");
                        } else {
                            ToastUtil.showToast("请求失败");
                        }
                    }
                });
            }

        }
    }
}
