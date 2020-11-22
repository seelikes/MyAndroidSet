package com.example.myjetpackapplication.annotationprocessor.business.annotation;

import java.io.Serializable;

/**
 * Created by liutiantian on 2019-12-22 01:04 星期日
 */
public class BusinessItem implements Comparable<BusinessItem>, Serializable {
    private String title;
    private String parent;
    private Class<?> targetClass;
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

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
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

    @Override
    public int compareTo(BusinessItem item) {
        if (path == null && item.path == null) {
            return 0;
        } else if (path == null) {
            return 1;
        } else if (item.path == null) {
            return -1;
        } else {
            return path.compareTo(item.path);
        }
    }
}
