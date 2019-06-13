package com.lieying.petcat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieying.petcat.MainActivity;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.base.BaseV4Fragment;

import java.util.ArrayList;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/26 0026 11:19
 */
public class ThridFragment extends BaseV4Fragment {
    public static ThridFragment newInstance() {
        ThridFragment fragment = new ThridFragment();
        return fragment;
    }
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    TopicFragment f1;
    BaseReactFragment f2;
    @Override
    protected View setContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fgm_third, viewGroup, false);
    }

    @Override
    public void findView() {
        mTabLayout = (TabLayout)findViewById(R.id.tab_third_fragment_progress);
        mViewPager = (ViewPager)findViewById(R.id.vp_fragment_progress);
    }

    @Override
    public void initView() {
        ArrayList<BaseV4Fragment> a = new ArrayList<>();
        f1= TopicFragment.newInstance();
        f2= CommentReactFragment.newInstance("fragment4", "MyReactNativeAppthree", "");
        f2.setFragmentCallback((MainActivity)getActivity());
        f2.setmReactInstanceManager(MainApplication.getInstance().getReactNativeHost().getReactInstanceManager());
        a.add(f1);
        a.add(f2);
        mAdapter = new MyAdapter(getFragmentManager(), a);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_main_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonReactActivity.startActivity(mContext ,"MyReactNativeAppthree" , "search");
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter{
        private ArrayList<BaseV4Fragment> arrayList;
        private String[] mTitles = new String[]{"互动话题", "莓友分享"};

        public MyAdapter(FragmentManager fm, ArrayList<BaseV4Fragment> fragments) {
            super(fm);
            arrayList = fragments;
        }


        @Override
        public Fragment getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
