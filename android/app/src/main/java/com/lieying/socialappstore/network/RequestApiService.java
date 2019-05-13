package com.lieying.socialappstore.network;



import com.lieying.comlib.bean.AppDetailsBean;
import com.lieying.comlib.bean.AppListBean;
import com.lieying.comlib.bean.CollectionBean;
import com.lieying.comlib.bean.ExploreBean;
import com.lieying.comlib.bean.TopicBean;
import com.lieying.comlib.bean.UserIndexInfoBean;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.bean.UserJoinBean;
import com.lieying.comlib.bean.UserShareBean;

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
    @GET("http://192.168.0.200:1530/interface/v1/user/auth/userlogin")
    Observable<ResponseData<UserInfoBean>> loginOrRegister(@Query("params") String route);



    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_app_recommend")
    Observable<ResponseData<List<ExploreBean>>> getExploreInfo(@Query("params") String route);



    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/app/get_app_list")
    Observable<ResponseData<List<AppListBean>>> getAppListInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/app/get_app_detail")
    Observable<ResponseData<AppDetailsBean>> getAppDetailsInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_play_recommend")
    Observable<ResponseData<List<TopicBean>>> getTopicInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:个人主页获取分享动态数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_other_sharelist")
    Observable<ResponseData<List<UserShareBean>>> getUserShareInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:个人主页获取参与互动数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_other_tasklist")
    Observable<ResponseData<List<UserJoinBean>>> getUserJoinInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取用户主页数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_other_info")
    Observable<ResponseData<UserIndexInfoBean>> getUserIndexInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:收藏内容
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/favorites/set_favorites")
    Observable<ResponseData<Object>> collectionContent(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:收藏内容
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/favorites/get_favorites_list")
    Observable<ResponseData<List<CollectionBean>>> getCollectionList(@Query("params") String route);

    /**
     * 发送请求
     * 上传用户头像
     */
    @Multipart
    @POST("http://192.168.0.200:1530/interface/v1/user/auth/upload_picture ")
    Observable<ResponseData<Object>> uploadHeadIcon(@Part MultipartBody.Part cmd, @Part MultipartBody.Part userID,
                                                           @Part MultipartBody.Part file, @Part MultipartBody.Part file2,
                                                           @Part MultipartBody.Part file3, @Part MultipartBody.Part file4,
                                                           @Part MultipartBody.Part file5);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取用户信息
     */
    @GET("/interface/v1/user/auth/get_userinfo")
    Observable<ResponseData<UserInfoBean>> getUserInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:更新用户信息
     */
    @GET("http://192.168.0.200:1530/interface/v1/user/auth/update_userinfo")
    Observable<ResponseData<Object>> updateUserInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:更新用户信息
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/push/update_xgpush_token")
    Observable<ResponseData<Object>> updateUserPushToken(@Query("params") String route);

}
