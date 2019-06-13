package com.lieying.petcat.bean;

import com.lieying.petcat.utils.GsonUtil;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 17:51
 */
public class ReactParamsJson {
    String contentId;
    String userID;
    String userPhone;
    String contentType;
    String position;
    String webUrl;

    public static class Builder {

        private ReactParamsJson mReactParamsJson;

        public Builder() {
            mReactParamsJson = new ReactParamsJson();
        }

        public Builder setContentID(String contenID) {
            mReactParamsJson.contentId = contenID;
            return this;
        }


        public Builder setUserID(String userID) {
            mReactParamsJson.userID = userID;
            return this;
        }

        public Builder setPosition(String position) {
            mReactParamsJson.position = position;
            return this;
        }

        public Builder setUserPhone(String phone) {
            mReactParamsJson.userPhone = phone;
            return this;
        }

        public Builder setContentType(String contentType) {
            mReactParamsJson.contentType = contentType;
            return this;
        }

        public Builder setWebUrl(String url) {
            mReactParamsJson.webUrl = url;
            return this;
        }


        public ReactParamsJson build() {
            return mReactParamsJson;
        }

        public String getRNParams() {
            return GsonUtil.GsonString(mReactParamsJson);
        }
    }
}
