package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/4/24 0024 18:30
 */
public class AppListBean {

    private String id;
    private String app_name_cn;
    private String app_logo;
    private String app_short_desc;
    private String create_time;
    private String fabulous;
    private boolean is_fabulous;

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

    public boolean isIs_fabulous() {
        return is_fabulous;
    }

    public void setIs_fabulous(boolean is_fabulous) {
        this.is_fabulous = is_fabulous;
    }
}
