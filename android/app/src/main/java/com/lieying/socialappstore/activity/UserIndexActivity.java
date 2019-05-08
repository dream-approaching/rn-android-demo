package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.AppDetailsBean;
import com.lieying.comlib.bean.UserIndexInfoBean;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.base.BaseFragmentActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.fragment.UserIndexJoinFragment;
import com.lieying.socialappstore.fragment.UserIndexShareFragment;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.NiceImageView;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class UserIndexActivity extends BaseFragmentActivity {
    private static String KEY_USER_PHONE = "KEY_USER_PHONE";
    NiceImageView mIVUserHeadIcon;
    TextView mTVUserName;
    TextView mTVUserPosition;
    TextView mTVUserFollowCount;
    TextView mTVUserFansCount;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseV4Fragment f1;
    private BaseV4Fragment f2;
    private MyAdapter mAdapter;

    public static void startActivity(Context context , String userPhone) {
        Intent intent = new Intent(context, UserIndexActivity.class);
        intent.putExtra(KEY_USER_PHONE , userPhone);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(false).keyboardEnable(true).init();
        setContentView(R.layout.activity_user_index);
    }

    @Override
    public void findView() {
        mTabLayout = findViewById(R.id.tab_user_index);
        mViewPager = findViewById(R.id.vp_user_index);
        mIVUserHeadIcon = findViewById(R.id.iv_user_index_head_icon);
        mTVUserName = findViewById(R.id.tv_user_index_user_nick);
        mTVUserPosition = findViewById(R.id.tv_user_index_user_position);
        mTVUserFollowCount = findViewById(R.id.tv_user_index_follow_count);
        mTVUserFansCount = findViewById(R.id.tv_user_index_fans_count);
    }

    @Override
    public void initView() {
        mTabLayout.setupWithViewPager(mViewPager);
        ArrayList<BaseV4Fragment> a = new ArrayList<>();
        f1= UserIndexShareFragment.newInstance();
        f2= UserIndexJoinFragment.newInstance();
        a.add(f1);
        a.add(f2);
        mAdapter = new MyAdapter(getSupportFragmentManager(), a);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_id", "1");
        map.put("app_ver", "1");
        map.put("app_ver_code", "1");
        map.put("ch", "1");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("othermobilephone", "18503068868");
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<UserIndexInfoBean>>>() {
            @Override
            public ObservableSource<ResponseData<UserIndexInfoBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getUserIndexInfo(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<UserIndexInfoBean>>() {
            @Override
            protected void onSuccees(ResponseData<UserIndexInfoBean> objectResponseData) {
                if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                    parseData(objectResponseData.getData());
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

    @Override
    public void initListener() {

    }

    private void parseData(UserIndexInfoBean userIndexInfoBean){
        GlideUtils.loadImageForUrl(mContext, mIVUserHeadIcon, userIndexInfoBean.getUserinfo().getHead_image());
        mTVUserName.setText(userIndexInfoBean.getUserinfo().getNick_name());
        mTVUserFollowCount.setText(userIndexInfoBean.getUserinfo().getGuan()+"   关  注");
        mTVUserFansCount.setText(userIndexInfoBean.getUserinfo().getFen()+"   粉  丝");
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private ArrayList<BaseV4Fragment> arrayList;
        private String[] mTitles = new String[]{"分享动态", "参与互动"};

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
