package com.lieying.socialappstore;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseFragmentActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.callback.FragmentCallback;
import com.lieying.socialappstore.fragment.BaseReactFragment;
import com.lieying.socialappstore.fragment.FirstFragment;
import com.lieying.socialappstore.fragment.SecondFragment;
import com.lieying.socialappstore.fragment.ThirdFragment;
import com.lieying.socialappstore.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements FragmentCallback {
    private BottomNavigationView mNavigation;//底部tab
    private final int WRITE_EXTERNAL_STORAGE_REQ_CODE = 1;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<BaseReactFragment> fragments = new ArrayList<>();
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    private int position;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_task:
                    mViewPager.setCurrentItem(0, true);
                    return true;
                case R.id.navigation_progress:
                    mViewPager.setCurrentItem(1, true);
                    return true;
                case R.id.navigation_main:
                    mViewPager.setCurrentItem(2, true);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void setContentView(Bundle bundle) {

        setContentView(R.layout.activity_main);
    }

    @Override
    public void findView() {
        mNavigation = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.main_viewpager);
    }

    @Override
    public void initView() {
        firstFragment = FirstFragment.newInstance("tab1" ,"MyReactNativeApp" , true);
        firstFragment.setFragmentCallback(this);
        secondFragment = SecondFragment.newInstance("tab2" ,"MyReactNativeApptwo" , false);
        secondFragment.setFragmentCallback(this);
        thirdFragment = ThirdFragment.newInstance("tab3" ,"MyReactNativeAppthree" , false);
        thirdFragment.setFragmentCallback(this);
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initData() {

        PermissionUtil.checkPermission(mContext, WRITE_EXTERNAL_STORAGE_REQ_CODE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int id = mNavigation.getMenu().getItem(i).getItemId();
                mNavigation.setSelectedItemId(id);
                position = i;
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQ_CODE) {

        }
    }

    @Override
    public void initReactManager(ReactInstanceManager reactInstanceManager) {

    }
    private long mExitTime;
    @Override
    public void fragmentBack() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<BaseReactFragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<BaseReactFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onBackPressed() {
        fragments.get(position).getmReactInstanceManager().onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
