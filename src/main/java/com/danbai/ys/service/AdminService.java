package com.danbai.ys.service;

import com.danbai.ys.entity.Config;
import com.danbai.ys.entity.Ylink;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author danbai
 */
public interface AdminService {
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
    /**
     * 获取所有配置
     * @return List
     */
    List<Config> getConfig();
    /**
     * 更新所有配置
     * @param list 配置列表
     */
    void updataAllConfig(List<Config> list);
    /**
     * 获取配置
     * @param name 配置项名
     * @return String
     */
    String getConfig(String name);
    /**
     * 设置配置
     * @param name 配置项名
     * @param value 值
     */
    void updataConfig(String name,String value);
}
