package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 配置信息类
 *
 * @author danbai
 * @date 2019-10-31 19:57
 */
@ApiModel(value = "配置信息实体类", description = "后台配置信息")
public class Config implements Serializable {
    @Id
    @ApiModelProperty(value = "配置名")
    private String item;
    @ApiModelProperty(value = "配置值")
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

    @Override
    public String toString() {
        return "Config{" +
                "item='" + item + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
