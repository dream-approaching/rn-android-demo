package com.lieying.socialappstore.network;



import com.lieying.comlib.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by liyi on 2018/1/24.
 */

public interface RequestApiService {

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:登陆或者注册
     */
    @GET("/interface/v1/user/auth/userlogin")
    Observable<ResponseData<UserInfoBean>> loginOrRegister(@Query("params") String route);

}
