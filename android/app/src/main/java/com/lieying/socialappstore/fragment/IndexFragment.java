package com.lieying.socialappstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.comlib.bean.ExploreBean;
import com.lieying.socialappstore.activity.SettingActivity;
import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.comlib.utils.ScreenUtils;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.activity.CommonReactActivity;
import com.lieying.socialappstore.adapter.ExploreAdapter;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.cardlayout.CardLayoutHelper;

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
public class IndexFragment extends BaseV4Fragment {
    private RecyclerView recyclerView;
    private ExploreAdapter exploreAdapter;
    private volatile List<ExploreBean> list = new ArrayList<>();
    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    public void initView() {
        for(int i = 0 ; i <20 ; i++){
            ExploreBean exploreBean = new ExploreBean();
            exploreBean.setTitle("第"+i+"个内容，当然我要长一点");
            exploreBean.setType("1");
            exploreBean.setImg("https://xcs.wwlihe.cn/uploads_cms_images/2019040318053241632_%E5%A2%A8%E8%BF%B9%E5%A4%A9%E6%B0%94.png");
            list.add(exploreBean);
        }
        CardLayoutHelper mCardLayoutHelper =  new  CardLayoutHelper();
        mCardLayoutHelper.attachToRecyclerView(recyclerView);
        mCardLayoutHelper.bindDataSource(new CardLayoutHelper.BindDataSource() {
            @Override
            public List bind() {
                return list;
            }
        });
        CardLayoutHelper.Config config = new CardLayoutHelper.Config()
                .setCardCount(3)
                .setMaxRotation(20f)
                .setOffset(ScreenUtils.dip2px(mContext , 4))
                .setSwipeThreshold(0.2f)
                .setDuration(300);
        mCardLayoutHelper.setConfig(config);
         exploreAdapter = new ExploreAdapter(mContext, list, new ExploreAdapter.OnCardClickListener() {
            @Override
            public void back() {
                mCardLayoutHelper.doBack();
            }

             @Override
             public void comments(ExploreBean exploreBean) {
//                 CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , new ReactParamsJson.Builder().setContentID(exploreBean.getId()).setContentType(exploreBean.getType()).getRNParams());
                 SettingActivity.startActivity(mContext);
            }



             @Override
             public void collection() {

             }

             @Override
             public void moveEnd() {
                 list.addAll(list);
                 exploreAdapter.notifyDataSetChanged();
             }
         });
        recyclerView.setAdapter(exploreAdapter);
    }

    @Override
    public void initData() {
        getCardData();
    }

    @Override
    public void initListener() {

    }
    /**
      * @Description:    获取首页卡片数据
      * @Author:         liyi
     */
    private void getCardData(){
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<List<ExploreBean>>>>() {
            @Override
            public ObservableSource<ResponseData<List<ExploreBean>>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getExploreInfo(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<List<ExploreBean>>>() {
            @Override
            protected void onSuccees(ResponseData<List<ExploreBean>> objectResponseData) {
                if(objectResponseData.getStatus()==0){
                    list.clear();
                    list.addAll(objectResponseData.getData());
                    exploreAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if(isNetWorkError){
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

}
