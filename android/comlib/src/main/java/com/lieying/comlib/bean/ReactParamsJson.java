package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 17:51
 */
public class ReactParamsJson {
    String contentId;
    String userID;

    public static class Builder {

        private ReactParamsJson mReactParamsJson;

        public Builder(){
            mReactParamsJson = new ReactParamsJson();
        }

        public Builder setName(String contenID) {
            mReactParamsJson.contentId = contenID;
            return this;
        }


        public Builder seUserID(String userID) {
            mReactParamsJson.userID = userID;
            return this;
        }

        public ReactParamsJson build(){
            return mReactParamsJson;
        }
    }
}
