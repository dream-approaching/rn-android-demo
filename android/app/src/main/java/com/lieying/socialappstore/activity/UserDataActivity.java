package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.utils.EmptyUtil;
import com.lieying.comlib.utils.FileUtil;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.callback.DialogClickCallback;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.DialogUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.SharedPreferencesUtil;
import com.lieying.socialappstore.widget.TitleView;
import com.lieying.socialappstore.widget.citypicker.AddressPickTask;
import com.lieying.socialappstore.widget.citypicker.City;
import com.lieying.socialappstore.widget.citypicker.County;
import com.lieying.socialappstore.widget.citypicker.Province;
import com.weiwang.photoalbumlibrary.matisse.Matisse;
import com.weiwang.photoalbumlibrary.matisse.MimeType;
import com.weiwang.photoalbumlibrary.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.lieying.socialappstore.activity.EditUserDataActivity.KEY_DATA_TYPE;
import static com.lieying.socialappstore.activity.EditUserDataActivity.KEY_REQUEST_CODE;
import static com.lieying.socialappstore.utils.ToastUtil.showToast;

public class UserDataActivity extends BaseActivity {
    private static final int REQUEST_CODE_CHOOSE = 3001;
    private TextView mTvNick;
    private TextView mTvSex;
    private TextView mTvOccupation;
    private TextView mTvCity;
    private TextView mTvBrief;
    private ImageView mIvHeaderIcon;
    private int msg_type;

