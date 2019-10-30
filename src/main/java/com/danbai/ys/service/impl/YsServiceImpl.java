package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.*;
import com.danbai.ys.mapper.VideoTimeMapper;
import com.danbai.ys.mapper.YsbMapper;
import com.danbai.ys.service.YsService;
import com.danbai.ys.utils.DateUtils;
import com.danbai.ys.utils.HtmlUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class YsServiceImpl implements YsService {
    static final String OKTAGIDS="oktagids";
    @Autowired
    YsbMapper ysbMapper;
    @Autowired
    VideoTimeMapper videoTimeMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MongoTemplate mongoTemplate;
    static String DMTYPE = "2";
    static String PAY_TYPE = "payType";
    static int MIN_DM = 100;
    @Override
    public List<Ysb> page(int page, int pagenum) {
        PageHelper.offsetPage(page, pagenum);
        // 设置分页查询条件
        Example example = new Example(Ysb.class);
        example.orderBy("id").desc();
        PageInfo<Ysb> pageInfo = new PageInfo<>(ysbMapper.selectByExample(example));
        List<Ysb> list1 = pageInfo.getList();
        return list1;
    }

    @Override
    public int contYs() {
        Example example = new Example(Ysb.class);
        return ysbMapper.selectCountByExample(example);
    }

    @Override
    public Ysb selectYsById(int id) {
        Example example = new Example(Ysb.class);
        example.createCriteria().andEqualTo("id", id);
        return ysbMapper.selectOneByExample(example);
    }

    @Override
    public List<Ysb> selectYsByPm(String pm) {
        Example example = new Example(Ysb.class);
        example.createCriteria().andLike("pm", "%" + pm + "%");
        return ysbMapper.selectByExample(example);
    }

    @Override
    public List<Ysb> selectYsByIdlist(int id) {
        Example example = new Example(Ysb.class);
        example.createCriteria().andEqualTo("id", id);
        return ysbMapper.selectByExample(example);
    }

    @Override
    public boolean addYs(Ysb ysb) {
        if (ysbMapper.insert(ysb) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Ysb ysb) {
        if (ysbMapper.updateByPrimaryKey(ysb) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delYs(Ysb ysb) {

        if (ysbMapper.delete(ysb) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public PageInfo getYs(String lx, int page, int size) {
        List<Ysb> relist = new ArrayList<>();
        Example example = new Example(Ysb.class);
        switch (lx) {
            case "电影":
                example.createCriteria().orLike("lx", "%动作片%").orLike("lx", "%喜剧片%").orLike("lx", "%爱情片%").orLike("lx", "%科幻片%");
                break;
            case "电视剧":
                example.createCriteria().orLike("lx", "%国产剧%").orLike("lx", "%韩国剧%").orLike("lx", "%欧美剧%").orLike("lx", "%海外剧%");
                break;
            case "综艺":
                example.createCriteria().andLike("lx", "%综艺%");
                break;
            case "动漫":
                example.createCriteria().andLike("lx", "%动漫%");
                break;
            case "全部":
                break;
            default:
        }
        example.orderBy("gxtime").desc();
        PageHelper.startPage(page, size).getPages();
        List<Ysb> ysbs = ysbMapper.selectByExample(example);
        PageInfo pages = new PageInfo(ysbs);
        relist.addAll(ysbs);
        return pages;
    }

    @Override
    public void addYsTime(VideoTime videoTime) {
        VideoTime videoTime1 = new VideoTime();
        videoTime1.setUsername(videoTime.getUsername());
        videoTime1.setYsid(videoTime.getYsid());
        VideoTime videoTime2 = videoTimeMapper.selectOne(videoTime1);
        if (videoTime2 != null) {
            Example example = new Example(VideoTime.class);
            example.createCriteria().andEqualTo("username", videoTime2.getUsername()).andEqualTo("ysid", videoTime2.getYsid());
            int i = videoTimeMapper.deleteByExample(example);
        }
        videoTimeMapper.insert(videoTime);
    }

    @Override
    public float getYsTime(VideoTime videoTime) {
        VideoTime videoTime1 = videoTimeMapper.selectOne(videoTime);
        if (videoTime1 != null) {
            return videoTime1.getTime();
        }
        return 0;
    }

    @Override
    public List<Gkls> getGkls(String username) {
        Example example = new Example(VideoTime.class);
        example.createCriteria().andEqualTo("username", username);
        example.orderBy("gktime").desc();
        List<VideoTime> select = videoTimeMapper.selectByExample(example);
        List<Gkls> list = new ArrayList<>();
        for (VideoTime v : select) {
            Gkls gkls = new Gkls();
            Ysb ysb = selectYsById(v.getYsid());
            if (ysb != null) {
                gkls.setPm(ysb.getPm());
                gkls.setYsimg(ysb.getTp());
            }
            gkls.setJi(v.getYsjiname());
            gkls.setTime(DateUtils.secondToTime(v.getTime().longValue()));
            gkls.setGktime(v.getGktime());
            gkls.setId(v.getYsid());
            list.add(gkls);
        }
        return list;
    }

    @Override
    public HashMap getYsLs(String username, int ysid) {
        HashMap<String, String> map = new HashMap<>(30);
        Ysb ysb = selectYsById(ysid);
        List<Gkls> gkls = getGkls(username);
        for (Gkls g : gkls) {
            if (g.getId() == ysid) {
                List<Ji> jis = JSON.parseArray(ysb.getGkdz(), Ji.class);
                for (Ji j : jis) {
                    if (g.getJi().equals(j.getName())) {
                        map.put("url", j.getUrl());
                        map.put("jiname", j.getName());
                        return map;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getYsDanMu(String pm, int jid,String ysid) {
        String rr = (String) redisTemplate.opsForValue().get(pm+jid);
        if (rr != null) {
            Query query = new Query(Criteria.where("player").is(ysid));
            if(redisTemplate.opsForSet().isMember(OKTAGIDS,rr)){
                if(mongoTemplate.count(query, Dan.class)<MIN_DM){
                    String json="{tagid:"+rr+",player:\""+ysid+ "\"}";
                    redisTemplate.opsForSet().add("tagids",json);
                    return rr;
                }
                return null;
            }
        }
        String encodePm = "";
        try {
            //对片面转码
            encodePm = URLEncoder.encode(pm, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        String urlStr = "http://v.qq.com/x/search/?q=" + encodePm;
        String content = HtmlUtils.getHtmlContent(urlStr);
        //匹配影视id
        String regEx = "data-id=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        String str = "";
        if (matcher.find()) {
            str = matcher.group(0);
        }
        //截取id
        String id = str.substring(9, str.length() - 1);
        content = HtmlUtils.getHtmlContent("http://s.video.qq.com/get_playsource?plat=2&type=4&range=1&otype=json&id=" + id);
        String json = content.substring(13, content.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject = jsonObject.getJSONObject("PlaylistItem");
        if (jsonObject == null || !(jsonObject.getString(PAY_TYPE).equals(DMTYPE))) {
            return null;
        }
        //解析为数组
        JSONArray jsonArray = jsonObject.getJSONArray("videoPlayList");
        urlStr = "http://bullet.video.qq.com/fcgi-bin/target/regist?otype=json&vid=" + jsonArray.getJSONObject(jsonArray.size()-jid-1).getString("id");
        content = HtmlUtils.getHtmlContent(urlStr);
        json = content.substring(13, content.length() - 1);
        jsonObject = JSONObject.parseObject(json);
        String tagid=jsonObject.getString("targetid");
        redisTemplate.opsForValue().set(pm+jid, tagid, 30, TimeUnit.DAYS);
        redisTemplate.opsForSet().add("tagids","{tagid:"+tagid+",player:\""+ysid+"\"}");
        return tagid;
    }

    @Override
    public List<Ysb> getAll() {
        return ysbMapper.selectAll();
    }
}
