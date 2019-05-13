package com.lieying.socialappstore.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.ToastUtil;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: 信鸽
 * @Author: liyi
 * @CreateDate: 2019/5/13 0013 10:26
 */
public class PushReceiver extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", UserManager.getCurrentUser().getAccessToken());
        map.put("xgpush_token" , "xgPushRegisterResult.getToken()");
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(context).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(context).getApiService().updateUserInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    Log.e("myPush" , "    2222222这里上传token: ===    "+xgPushRegisterResult.getToken());
                } else {
                    ToastUtil.showToast("Token上报失败:"+objectResponseData.getMsg());
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

    @Override
    public void onUnregisterResult(Context context, int i) {
        Log.e("myPush" , "onUnregisterResult-------- "+"        "+i);
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
        Log.e("myPush" , "onSetTagResult-------- "+s+"        "+i);
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
        Log.e("myPush" , "onDeleteTagResult-------- "+s+"        "+i);
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.e("myPush" , "onTextMessage-------- "+xgPushTextMessage.toString());
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        Log.e("myPush" , "onNotifactionClickedResult-------- "+xgPushClickedResult.toString());
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        Log.e("myPush" , "onNotifactionShowedResult-------- "+xgPushShowedResult.toString());
    }
}
