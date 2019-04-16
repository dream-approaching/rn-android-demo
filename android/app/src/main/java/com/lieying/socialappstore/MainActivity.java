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

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseFragmentActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.fragment.FirstFragment;
import com.lieying.socialappstore.fragment.SecondFragment;
import com.lieying.socialappstore.fragment.ThirdFragment;
import com.lieying.socialappstore.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {
    private BottomNavigationView mNavigation;//底部tab
    private final int WRITE_EXTERNAL_STORAGE_REQ_CODE = 1;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    private List<ReactInstanceManager> reactInstanceManagerList;
    private ReactInstanceManager mReactInManager_one;
    private ReactInstanceManager mReactInManager_two;
    private ReactInstanceManager mReactInManager_three;
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
        firstFragment = FirstFragment.newInstance("" ,"");
        secondFragment = SecondFragment.newInstance("" ,"");
        thirdFragment = ThirdFragment.newInstance("" ,"");
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOffscreenPageLimit(3);
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
        switch (position){
            case 0:
                if (firstFragment != null && firstFragment.getmReactInstanceManager()!=null) {
                    firstFragment.getmReactInstanceManager().onBackPressed();
                } else {
                    super.onBackPressed();
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    public void initReactManager(List<String> fgs){
        for(int i = 0 ; i <fgs.size() ;i++){
            ReactInstanceManager reactInstanceManager =  ReactInstanceManager.builder()
                    .setApplication(getApplication())
                    .setCurrentActivity(this)
                    .setBundleAssetName(fgs.get(i))
                    .setJSMainModulePath("index")
                    .addPackage(new MainReactPackage())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();
            reactInstanceManagerList.add(reactInstanceManager);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
