package com.danbai.ys.entity;

import javax.persistence.*;

/**配置信息类
 * @author danbai
 * @date 2019-10-31 19:57
 */
public class Config {
    @Id
    private String item;
    private String value;
    public static String YLINK = "ylink";
    public static String GG = "gg";
    public static String AD = "ad";
    public static String DMCACHE = "dmcache";
    public static String HEAD = "head";
    public static String FOOTER = "footer";
    public Config(String item, String value) {
        this.item = item;
        this.value = value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Config() {
    }

    public Config(String item) {
        this.item = item;
    }
}
