package com.danbai.ys.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 弹幕类
 *
 * @author danbai
 * @date 2019-10-16 22:21
 */
@Document(collection = "dans")
public class Dan implements Serializable {
    private String id;
    private String player;
    private String author;
    private double time;
    private String text;
    private long color;
    private int type;
    private String ip;
    private String referer;
    private long date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public long getColor() {
        return color;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getReferer() {
        return referer;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }


}