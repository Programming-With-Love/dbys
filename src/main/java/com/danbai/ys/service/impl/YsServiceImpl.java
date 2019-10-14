package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.Gkls;
import com.danbai.ys.entity.Ji;
import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.mapper.VideoTimeMapper;
import com.danbai.ys.mapper.YsbMapper;
import com.danbai.ys.service.YsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class YsServiceImpl implements YsService {
    @Autowired
    YsbMapper ysbMapper;
    @Autowired
    VideoTimeMapper videoTimeMapper;
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
            gkls.setTime(v.getTime() / 60 + "分");
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
}
