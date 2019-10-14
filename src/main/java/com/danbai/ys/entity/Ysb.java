package com.danbai.ys.entity;

import javax.persistence.*;
import java.io.Serializable;

public class Ysb implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float pf;

    private String pm;

    private String tp;

    private String zt;

    private String bm;

    private String dy;

    private String zy;

    private String lx;

    private String dq;

    private String yy;

    private String sytime;

    private String pctime;

    private String gxtime;

    private String js;

    private String gkdz;

    private String xzdz;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return pf
     */
    public Float getPf() {
        return pf;
    }

    /**
     * @param pf
     */
    public void setPf(Float pf) {
        this.pf = pf;
    }

    /**
     * @return pm
     */
    public String getPm() {
        return pm;
    }

    /**
     * @param pm
     */
    public void setPm(String pm) {
        this.pm = pm;
    }

    /**
     * @return tp
     */
    public String getTp() {
        return tp;
    }

    /**
     * @param tp
     */
    public void setTp(String tp) {
        this.tp = tp;
    }

    /**
     * @return zt
     */
    public String getZt() {
        return zt;
    }

    /**
     * @param zt
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * @return bm
     */
    public String getBm() {
        return bm;
    }

    /**
     * @param bm
     */
    public void setBm(String bm) {
        this.bm = bm;
    }

    /**
     * @return dy
     */
    public String getDy() {
        return dy;
    }

    /**
     * @param dy
     */
    public void setDy(String dy) {
        this.dy = dy;
    }

    /**
     * @return zy
     */
    public String getZy() {
        return zy;
    }

    /**
     * @param zy
     */
    public void setZy(String zy) {
        this.zy = zy;
    }

    /**
     * @return lx
     */
    public String getLx() {
        return lx;
    }

    /**
     * @param lx
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    /**
     * @return dq
     */
    public String getDq() {
        return dq;
    }

    /**
     * @param dq
     */
    public void setDq(String dq) {
        this.dq = dq;
    }

    /**
     * @return yy
     */
    public String getYy() {
        return yy;
    }

    /**
     * @param yy
     */
    public void setYy(String yy) {
        this.yy = yy;
    }

    /**
     * @return sytime
     */
    public String getSytime() {
        return sytime;
    }

    /**
     * @param sytime
     */
    public void setSytime(String sytime) {
        this.sytime = sytime;
    }

    /**
     * @return pctime
     */
    public String getPctime() {
        return pctime;
    }

    /**
     * @param pctime
     */
    public void setPctime(String pctime) {
        this.pctime = pctime;
    }

    /**
     * @return gxtime
     */
    public String getGxtime() {
        return gxtime;
    }

    /**
     * @param gxtime
     */
    public void setGxtime(String gxtime) {
        this.gxtime = gxtime;
    }

    /**
     * @return js
     */
    public String getJs() {
        return js;
    }

    /**
     * @param js
     */
    public void setJs(String js) {
        this.js = js;
    }

    /**
     * @return gkdz
     */
    public String getGkdz() {
        return gkdz;
    }

    /**
     * @param gkdz
     */
    public void setGkdz(String gkdz) {
        this.gkdz = gkdz;
    }

    /**
     * @return xzdz
     */
    public String getXzdz() {
        return xzdz;
    }

    /**
     * @param xzdz
     */
    public void setXzdz(String xzdz) {
        this.xzdz = xzdz;
    }
}