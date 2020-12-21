package com.tickoff.service;

import com.tickoff.domain.User;

public interface UserService {

    public User getUserById(String userId);

    boolean queryOpenIdIsExist(String openid);

    User queryUserByOpenID(String openid);

    Boolean saveUser(User users);
}
