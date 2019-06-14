package com.lieying.petcat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.comlib.bean.SecondFgBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.pull.PullToRefreshListener;
import com.lieying.comlib.pull.PullToRefreshRecyclerView;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.adapter.SecondFgMainAdapter;
import com.lieying.petcat.adapter.SecondFgTopAdapter;
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
 * @Description: 本地Fragment
 * @Author: liyi
 * @CreateDate: 2019/4/24 0024 17:24
 */
public class SecondFragment extends BaseV4Fragment implements PullToRefreshListener {
    private List<SecondFgBean.ListBean> mLists = new ArrayList<>();
    private List<SecondFgBean.LikeBean> mTopLists = new ArrayList<>();
    private PullToRefreshRecyclerView mRecyclerVBottom;
    private RecyclerView mRecyclerVTop;
    SecondFgMainAdapter myAdapter;
    SecondFgTopAdapter mTopAdapter;
    private Handler myHandler;
    private SwipeRefreshLayout mSrf;
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
        mSrf = (SwipeRefreshLayout)findViewById(R.id.srf_second_fg_fresh);
        mRecyclerVBottom = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view_bottom);
        mRecyclerVTop = (RecyclerView) findViewById(R.id.recycler_view_top);
    }

    @Override
    public void initView() {
        mTopAdapter = new SecondFgTopAdapter(mContext , mTopLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerVTop.setLayoutManager(linearLayoutManager);
        mRecyclerVTop.setAdapter(mTopAdapter);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        myAdapter = new SecondFgMainAdapter(mContext , mLists);
        mRecyclerVBottom.setCanRefresh(false)
                .setCanLoadMore(false)
                .setPullLayoutManager(manager)
                .setPullToRefreshListener(this)
                .setPullItemAnimator(null)
                .build(myAdapter);


    }

    @Override
    public void initData() {
        myHandler = new MyHandler(this);
        getData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constants.NEED_REFREASH_SENCOND_FG){
            getData(true);
            Constants.NEED_REFREASH_SENCOND_FG = false;
        }
    }

    @Override
    public void initListener() {
        mSrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });
    }

    /**
     * @Description: 获取本周排行
     * @Author: liyi
     */
    private void getData(boolean isFresh) {
        HashMap<String, String> map = new HashMap<>();
        if(!isFresh){
            map.put("sharemintime", mLists.get(mLists.size() - 1).getSharemintime()+"");
            map.put("videomintime", mLists.get(mLists.size() - 1).getVideomintime()+"");
        }
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("pagesize" , Constants.DEFAULT_PAGE_SIZE+"");
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<SecondFgBean>>>() {
            @Override
            public ObservableSource<ResponseData<SecondFgBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getSecondFgRData(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<SecondFgBean>>() {
            @Override
            protected void onSuccees(ResponseData<SecondFgBean> objectResponseData) {
                mSrf.setRefreshing(false);
                if (mRecyclerVBottom.isRefresh()) {
                    mRecyclerVBottom.onPullComplete();
                }
                if (mRecyclerVBottom.isLoadMore()) {
                    mRecyclerVBottom.onLoadMoreComplete();
                }
                if(objectResponseData.getStatus() != 0&& objectResponseData.getData() != null){
                    return;
                }
                if ( objectResponseData.getData().getList()!=null) {
                    if (isFresh) {
                        mLists.clear();
                        mLists.addAll(objectResponseData.getData().getList());
                        myAdapter.notifyDataSetChanged();
                    } else {
                        mLists.addAll(objectResponseData.getData().getList());
                        int start = mLists.size() - objectResponseData.getData().getList().size();
                        myAdapter.notifyItemRangeInserted(start,objectResponseData.getData().getList().size());
                    }
                    mRecyclerVBottom.setCanLoadMore(false);
                    myAdapter.setExitsMore(false);
                }

                if(objectResponseData.getData().getLike()!=null){
                    mTopLists.clear();
                    mTopLists.addAll(objectResponseData.getData().getLike());
                    mTopAdapter.notifyDataSetChanged();
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                mSrf.setRefreshing(false);
                if (mRecyclerVBottom.isRefresh()) {
                    mRecyclerVBottom.onPullFail();
                }

                if (mRecyclerVBottom.isLoadMore()) {
                    mRecyclerVBottom.onLoadMoreFail();
                }
                if (isNetWorkError) {
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    public void onLoadMore() {
        getData(false);
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
