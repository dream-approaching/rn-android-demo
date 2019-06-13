package com.lieying.petcat.network;

import com.lieying.petcat.BuildConfig;
import com.lieying.petcat.manager.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by liyi on 2018/2/1.
 *
 * 获取请参数，也可以在外面拼，在这里搞个工具类只是只是为了方便外面调用
 */

public class ReqBody {

    /**
     *  获取请求体
     *
     * */
    public static RequestBody getReqBody(Map<String , String > map){
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        EnDeParams params = getEnDeParams("123&&@#!12312340");
//        String ende = EnDeUtils.encrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), jsonObject.toString());
//        Log.e("test" , "我发送的    数据加密： ===========    "+ende);
//
//        String jiemi = EnDeUtils.decrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), ende);
//
//        Log.e("test" , "我发送的    数据解密： ===========    "+jiemi);
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }

    public static String getReqString(Map<String , String > map){
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            jsonObject.put("channel_id" , BuildConfig.APP_ID);
            jsonObject.put("app_ver", BuildConfig.APP_VER);
            jsonObject.put("app_ver_code", BuildConfig.APP_VER_CODE);
            jsonObject.put("ch", BuildConfig.CN);
            jsonObject.put("access_token", UserManager.getCurrentUser().getAccessToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        EnDeParams params = getEnDeParams("123&&@#!12312340");
//        String ende = EnDeUtils.encrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), jsonObject.toString());
//        Log.e("test" , "我发送的    数据加密： ===========    "+ende);
//        String jiemi = EnDeUtils.decrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), ende);
//        Log.e("test" , "我发送的    数据解密： ===========    "+jiemi);
        return jsonObject.toString();
    }
//    //数据加密
//    public static EnDeParams getEnDeParams(String key) {
//        EnDeParams enDeParams = new EnDeParams();
//        enDeParams.setAlgorithm("AES");
//        enDeParams.setTransformation("AES/ECB/pkcs5padding");
//        enDeParams.setKey(key);
//        return enDeParams;
//    }


    /**
     *  获取请求体
     *
     *  注册请求请
     * */
    public static RequestBody getRegisterBody(String phone , String password , String verificationCode ){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "register");
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("verificationCode", verificationCode);
            jsonObject.put("userType", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }


    /**
     *  获取请求体
     *
     *  登录请求
     * */
    public static RequestBody getLoginBody(String phone , String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "login");
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }

    /**
     *  获取请求体
     *
     *  重置密码
     * */
    public static RequestBody getResetPasswordBody(String phone , String password , String verificationCode){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "resetPassword");
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("verificationCode", verificationCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }


    /**
     *  获取请求体
     *
     *  获取我的
     *
     *  @param jobTitle   职位
     * */
    public static RequestBody getMyFollowAsks(String userID , String realName , String company , String jobTitle , String experience){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "applyAnswerer");
            jsonObject.put("userID", userID);
            jsonObject.put("company", company);
            jsonObject.put("realName", realName);
            jsonObject.put("jobTitle", jobTitle);
            jsonObject.put("experience", experience);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }


    /**
     *  获取请求体
     *
     *  申请成为答主
     *
     * */
    public static RequestBody uploadID(String userID){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "userID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        return body;
    }
}
