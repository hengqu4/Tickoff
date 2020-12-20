package com.tickoff.dao;


import com.tickoff.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from user where user_id=#{userId}")
    User getUserById(String userId);


}
