package com.danbai.ys.service.Impl;

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
import java.util.List;
@Service
public class YsServiceImpl implements YsService {
    @Autowired
    YsbMapper ysbMapper;
    @Autowired
    VideoTimeMapper videoTimeMapper;
    public List<Ysb> page(int page, int pagenum){
        PageHelper.offsetPage(page, pagenum);
        // 设置分页查询条件
        Example example = new Example(Ysb.class);
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
        Example example =new Example(Ysb.class);
        example.createCriteria().andEqualTo("id",id);
        return ysbMapper.selectOneByExample(example);
    }

    @Override
    public List<Ysb> selectYsByPm(String pm) {
        Example example =new Example(Ysb.class);
        example.createCriteria().andLike("pm","%"+pm+"%");
        return ysbMapper.selectByExample(example);
    }

    @Override
    public List<Ysb> selectYsByIdlist(int id) {
        Example example =new Example(Ysb.class);
        example.createCriteria().andEqualTo("id",id);
        return ysbMapper.selectByExample(example);
    }

    @Override
    public boolean addYs(Ysb ysb) {
        if(ysbMapper.insert(ysb)>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Ysb ysb) {
        if (ysbMapper.updateByPrimaryKey(ysb)>0){
            return true;
        }
        return false;
    }
    @Override
    public boolean delYs(Ysb ysb) {

        if(ysbMapper.delete(ysb)>0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo getYs(String lx,int page,int size) {
        List<Ysb> relist = new ArrayList<>();
        Example example=new Example(Ysb.class);
        switch (lx){
            case "电影":
                example.createCriteria().orEqualTo("lx","动作片").orEqualTo("lx","喜剧片").orEqualTo("lx","爱情片").orEqualTo("lx","科幻片");
                break;
            case "电视剧":
                example.createCriteria().orEqualTo("lx","国产剧").orEqualTo("lx","韩国剧").orEqualTo("lx","欧美剧").orEqualTo("lx","海外剧");
                break;
            case "综艺":
                example.createCriteria().andLike("lx","%综艺片%");
                break;
            case "动漫":
                example.createCriteria().andLike("lx","%动漫片%");
        }
        example.orderBy("id").desc();
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
        videoTime1.setYsidname(videoTime.getYsidname());
        VideoTime videoTime2 = videoTimeMapper.selectOne(videoTime1);
        if(videoTime2!=null){
            Example example =new Example(VideoTime.class);
            example.createCriteria().andEqualTo("username",videoTime2.getUsername()).andEqualTo("ysidname",videoTime2.getYsidname());
            int i = videoTimeMapper.deleteByExample(example);
        }
        videoTimeMapper.insert(videoTime);
    }

    @Override
    public float getYsTime(VideoTime videoTime) {
        VideoTime videoTime1 = videoTimeMapper.selectOne(videoTime);
        if (videoTime1!=null){
            return videoTime1.getTime();
        }
        return 0;
    }
}
