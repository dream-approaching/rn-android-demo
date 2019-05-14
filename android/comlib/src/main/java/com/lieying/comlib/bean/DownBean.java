package com.lieying.comlib.bean;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/14 0014 16:34
 */
public class DownBean implements Serializable {
    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String downPath;
    String fileName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
