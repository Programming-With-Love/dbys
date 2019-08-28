package com.danbai.ys.service;

import com.danbai.ys.entity.User;

public interface UserService {
    User getUser(User user);
    boolean addUser(User user);
    User getUserByEmail(String email);
}
