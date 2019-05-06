package com.lieying.socialappstore.manager;

import android.util.SparseArray;

/**
  * @Description:    界面布局管理器
  * @Author:         liyi
  * @CreateDate:    2019/4/18 0018 15:11
 */
public class ViewFrameManager {
    private static ViewFrameManager viewFrameManager;

    public static ViewFrameManager  getInstance(){
        if(viewFrameManager == null){
            synchronized (ViewFrameManager.class){
                if(viewFrameManager == null){
                    viewFrameManager = new ViewFrameManager();
                }
            }
        }
        return viewFrameManager;
    }


    SparseArray<IndexFgBean> sparseArray = new SparseArray<>();

    public SparseArray createIndexMap(){
        IndexFgBean indexFgBean0 = new IndexFgBean();
        indexFgBean0.setNative(true);
        IndexFgBean indexFgBean1 = new IndexFgBean();
        indexFgBean1.setNative(true);
        IndexFgBean indexFgBean2 = new IndexFgBean();
        indexFgBean2.setNative(true);
        sparseArray.put( 0 , indexFgBean0);
        sparseArray.put( 1 , indexFgBean1);
        sparseArray.put( 2 , indexFgBean2);
        return sparseArray;
    }

    public class IndexFgBean{
        boolean isNative;
        int position;
        String icon_normal;

        public boolean isNative() {
            return isNative;
        }

        public void setNative(boolean aNative) {
            isNative = aNative;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getIcon_normal() {
            return icon_normal;
        }

        public void setIcon_normal(String icon_normal) {
            this.icon_normal = icon_normal;
        }

        public String getIcon_press() {
            return icon_press;
        }

        public void setIcon_press(String icon_press) {
            this.icon_press = icon_press;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText_color_normal() {
            return text_color_normal;
        }

        public void setText_color_normal(String text_color_normal) {
            this.text_color_normal = text_color_normal;
        }

        public String getText_color_press() {
            return text_color_press;
        }

        public void setText_color_press(String text_color_press) {
            this.text_color_press = text_color_press;
        }

        public String getBundleName() {
            return bundleName;
        }

        public void setBundleName(String bundleName) {
            this.bundleName = bundleName;
        }

        String icon_press;
        String text;
        String text_color_normal;
        String text_color_press;
        String bundleName;
    }
}
