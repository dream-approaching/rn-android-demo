package com.lieying.petcat.manager;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.lieying.comlib.bean.DownBean;
import com.lieying.comlib.bean.UpgradeBean;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.network.BaseObserver;
import com.lieying.petcat.network.ReqBody;
import com.lieying.petcat.network.ResponseData;
import com.lieying.petcat.network.RetrofitUtils;
import com.lieying.petcat.service.UpdateService;
import com.lieying.petcat.utils.DialogUtils;
import com.lieying.petcat.utils.SysUtils;
import com.lieying.petcat.utils.ToastUtil;
import com.umeng.analytics.AnalyticsConfig;

import java.util.HashMap;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: 版本管理
 * @Author: liyi
 * @CreateDate: 2019/5/30 0030 15:34
 */
public class VerisonManager {
    private volatile static VerisonManager verisonManager;
    private boolean checaking;

    public static VerisonManager getInstance(){
        if(verisonManager == null){
            synchronized (VerisonManager.class){
                if(verisonManager == null){
                    verisonManager = new VerisonManager();
                }
            }
        }
        return verisonManager;
    }

    public void cheakUpload(){

        checaking = true;
        HashMap<String, String> map = new HashMap<>();
        map.put("app_ver", SysUtils.getAppVersionName(MainApplication.getInstance()));
        map.put("channel", AnalyticsConfig.getChannel(MainApplication.getInstance()));
        Log.e("test" , SysUtils.getAppVersionName(MainApplication.getInstance())+ "~~~~ -------  "+AnalyticsConfig.getChannel(MainApplication.getInstance()));
        RetrofitUtils.getInstance(MainApplication.getInstance()).sendRequset(new Function<String, ObservableSource<ResponseData<UpgradeBean>>>() {
            @Override
            public ObservableSource<ResponseData<UpgradeBean>> apply(String s) throws Exception {
                return RetrofitUtils.getInstance(MainApplication.getInstance()).getApiService().getUpgradeInfo(ReqBody.getReqString(map));
            }
        }, new BaseObserver<ResponseData<UpgradeBean>>() {
            @Override
            protected void onSuccees(ResponseData<UpgradeBean> objectResponseData) {
                if (objectResponseData.getStatus() == 0 && AppActivityManager.getInstance().getTopActivity()!=null) {  //需要升级
                    DialogUtils.showUpGradeDialog(AppActivityManager.getInstance().getTopActivity(), objectResponseData.getData(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {
                    ToastUtil.showToast(objectResponseData.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) {
                e.printStackTrace();
                ToastUtil.showToast("请求失败");
            }
        });
    }

    private void downLoad(String url , String fileName){
        Intent intent = new Intent(MainApplication.getInstance(), UpdateService.class);
        DownBean downBean = new DownBean();
        downBean.setName("树莓最新版");
        downBean.setDownPath(url);
        downBean.setFileName(fileName);
        intent.putExtra("appURL", downBean);
        MainApplication.getInstance().startService(intent);
    }


}
