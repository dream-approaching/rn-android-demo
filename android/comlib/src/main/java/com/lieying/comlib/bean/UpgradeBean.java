package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/30 0030 15:30
 */
public class UpgradeBean {

    /**
     * app_name : shumei
     * ch : CESHI
     * ver_code : 1.0.07
     * content : 1.增加版本提醒功能|2.更好地支持Android手机操作系统|3.数十项性能优化
     * is_force : false
     */

    private String app_name;
    private String ch;
    private String ver_code;
    private String content;
    private boolean is_force;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getVer_code() {
        return ver_code;
    }

    public void setVer_code(String ver_code) {
        this.ver_code = ver_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_force() {
        return is_force;
    }

    public void setIs_force(boolean is_force) {
        this.is_force = is_force;
    }
}
