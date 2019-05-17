package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/15 0015 11:00
 */
public class OtherUserBean {

    /**
     * mobilephone : 13000000000
     * nick_name : bchIKL049184824
     * head_image : 1234566.jpg
     * cnt : 7
     * profile : XXX开发者
     * follow_type : true
     */

    private String mobilephone;
    private String nick_name;
    private String head_image;
    private String cnt;
    private String profile;

    public String getFollow_type() {
        return follow_type;
    }

    public void setFollow_type(String follow_type) {
        this.follow_type = follow_type;
    }

    private String follow_type;

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
