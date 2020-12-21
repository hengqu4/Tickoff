package com.tickoff.dao;

import com.tickoff.domain.Mission_set;
import com.tickoff.util.common.Mission_setConstants;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface Mission_setDao {

    @Select("select a.* from mission_mset a " +
            "inner join user_mset b " +
            "on a.mset_id=b.mset_id " +
            "where b.openid=#{openid}" )
    List<Mission_set> getMissionSetList(String openid);

    @Select("select * from "+ Mission_setConstants.Mission_setTable+" where mset_id=#{mset_id}")
    Mission_set getMissionSetById(String mset_id);

    @Insert("insert into "+ Mission_setConstants.Mission_setTable+
    " (mset_id, name, description, createDate) values(#{mset_id}, #{name}, #{dscb}, #{create_date})")
    Boolean saveMissionSet(Mission_set mission_set);

    @Delete("delete from "+Mission_setConstants.Mission_setTable+
    " where mset_id=#{mset_id}")
    Boolean deleteMissionSet(String mset_id);

    @Update("insert into "+Mission_setConstants.Mission_setTable+
            " (mset_id, name, description, createDate) values(#{mset_id}, #{name}, #{dscb}, #{create_date}) " +
            "ON DUPLICATE KEY UPDATE " +
            " mset_id=#{mset_id}, name=#{name}, description=#{dscb}, createDate=#{create_date}")
    Boolean modifyMissionSet(Mission_set mission_set);

}
