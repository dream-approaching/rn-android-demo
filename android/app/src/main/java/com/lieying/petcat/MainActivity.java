package com.lieying.petcat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.gyf.immersionbar.ImmersionBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.activity.UserDataActivity;
import com.lieying.petcat.base.BaseFragmentActivity;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.callback.FragmentCallback;
import com.lieying.petcat.fragment.BaseReactFragment;
import com.lieying.petcat.fragment.FirstFragment;
import com.lieying.petcat.fragment.SecondFragment;
import com.lieying.petcat.fragment.ThridFragment;
import com.lieying.petcat.fragment.FourthFragment;
import com.lieying.petcat.manager.StatusBarUtil;
import com.lieying.petcat.utils.PermissionUtil;

import java.util.ArrayList;

import com.lieying.petcat.manager.ViewFrameManager;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.petcat.videorecord.TCVideoRecordActivity;
import com.lieying.petcat.widget.CustomViewPager;
import com.weiwang.photoalbumlibrary.matisse.Matisse;
import com.weiwang.photoalbumlibrary.matisse.MimeType;
import com.weiwang.photoalbumlibrary.matisse.engine.impl.GlideEngine;

public class MainActivity extends BaseFragmentActivity implements FragmentCallback {
    private BottomNavigationViewEx mNavigation;//底部tab
    private static final int REQUEST_CODE_CHOOSE = 3001;
    private final int WRITE_EXTERNAL_STORAGE_REQ_CODE = 1;
    private CustomViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ImageView mIvSearch;
    private ArrayList<BaseV4Fragment> fragments = new ArrayList<>();
    BaseReactFragment thirdFragment;
    RelativeLayout mRlToorbar;
    TextView mTvToolTitle;
    private int position;
    private MainReceiver mMainReceiver;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
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
                case R.id.navigation_add:
                    startActivity(new Intent(mContext, TCVideoRecordActivity.class));

//                    Matisse.from(MainActivity.this)
//                            .choose(MimeType.ofAll())
//                            .theme(R.style.Matisse_Dracula)
//                            .countable(false)
//                            .maxSelectable(9)
//                            .originalEnable(true)
//                            .maxOriginalSize(10)
//                            .imageEngine(new GlideEngine())
//                            .forResult(REQUEST_CODE_CHOOSE);
                    return false;
            }
            return false;
        }
    };

    @Override
    protected void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    public void findView() {
        mNavigation = findViewById(R.id.navigation);
        mNavigation.setItemIconTintList(null);
        mViewPager = findViewById(R.id.main_viewpager);
        mViewPager.setScanScroll(false);
        mRlToorbar = findViewById(R.id.rl_main_activity_top_bar);
        mTvToolTitle = findViewById(R.id.rl_main_activity_top_title);
        mIvSearch = findViewById(R.id.iv_main_search);

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
        thirdFragment = FourthFragment.newInstance("fragment3", "MyReactNativeAppthree","");
        thirdFragment.setFragmentCallback(this);
        thirdFragment.setmReactInstanceManager(mReactInstanceManager);
        fragments.add(thirdFragment);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    private void createNativeFragment(int position) {
        switch (position) {
            case 0:
                FirstFragment firstFragment = FirstFragment.newInstance();
                fragments.add(firstFragment);
                break;
            case 1:
                SecondFragment secondFragment = SecondFragment.newInstance();
                fragments.add(secondFragment);
                break;
            case 2:
                ThridFragment thridFragment = ThridFragment.newInstance();
                fragments.add(thridFragment);
                break;
        }

    }


    @Override
    public void initData() {
        PermissionUtil.checkPermission(mContext, WRITE_EXTERNAL_STORAGE_REQ_CODE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_FLAG_REFRESH_MAIN);

        mMainReceiver = new MainReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMainReceiver, filter);
    }

    private class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) { //刷新主界面三个fragment数据
            for(BaseV4Fragment fragment : fragments){
                fragment.initData();
            }
        }
    }

    @Override
    public void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i>2){
                    i++;
                }
                int id = mNavigation.getMenu().getItem(i).getItemId();
                mNavigation.setSelectedItemId(id);
                position = i;
                switch (i) {
                    case 0:
                        mRlToorbar.setVisibility(View.VISIBLE);
                        mTvToolTitle.setText(mContext.getString(R.string.string_first_fg_title));
                        StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, true);
                        break;
                    case 1:
                        mRlToorbar.setVisibility(View.VISIBLE);
                        mTvToolTitle.setText(mContext.getString(R.string.string_second_fg_title));
                        StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, true);
                        break;
                    case 2:
                        StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, true);
                        mRlToorbar.setVisibility(View.GONE);
                        break;
                    case 3:
                        mRlToorbar.setVisibility(View.GONE);
                        StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonReactActivity.startActivity(mContext ,"MyReactNativeAppthree" , "search");
            }
        });
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

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
