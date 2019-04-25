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
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.socialappstore.activity.LoginActivity;
import com.lieying.socialappstore.base.BaseFragmentActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.callback.FragmentCallback;
import com.lieying.socialappstore.fragment.BaseReactFragment;
import com.lieying.socialappstore.fragment.FirstFragment;
import com.lieying.socialappstore.fragment.IndexFragment;
import com.lieying.socialappstore.fragment.NativeSecondFragment;
import com.lieying.socialappstore.fragment.SecondFragment;
import com.lieying.socialappstore.fragment.ThirdFragment;
import com.lieying.socialappstore.manager.StatusBarUtil;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.utils.GsonUtil;
import com.lieying.socialappstore.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.Arrays;

import com.lieying.socialappstore.manager.ViewFrameManager;
import com.lieying.socialappstore.utils.SharedPreferencesUtil;

public class MainActivity extends BaseFragmentActivity implements FragmentCallback {
    private BottomNavigationView mNavigation;//底部tab
    private final int WRITE_EXTERNAL_STORAGE_REQ_CODE = 1;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<BaseV4Fragment> fragments = new ArrayList<>();
//    BaseReactFragment firstFragment;
    BaseReactFragment secondFragment;
    BaseReactFragment thirdFragment;
    RelativeLayout mRlToorbar;
    TextView mTvToolTitle;
    private int position;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_index:
                    mViewPager.setCurrentItem(0, true);
                    return true;
                case R.id.navigation_task:
                    mViewPager.setCurrentItem(1, true);
                    return true;
                case R.id.navigation_progress:
                    mViewPager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_main:
                    mViewPager.setCurrentItem(3, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        String userInfoString = SharedPreferencesUtil.getInstance().getString(Constants.SP_KEY_USER_INFO);
        if (!TextUtils.isEmpty(userInfoString)) {
            UserInfoBean userInfoBean = GsonUtil.GsonToBean(userInfoString, UserInfoBean.class);
            UserManager.getInstance().setCurrentUser(userInfoBean);
        }
    }

    @Override
    public void findView() {
        mNavigation = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.main_viewpager);
        mRlToorbar = findViewById(R.id.rl_main_activity_top_bar);
        mTvToolTitle = findViewById(R.id.rl_main_activity_top_title);
    }

    @Override
    public void initView() {
        //这个地方根据配置加载fragment，目前只支持动态再前面添加fragment
        SparseArray<ViewFrameManager.IndexFgBean> myViewMap = ViewFrameManager.getInstance().createIndexMap();
        for (int i = 0; i < myViewMap.size(); i++) {
            int key = myViewMap.keyAt(i);
            ViewFrameManager.IndexFgBean user = myViewMap.get(key);
            if (user.isNative()) {
                createNativeFragment(key);
            }
        }
        ReactInstanceManager mReactInstanceManager = MainApplication.getInstance().getReactNativeHost().getReactInstanceManager();
//        firstFragment = FirstFragment.newInstance("fragment1", "MyReactNativeAppthree", false, "tab3.bundle");
//        firstFragment.setFragmentCallback(this);
//        firstFragment.setmReactInstanceManager(mReactInstanceManager);
        secondFragment = SecondFragment.newInstance("fragment2", "MyReactNativeAppthree", false, "tab3.bundle");
        secondFragment.setFragmentCallback(this);
        secondFragment.setmReactInstanceManager(mReactInstanceManager);
        thirdFragment = ThirdFragment.newInstance("fragment3", "MyReactNativeAppthree", false, "tab3.bundle");
        thirdFragment.setFragmentCallback(this);
        thirdFragment.setmReactInstanceManager(mReactInstanceManager);
//        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    private void createNativeFragment(int position) {
        switch (position) {
            case 0:
                IndexFragment indexFragment = IndexFragment.newInstance();
                fragments.add(indexFragment);
                break;
            case 1:
                NativeSecondFragment nativeSecondFragment = NativeSecondFragment.newInstance();
                fragments.add(nativeSecondFragment);
                break;
        }

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
                        mRlToorbar.setVisibility(View.VISIBLE);
                        mTvToolTitle.setText("探索");
                        break;
                    case 1:
                        mRlToorbar.setVisibility(View.VISIBLE);
                        mTvToolTitle.setText("本周排行");
                        break;
                    case 2:
                        mRlToorbar.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mRlToorbar.setVisibility(View.GONE);
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
        private ArrayList<BaseV4Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<BaseV4Fragment> fragments) {
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
        if (fragments.get(position) instanceof BaseReactFragment) {
            ((BaseReactFragment) fragments.get(position)).getmReactInstanceManager().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && fragments.get(position) instanceof BaseReactFragment && ((BaseReactFragment) fragments.get(position)).getmReactInstanceManager() != null) {
            ((BaseReactFragment) fragments.get(position)).getmReactInstanceManager().showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
