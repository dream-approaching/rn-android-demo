package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/6 0006 11:52
 */
public class TopicBean {

    /**
     * title : 如果手机里只能留一个APP 你会留哪个
     * img : http://192.168.0.200:1130/uploads_cms_images/7111.jpg
     * type : 1
     * id : 18
     * short_content : 互联网的高速发展催生了无数个手机应用，很多APP在还没有被大众认识的情况下就下...
     * join_people : 0
     */

    private String title;
    private String img;
    private String type;
    private String id;
    private String short_content;
    private int join_people;

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

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public int getJoin_people() {
        return join_people;
    }

    public void setJoin_people(int join_people) {
        this.join_people = join_people;
    }
}
