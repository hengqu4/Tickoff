package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.util.common.TableConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MissionSetDao {
    @Select("select * from "+ TableConstants.UserMSetTable)
    Mission getMissionById(String missionId);
}
