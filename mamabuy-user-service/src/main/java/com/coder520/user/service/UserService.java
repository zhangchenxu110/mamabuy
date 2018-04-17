package com.coder520.user.service;

import com.coder520.user.entity.User;
import com.coder520.user.entity.UserElement;

/**
 * Created by JackWangon[www.coder520.com] 2018/4/4.
 */
public interface UserService {
    UserElement login(User user);

    void registerUser(User user) throws Exception;
}
