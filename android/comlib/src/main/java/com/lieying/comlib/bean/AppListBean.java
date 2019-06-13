package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/24 0024 18:30
 */
public class AppListBean {


    /**
     * id : 10621
     * app_name_cn : 票牛
     * app_logo : http://api.appstore.lynav.com/uploads_cms_images/17.png
     * app_short_desc : 看起来是一款专注用户票务的APP，但其实它提供了所有演唱会/话剧歌剧/休闲展览/音乐会/体育赛事等的资讯信息，想看哪一场都能轻松购票，无论是对追星族还是需要精神食粮的大众们，都很方便！
     * create_time : 1558433480
     * favorites : 9
     * fabulous : 1
     * app_down_url : https://www.wandoujia.com/apps/com.ipiaoniu.android/download/dot?ch=detail_normal_dl
     * is_fabulous : false
     * url_content :
     */

    private String id;
    private String app_name_cn;
    private String app_logo;
    private String app_short_desc;
    private String create_time;
    private String favorites;
    private String fabulous;
    private String app_down_url;
    private boolean is_fabulous;
    private String url_content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_name_cn() {
        return app_name_cn;
    }

    public void setApp_name_cn(String app_name_cn) {
        this.app_name_cn = app_name_cn;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getApp_short_desc() {
        return app_short_desc;
    }

    public void setApp_short_desc(String app_short_desc) {
        this.app_short_desc = app_short_desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public String getApp_down_url() {
        return app_down_url;
    }

    public void setApp_down_url(String app_down_url) {
        this.app_down_url = app_down_url;
    }

    public boolean isIs_fabulous() {
        return is_fabulous;
    }

    public void setIs_fabulous(boolean is_fabulous) {
        this.is_fabulous = is_fabulous;
    }

    public String getUrl_content() {
        return url_content;
    }

    public void setUrl_content(String url_content) {
        this.url_content = url_content;
    }
}
