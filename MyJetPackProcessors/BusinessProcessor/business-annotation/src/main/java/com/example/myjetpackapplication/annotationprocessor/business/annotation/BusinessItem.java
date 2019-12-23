package com.example.myjetpackapplication.annotationprocessor.business.annotation;

import java.io.Serializable;

/**
 * Created by liutiantian on 2019-12-22 01:04 星期日
 */
public class BusinessItem implements Serializable {
    private String title;
    private String parent;
    private String path;
    private int priority;
    private boolean enable;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}