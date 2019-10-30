package com.danbai.ys.service;

import java.util.List;

/**
 * @author danbai
 * @date 2019/10/14
 */
public interface Statistical {
    /**
     * 增加访问统计
     */
    void addAccess();

    /**
     * 查询今日ip是否访问过
     *
     * @param ip
     * @return
     */
    boolean isIpInTheDatabase(String ip);

    /**
     * 增加今日访问ip
     *
     * @param ip
     */
    void addIp(String ip);

    /**
     * 获取总访问量
     *
     * @return
     */
    int getAccess();

    /**
     * 获取今日访问量
     *
     * @return
     */
    int getDayAccess();

    /**
     * 获取近30天访问
     *
     * @return
     */
    List get30DayAccess();
}
