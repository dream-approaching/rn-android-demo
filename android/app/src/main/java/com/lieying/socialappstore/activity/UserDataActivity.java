package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.comlib.utils.EmptyUtil;
import com.lieying.comlib.utils.FileUtil;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.SharedPreferencesUtil;
import com.lieying.socialappstore.utils.ToastUtil;
import com.weiwang.photoalbumlibrary.matisse.Matisse;
import com.weiwang.photoalbumlibrary.matisse.MimeType;
import com.weiwang.photoalbumlibrary.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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

    }

    @Override
    public void onUnDoubleClickView(View v) {
        switch (v.getId()){
            case R.id.rl_user_data_header:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(1)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.rl_user_data_nick:
                msg_type = 0;
                EditUserDataActivity.startActivity(this , msg_type);
                break;
            case R.id.rl_user_data_sex:
                break;
            case R.id.rl_user_data_city:
                break;
            case R.id.rl_user_data_occupation:
                msg_type = 1;
                EditUserDataActivity.startActivity(this , msg_type);
                break;
            case R.id.rl_user_data_brief:
                msg_type = 2;
                EditUserDataActivity.startActivity(this , msg_type);
                break;

        }
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
                String msg = data.getStringExtra(msg_type+"");
                switch (msg_type){
                    case 0:
                        mTvNick.setText(msg);
                        break;
                    case 1:
                        mTvOccupation.setText(msg);
                        break;
                    case 2:
                        mTvBrief.setText(msg);
                        break;
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
     * @param file
     */
    private void uploadPic(String file) {
        RetrofitUtils.getInstance(mContext).sendHeadIcon(new File(file), UserManager.getCurrentUser().getAccessToken(), new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> stringResponseData) throws Exception {
                if (stringResponseData.getStatus() == 0) {
                    ToastUtil.showToast("上传成功");
                } else {
                    ToastUtil.showToast(stringResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                ToastUtil.showToast("上传成功");
                e.printStackTrace();
            }
        });
    }

    private void getUserInfo() {
        if (UserManager.getCurrentUser().getUserinfo() == null) {
            ToastUtil.showToast("您尚未登陆");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("app_ver", "1");
        map.put("app_ver_code", "1");
        map.put("ch", "1");
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
                    String userInfo = new Gson().toJson(objectResponseData.getData());
                    SharedPreferencesUtil.getInstance().putString(Constants.SP_KEY_USER_INFO, userInfo);
                    setView();
                } else {
                    ToastUtil.showToast(objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if (isNetWorkError) {
                    ToastUtil.showToast("网络层错误");
                } else {
                    ToastUtil.showToast("请求失败");
                }
            }
        });
    }

    private void setView(){
        if(UserManager.getCurrentUser()!=null && UserManager.getCurrentUser().getUserinfo()!=null){
            GlideUtils.loadCircleImageForUrl(mContext, R.drawable.ic_default_header, mIvHeaderIcon, UserManager.getCurrentUser().getUserinfo().getHead_image());
            mTvNick.setText(UserManager.getCurrentUser().getUserinfo().getNick_name());
            mTvSex.setText(UserManager.getCurrentUser().getUserinfo().getSex().equals("m")?"男" : "女");
            mTvOccupation.setText(UserManager.getCurrentUser().getUserinfo().getCareer());
            mTvCity.setText(UserManager.getCurrentUser().getUserinfo().getLocation());
            mTvBrief.setText(UserManager.getCurrentUser().getUserinfo().getProfile());
        }
    }
}
