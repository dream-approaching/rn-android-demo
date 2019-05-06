package com.lieying.socialappstore.fragment;

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

import com.lieying.socialappstore.MainActivity;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseV4Fragment;

import java.util.ArrayList;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/26 0026 11:19
 */
public class NativeThridFragment extends BaseV4Fragment {
    public static NativeThridFragment newInstance() {
        NativeThridFragment fragment = new NativeThridFragment();
        return fragment;
    }
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    TopicFragment f1;
    XFriendShareFragment f2;
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
        mTabLayout.setupWithViewPager(mViewPager);
        ArrayList<BaseV4Fragment> a = new ArrayList<>();
        f1= TopicFragment.newInstance();
        f2= XFriendShareFragment.newInstance("fragment4", "MyReactNativeAppthree", false, "tab3.bundle");
        f2.setFragmentCallback((MainActivity)getActivity());
        f2.setmReactInstanceManager(MainApplication.getInstance().getReactNativeHost().getReactInstanceManager());
        a.add(f1);
        a.add(f2);
        mAdapter = new MyAdapter(getFragmentManager(), a);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private class MyAdapter extends FragmentPagerAdapter{
        private ArrayList<BaseV4Fragment> arrayList;
        private String[] mTitles = new String[]{"互动话题", "X友分享"};

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
