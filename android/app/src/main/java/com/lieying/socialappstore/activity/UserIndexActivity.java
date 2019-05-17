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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.react.ReactInstanceManager;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserIndexInfoBean;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseFragmentActivity;
import com.lieying.socialappstore.base.BaseLoadingFragmentActivity;
import com.lieying.socialappstore.base.BaseV4Fragment;
import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.socialappstore.callback.FragmentCallback;
import com.lieying.socialappstore.fragment.CommentReactFragment;
import com.lieying.socialappstore.fragment.UserIndexJoinFragment;
import com.lieying.socialappstore.fragment.UserIndexShareFragment;
import com.lieying.socialappstore.fragment.XFriendShareFragment;
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

public class UserIndexActivity extends BaseLoadingFragmentActivity implements FragmentCallback {
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
    private String phone;
    private ImageView mIvVip;

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
        mIvVip = findViewById(R.id.iv_user_index_vip);
    }

    @Override
    public void initView() {
        phone = getIntent().getStringExtra(KEY_USER_PHONE);
        mTabLayout.setupWithViewPager(mViewPager);
        ArrayList<BaseV4Fragment> a = new ArrayList<>();
        String params = new ReactParamsJson.Builder().setUserPhone(phone).getRNParams();
        f1= CommentReactFragment.newInstance("myShare", "MyReactNativeAppthree", params);
        ((CommentReactFragment) f1).setFragmentCallback(this);
        f2 = UserIndexJoinFragment.newInstance();
        a.add(f1);
        a.add(f2);
        mAdapter = new MyAdapter(getSupportFragmentManager(), a);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void initData() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        map.put("othermobilephone", phone);
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<UserIndexInfoBean>>>() {
            @Override
            public ObservableSource<ResponseData<UserIndexInfoBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getUserIndexInfo(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<UserIndexInfoBean>>() {
            @Override
            protected void onSuccees(ResponseData<UserIndexInfoBean> objectResponseData) {
                dissMissDialog();
                if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){
                    parseData(objectResponseData.getData());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                dissMissDialog();
                if(isNetWorkError){
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

    @Override
    public void initListener() {
        findViewById(R.id.iv_user_index_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void parseData(UserIndexInfoBean userIndexInfoBean){
        GlideUtils.loadImageForUrl(mContext, mIVUserHeadIcon, userIndexInfoBean.getUserinfo().getHead_image());
        mTVUserName.setText(userIndexInfoBean.getUserinfo().getNick_name());
        mTVUserFollowCount.setText(userIndexInfoBean.getUserinfo().getGuan()+"   关  注");
        mTVUserFansCount.setText(userIndexInfoBean.getUserinfo().getFen()+"   粉  丝");
        mIvVip.setVisibility(userIndexInfoBean.getUserinfo().getIs_big_v().equals("1") ? View.GONE : View.VISIBLE);
    }

    @Override
    public void initReactManager(ReactInstanceManager reactInstanceManager) {

    }

    @Override
    public void fragmentBack() {

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
