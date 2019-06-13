package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 14:49
 */
public class UserIndexInfoBean {


    /**
     * userinfo : {"id":"3","channel_id":"1","nick_name":"rre","refresh_token":"82ac77abd1c1e19cb6cc161a1704f8ec","access_token":"ae4d8e598abb38657be0f1f1e015c0c6","mobilephone":"15864578953","expires_in":"1560913549","head_image":"http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg","sex":"m","user_title":"","user_type":"0","career":"","location":"","profile":"","age":"0","is_big_v":"1","guan":"0","fen":"0","is_self":true,"is_add_friends":true}
     */

    private UserinfoBean userinfo;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * id : 3
         * channel_id : 1
         * nick_name : rre
         * refresh_token : 82ac77abd1c1e19cb6cc161a1704f8ec
         * access_token : ae4d8e598abb38657be0f1f1e015c0c6
         * mobilephone : 15864578953
         * expires_in : 1560913549
         * head_image : http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg
         * sex : m
         * user_title :
         * user_type : 0
         * career :
         * location :
         * profile :
         * age : 0
         * is_big_v : 1
         * guan : 0
         * fen : 0
         * is_self : true
         * is_add_friends : true
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
        private String is_big_v;
        private String guan;
        private String fen;
        private boolean is_self;
        private boolean is_add_friends;

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

        public String getIs_big_v() {
            return is_big_v;
        }

        public void setIs_big_v(String is_big_v) {
            this.is_big_v = is_big_v;
        }

        public String getGuan() {
            return guan;
        }

        public void setGuan(String guan) {
            this.guan = guan;
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }

        public boolean isIs_self() {
            return is_self;
        }

        public void setIs_self(boolean is_self) {
            this.is_self = is_self;
        }

        public boolean isIs_add_friends() {
            return is_add_friends;
        }

        public void setIs_add_friends(boolean is_add_friends) {
            this.is_add_friends = is_add_friends;
        }
    }
}
