package com.lieying.comlib.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/27 0027 17:09
 */
public class FollowOrFansBean {

    /**
     * is_me : false
     * list : [{"id":"10103","mobilephone":"13213121312","follow_type":"1","cnt":"3","nick_name":"glqMSU268161926","head_image":"http://api.user.lynav.com/uploads_cms_images/1558663773187_994.png","profile":"","status":3,"is_big_v":"1"},{"id":"10101","mobilephone":"13942673665","follow_type":"1","cnt":"6","nick_name":"dd","head_image":"http://api.user.lynav.com/uploads_cms_images/1558665092477_11196.png","profile":"喜欢看动漫的程序媛","status":3,"is_big_v":"1"}]
     */

    private boolean is_me;
    private List<OtherUserBean> list;

    public boolean isIs_me() {
        return is_me;
    }

    public void setIs_me(boolean is_me) {
        this.is_me = is_me;
    }

    public List<OtherUserBean> getList() {
        return list;
    }

    public void setList(List<OtherUserBean> list) {
        this.list = list;
    }

}
