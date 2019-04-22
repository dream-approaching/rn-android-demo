package com.lieying.socialappstore.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Created by liyi on 2018/3/9.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        AllResponseData allResponseData = gson.fromJson(response, AllResponseData.class);
        //获取加密数据，解密，之后再让adapter去处理json串，解析具体的数据就可以了

//        Log.e("test" , "response ------------    "+response);
//        EnDeParams params = getEnDeParams("123&&@#!12312340");
//        Log.e("test" , "获取到的数据 ------------    "+EnDeUtils.decrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), response));
//
//        T t = adapter.fromJson(EnDeUtils.decrypt(params.getTransformation(), params.getAlgorithm(), params.getKey(), response));
//        try{
//            return t;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            value.close();
//        }
        return  null;
    }
}