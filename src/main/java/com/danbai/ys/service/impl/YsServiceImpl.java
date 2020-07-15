package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.*;
import com.danbai.ys.mapper.TvbMapper;
import com.danbai.ys.mapper.VideoTimeMapper;
import com.danbai.ys.mapper.YsbMapper;
import com.danbai.ys.service.YsService;
import com.danbai.ys.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class YsServiceImpl implements YsService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String OKTAGIDS = "oktagids";
    @Autowired
    TvbMapper tvbMapper;
    @Autowired
    YsbMapper ysbMapper;
    @Autowired
    VideoTimeMapper videoTimeMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    AdminServiceImpl adminService;
    static String NULL = "null";
    static String KONG = "";
    static int MIN_DM = 100;
    static int MAXTJ = 8;

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
    public List<Ysb> selectYsByGjc(String gjc) {
        Example example = new Example(Ysb.class);
        example.createCriteria().andLike("pm", "%" + gjc + "%").orLike("zy", "%" + gjc + "%").orLike("dy", "%" + gjc + "%");
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
            logger.info("增加了新的影视:" + ysb.getPm());
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
            logger.info("影视被删除:" + JSON.toJSONString(ysb));
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
                example.createCriteria().orLike("lx", "%动作片%").orLike("lx", "%喜剧片%").orLike("lx", "%爱情片%").orLike("lx"
                        , "%科幻片%");
                break;
            case "电视剧":
                example.createCriteria().orLike("lx", "%国产剧%").orLike("lx", "%韩国剧%").orLike("lx", "%欧美剧%").orLike("lx"
                        , "%海外剧%");
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
        videoTime1.setYsjiname(videoTime.getYsjiname());
        VideoTime videoTime2 = videoTimeMapper.selectOne(videoTime1);
        if (videoTime2 != null) {
            videoTime.setId(videoTime2.getId());
            videoTimeMapper.updateByPrimaryKey(videoTime);
        } else {
            videoTimeMapper.insert(videoTime);
        }
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
            if ((v.getTime() > 30)) {
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
        }
        return list;
    }

    @Override
    public List<Gkls> getGklsSole(String username) {
        Example example = new Example(VideoTime.class);
        example.createCriteria().andEqualTo("username", username);
        example.orderBy("gktime").desc();
        List<VideoTime> select = videoTimeMapper.selectByExample(example);
        List<Gkls> list = new ArrayList<>();
        for (VideoTime v : select) {
            if ((v.getTime() > 30)) {
                AtomicBoolean in = new AtomicBoolean(false);
                list.forEach(gkls -> {
                    if (gkls.id == v.getYsid()) {
                        in.set(true);
                        return;
                    }
                });
                if (in.get()) {
                    continue;
                }
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
    public String getYsDanMu(String pm, String ysid) {
        String rr = String.valueOf(redisTemplate.opsForValue().get(pm + ysid));
        if (rr == NULL) {
            return null;
        }
        if (rr != null && !rr.equals(KONG)) {
            if (adminService.getConfig(Config.DMCACHE).equals(AdminServiceImpl.YES)) {
                if (redisTemplate.opsForSet().isMember(OKTAGIDS, rr)) {
                    Query query = new Query(Criteria.where("player").is(ysid));
                    if (mongoTemplate.count(query, Dan.class) < MIN_DM) {
                        return rr;
                    }
                    return null;
                }
                String json = "{tagid:" + rr + ",player:\"" + ysid + "\"}";
                redisTemplate.opsForSet().add("tagids", json);
                return rr;
            }
            return rr;
        }
        return null;
    }

    @Override
    public List<Ysb> getAll() {
        return ysbMapper.selectAll();
    }

    @Override
    public List<Ysb> qcsy(List<Ysb> list) {
        List<Ysb> tmp = new ArrayList<>();
        //清除掉不需要的数据
        for (Ysb y : list) {
            y.setGkdz("");
            y.setXzdz("");
            y.setJs("");
            tmp.add(y);
        }
        return tmp;
    }

    @Override
    public List<Ysb> tuijian() {
        Example example = new Example(Ysb.class);
        try {
            example.createCriteria().andGreaterThan("gxtime", DateUtils.dateAdd(null, -30, true)).andGreaterThan("pf", 8);
            List<Ysb> ysbs = ysbMapper.selectByExample(example);
            ArrayList<Ysb> rys = new ArrayList<>();
            for (int i = 0; i < MAXTJ; i++) {
                int j = (int) (Math.random() * (ysbs.size() - 1));
                rys.add(ysbs.get(j));
                ysbs.remove(j);
            }
            return rys;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tvb> getAllTv() {
        return tvbMapper.selectAll();
    }

    @Override
    public List<Ysb> getNewYsb(int num) {
        Example example = new Example(Ysb.class);
        example.setOrderByClause("gxtime DESC limit 0," + num);
        return ysbMapper.selectByExample(example);
    }

    @Override
    public List<Ysb> getByType(String type1, String type2, String region, String year, String sort, int page) {
        PageHelper.startPage(page, 20).getPages();
        List<Ysb> ysbs = ysbMapper.getByType(type1, type2, region, year, sort);
        PageInfo pages = new PageInfo(ysbs);
        return pages.getList();
    }
}
