package com.lieying.petcat.network;


import com.lieying.comlib.utils.EmptyUtil;
import com.lieying.petcat.BuildConfig;

/**
 * wudi
 * 域名配置清单
 * 若多域名，全部在buildConfig中配置，在Hosts中通过对应的get()方法获取
 */
public class Hosts {
    private final static String HTTP = "http://";
    private final static String HTTPS = "https://";

    public static String getMainHost() {
        return getHttpHost(BuildConfig.MAIN_HOST);
    }

    public static String getHttpHost(String url) {
//        if (EmptyUtil.isEmpty(url)) return null;
//        StringBuilder urlSb = new StringBuilder(url);
//
//        if (url.startsWith(HTTPS)) {
//            urlSb = urlSb.replace(0, urlSb.length(), urlSb.substring(HTTPS.length(), url.length()));
//            urlSb.insert(0, HTTP);
//        } else if (!url.startsWith(HTTP)) {
//            urlSb.insert(0, HTTP);
//        } else {
//            //以http://开头
//        }
//        return urlSb.toString();
        return url;
    }

    public static String getHttpsHost(String url) {
        if (EmptyUtil.isEmpty(url)) return null;
        StringBuilder urlSb = new StringBuilder(url);

        if (url.startsWith(HTTP)) {
            urlSb = urlSb.replace(0, urlSb.length(), urlSb.substring(HTTP.length(), url.length()));
            urlSb.insert(0, HTTPS);
        } else if (!url.startsWith(HTTPS)) {
            urlSb.insert(0, HTTPS);
        } else {
            //以httpS://开头
        }
        return urlSb.toString();
    }

}
