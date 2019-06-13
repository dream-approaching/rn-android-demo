package com.lieying.petcat.receiver;

import android.content.Context;
import android.util.Log;

import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
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
        map.put("xgpush_token" , xgPushRegisterResult.getToken());
        map.put("mobilephone", UserManager.getCurrentUser().getPhone());
        RetrofitUtils.getInstance(context).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(context).getApiService().updateUserPushToken(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<Object>>() {
            @Override
            protected void onSuccees(ResponseData<Object> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && objectResponseData.getData() != null) {
                    Log.e("myPush" , "    2222222这里上传token: ===    "+xgPushRegisterResult.getToken());
                } else {
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
            }
        });

    }

    @Override
    public void onUnregisterResult(Context context, int i) {
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
    }
}
