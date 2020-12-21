package com.tickoff.service;

import com.tickoff.domain.User_mset;

import java.util.List;

public interface User_msetService {

    public List<User_mset> getAllUser_mset(String openid);

    public List<User_mset> getAllMsetAllUser(String mset_id);

    public User_mset getUser_mset(String openid,String mset_id);

    public boolean addUser_mset(User_mset user_mset);

    public boolean deleteUser_mset(String openid,String mset_id);
}
