package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/7 0007 10:31
 */
public class UserJoinBean {


    /**
     * id : 61
     * type : 1
     * content_id : 11
     * mobilephone : 18503068868
     * created_time : 1557309548
     * head_image : http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg
     * is_big_v : 1
     * nick_name : adxDIY139111944
     * mydata : {"title":"543444330000333rerttr34344","img":"http://192.168.0.200:1130/uploads_cms_images/15554894766938817.jpg","type":"3","id":"11","detail_type":"1","my_content":""}
     * mytype : 3
     * listtype : 3
     * timestr : 23分钟前
     */

    private String id;
    private String type;
    private String content_id;
    private String mobilephone;
    private String created_time;
    private String head_image;
    private String is_big_v;
    private String nick_name;
    private MydataBean mydata;
    private String mytype;
    private int listtype;
    private String timestr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getIs_big_v() {
        return is_big_v;
    }

    public void setIs_big_v(String is_big_v) {
        this.is_big_v = is_big_v;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public MydataBean getMydata() {
        return mydata;
    }

    public void setMydata(MydataBean mydata) {
        this.mydata = mydata;
    }

    public String getMytype() {
        return mytype;
    }

    public void setMytype(String mytype) {
        this.mytype = mytype;
    }

    public int getListtype() {
        return listtype;
    }

    public void setListtype(int listtype) {
        this.listtype = listtype;
    }

    public String getTimestr() {
        return timestr;
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }

    public static class MydataBean {
        /**
         * title : 543444330000333rerttr34344
         * img : http://192.168.0.200:1130/uploads_cms_images/15554894766938817.jpg
         * type : 3
         * id : 11
         * detail_type : 1
         * my_content :
         */

        private String title;
        private String img;
        private String type;
        private String id;
        private String detail_type;
        private String my_content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDetail_type() {
            return detail_type;
        }

        public void setDetail_type(String detail_type) {
            this.detail_type = detail_type;
        }

        public String getMy_content() {
            return my_content;
        }

        public void setMy_content(String my_content) {
            this.my_content = my_content;
        }
    }
}
