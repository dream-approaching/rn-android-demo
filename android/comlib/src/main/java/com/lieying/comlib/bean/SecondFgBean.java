package com.lieying.comlib.bean;

import java.util.List;

/**
 * @Description: 第二个fragment的数据对象
 * @Author: liyi
 * @CreateDate: 2019/6/14 0014 15:25
 */
public class SecondFgBean {

    private List<LikeBean> like;
    private List<ListBean> list;

    public List<LikeBean> getLike() {
        return like;
    }

    public void setLike(List<LikeBean> like) {
        this.like = like;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class LikeBean {
        /**
         * follow_mobilephone : 13613033073
         * head_image : null
         * nick_name : null
         * profile : null
         */

        private String follow_mobilephone;
        private String head_image;
        private String nick_name;
        private String profile;

        public String getFollow_mobilephone() {
            return follow_mobilephone;
        }

        public void setFollow_mobilephone(String follow_mobilephone) {
            this.follow_mobilephone = follow_mobilephone;
        }

        public String getHead_image() {
            return head_image;
        }

        public void setHead_image(String head_image) {
            this.head_image = head_image;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }

    public static class ListBean {
        /**
         * id : 448
         * channel_id : 2
         * commit_user : rre
         * mobilephone : 13000000000
         * fabulous : 0
         * video_gif : http://192.168.0.200:1230/uploads_cms_images/1560242376583_42544.gif
         * comment_num : 0
         * created_time : 1560242377
         * forward_num : 0
         * location :
         * title : 短视频
         * mill_created_time : 1560242377292
         * type : 8
         * head_image : null
         * is_fabulous : true
         * file : http://192.168.0.200:1230/uploads_cms_images/1560242376583_42544.gif
         * typesetting : 1
         * is_add_friends : true
         * sharemintime : 0
         * videomintime : 1560242377292
         */

        private String id;
        private String channel_id;
        private String commit_user;
        private String mobilephone;
        private String fabulous;
        private String video_gif;
        private String comment_num;
        private String created_time;
        private String forward_num;
        private String location;
        private String title;
        private String mill_created_time;
        private String type;
        private String head_image;
        private boolean is_fabulous;
        private String file;
        private int typesetting;
        private boolean is_add_friends;
        private int sharemintime;
        private String videomintime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
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

        public String getFabulous() {
            return fabulous;
        }

        public void setFabulous(String fabulous) {
            this.fabulous = fabulous;
        }

        public String getVideo_gif() {
            return video_gif;
        }

        public void setVideo_gif(String video_gif) {
            this.video_gif = video_gif;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getForward_num() {
            return forward_num;
        }

        public void setForward_num(String forward_num) {
            this.forward_num = forward_num;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMill_created_time() {
            return mill_created_time;
        }

        public void setMill_created_time(String mill_created_time) {
            this.mill_created_time = mill_created_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getTypesetting() {
            return typesetting;
        }

        public void setTypesetting(int typesetting) {
            this.typesetting = typesetting;
        }

        public boolean isIs_add_friends() {
            return is_add_friends;
        }

        public void setIs_add_friends(boolean is_add_friends) {
            this.is_add_friends = is_add_friends;
        }

        public int getSharemintime() {
            return sharemintime;
        }

        public void setSharemintime(int sharemintime) {
            this.sharemintime = sharemintime;
        }

        public String getVideomintime() {
            return videomintime;
        }

        public void setVideomintime(String videomintime) {
            this.videomintime = videomintime;
        }
    }
}
