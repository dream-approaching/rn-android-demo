package com.lieying.comlib.constant;

public class Constants {

    public static String SP_KEY_USER_INFO = "my_user_info";   //我的用户信息key

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String COLLECTION_TYPE_ARTICLE = "1";  //搜藏类型 ： 文章

    public static final String COLLECTION_TYPE_TOPIC = "2";//搜藏类型 ： 话题

    public static final String COLLECTION_TYPE_APP = "3";//搜藏类型 ： App

    public static final String COLLECTION_TYPE_APP_DETAILS = "4";//搜藏类型 ： App详情

    public static final String JOIN_TYPE_PRAISE = "1";   //参与互动类型：点赞

    public static final String JOIN_TYPE_COMMONTS = "2";//参与互动类型：评论

    public static final String JOIN_TYPE_COLLECTION = "3";//参与互动类型：收藏

    public static final String INDEX_ARITCLE_TYPE_TOPIC = "1";//首页文章类型 ： 互动话题

    public static final String INDEX_ARITCLE_TYPE_SHUZI = "2";//首页文章类型 ：数字生活研所

    public static final String INDEX_ARITCLE_TYPE_APP = "3";//首页文章类型 ：App


    public static final String COMMENT_TYPE_TOPIC = "1";//打开公共评论页的内容类型：互动话题
    public static final String COMMENT_TYPE_SHUZI = "2";//打开公共评论页的内容类型：数字生活研究所
    public static final String COMMENT_TYPE_APP = "3";//打开公共评论页的内容类型：应用推荐
    public static final String COMMENT_TYPE_APP_DETAILS= "4";//打开公共评论页的内容类型：APP详情
    public static final String COMMENT_TYPE_XFRIEND = "5";//打开公共评论页的内容类型：X友分享

    public static final String USER_TYPE_FOLLOWS = "1";//用户类型:我的关注
    public static final String USER_TYPE_FANS = "2";//用户类型:我的粉丝

    public static boolean NEED_REFREASH_SENCOND_FG = false;//用户类型:我的粉丝


    //请求截图
    public static final int REQUEST_CROP_PHOTO = 102;

    public static final int PRAISE_DELAY_MILES = 1000;

    /**----------广播flag-----------*/
    public static final String BROADCAST_FLAG_REFRESH_MAIN = "main_date_refresh";

    public static final String KEY_FIRSTH_FG_JSON = "first_fragment_json";

    public static String KEY_WEB_URL = "key_web_url";
    public static String KEY_WEB_SHOW_TITLE = "key_web_show_title";


    /**----------广播flag-----------*/
    public static final String BROADCAST_FLAG_FIRST_FG_FRESH_FIRST_ITEM = "first_fragment_refresh";

    public static final String BROADCAST_FLAG_FIRST_FG_FRESH_BY_ID = "first_fragment_refresh_by_item";

    public static final String BROADCAST_FLAG_TOPIC_FG_FRESH = "top_fragment_refresh";

    /**------------发送事件给Rn---------------------*/
    public static final String RN_EVENT_NAME_LOGIN = "UserLogin";

    public static final String NATIVE_CALL_RN_PARAMS = "native_call_rn_params";


    /**------------对用户操作---------------------*/

    public static final String OPT_TO_USER_FOLLOW = "add";
    public static final String OPT_TO_USER_CANCLE_FOLLOW = "del";

    /**------------Navive call Rn 第一个参数，操作类型---------------------*/
    public static final String OPTION_SET_FOLLOW = "set_follow";    //用户关注状态改变

    public static final String OPTION_DELETE_X_SHARE = "delete_x_share";   //删除X友分享

    public static final String OPTION_FABULOUS_X_SHARE = "set_fabulous_x_share";   //点赞X友分享

    public static final String OPTION_LOGIN = "login";   //登陆

    public static final String OPTION_UPDATA_USER_INFO= "UpdataUserInfo";   //更新个人信息

    public static final String OPTION_USER_EXIT = "UserExit";   //退出登陆

}
