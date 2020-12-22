package com.tickoff.serviceImpl;

import com.tickoff.dao.User_msetDao;
import com.tickoff.domain.User_mset;
import com.tickoff.service.User_msetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("User_msetService")
public class User_msetServiceImpl implements User_msetService {
    @Autowired
    private User_msetDao user_msetDao;

    @Override
    public List<User_mset> getAllUser_mset(String openid) {
        return user_msetDao.getUserAllMset(openid);
    }

    @Override
    public List<User_mset> getMsetAllUser(String mset_id) {
        return user_msetDao.getMsetAllUser(mset_id);
    }

    @Override
    public User_mset getUser_mset(String openid, String mset_id) {
        return user_msetDao.getUser_msetById(openid, mset_id);
    }

    @Override
    public boolean addUser_mset(User_mset user_mset) {
        return user_msetDao.saveUser_mset(user_mset);
    }


    @Override
    public boolean deleteUser_mset(String openid, String mset_id) {
        return user_msetDao.deleteUser_mset(openid, mset_id);
    }


}
