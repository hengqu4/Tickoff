package com.tickoff.dao;


import com.tickoff.domain.Mission;
import com.tickoff.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from user where user_id=#{userId}")
    User getUserById(String userId);

    @Insert({"insert into user" +
            "(user_id, nickname, avatar_url, like) " +
            "values(#{user_id}, #{nickname}, #{avatar_url}, #{like}" })
    Boolean saveUser(User user);
}
