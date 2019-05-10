package com.lieying.socialappstore.network;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.lieying.socialappstore.utils.ImageHandler;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private RequestApiService apiService;

    public static String baseUrl = Hosts.getMainHost();

    public static Context mContext;

    private static RetrofitUtils sNewInstance;

    private static class SingletonHolder {
        private static RetrofitUtils INSTANCE = new RetrofitUtils(
                mContext);
    }

    public static RetrofitUtils getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }



    public static RetrofitUtils getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        sNewInstance = new RetrofitUtils(context, url);
        return sNewInstance;
    }

    private RetrofitUtils(Context context) {

        this(context, null);
    }


    private RetrofitUtils(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpUtils.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(RequestApiService.class);
    }

    public <T> void sendRequset(final Function<String, ObservableSource<T>> apiObservableFunction, Observer<T> subscriber) {
        Observable.just("url")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(apiObservableFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public <T> void sendRequset(List<Disposable> list  , final Function<String, ObservableSource<T>> apiObservableFunction, BaseObserver<T> subscriber) {
        Observable.just("url")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(apiObservableFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        list.add(subscriber.getDisposable());
    }
    /**
     *  上传头像
     * */
    public void sendHeadIcon(final File file , String token,  Observer<ResponseData<Object>> subscriber ){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("image_content", ImageHandler.getImage(file.getPath()));
        final MultipartBody.Part image_size =
                MultipartBody.Part.createFormData("image_size", "1");
        final MultipartBody.Part access_token =
                MultipartBody.Part.createFormData("access_token", token);
        final MultipartBody.Part ch =
                MultipartBody.Part.createFormData("ch", "1");
        final MultipartBody.Part app_ver =
                MultipartBody.Part.createFormData("app_ver", "1");
        final MultipartBody.Part app_ver_code =
                MultipartBody.Part.createFormData("app_ver_code", "1");
        final MultipartBody.Part image_type =
                MultipartBody.Part.createFormData("image_type", "png");
        final MultipartBody.Part channel_id =
                MultipartBody.Part.createFormData("channel_id", "1");
//
        sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
            @Override
            public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                return apiService.uploadHeadIcon(image_size,access_token , ch , app_ver , app_ver_code , image_type  ,body);
            }
        }, subscriber);
    }


    public RequestApiService getApiService() {
        return apiService;
    }

}