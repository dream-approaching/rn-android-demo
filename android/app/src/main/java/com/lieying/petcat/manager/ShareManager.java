package com.lieying.petcat.manager;

import com.lieying.petcat.share.ShareContent;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/6/3 0003 11:18
 */
public class ShareManager {
    private volatile static ShareManager verisonManager;

    public static ShareManager getInstance(){
        if(verisonManager == null){
            synchronized (ShareManager.class){
                if(verisonManager == null){
                    verisonManager = new ShareManager();
                }
            }
        }
        return verisonManager;
    }

    public ShareContent getShareContent (String title , String content , String imagUrl , String url){
        ShareContent WXContent = new ShareContent();
        WXContent.setTitle(title);
        WXContent.setContent(content);
        WXContent.setImgUrl(imagUrl);
        WXContent.setUrl(url);
        return WXContent;
    }
}
