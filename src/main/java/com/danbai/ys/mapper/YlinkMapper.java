package com.danbai.ys.mapper;

import com.danbai.ys.entity.Ylink;
import com.danbai.ys.utils.MyMapper;

/**
 * @author danbai
 */
public interface YlinkMapper extends MyMapper<Ylink> {
    /**
     * 清空数据
     */
    void deleteAll();
}
