package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/19 0019 10:59
 */
public class UserInfoBean {


    /**
     * userinfo : {"id":"14","channel_id":"1","nick_name":"试玩","refresh_token":"e3546129237d587df017fbbcc11ed946","access_token":"c3c4db657e05f684f696096ec6f06371","mobilephone":"18503068868","expires_in":"1558755079","head_image":"http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg","sex":"m","user_title":"","user_type":"0","career":"科学家","location":"北京","profile":"dsddsf","age":"0"}
     */

    private UserinfoBean userinfo;

    public String getAccessToken(){
        if(userinfo == null){
            return "";
        }
        return userinfo.access_token;
    }
    public String getPhone(){
        if(userinfo == null){
            return "";
        }
        return userinfo.mobilephone;
    }
    public String getName(){
        if(userinfo == null){
            return "";
        }
        return userinfo.nick_name;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }


    public static class UserinfoBean {
        /**
         * id : 14
         * channel_id : 1
         * nick_name : 试玩
         * refresh_token : e3546129237d587df017fbbcc11ed946
         * access_token : c3c4db657e05f684f696096ec6f06371
         * mobilephone : 18503068868
         * expires_in : 1558755079
         * head_image : http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg
         * sex : m
         * user_title :
         * user_type : 0
         * career : 科学家
         * location : 北京
         * profile : dsddsf
         * age : 0
         */

        private String id;
        private String channel_id;
        private String nick_name;
        private String refresh_token;
        private String access_token;
        private String mobilephone;
        private String expires_in;
        private String head_image;
        private String sex;
        private String user_title;
        private String user_type;
        private String career;
        private String location;
        private String profile;
        private String age;

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

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getHead_image() {
            return head_image;
        }

        public void setHead_image(String head_image) {
            this.head_image = head_image;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUser_title() {
            return user_title;
        }

        public void setUser_title(String user_title) {
            this.user_title = user_title;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getCareer() {
            return career;
        }

        public void setCareer(String career) {
            this.career = career;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
