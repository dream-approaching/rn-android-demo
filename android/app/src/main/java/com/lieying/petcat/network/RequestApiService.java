package com.lieying.petcat.network;



import com.lieying.comlib.bean.AppDetailsBean;
import com.lieying.comlib.bean.AppListBean;
import com.lieying.comlib.bean.CollectionBean;
import com.lieying.comlib.bean.ExploreBean;
import com.lieying.comlib.bean.FollowOrFansBean;
import com.lieying.comlib.bean.FstFgRcmdBean;
import com.lieying.comlib.bean.NoticeBean;
import com.lieying.comlib.bean.RegisterBean;
import com.lieying.comlib.bean.SecondFgBean;
import com.lieying.comlib.bean.TopicBean;
import com.lieying.comlib.bean.UpgradeBean;
import com.lieying.comlib.bean.UserIndexInfoBean;
import com.lieying.comlib.bean.UserInfoBean;
import com.lieying.comlib.bean.UserJoinBean;
import com.lieying.comlib.bean.UserShareBean;
import com.lieying.petcat.BuildConfig;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
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
    @GET(BuildConfig.USER_HOST+"/interface/v1/user/auth/userlogin")
    Observable<ResponseData<UserInfoBean>> loginOrRegister(@Query("params") String route);



    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/get_app_recommend")
    Observable<ResponseData<List<ExploreBean>>> getExploreInfo(@Query("params") String route);



    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/app/get_popular_app_list")
    Observable<ResponseData<List<AppListBean>>> getAppListInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/app/get_app_detail")
    Observable<ResponseData<AppDetailsBean>> getAppDetailsInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取首页卡片数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/get_play_recommend")
    Observable<ResponseData<List<TopicBean>>> getTopicInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:个人主页获取分享动态数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/get_other_sharelist")
    Observable<ResponseData<List<UserShareBean>>> getUserShareInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:个人主页获取参与互动数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/get_other_tasklist")
    Observable<ResponseData<List<UserJoinBean>>> getUserJoinInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取用户主页数据
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/get_other_info")
    Observable<ResponseData<UserIndexInfoBean>> getUserIndexInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:收藏内容
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/favorites/set_favorites")
    Observable<ResponseData<Object>> collectionContent(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取收藏列表
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/favorites/get_favorites_list")
    Observable<ResponseData<List<CollectionBean>>> getCollectionList(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取粉丝或者关注列表
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/follow/get_follow_list")
    Observable<ResponseData<FollowOrFansBean>> getFollowOrFansList(@Query("params") String route);

    /**
     * 发送请求
     * 上传用户头像
     */
    @Multipart
    @POST(BuildConfig.USER_HOST+"/interface/v1/user/auth/upload_headimg")
    Observable<ResponseData<Object>> uploadHeadIcon(@Part MultipartBody.Part cmd, @Part MultipartBody.Part userID,
                                                           @Part MultipartBody.Part file, @Part MultipartBody.Part file2,
                                                           @Part MultipartBody.Part file3, @Part MultipartBody.Part file4,
                                                           @Part MultipartBody.Part file5 , @Part MultipartBody.Part file6);

    /**
     * 发送请求
     * 上传用户头像
     */
    @Multipart
    @POST(BuildConfig.USER_HOST+"/interface/v1/user/auth/upload_picture ")
    Observable<ResponseData<Object>> uploadpic(@Part MultipartBody.Part cmd, @Part MultipartBody.Part userID,
                                                    @Part MultipartBody.Part file, @Part MultipartBody.Part file2,
                                                    @Part MultipartBody.Part file3, @Part MultipartBody.Part file4,
                                                    @Part MultipartBody.Part file5 , @Part MultipartBody.Part file6);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取用户信息
     */
    @GET(BuildConfig.USER_HOST+"/interface/v1/user/auth/get_userinfo")
    Observable<ResponseData<UserInfoBean>> getUserInfo(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:更新用户信息
     */
    @GET(BuildConfig.USER_HOST+"/interface/v1/user/auth/update_userinfo")
    Observable<ResponseData<Object>> updateUserInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:更新用户推送token
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/push/update_xgpush_token")
    Observable<ResponseData<Object>> updateUserPushToken(@Query("params") String route);



    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:举报
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/content/commit_report")
    Observable<ResponseData<Object>> report(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:删除粉丝
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/follow/set_fun")
    Observable<ResponseData<Object>> deleteFans(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:设置通知
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/push/update_user_push")
    Observable<ResponseData<Object>> setNoticeConfig(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取通知设置
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/push/get_user_push")
    Observable<ResponseData<NoticeBean>> getNoticeConfig(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:添加关注
     */
    @GET(BuildConfig.CONTENT_HOST+"/interface/v1/app/follow/set_follow")
    Observable<ResponseData<Object>> setFollowUser(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取验证码
     */
    @GET(BuildConfig.USER_HOST+"/interface/v1/user/auth/sendsms")
    Observable<ResponseData<RegisterBean>> getVerifyCode(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:获取升级信息
     */
    @GET(BuildConfig.CONTENT_HOST + "/interface/v1/app/app/get_app_update")
    Observable<ResponseData<UpgradeBean>> getUpgradeInfo(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:点赞
     */
    @GET(BuildConfig.CONTENT_HOST + "/interface/v1/app/fabulous/set_fabulous")
    Observable<ResponseData<Object>> setFabulous(@Query("params") String route);

    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:删除分享
     */
    @GET(BuildConfig.CONTENT_HOST + "/interface/v1/app/content/del_app_share")
    Observable<ResponseData<Object>> delShare(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:第一个fragment请求数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_video_recommend")
    Observable<ResponseData<List<FstFgRcmdBean>>> getFirstFgRcmdData(@Query("params") String route);


    /**
     * @author liyi
     * date 2019/3/7 0007 11:38
     * @function:第二个fragment数据
     */
    @GET("http://192.168.0.200:1230/interface/v1/app/content/get_follow_recommend")
    Observable<ResponseData<SecondFgBean>> getSecondFgRData(@Query("params") String route);
}
