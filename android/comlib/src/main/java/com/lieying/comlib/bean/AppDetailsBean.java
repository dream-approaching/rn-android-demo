package com.lieying.comlib.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/25 0025 17:33
 */
public class AppDetailsBean {

    /**
     * id : 7
     * app_name_cn : 快看漫画
     * app_logo : http://192.168.0.200:1130/uploads_cms_images/15554903936696521.png
     * app_short_desc : 全都超好看，搞笑治愈段子图片神器
     * create_time : 1555490078
     * app_desc : 漫画超全，更新快，高清全彩不费眼！～￣▽￣～操作简单，加载快，条式阅读体验赞！～￣▽￣～看漫画，就上快看漫画！【一上线就连续天蝉联免费排行榜榜首】【下载必看】《整容游戏》：一个职场白领偶然使用了一款美颜软件后现实的脸也跟着整容，先要保持美貌就必须完成一系列考验人性的任务···《零分偶像》：低智商明星毕业考试只考零分？与土鳖学霸女欢乐爆笑的爱情故事。《密会情人》：社会热点题材，讲述婚姻、爱情和欲望之间的矛盾纠葛···【软件特色】、海量正版资源，亿用户的选择快看漫画发展至今累计用户近亿人！这里有上千部高质量正
     * comment_num : 0
     * app_screenshot : http://192.168.0.200:1130/uploads_cms_images/1555490393930404.jpg,http://192.168.0.200:1130/uploads_cms_images/1555490394181908.jpg,http://192.168.0.200:1130/uploads_cms_images/15554903944389007.jpg,http://192.168.0.200:1130/uploads_cms_images/15554903946905796.jpg
     * develop_classfy : 图书阅读
     * likedata : [{"id":"4","app_name_cn":"免费小说大全","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15554894758525927.png","app_short_desc":"打发无聊时间的最佳装备！","create_time":"1555489297","fabulous":"0"}]
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

    public List<LikedataBean> getLikedata() {
        return likedata;
    }

    public void setLikedata(List<LikedataBean> likedata) {
        this.likedata = likedata;
    }

    public static class LikedataBean {
        /**
         * id : 4
         * app_name_cn : 免费小说大全
         * app_logo : http://192.168.0.200:1130/uploads_cms_images/15554894758525927.png
         * app_short_desc : 打发无聊时间的最佳装备！
         * create_time : 1555489297
         * fabulous : 0
         */

        private String id;
        private String app_name_cn;
        private String app_logo;
        private String app_short_desc;
        private String create_time;
        private String fabulous;

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
    }
}
