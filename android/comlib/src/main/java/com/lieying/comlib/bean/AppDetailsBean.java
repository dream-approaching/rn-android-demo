package com.lieying.comlib.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/25 0025 17:33
 */
public class AppDetailsBean {
    /**
     * id : 68
     * app_name_cn : 百度浏览器
     * app_logo : http://192.168.0.200:1130/uploads_cms_images/15574571574094071.png
     * app_short_desc : 清爽利落，好用省流量浏览器
     * create_time : 1557457060
     * app_desc : 上百度浏览器，做个有趣的人！上网更快更安全更省流，超亿用户的选择！【趣星球】脑洞大开的奇葩趣事，弹幕吐不停！“逗比”红人纷纷入驻，新鲜趣味持续放送，更有“话题吐槽”全新上线，支持图文并发，焦点内容，你来创造！【阅资讯】聚合内容精挑细选，时事热点一手掌握，点评互动，更有态度！【读小说】热门小说精致阅读体验，精选书单、权威榜单，可整本离线，更新随时提醒！【看视频】丰富视频汇聚万千资源，高清内容无广告播放，看得爽！【个性化】新首页配合多种主题壁纸唯美上线，更可自定义上传，我的首页我做主！
     * comment_num : 0
     * app_screenshot : http://192.168.0.200:1130/uploads_cms_images/15574571576908369.jpg,http://192.168.0.200:1130/uploads_cms_images/15574571580607434.jpg,http://192.168.0.200:1130/uploads_cms_images/15574571583729164.jpg,http://192.168.0.200:1130/uploads_cms_images/15574571586292191.jpg,http://192.168.0.200:1130/uploads_cms_images/15574571589547058.jpg
     * develop_classfy : 浏览器
     * app_down_url : http://192.168.0.200:1130/uploads_cms_images/15574571570971372.apk
     * is_fabulous : false
     * likedata : [{"id":"67","app_name_cn":"上网导航","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15574571399689197.png","app_short_desc":"手机上网从hao123开始！","create_time":"1557457060","fabulous":"0","app_down_url":"http://192.168.0.200:1130/uploads_cms_images/15574571396753600.apk"},{"id":"66","app_name_cn":"浏览器","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15574571200528478.png","app_short_desc":"UC小游戏，闯关赢钱撩妹去！","create_time":"1557457060","fabulous":"0","app_down_url":""},{"id":"65","app_name_cn":"手机百度","app_logo":"http://192.168.0.200:1130/uploads_cms_images/1557457118338103.png","app_short_desc":"经典 搜索 全面 精准","create_time":"1557457060","fabulous":"0","app_down_url":""},{"id":"64","app_name_cn":"浏览器","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15574571162769720.png","app_short_desc":"便捷管理手机文件","create_time":"1557457060","fabulous":"0","app_down_url":"http://192.168.0.200:1130/uploads_cms_images/15574571159841397.apk"},{"id":"35","app_name_cn":"热点浏览器","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15573722815656870.png","app_short_desc":"热点浏览器，上网必备APP。","create_time":"1557372183","fabulous":"0","app_down_url":"http://192.168.0.200:1130/uploads_cms_images/15573722745379931.apk"}]
     */

    private String id;
    private String app_name_cn;
    private String app_logo;
    private String app_short_desc;
    private String create_time;
    private String app_desc;
    private String comment_num;
    private String app_screenshot;
    private String develop_classfy;
    private String app_down_url;
    private boolean is_fabulous;
    private List<LikedataBean> likedata;

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

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getApp_screenshot() {
        return app_screenshot;
    }

    public void setApp_screenshot(String app_screenshot) {
        this.app_screenshot = app_screenshot;
    }

    public String getDevelop_classfy() {
        return develop_classfy;
    }

    public void setDevelop_classfy(String develop_classfy) {
        this.develop_classfy = develop_classfy;
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

    public List<LikedataBean> getLikedata() {
        return likedata;
    }

    public void setLikedata(List<LikedataBean> likedata) {
        this.likedata = likedata;
    }

    public static class LikedataBean {
        /**
         * id : 67
         * app_name_cn : 上网导航
         * app_logo : http://192.168.0.200:1130/uploads_cms_images/15574571399689197.png
         * app_short_desc : 手机上网从hao123开始！
         * create_time : 1557457060
         * fabulous : 0
         * app_down_url : http://192.168.0.200:1130/uploads_cms_images/15574571396753600.apk
         */

        private String id;
        private String app_name_cn;
        private String app_logo;
        private String app_short_desc;
        private String create_time;
        private String fabulous;
        private String app_down_url;

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
    }
}
