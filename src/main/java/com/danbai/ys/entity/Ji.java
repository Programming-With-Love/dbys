package com.danbai.ys.entity;

import java.io.Serializable;

/**
 * @author danbai
 * @date 2019/10/13
 */
public class Ji implements Serializable {
    String name;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

