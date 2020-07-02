/**
 * @author DanBai
 * @create 2020-03-10 23:57
 * @desc 影院room
 **/
package com.danbai.ys.entity;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

public class CinemaRoom implements Serializable {
    private int id;
    private String name;
    private CopyOnWriteArraySet<String> sockets;
    private String pass;
    private String authorId;
    private String url;
    private double time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CopyOnWriteArraySet<String> getSockets() {
        return sockets;
    }

    public void setSockets(CopyOnWriteArraySet<String> sockets) {
        this.sockets = sockets;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public CinemaRoom(int id, String name, String pass, String authorId) {
        this.id = id;
        this.name = name;
        this.sockets = new CopyOnWriteArraySet<>();
        this.pass = pass;
        this.authorId = authorId;
    }
}
