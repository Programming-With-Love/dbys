package com.danbai.ys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**影视类
 * @author danbai
 */
public class Ysb implements Serializable {

    private static final long serialVersionUID = 1L;
    public static String DY="dy";
    public static String DSJ="dsj";
    public static String DM="dm";
    public static String ZY="zy";
    public static String TJ="tj";
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
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 评分
     */
    public Float getPf() {
        return pf;
    }

    /**
     * @param pf 评分
     */
    public void setPf(Float pf) {
        this.pf = pf;
    }

    /**
     * @return pm 片名
     */
    public String getPm() {
        return pm;
    }

    /**
     * @param pm 片名
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
     * @param tp 图片
     */
    public void setTp(String tp) {
        this.tp = tp;
    }

    /**
     * @return zt 状态
     */
    public String getZt() {
        return zt;
    }

    /**
     * @param zt 状态
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * @return bm 别名
     */
    public String getBm() {
        return bm;
    }

    /**
     * @param bm 别名
     */
    public void setBm(String bm) {
        this.bm = bm;
    }

    /**
     * @return dy 导演
     */
    public String getDy() {
        return dy;
    }

    /**
     * @param dy 导演
     */
    public void setDy(String dy) {
        this.dy = dy;
    }

    /**
     * @return zy 主演
     */
    public String getZy() {
        return zy;
    }

    /**
     * @param zy 主演
     */
    public void setZy(String zy) {
        this.zy = zy;
    }

    /**
     * @return lx 类型
     */
    public String getLx() {
        return lx;
    }

    /**
     * @param lx 类型
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    /**
     * @return dq 地区
     */
    public String getDq() {
        return dq;
    }

    /**
     * @param dq 地区
     */
    public void setDq(String dq) {
        this.dq = dq;
    }

    /**
     * @return yy 语言
     */
    public String getYy() {
        return yy;
    }

    /**
     * @param yy 语言
     */
    public void setYy(String yy) {
        this.yy = yy;
    }

    /**
     * @return sytime 上映时间
     */
    public String getSytime() {
        return sytime;
    }

    /**
     * @param sytime 上映时间
     */
    public void setSytime(String sytime) {
        this.sytime = sytime;
    }

    /**
     * @return pctime 片长
     */
    public String getPctime() {
        return pctime;
    }

    /**
     * @param pctime 片长
     */
    public void setPctime(String pctime) {
        this.pctime = pctime;
    }

    /**
     * @return gxtime 更新时间
     */
    public String getGxtime() {
        return gxtime;
    }

    /**
     * @param gxtime 更新时间
     */
    public void setGxtime(String gxtime) {
        this.gxtime = gxtime;
    }

    /**
     * @return js 介绍
     */
    public String getJs() {
        return js;
    }

    /**
     * @param js 介绍
     */
    public void setJs(String js) {
        this.js = js;
    }

    /**
     * @return gkdz 观看地址
     */
    public String getGkdz() {
        return gkdz;
    }

    /**
     * @param gkdz 观看地址
     */
    public void setGkdz(String gkdz) {
        this.gkdz = gkdz;
    }

    /**
     * @return xzdz 下载地址
     */
    public String getXzdz() {
        return xzdz;
    }

    /**
     * @param xzdz 下载地址
     */
    public void setXzdz(String xzdz) {
        this.xzdz = xzdz;
    }
}