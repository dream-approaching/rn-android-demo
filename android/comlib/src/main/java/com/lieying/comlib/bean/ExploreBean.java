package com.lieying.comlib.bean;

public class ExploreBean {


    /**
     * title : 543444330000333rerttr34344
     * img : http://192.168.0.200:1130/uploads_cms_images/15554894766938817.jpg
     * comment_num : 0
     * type : 3
     * id : 11
     * is_favorites : true
     */

    private String title;
    private String img;
    private String comment_num;
    private String type;
    private String id;

    public String getUrl_content() {
        return url_content;
    }

    public void setUrl_content(String url_content) {
        this.url_content = url_content;
    }

    private String url_content;
    private boolean is_favorites;

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

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
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

    public boolean isIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(boolean is_favorites) {
        this.is_favorites = is_favorites;
    }
}
