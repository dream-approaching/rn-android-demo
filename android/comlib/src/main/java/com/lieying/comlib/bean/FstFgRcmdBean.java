package com.lieying.comlib.bean;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/6/6 0006 11:38
 */
public class FstFgRcmdBean {

    /**
     * id : 443
     * label : 搞笑视频
     * title : 短视频
     * content : http://192.168.0.200:1230/uploads_cms_images/1560137463704_72432.mp4
     * commit_user : rre
     * mobilephone : 15864578953
     * favorites : 0
     * created_time : 1560137463
     * head_image : http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg
     * is_fabulous : true
     * type : 2
     * file : http://192.168.0.200:1230/uploads_cms_images/1560137463704_72432.gif
     */

    private String id;
    private String label;
    private String title;
    private String content;
    private String commit_user;
    private String mobilephone;
    private String favorites;
    private String created_time;
    private String head_image;
    private boolean is_fabulous;
    private int type;
    private String file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommit_user() {
        return commit_user;
    }

    public void setCommit_user(String commit_user) {
        this.commit_user = commit_user;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
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

    public boolean isIs_fabulous() {
        return is_fabulous;
    }

    public void setIs_fabulous(boolean is_fabulous) {
        this.is_fabulous = is_fabulous;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
