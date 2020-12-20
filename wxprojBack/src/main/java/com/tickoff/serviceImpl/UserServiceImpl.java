package com.tickoff.serviceImpl;

import com.tickoff.dao.UserDao;
import com.tickoff.domain.User;
import com.tickoff.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }


}
