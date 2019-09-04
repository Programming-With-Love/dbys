package com.danbai.ys.entity;
import java.util.Date;
public class Gkls {
    public String ysimg;
    public String pm;
    public String ji;
    public String time;
    public Date gktime;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYsimg() {
        return ysimg;
    }

    public void setYsimg(String ysimg) {
        this.ysimg = ysimg;
    }


    public String getPm() {
        return pm;
    }

    public Date getGktime() {
        return gktime;
    }

    public void setGktime(Date gktime) {
        this.gktime = gktime;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
