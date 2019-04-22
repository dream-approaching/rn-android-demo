package com.lieying.socialappstore.network;


/**
 * 接口返回数据结构bean
 *
 * @author liyi
 */
public class ResponseData<T> {
    public int getStatus() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    int code ;

    String msg ;


    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }

    int encrypt;


    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 数据主体
     */
    T data;


}
