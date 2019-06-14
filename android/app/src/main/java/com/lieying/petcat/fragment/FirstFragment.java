package com.lieying.petcat.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lieying.comlib.bean.FstFgRcmdBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.adapter.FirstFgFallsAdapter;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * @Description: 默认fragment
 * @Author: liyi
 * @CreateDate: 2019/4/18 0018 16:14
 */
public class FirstFragment extends BaseV4Fragment implements PullToRefreshListener {
    private PullToRefreshRecyclerView recyclerView;
    private FirstFgFallsAdapter firstAdatper;
    private volatile List<FstFgRcmdBean> list = new ArrayList<>();
    private RelativeLayout mCvNodataLayout;
    private MyHandler myHandler;
    private FirstFragment.MainReceiver mMainReceiver;
    private SwipeRefreshLayout mSrf;

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    public void onLoadMore() {
        getData(false);
    }

    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) { //rn产生新数据后这里收到通知
            if(intent.getAction().equals(Constants.BROADCAST_FLAG_FIRST_FG_FRESH_FIRST_ITEM)){
            }
        }
    }

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fgm_index, viewGroup, false);
    }

    @Override
    public void findView() {
        recyclerView =  (PullToRefreshRecyclerView) findViewById(R.id.recyclerView);
        mSrf = (SwipeRefreshLayout) findViewById(R.id.srf_first_fg_fresh);
        mCvNodataLayout = (RelativeLayout) findViewById(R.id.cv_nodate);
        mCvNodataLayout.setVisibility(View.GONE);
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_FLAG_FIRST_FG_FRESH_FIRST_ITEM);
        filter.addAction(Constants.BROADCAST_FLAG_FIRST_FG_FRESH_BY_ID);
        mMainReceiver = new MainReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMainReceiver, filter);
    }

    @Override
    public void initView() {
        firstAdatper = new FirstFgFallsAdapter(mContext, list, new FirstFgFallsAdapter.OnCardClickListener() {
            @Override
            public void onItemClick() {

            }

        });


        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setCanRefresh(false)
                .setCanLoadMore(false)
                .setPullLayoutManager(manager)
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(firstAdatper);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);
                manager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
//                mSrf.setEnabled(recyclerView.isOnTop());
            }
        });
        mSrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(firstAdatper);
    }

    @Override
    public void initData() {
        myHandler = new MyHandler(this);
        getData(true);
    }

    @Override
    public void initListener() {

        findViewById(R.id.iv_item_card_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCvNodataLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @Description: 获取首页卡片数据
     * @Author: liyi
     */
    private void getData(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        if(!isFresh){
            map.put("sharemintime", list.get(list.size() - 1).getSharemintime());
            map.put("videomintime", list.get(list.size() - 1).getVideomintime());
        }
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("pagesize" , Constants.DEFAULT_PAGE_SIZE+"");
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<FstFgRcmdBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<FstFgRcmdBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getFirstFgRcmdData(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<List<FstFgRcmdBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<FstFgRcmdBean>> objectResponseData) {
                mSrf.setRefreshing(false);
                if (recyclerView.isRefresh()) {
                    recyclerView.onPullComplete();
                }
                if (recyclerView.isLoadMore()) {
                    recyclerView.onLoadMoreComplete();
                }
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData().size() > 0) {
                    if (isFresh) {
                        list.clear();
                        list.addAll(objectResponseData.getData());
                        firstAdatper.notifyDataSetChanged();
                    } else {
                        list.addAll(objectResponseData.getData());
                        int start = list.size() - objectResponseData.getData().size();
                        firstAdatper.notifyItemRangeInserted(start, objectResponseData.getData().size());
                    }
                }
                boolean hasMore = (objectResponseData.getData().size() == Constants.DEFAULT_PAGE_SIZE);
                recyclerView.setCanLoadMore(hasMore);
                firstAdatper.setExitsMore(hasMore);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                mSrf.setRefreshing(false);
                if (recyclerView.isRefresh()) {
                    recyclerView.onPullFail();
                }

                if (recyclerView.isLoadMore()) {
                    recyclerView.onLoadMoreFail();
                }
            }
        });
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
        WeakReference<FirstFragment> mWeakReference;

        public MyHandler(FirstFragment activity) {
            mWeakReference = new WeakReference<FirstFragment>(activity);
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
