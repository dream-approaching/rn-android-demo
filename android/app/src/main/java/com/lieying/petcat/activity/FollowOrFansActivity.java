package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.FollowOrFansBean;
import com.lieying.comlib.bean.OtherUserBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;
import com.lieying.petcat.bean.NoticeBean;
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
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class FollowOrFansActivity extends BaseActivity implements PullToRefreshListener {
    private static String KEY_USER_TYPE = "user_type";
    private static String KEY_USER_PHONE = "user_phone";
    List<OtherUserBean> list = new ArrayList<>();
    private String userTypeFans = Constants.USER_TYPE_FANS;
    private MyAdapter myAdapter;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    TitleView titleView;
    private boolean isFans;
    private boolean isMe;
    private String userPhone;

    public static void startActivity(Context context, String type, String otherPhone) {
        if (UserManager.getInstance().NoUserLogin()) {
            LoginActivity.startActivity(context);
            return;
        }
        Intent intent = new Intent(context, FollowOrFansActivity.class);
        intent.putExtra(KEY_USER_TYPE, type);
        intent.putExtra(KEY_USER_PHONE, otherPhone);
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

        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if(clickedResult!=null){
            if(UserManager.getInstance().NoUserLogin()){
                SplashActivity.startActivity(mContext);
                finish();
                return;
            }
            String ster = clickedResult.getCustomContent();
            NoticeBean noticeBean = GsonUtil.GsonToBean(ster , NoticeBean.class);
            userTypeFans = noticeBean.getType();
            userPhone = noticeBean.getPhone();
        }else{
            if (getIntent().getStringExtra(KEY_USER_TYPE) != null) {
                userTypeFans = getIntent().getStringExtra(KEY_USER_TYPE);
            }
            userPhone = getIntent().getStringExtra(KEY_USER_PHONE);
        }

        if (userTypeFans.equals(Constants.USER_TYPE_FANS)) {
            if (userPhone.equals(UserManager.getCurrentUser().getPhone())) {
                titleView.setTitle("我的粉丝");
            } else {
                titleView.setTitle("ta的粉丝");
            }
            isFans = true;
        } else {
            if (userPhone.equals(UserManager.getCurrentUser().getPhone())) {
                titleView.setTitle(getString(R.string.string_mine) + getString(R.string.string_follow));
            } else {
                titleView.setTitle("ta的"+ getString(R.string.string_follow));
            }
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

    private void getCollectionList(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", userTypeFans);
        if(!isFresh){
            map.put("follow_type", (list.size() > 0 ? list.get(list.size() - 1).getFollow_type() : "2"));
            map.put("id" ,  list.size() == 0 ? "" : list.get(list.size() -1).getId());
        }else{
            map.put("follow_type" , "2");
        }
        map.put("pagesize", Constants.DEFAULT_PAGE_SIZE + "");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("other_mobilephone", userPhone);
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<FollowOrFansBean>>>() {
            @Override
            public ObservableSource<ResponseData<FollowOrFansBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getFollowOrFansList(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<FollowOrFansBean>>() {
            @Override
            protected void onSuccees(ResponseData<FollowOrFansBean> objectResponseData) {
                if (pullToRefreshRecyclerView.isRefresh()) {
                    pullToRefreshRecyclerView.onPullComplete();
                }
                if (pullToRefreshRecyclerView.isLoadMore()) {
                    pullToRefreshRecyclerView.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null && objectResponseData.getData().getList() != null) {
                    isMe = objectResponseData.getData().isIs_me();
                    if (isFresh) {
                        list.clear();
                        list.addAll(objectResponseData.getData().getList());
                        myAdapter.notifyDataSetChanged();
                    } else {
                        list.addAll(objectResponseData.getData().getList());
                        int start = list.size() - objectResponseData.getData().getList().size();
                        myAdapter.notifyItemRangeInserted(start, objectResponseData.getData().getList().size());
                    }
                }
                boolean hasMore = (objectResponseData.getData().getList().size() == Constants.DEFAULT_PAGE_SIZE);
                pullToRefreshRecyclerView.setCanLoadMore(hasMore);
                myAdapter.setExitsMore(hasMore);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                e.printStackTrace();
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
                if (list.size() <= 0) {
                    return new DefaultNoMoreViewHolder(mContext);
                }
                return new DefaultNoMoreViewHolder(mContext);
            }
            if (isMe) {
                return new FansHolder(mContext);
            } else {
                return new FansHolderOhter(mContext);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FansHolder) {
                ((FansHolder) holder).setData(position, list.get(position));
            }else if(holder instanceof FansHolderOhter){
                ((FansHolderOhter) holder).setData(position, list.get(position));
            } else if (holder instanceof DefaultNoMoreViewHolder) {
                if (list.size() <= 0) {
                    ((DefaultNoMoreViewHolder) holder).setTip(isFans ? getString(R.string.string_no_fans) : getString(R.string.string_no_follows));
                } else {
                    ((DefaultNoMoreViewHolder) holder).setTip(getString(R.string.string_no_datas));
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
            ImageView mIvVip;

            private FansHolder(Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.item_follow_or_fans_activity, null));
                tvTitle = itemView.findViewById(R.id.tv_item_fans_title);
                tvContent = itemView.findViewById(R.id.tv_item_fans_content);
                imageView = itemView.findViewById(R.id.iv_item_fans_head_icon);
                tvFansCount = itemView.findViewById(R.id.tv_item_fans_fans);
                mIvStutas = itemView.findViewById(R.id.tv_item_fans_follow_stutas);
                mTvDelete = itemView.findViewById(R.id.tv_item_fans_follow_delete);
                mTvShield = itemView.findViewById(R.id.tv_item_fans_follow_shield);
                mIvVip = itemView.findViewById(R.id.iv_item_fans_vip);
            }

            private void setData(int index, OtherUserBean bean) {
                mIvVip.setVisibility(bean.getIs_big_v().equals("2") ? View.VISIBLE : View.GONE);
                if (isFans) {
                    mTvDelete.setText("删除");
                }else{
                    mTvShield.setVisibility(View.GONE);
                    mTvDelete.setText(getString(R.string.string_cancle)+"\n"+getString(R.string.string_follow));
                }

                tvTitle.setText(bean.getNick_name());
                tvContent.setText(bean.getProfile());
                tvFansCount.setText("粉丝" + bean.getCnt());
                GlideUtils.loadImageForUrl(mContext, imageView, bean.getHead_image());
                setOnDeleteListener(index);
                int res = getSrc(bean.getStatus());
                if (res == 0) {
                    mIvStutas.setVisibility(View.GONE);
                }
                mIvStutas.setImageResource(res);
                mIvStutas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getStatus() == 3) {
                            mIvStutas.setImageResource(R.drawable.ic_stutas_follow_each);
                            follow(bean.getMobilephone() , Constants.OPT_TO_USER_FOLLOW, index);
                        }
                    }
                });
            }

            private void setOnDeleteListener(final int index) {

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserIndexActivity.startActivity(mContext, list.get(index).getMobilephone());
                    }
                });

                mTvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFans){
                            delete(Constants.OPT_TO_USER_CANCLE_FOLLOW, index);
                        }else{
                            follow(list.get(index).getMobilephone() , Constants.OPT_TO_USER_CANCLE_FOLLOW , index);
                        }

                    }
                });
                mTvShield.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete("shield", index);
                    }
                });
            }
        }
    }

    private class FansHolderOhter extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvFansCount;
        NiceImageView imageView;
        ImageView mIvStutas;
        ImageView mIvVip;

        private FansHolderOhter(Context context) {
            super(LayoutInflater.from(context).inflate(R.layout.item_follow_or_fans_activity_other, null));
            tvTitle = itemView.findViewById(R.id.tv_item_fans_title);
            tvContent = itemView.findViewById(R.id.tv_item_fans_content);
            imageView = itemView.findViewById(R.id.iv_item_fans_head_icon);
            tvFansCount = itemView.findViewById(R.id.tv_item_fans_fans);
            mIvStutas = itemView.findViewById(R.id.tv_item_fans_follow_stutas);
            mIvVip = itemView.findViewById(R.id.iv_item_fans_vip);
        }

        private void setData(int index, OtherUserBean bean) {
            mIvVip.setVisibility(bean.getIs_big_v().equals("2") ? View.VISIBLE : View.GONE);

            tvTitle.setText(bean.getNick_name());
            tvContent.setText(bean.getProfile());
            tvFansCount.setText("粉丝" + bean.getCnt());
            GlideUtils.loadImageForUrl(mContext, imageView, bean.getHead_image());
            setOnDeleteListener(index);
            int res = getSrc(bean.getStatus());
            if (res == 0) {
                mIvStutas.setVisibility(View.GONE);
            }
            mIvStutas.setImageResource(res);
            mIvStutas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getStatus() == 3) {
                        mIvStutas.setImageResource(R.drawable.ic_stutas_follow);
                        list.get(index).setStatus(2);
                        follow( bean.getMobilephone() , Constants.OPT_TO_USER_FOLLOW , index);
                    }
                }
            });
        }

        private void setOnDeleteListener(final int index) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserIndexActivity.startActivity(mContext, list.get(index).getMobilephone());
                }
            });
        }
    }

    private int getSrc(int type) {
        switch (type) {
            case 1:   //互相关注
                return R.drawable.ic_stutas_follow_each;
            case 2:   //单方关注
                return R.drawable.ic_stutas_follow;
            case 3:   // 未添加状态
                return R.drawable.ic_stutas_add;
            case 4:
            default:
                return 0;
        }
    }

    private void delete(String type, int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("opt", type);
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
                if (type.equals(Constants.OPT_TO_USER_FOLLOW)) {
                    list.get(position).setStatus(2);
                    myAdapter.notifyItemChanged(position + 1);
                    return;
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    list.remove(position);
                    myAdapter.notifyItemRemoved(position + 1);
                    myAdapter.notifyItemRangeChanged(position + 1, list.size() - position);
                } else {
                    ToastUtil.showToast("操作失败:" + objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_net_errors));
                } else {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    private void follow(String otherPhone , String opt , int position){
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("opt", opt);
        map.put("follow_mobilephone", otherPhone);
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().setFollowUser(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                    if(opt.equals(Constants.OPT_TO_USER_CANCLE_FOLLOW)){
                        list.remove(position);
                        myAdapter.notifyItemRemoved(position + 1);
                        myAdapter.notifyItemRangeChanged(position + 1, list.size() - position);
                    }
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
