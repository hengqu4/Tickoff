package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.util.common.TableConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MissionDao {
    @Select("select * from " + TableConstants.MissionTable + " where missionID = #{missionId}")
    Mission getMissionById(String missionId);
}
