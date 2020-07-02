package com.danbai.ys.entity;

import com.danbai.ys.utils.DateUtils;
import com.danbai.ys.utils.SiteMapUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author danbai
 * @date 2019-11-20 16:53
 */
public class SiteMap {
    public SiteMap() {
    }

    public SiteMap(String loc) {
        this.loc = loc;
        this.lastmod = new Date();
        this.changefreq = SiteMapUtils.CHANGEFREQ_ALWAYS;
        this.priority = "1.0";
    }

    public SiteMap(String loc, Date lastmod, String changefreq, String priority) {
        this.loc = loc;
        this.lastmod = lastmod;
        this.changefreq = changefreq;
        this.priority = priority;
    }

    /**
     * url https://www.xxx.com
     */
    private String loc;
    /**
     * 最后更新时间 yyyy-MM-dd
     */
    private Date lastmod;
    /**
     * 更新速度 always hourly daily weekly monthly yearly never
     */
    private String changefreq;
    /**
     * 权重 1.0 0.9 0.8
     */
    private String priority;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Date getLastmod() {
        return lastmod;
    }

    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    /** 重写 toString 适应xml格式 */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<url>");
        sb.append("<loc>" + loc + "</loc>");
        try {
            sb.append("<lastmod>" + DateUtils.dateFormat(lastmod, DateUtils.DATE_PATTERN) + "</lastmod>");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sb.append("<changefreq>" + changefreq + "</changefreq>");
        sb.append("<priority>" + priority + "</priority>");
        sb.append("</url>");
        return sb.toString();
    }
}
