package com.danbai.ys.mapper;

import com.danbai.ys.entity.User;
import com.danbai.ys.utils.MyMapper;
import com.danbai.ys.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

@Repository

public interface UserMapper extends MyMapper<User> {
}