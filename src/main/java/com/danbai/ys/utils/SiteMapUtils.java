package com.danbai.ys.utils;

import com.danbai.ys.entity.SiteMap;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.impl.YsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * @author danbai
 * @date 2019-11-20 16:58
 */
@Component
public class SiteMapUtils {
    public final static String BEGIN_DOC = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
    public final static String END_DOC = "</urlset>";
    public final static String CHANGEFREQ_ALWAYS = "always";
    public final static String CHANGEFREQ_HOURLY = "hourly";
    public final static String CHANGEFREQ_DAILY = "daily";
    public final static String CHANGEFREQ_WEEKLY = "weekly";
    public final static String CHANGEFREQ_MONTHLY = "monthly";
    public final static String CHANGEFREQ_YEARLY = "yearly";
    public final static String CHANGEFREQ_NEVER = "never";
    @Autowired
    private YsServiceImpl ysService;
    public String getSiteMap() {
        StringBuffer sb = new StringBuffer();
        sb.append(BEGIN_DOC);
        sb.append(new SiteMap("https://dbys.vip"));
        List<Ysb> ysbs = ysService.getNewYsb(1000);
        for (Ysb ys : ysbs) {
            try {
                sb.append(new SiteMap("https://dbys.vip/ys?id=" + ys.getId(),DateUtils.dateParse(ys.getGxtime(),DateUtils.MINUTE_PATTERN),CHANGEFREQ_MONTHLY, "0.9"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sb.append(END_DOC);
        return sb.toString();
    }
}