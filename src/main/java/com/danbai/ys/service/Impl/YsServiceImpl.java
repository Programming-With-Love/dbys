package com.danbai.ys.service.Impl;

import com.danbai.ys.entity.Ysb;
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
        return ysbMapper.selectAll().size();
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
    public List<Ysb> indexYs(String lx) {
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
        PageHelper.startPage(1, 12,false); // 每次查询20条
        List<Ysb> ysbs = ysbMapper.selectByExample(example);
        relist.addAll(ysbs);
        return relist;
    }
}
