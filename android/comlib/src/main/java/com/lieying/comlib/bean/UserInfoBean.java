package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/19 0019 10:59
 */
public class UserInfoBean {


    public String getAccessToken(){
        if(userinfo == null){
            return "c3c4db657e05f684f696096ec6f06371";
        }
        return userinfo.access_token;
    }
    public String getPhone(){
        if(userinfo == null){
            return "18503068868";
        }
        return userinfo.mobilephone;
    }
    public String getName(){
        if(userinfo == null){
            return "liyi";
        }
        return userinfo.nick_name;
    }

    @Override
    public String toString() {
        return getAccessToken()+","+getPhone()+","+getName();
    }

    /**
     * userinfo : {"id":"14","channel_id":"1","nick_name":"bchIKL049184823","refresh_token":"3cb441e8358f6bf889b9a11abe674cc8","access_token":"e11c20ca95cf1fd63ebbe87c7f418f68","mobilephone":"18503068868","expires_in":"1558506575","head_image":""}
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
         * id : 14
         * channel_id : 1
         * nick_name : bchIKL049184823
         * refresh_token : 3cb441e8358f6bf889b9a11abe674cc8
         * access_token : e11c20ca95cf1fd63ebbe87c7f418f68
         * mobilephone : 18503068868
         * expires_in : 1558506575
         * head_image :
         */

        private String id;
        private String channel_id;
        private String nick_name;
        private String refresh_token;
        private String access_token;
        private String mobilephone;
        private String expires_in;
        private String head_image;

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
    }
}
