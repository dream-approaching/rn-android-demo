package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 10:31
 */
public class UserShareBean {

    /**
     * id : 8
     * channel_id : 1
     * commit_user : chxFKL347170615
     * mobilephone : 18503068868
     * fabulous : 0
     * label : 工具,旅游
     * content : 11发达的方式
     * app_info : 8
     * comment_num : 0
     * created_time : 1556088283
     * forward_num : 0
     * head_image : http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg
     * is_big_v : 1
     * nick_name : adxDIY139111944
     * is_add_friends : true
     * appdata : {"id":"8","app_short_desc":"最温馨好玩的语音交友社区。","app_down_url":"http://192.168.0.200:1130/uploads_cms_images/15554904004146916.apk","app_logo":"http://192.168.0.200:1130/uploads_cms_images/15554904006273519.png"}
     * is_fabulous : true
     * timestr : 2019-04
     */

    private String id;
    private String channel_id;
    private String commit_user;
    private String mobilephone;
    private String fabulous;
    private String label;
    private String content;
    private String app_info;
    private String comment_num;
    private String created_time;
    private String forward_num;
    private String head_image;
    private String is_big_v;
    private String nick_name;
    private boolean is_add_friends;
    private AppdataBean appdata;
    private boolean is_fabulous;
    private String timestr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCommit_user() {
        return commit_user;
    }

    public void setCommit_user(String commit_user) {
        this.commit_user = commit_user;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApp_info() {
        return app_info;
    }

    public void setApp_info(String app_info) {
        this.app_info = app_info;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getForward_num() {
        return forward_num;
    }

    public void setForward_num(String forward_num) {
        this.forward_num = forward_num;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getIs_big_v() {
        return is_big_v;
    }

    public void setIs_big_v(String is_big_v) {
        this.is_big_v = is_big_v;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public boolean isIs_add_friends() {
        return is_add_friends;
    }

    public void setIs_add_friends(boolean is_add_friends) {
        this.is_add_friends = is_add_friends;
    }

    public AppdataBean getAppdata() {
        return appdata;
    }

    public void setAppdata(AppdataBean appdata) {
        this.appdata = appdata;
    }

    public boolean isIs_fabulous() {
        return is_fabulous;
    }

    public void setIs_fabulous(boolean is_fabulous) {
        this.is_fabulous = is_fabulous;
    }

    public String getTimestr() {
        return timestr;
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }

    public static class AppdataBean {
        /**
         * id : 8
         * app_short_desc : 最温馨好玩的语音交友社区。
         * app_down_url : http://192.168.0.200:1130/uploads_cms_images/15554904004146916.apk
         * app_logo : http://192.168.0.200:1130/uploads_cms_images/15554904006273519.png
         */

        private String id;
        private String app_short_desc;
        private String app_down_url;
        private String app_logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApp_short_desc() {
            return app_short_desc;
        }

        public void setApp_short_desc(String app_short_desc) {
            this.app_short_desc = app_short_desc;
        }

        public String getApp_down_url() {
            return app_down_url;
        }

        public void setApp_down_url(String app_down_url) {
            this.app_down_url = app_down_url;
        }

        public String getApp_logo() {
            return app_logo;
        }

        public void setApp_logo(String app_logo) {
            this.app_logo = app_logo;
        }
    }
}
