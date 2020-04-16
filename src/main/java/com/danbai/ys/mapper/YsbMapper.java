package com.danbai.ys.mapper;

import com.danbai.ys.entity.Ysb;
import com.danbai.ys.utils.MyMapper;
import com.danbai.ys.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author danbai
 */

@Repository
@CacheNamespace(implementation = RedisCache.class)
public interface YsbMapper extends MyMapper<Ysb> {
    List<Ysb> getByType(String type1, String type2, String region, String year, String sort);
}