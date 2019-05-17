package com.lieying.socialappstore.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/15 0015 15:06
 */
public class NoticeBean {

    /**
     * bundle_path : MyReactNativeAppthree
     * bundle_name : myNotice
     */

    private String bundle_path;

    public String getBundle_path() {
        return bundle_path;
    }

    public void setBundle_path(String bundle_path) {
        this.bundle_path = bundle_path;
    }

    public String getBundle_router_name() {
        return bundle_router_name;
    }

    public void setBundle_router_name(String bundle_router_name) {
        this.bundle_router_name = bundle_router_name;
    }

    public String getBundle_params() {
        return bundle_params;
    }

    public void setBundle_params(String bundle_params) {
        this.bundle_params = bundle_params;
    }

    private String bundle_router_name;
    private String bundle_params;
}
