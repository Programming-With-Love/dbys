package com.danbai.ys.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.Dan;
import com.danbai.ys.utils.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;

/**
 * @author danbai
 * @date 2019-10-17 09:12
 */
@Component
public class Dmas {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 保存弹幕
     *
     * @param url
     * @param player
     */
    @Async
    public void xzbcdm(String url, String player) {
        String json = HtmlUtils.getHtmlContentNp(url);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray comments = jsonObject.getJSONArray("comments");
        int maxc = comments.size();
        for (int j = 0; j < maxc; j++) {
            JSONObject comment = comments.getJSONObject(j);
            Dan d = new Dan();
            d.setReferer("https://v.qq.com");
            d.setIp("::ffff:111.111.111.111");
            d.setType(0);
            d.setTime(comment.getDouble("timepoint"));
            d.setAuthor(comment.getString("opername"));
            d.setPlayer(player);
            d.setText(comment.getString("content"));
            d.setColor(14277107);
            d.setDate(currentTimeMillis());
            mongoTemplate.insert(d);
        }
    }
}
