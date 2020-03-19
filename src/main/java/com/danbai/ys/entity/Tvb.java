
package com.danbai.ys.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author danbai
 * @date 2019-11-04 15:25
 */
public class Tvb implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
    private String tpurl;
    private String dmid;

    public Tvb(int id, String name, String url, String tpurl, String dmid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.tpurl = tpurl;
        this.dmid = dmid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Tvb tvb = (Tvb) o;
        return id.equals( tvb.id) &&
                Objects.equals(name, tvb.name) &&
                Objects.equals(url, tvb.url) &&
                Objects.equals(tpurl, tvb.tpurl) &&
                Objects.equals(dmid, tvb.dmid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, tpurl, dmid);
    }

    public Tvb() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTpurl() {
        return tpurl;
    }

    public void setTpurl(String tpurl) {
        this.tpurl = tpurl;
    }

    public String getDmid() {
        return dmid;
    }

    public void setDmid(String dmid) {
        this.dmid = dmid;
    }
}