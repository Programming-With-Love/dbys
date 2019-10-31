package com.danbai.ys.service;

import com.danbai.ys.entity.Ylink;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author danbai
 */
public interface AdminService {
    /**
     * 修改公告
     * @param gg 公告内容
     */
    void updatagg(String gg);

    /**
     * 判断是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 返回所有友链 json格式
     * @return String
     */
    List<Ylink> getYlink();
    /**
     * 设置全部友链 json格式
     * @return String
     */
    /**
     * 更新所有友情连接
     * @param json json格式
     */
    void updataYlink(String json);
}
