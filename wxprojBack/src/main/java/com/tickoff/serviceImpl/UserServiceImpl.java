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

    @Override
    public boolean queryOpenIdIsExist(String openid) {
        User user =  userDao.getUserById(openid);
        if(user == null||openid == null){
            //不存在
            return false;
        }
        return true;
    }

    @Override
    public User queryUserByOpenID(String openid) {
        User user =  userDao.getUserById(openid);
        if(user == null||openid == null){
            //不存在
            return null;
        }
        return user;
    }

    @Override
    public Boolean saveUser(User users) {
        return userDao.saveUser(users);
    }

    @Override
    public Boolean addLike(String openid) {
        User user=userDao.getUserById(openid);
        user.setOtherlike(user.getOtherlike()+1);
        return userDao.updateUser(user);
    }


}
