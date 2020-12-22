package com.tickoff.dao;

import com.tickoff.domain.User_mset;
import com.tickoff.util.common.User_msetConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface User_msetDao {
    @Select("select * from "+ User_msetConstants.User_msetTable+" where openid= #{openid}")
    List<User_mset> getUserAllMset(String openid);

    @Select("select * from "+ User_msetConstants.User_msetTable+" where mset_id= #{mset_id}")
    List<User_mset> getMsetAllUser(String mset_id);

    @Select("select * from "+User_msetConstants.User_msetTable+" where openid=#{openid} and mset_id=#{mset_id}")
    User_mset getUser_msetById(String openid,String mset_id);

    @Delete("delete from "+User_msetConstants.User_msetTable+" where openid=#{openid} and mset_id=#{mset_id}")
    Boolean deleteUser_mset(String openid,String mset_id);

    @Insert({"insert into "+User_msetConstants.User_msetTable+
    "(openid, mset_id ) values(#{openid}, #{mset_id})"})
    Boolean saveUser_mset(User_mset user_mset);


}
