package com.lieying.petcat.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.NativeCallRnBean;
import com.lieying.comlib.bean.UserIndexInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseLoadingFragmentActivity;
import com.lieying.petcat.base.BaseV4Fragment;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.petcat.callback.FragmentCallback;
import com.lieying.petcat.fragment.UserIndexJoinFragment;
import com.lieying.petcat.fragment.UserIndexShareFragment;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.GsonUtil;
import com.lieying.petcat.utils.ToastUtil;
import com.lieying.petcat.widget.NiceImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class UserIndexActivity extends BaseLoadingFragmentActivity implements FragmentCallback {
    private static String KEY_USER_PHONE = "KEY_USER_PHONE";
    NiceImageView mIVUserHeadIcon;
    TextView mTVUserName;
    TextView mTVUserPosition;
    TextView mTVUserPro;
    TextView mTVUserFollowCount;
    TextView mTVUserFansCount;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseV4Fragment f1;
    private BaseV4Fragment f2;
    private MyAdapter mAdapter;
    private String phone;
    private ImageView mIvVip;
    private TextView mTvFollow;
    private AppBarLayout mAppBar;
    UserIndexInfoBean userIndexInfoBean;
    UserIndexActivity.MyHandler myHandler = new UserIndexActivity.MyHandler(this);

    private NiceImageView mIvBarHeader;
    private TextView mTvBarNick;
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
        mTVUserPro = findViewById(R.id.tv_user_index_user_dignity);
        mTVUserFollowCount = findViewById(R.id.tv_user_index_follow_count);
        mTVUserFansCount = findViewById(R.id.tv_user_index_fans_count);
        mIvVip = findViewById(R.id.iv_user_index_vip);
        mTvFollow = findViewById(R.id.tv_user_index_follow);
        mAppBar = findViewById(R.id.abl_scoll_bar);
        mIvBarHeader = findViewById(R.id.iv_user_index_bar_head_icon);
        mTvBarNick = findViewById(R.id.iv_user_index_bar_nick_name);
    }

    @Override
    public void initView() {
        phone = getIntent().getStringExtra(KEY_USER_PHONE);
        mTabLayout.setupWithViewPager(mViewPager);
        ArrayList<BaseV4Fragment> a = new ArrayList<>();
        String params = new ReactParamsJson.Builder().setUserPhone(phone).getRNParams();
        f1= UserIndexShareFragment.newInstance(phone);
        f2 = UserIndexJoinFragment.newInstance(phone);
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
                    userIndexInfoBean = objectResponseData.getData();
                    parseData();
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                dissMissDialog();
                if(isNetWorkError){
                    ToastUtil.showToast(getString(R.string.string_request_fails));
                }
            }
        });
    }

    @Override
    public void initListener() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                ((UserIndexJoinFragment)f2).setCanRefresh(verticalOffset==0);//刷新控件设置是否刷新
                ((UserIndexShareFragment)f1).setCanRefresh(verticalOffset==0);//刷新控件设置是否刷新
            }
        });
        findViewById(R.id.iv_user_index_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance().NoUserLogin()){
                    LoginActivity.startActivity(mContext);
                    return;
                }
                follow(userIndexInfoBean.getUserinfo().isIs_add_friends() ? Constants.OPT_TO_USER_FOLLOW : Constants.OPT_TO_USER_CANCLE_FOLLOW);

                userIndexInfoBean.getUserinfo().setIs_add_friends(!userIndexInfoBean.getUserinfo().isIs_add_friends());
                mTvFollow.setText(userIndexInfoBean.getUserinfo().isIs_add_friends() ? getString(R.string.string_add_follow) :getString(R.string.string_cancle)+ getString(R.string.string_follow));

                NativeCallRnBean nativeCallRnBean = new NativeCallRnBean();
                nativeCallRnBean.setOption(Constants.OPTION_SET_FOLLOW);
                nativeCallRnBean.setMobilephone(userIndexInfoBean.getUserinfo().getMobilephone());
                nativeCallRnBean.setAttention(userIndexInfoBean.getUserinfo().isIs_add_friends());
                MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(Constants.NATIVE_CALL_RN_PARAMS, GsonUtil.GsonString(nativeCallRnBean));
                int fansCount = Integer.parseInt(userIndexInfoBean.getUserinfo().getFen());
                fansCount = userIndexInfoBean.getUserinfo().isIs_add_friends() ? fansCount-1 : fansCount +1;
                userIndexInfoBean.getUserinfo().setFen(fansCount+"");
                mTVUserFansCount.setText(userIndexInfoBean.getUserinfo().getFen()+"   粉  丝");

            }
        });
    }

    private void parseData(){
        if(userIndexInfoBean.getUserinfo().isIs_self()){
            mTvFollow.setVisibility(View.GONE);
        }else{
            mTvFollow.setVisibility(View.VISIBLE);
            mTvFollow.setText(userIndexInfoBean.getUserinfo().isIs_add_friends() ? "+关注" : "取消关注");
        }


        GlideUtils.loadImageForUrl(mContext, mIVUserHeadIcon, userIndexInfoBean.getUserinfo().getHead_image() , R.drawable.ic_default_header);
        GlideUtils.loadImageForUrl(mContext, mIvBarHeader, userIndexInfoBean.getUserinfo().getHead_image() , R.drawable.ic_default_header);
        mTVUserName.setText(userIndexInfoBean.getUserinfo().getNick_name());
        mTvBarNick.setText(userIndexInfoBean.getUserinfo().getNick_name());
        mTVUserFollowCount.setText(userIndexInfoBean.getUserinfo().getGuan()+"   关  注");
        mTVUserFansCount.setText(userIndexInfoBean.getUserinfo().getFen()+"   粉  丝");
        mTVUserPosition.setText(userIndexInfoBean.getUserinfo().getLocation()+ "-" + userIndexInfoBean.getUserinfo().getCareer());
        mTVUserPro.setText(userIndexInfoBean.getUserinfo().getProfile());
        mIvVip.setVisibility(userIndexInfoBean.getUserinfo().getIs_big_v().equals("1") ? View.GONE : View.VISIBLE);

        mTVUserFollowCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowOrFansActivity.startActivity(mContext , Constants.USER_TYPE_FOLLOWS ,  phone);
            }
        });

        mTVUserFansCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowOrFansActivity.startActivity(mContext , Constants.USER_TYPE_FANS , phone);
            }
        });
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
    private static class MyHandler extends Handler {
        WeakReference<UserIndexActivity> mWeakReference;

        public MyHandler(UserIndexActivity activity) {
            mWeakReference = new WeakReference<UserIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            HashMap<String, String> map = new HashMap<>();
            map.put("mobilephone", UserManager.getCurrentUser().getPhone());
            map.put("opt", bundle.getString("opt"));
            map.put("follow_mobilephone", bundle.getString("follow_mobilephone"));
            map.put("access_token", UserManager.getCurrentUser().getAccessToken());
            RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                @Override
                public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                    return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().setFollowUser(ReqBody.getReqString(map));
                }
            } , new BaseObserver<ResponseData<Object>>() {
                @Override
                protected void onSuccees(ResponseData<Object> objectResponseData) {
                    if(objectResponseData.getStatus()==0 && objectResponseData.getData() != null){

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

    public void follow(String opt ) {
        Message message = Message.obtain();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString("opt" , opt);
        bundle.putString("follow_mobilephone" , phone);
        message.setData(bundle);
        myHandler.removeMessages(1);
        myHandler.sendMessageDelayed(message , Constants.PRAISE_DELAY_MILES);
    }
}
