package com.danbai.ys.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.danbai.ys.entity.Dan;
import com.danbai.ys.entity.PageResult;
import com.danbai.ys.service.DmService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: dbys
 * @description: dm 服务实现类
 * @author: DanBai
 * @create: 2020-07-01 13:55
 **/
@Service
public class DmServiceImpl implements DmService {
    private static final int FIRST_PAGE_NUM = 1;
    private static final String ID = "_id";
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public PageResult<Dan> getDmList(Integer pageSize, Integer pageNum) {
        return pageQuery(new Query(), pageSize, pageNum, null);
    }

    @Override
    public PageResult<Dan> getDmListById(String id, Integer pageSize, Integer pageNum) {
        return pageQuery(new Query(Criteria.where("_id").is(id)), pageSize, pageNum, null);
    }

    @Override
    public PageResult<Dan> getDmListByYsJi(String ysJi, Integer pageSize, Integer pageNum) {
        Query query = new Query(Criteria.where("player").is(ysJi));
        return pageQuery(query, pageSize, pageNum, null);
    }

    @Override
    public PageResult<Dan> getDmListByYsUsername(String username, Integer pageSize, Integer pageNum) {
        Query query = new Query(Criteria.where("author").is(username));
        return pageQuery(query, pageSize, pageNum, null);
    }

    @Override
    public PageResult<Dan> pageQuery(Query query, Integer pageSize, Integer pageNum, String lastId) {
        //条件查询总条数
        long total = mongoTemplate.count(query, Dan.class);
        //算页数
        final Integer pages = (int) Math.ceil(total / (double) pageSize);
        if (pageNum <= 0 || pageNum > pages) {
            pageNum = FIRST_PAGE_NUM;
        }
        final Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(lastId)) {
            //有lastId的分页
            if (pageNum != FIRST_PAGE_NUM) {
                criteria.and(ID).gt(new ObjectId(lastId));
            }
            query.limit(pageSize);
        } else {
            //分页
            int skip = pageSize * (pageNum - 1);
            query.skip(skip).limit(pageSize);
        }
        final List<Dan> entityList = mongoTemplate
                .find(query.addCriteria(criteria), Dan.class);
        final PageResult<Dan> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setPages(pages);
        pageResult.setPageSize(pageSize);
        pageResult.setPageNum(pageNum);
        pageResult.setList(entityList);
        return pageResult;
    }

    @Override
    public void addDm(Dan dan) {
        mongoTemplate.insert(dan);
    }

    @Override
    public void updateDm(Dan dan) {
        delDm(dan);
        addDm(dan);
    }

    @Override
    public void delDm(Dan dan) {
        mongoTemplate.remove(dan);
    }

}
