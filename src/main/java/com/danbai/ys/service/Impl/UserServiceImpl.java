package com.danbai.ys.service.Impl;

import com.danbai.ys.entity.User;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.mapper.UserMapper;
import com.danbai.ys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public boolean addUser(User user) {
        if(userMapper.insert(user)>0)
            return true;
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        Example example=new Example(User.class);
        example.createCriteria().andEqualTo("email",email);
        return userMapper.selectOneByExample(example);
    }
}
