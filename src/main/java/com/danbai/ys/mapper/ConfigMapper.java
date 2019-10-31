package com.danbai.ys.mapper;

import com.danbai.ys.entity.Config;
import com.danbai.ys.utils.MyMapper;

public interface ConfigMapper extends MyMapper<Config> {
    /**
     * 清空数据
     */
    void deleteAll();
}
