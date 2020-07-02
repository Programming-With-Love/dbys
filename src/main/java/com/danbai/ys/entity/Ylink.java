package com.danbai.ys.entity;

import java.io.Serializable;

/**友链类
 * @author danbai
 * @date 2019-10-31 15:53
 */
public class Ylink implements Serializable {
    /**
     * 友链名字
     */
    private String name;
    /**
     * 链接地址
     */
    private String url;

    public Ylink(String name, String url) {
        this.name = name;
        this.url = url;
    }

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
