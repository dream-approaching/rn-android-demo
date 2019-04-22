package com.lieying.socialappstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.comlib.bean.ExploreBean;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.adapter.ExploreAdapter;
import com.lieying.socialappstore.base.BaseV4Fragment;

import java.util.ArrayList;
import java.util.List;

import me.hiten.jkcardlayout.CardLayoutHelper;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/18 0018 16:14
 */
public class IndexFragment extends BaseV4Fragment {
    RecyclerView recyclerView;
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
        List<ExploreBean> list = new ArrayList<>();
        for(int i = 0 ; i <20 ; i++){
            ExploreBean exploreBean = new ExploreBean();
            exploreBean.setContent("第"+i+"个内容，当然我要长一点");
            exploreBean.setTitle("第"+i+"个标题");
            exploreBean.setImgpath("https://xcs.wwlihe.cn/uploads_cms_images/2019040318053241632_%E5%A2%A8%E8%BF%B9%E5%A4%A9%E6%B0%94.png");
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
                .setCardCount(2)
                .setMaxRotation(20f)
                .setOffset(8)
                .setSwipeThreshold(0.2f)
                .setDuration(300);
        mCardLayoutHelper.setConfig(config);

        ExploreAdapter exploreAdapter = new ExploreAdapter(mContext ,list );
        recyclerView.setAdapter(exploreAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
