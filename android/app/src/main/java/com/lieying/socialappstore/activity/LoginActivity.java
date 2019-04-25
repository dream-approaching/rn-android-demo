package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.utils.ButtonWaiting;
import com.lieying.comlib.utils.MatcheUtils;
import com.lieying.comlib.utils.ViewUtil;
import com.lieying.comlib.utils.ViewUtils;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.manager.StatusBarUtil;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.subscribe.ControlSubscriber;
import com.lieying.socialappstore.utils.SharedPreferencesUtil;
import com.lieying.socialappstore.utils.ToastUtil;
import com.lieying.socialappstore.widget.CountTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.lieying.comlib.constant.Constants.SP_KEY_USER_INFO;

public class LoginActivity extends BaseActivity implements  ButtonWaiting.OnWaitingListener<String> , TextWatcher, CountTimer.OnTimerListener {
    private List<Disposable> mSubscription = new ArrayList<>();
    EditText mEtPhone;
    EditText mEtVertifyCode;
    LinearLayout mLlLogin;
    private TextView mTvSendCode;//发送验证码
    private ButtonWaiting<String> mButtonWaiting;
    private CountTimer mCountTimer;
    private TextView mTvLogin;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    boolean register = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    protected void setContentView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    public void findView() {
        mEtPhone = findViewById(R.id.et_account);
        mEtVertifyCode = findViewById(R.id.et_code);
        mLlLogin = findViewById(R.id.ll_register);
        mLlLogin.setEnabled(false);
        mTvSendCode = findViewById(R.id.tv_send_vft);
        mTvLogin = findViewById(R.id.tv_register);
        mButtonWaiting = new ButtonWaiting<>(mContext, (ImageView) findViewById(R.id.iv_anim));

    }

    @Override
    public void initView() {
        mCountTimer = new CountTimer(mContext, mTvSendCode, 60000, 1000);
        mCountTimer.setColor(R.color.color_94fb716b, R.color.color_fb716b);
        mCountTimer.setFinishTip(getString(R.string.string_fd_send_vft));
        mCountTimer.setUnits("s后再次获取");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        mButtonWaiting.setOnWaitingListener(this);
        ViewUtil.setEditTextFocusListener(mEtPhone, ((TextView) findViewById(R.id.tv_ac_tip)), null);
        ViewUtil.setEditTextFocusListener(mEtVertifyCode, ((TextView) findViewById(R.id.tv_code_tip)), null);
        mOnGlobalLayoutListener = ViewUtils
                .getKeyboardListener(getWindow().getDecorView(), mLlLogin);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        ViewUtil.WatchEnable watchEnable = new ViewUtil.WatchEnable(mLlLogin);
        watchEnable.setEditTexts(mEtPhone, mEtVertifyCode);
        watchEnable.setLengths(11, 6);
        watchEnable.setWatcher(this);
        watchEnable.start();
        mCountTimer.setTimerListener(this);
        mTvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhone(mEtPhone.getText().toString(), new ControlSubscriber<String>(mSubscription) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (TextUtils.isEmpty(s)) {
                            mCountTimer.start();
                        } else {
                            mCountTimer.stop();
                            ToastUtil.showToast(mContext, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("test" , "onComplete -----------  ");
                    }
                });
            }
        });
        mLlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButtonWaiting.startWaiting()) {
                    checkPhone(mEtPhone.getText().toString(), new ControlSubscriber<String>(mSubscription) {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            if (TextUtils.isEmpty(s)) {
                                login( mEtPhone.getText().toString() ,mEtVertifyCode.getText().toString() );
                            } else {
                                mButtonWaiting.stopWaiting("注册");
                                ToastUtil.showToast(mContext, s);
                            }
                        }
                    });
                }

            }
        });

        findViewById(R.id.iv_login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.super.onBackPressed();
            }
        });

        mEtPhone.setText("18503068868");
        mEtVertifyCode.setText("123456");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!mCountTimer.isStart()) {
            changeSendEnable();
        }
    }
    //改变发送验证码按钮的是否可点击状态
    private void changeSendEnable() {
        boolean enable = mEtPhone.length() >= 8;
        mTvSendCode.setEnabled(enable);
        mTvSendCode.setTextColor(ContextCompat.getColor(mContext, enable ? R.color.color_fb716b : R.color.color_94fb716b ));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onStartWaiting() {

    }

    @Override
    public void onStopWaiting(String s) {

    }

    @Override
    public void onStartTimer() {

    }

    @Override
    public void onTickTimer(long millisUntilFinished) {

    }

    @Override
    public void onFinishTimer() {

    }

    //先正则匹配
    private void checkPhone(final String ac, ControlSubscriber<String> controlSubscriber) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                boolean isRule = true;
                if (!MatcheUtils.isRule(MatcheUtils.REGEX_MOBILE, ac)) {
                    emitter.onNext(getString(R.string.app_ip_rl_po));
                    isRule = false;
                }
                if (isRule) {
                    emitter.onNext("");
                }
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(controlSubscriber);
    }

    private void login( String phone  , String vertifyCode){
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_id", "1");
        map.put("app_ver", "1");
        map.put("app_ver_code", "1");
        map.put("ch", "1");
        map.put("mobilephone",phone);
        map.put("validate_code",vertifyCode);
        RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<UserInfoBean>>>() {
            @Override
            public ObservableSource<ResponseData<UserInfoBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(mContext).getApiService().loginOrRegister(ReqBody.getReqString(map));
            }
        } , new BaseObserver<ResponseData<UserInfoBean>>() {
            @Override
            protected void onSuccees(ResponseData<UserInfoBean> objectResponseData) {
                if(objectResponseData.getStatus()==0){
                    UserManager.getInstance().setCurrentUser(objectResponseData.getData());
                    SharedPreferencesUtil.getInstance().putString(SP_KEY_USER_INFO, new Gson().toJson(objectResponseData.getData()));
                    ToastUtil.showToast("登陆成功!");
                    finish();
                }else{
                    mButtonWaiting.stopWaiting("登陆");
                    ToastUtil.showToast(objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                if(isNetWorkError){
                    ToastUtil.showToast("请求失败");
                }
                mButtonWaiting.stopWaiting("登陆");
                e.printStackTrace();
            }
        });
    }
}