    private String mHeaderPath;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_user_data);
    }

    @Override
    public void findView() {
        findViewById(R.id.rl_user_data_header).setOnClickListener(this);
        findViewById(R.id.rl_user_data_nick).setOnClickListener(this);
        findViewById(R.id.rl_user_data_sex).setOnClickListener(this);
        findViewById(R.id.rl_user_data_occupation).setOnClickListener(this);
        findViewById(R.id.rl_user_data_brief).setOnClickListener(this);
        findViewById(R.id.rl_user_data_city).setOnClickListener(this);
        mIvHeaderIcon = findViewById(R.id.civ_head);
        mTvNick = findViewById(R.id.tv_user_data_nick);
        mTvSex = findViewById(R.id.tv_user_data_sex);
        mTvOccupation = findViewById(R.id.tv_user_data_occupation);
        mTvCity = findViewById(R.id.tv_user_data_city);
        mTvBrief = findViewById(R.id.tv_user_data_brief);
    }

    @Override
    public void initView() {
        setView();
    }

    @Override
    public void initData() {
        getUserInfo();
    }

    @Override
    public void initListener() {
        ((TitleView)findViewById(R.id.title_user_data_activity)).setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("refreshUserData", null);
                finish();
            }
        });
    }

    @Override
    public void onUnDoubleClickView(View v) {
        if(UserManager.getInstance().NoUserLogin()){
            LoginActivity.startActivity(mContext);
            return;
        }
        switch (v.getId()) {
            case R.id.rl_user_data_header:
                DialogUtils.showBottomDialog(mContext, "拍照", "从相册选择", "取消", new DialogClickCallback() {
                    @Override
                    public void onClick(int postion) {
                        switch (postion){
                            case 1:
                                break;
                            case 2:  //从相册选择
                                Matisse.from(UserDataActivity.this)
                                        .choose(MimeType.ofImage())
                                        .theme(R.style.Matisse_Dracula)
                                        .countable(false)
                                        .maxSelectable(1)
                                        .originalEnable(true)
                                        .maxOriginalSize(10)
                                        .imageEngine(new GlideEngine())
                                        .forResult(REQUEST_CODE_CHOOSE);
                                break;
                        }
                    }
                });

                break;
            case R.id.rl_user_data_nick:
                msg_type = 0;
                startEditActivity(msg_type);
                break;
            case R.id.rl_user_data_sex:
                DialogUtils.showBottomDialog(mContext, "男", "女", "取消", new DialogClickCallback() {
                    @Override
                    public void onClick(int postion) {
                        switch (postion){
                            case 1:
                                update("sex" , "m");  // 男
                                break;
                            case 2:
                                update("sex" , "f");  // 女
                                break;
                        }

                    }
                });
                break;
            case R.id.rl_user_data_city:
                onAddress3Picker();
                break;
            case R.id.rl_user_data_occupation:
                msg_type = 1;
                startEditActivity(msg_type);
                break;
            case R.id.rl_user_data_brief:
                msg_type = 2;
                startEditActivity(msg_type);
                break;

        }
    }

    private void startEditActivity(int type) {
        Intent intent = new Intent(this, EditUserDataActivity.class);
        intent.putExtra(KEY_DATA_TYPE, type);
        startActivityForResult(intent, KEY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (null != Matisse.obtainPathResult(data)) {
                Intent intent = new Intent();
                intent.setClass(mContext, ClipHeaderIconActivity.class);
                intent.putExtra("type", 2);
                intent.setData(Uri.parse(Matisse.obtainPathResult(data).get(0)));
                startActivityForResult(intent, Constants.REQUEST_CROP_PHOTO);
            }
        }
        if (requestCode == Constants.REQUEST_CROP_PHOTO) {  //剪切图片返回
            if (resultCode == RESULT_OK) {
                final Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                String cropImagePath = FileUtil.getRealFilePathFromUri(mContext, uri);
                uploadPic(cropImagePath);
                setHeaderIMG(cropImagePath);
            }
        }
        if (requestCode == 8) {  //填完个人信息之后返回
            if (resultCode == 24) {
                String msg = data.getStringExtra("msg");
                switch (msg_type) {
                    case 0:
                        mTvNick.setText(msg);
                        if(UserManager.getCurrentUser().getUserinfo()!=null){
                            UserManager.getCurrentUser().getUserinfo().setNick_name(msg);
                        }
                        break;
                    case 1:
                        mTvOccupation.setText(msg);
                        if(UserManager.getCurrentUser().getUserinfo()!=null){
                            UserManager.getCurrentUser().getUserinfo().setCareer(msg);
                        }
                        break;
                    case 2:
                        mTvBrief.setText(msg);
                        if(UserManager.getCurrentUser().getUserinfo()!=null){
                            UserManager.getCurrentUser().getUserinfo().setProfile(msg);
                        }
                        break;
                }
                if(UserManager.getCurrentUser().getUserinfo()!=null){
                    String userInfo = new Gson().toJson( UserManager.getCurrentUser());
                    SharedPreferencesUtil.getInstance().putString(Constants.SP_KEY_USER_INFO, userInfo);
                }
            }
        }

    }

    private void setHeaderIMG(String path) {
        if (EmptyUtil.isNotEmpty(path)) {
            mHeaderPath = path;
            GlideUtils.loadCircleImageForUrl(mContext, R.drawable.ic_default_header, mIvHeaderIcon, path);
        }
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadPic(String file) {
        RetrofitUtils.getInstance(mContext).sendHeadIcon(new File(file), UserManager.getCurrentUser().getAccessToken(), new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> stringResponseData) throws Exception {
                if (stringResponseData.getStatus() == 0) {
                    showToast("上传成功");
                } else {
                    showToast(stringResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                showToast("上传成功");
                e.printStackTrace();
            }
        });
    }

    private void getUserInfo() {
        if (UserManager.getCurrentUser().getUserinfo() == null) {
            showToast("您尚未登陆");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());

        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<UserInfoBean>>>() {
            @Override
            public ObservableSource<ResponseData<UserInfoBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().getUserInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<UserInfoBean>>() {
            @Override
            protected void onSuccees(ResponseData<UserInfoBean> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    UserManager.getInstance().setCurrentUser(objectResponseData.getData());
                    setView();
                } else {
                    showToast(objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    showToast("网络层错误");
                } else {
                    showToast("请求失败");
                }
            }
        });
    }

    private void setView() {
        if (UserManager.getCurrentUser() != null && UserManager.getCurrentUser().getUserinfo() != null) {
            GlideUtils.loadCircleImageForUrl(mContext, R.drawable.ic_default_header, mIvHeaderIcon, UserManager.getCurrentUser().getUserinfo().getHead_image());
            mTvNick.setText(UserManager.getCurrentUser().getUserinfo().getNick_name());
            mTvSex.setText(UserManager.getCurrentUser().getUserinfo().getSex() == null ? "未知" : (UserManager.getCurrentUser().getUserinfo().getSex().equals("m") ? "男" : "女"));
            mTvOccupation.setText(UserManager.getCurrentUser().getUserinfo().getCareer());
            mTvCity.setText(UserManager.getCurrentUser().getUserinfo().getLocation());
            mTvBrief.setText(UserManager.getCurrentUser().getUserinfo().getProfile());
        }
    }

    private void update(String params , String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put(params , msg);
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().updateUserInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    if(params.equals("sex")){
                        UserManager.getCurrentUser().getUserinfo().setSex(msg);
                        switch (msg){
                            case "m":
                                mTvSex.setText("男");
                                break;
                            case "f":
                                mTvSex.setText("女");
                                break;
                        }
                    }else{
                        mTvCity.setText(msg);
                        UserManager.getCurrentUser().getUserinfo().setLocation(msg);
                    }
                    UserManager.getInstance().saveUserData();
                } else {
                    showToast(objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    showToast("网络层错误");
                } else {
                    showToast("请求失败");
                }
            }
        });
    }
    public void onAddress3Picker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                update("location" ,  city.getAreaName());
            }
        });
        task.execute("广东", "深圳");
    }
}
