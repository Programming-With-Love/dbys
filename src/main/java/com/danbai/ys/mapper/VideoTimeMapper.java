package com.danbai.ys.mapper;

import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.utils.MyMapper;
import com.danbai.ys.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoTimeMapper extends MyMapper<VideoTime> {
}