package com.danbai.ys.service;

import com.danbai.ys.entity.Dan;
import com.danbai.ys.entity.PageResult;
import org.springframework.data.mongodb.core.query.Query;


/**
 * @program: dbys
 * @description: 弹幕服务
 * @author: DanBai
 * @create: 2020-07-01 13:41
 **/
public interface DmService {
    /**
     * 无条件分页
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageResult<Dan> getDmList(Integer pageSize, Integer pageNum);

    /**
     * 查询某条弹幕 by id
     *
     * @param id       id
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageResult<Dan> getDmListById(String id, Integer pageSize, Integer pageNum);

    /**
     * 查询某影视集的弹幕
     *
     * @param ysJi     用户名
     * @param pageSize 页大小
     * @param pageNum  页数
     * @return 结果
     */
    PageResult<Dan> getDmListByYsJi(String ysJi, Integer pageSize, Integer pageNum);

    /**
     * 查询某位用户的弹幕
     *
     * @param username 用户名
     * @param pageSize 页大小
     * @param pageNum  页数
     * @return 结果
     */
    PageResult<Dan> getDmListByYsUsername(String username, Integer pageSize, Integer pageNum);

    /**
     * 分页查询.
     *
     * @param query    Mongo Query对象，构造你自己的查询条件.
     * @param pageSize 分页的大小.
     * @param pageNum  当前页.
     * @param lastId   条件分页参数, 区别于skip-limit，采用find(_id>lastId).limit分页.
     *                 如果不跳页，像朋友圈，微博这样下拉刷新的分页需求，需要传递上一页的最后一条记录的ObjectId。 如果是null，则返回pageNum那一页.
     * @return PageResult，一个封装page信息的对象.
     */
    PageResult<Dan> pageQuery(Query query, Integer pageSize, Integer pageNum, String lastId);

    /**
     * 添加弹幕
     *
     * @param dan 弹幕
     */
    void addDm(Dan dan);

    /**
     * 更新弹幕
     *
     * @param dan
     */
    void updateDm(Dan dan);

    /**
     * 删除
     *
     * @param dan
     */
    void delDm(Dan dan);
}
