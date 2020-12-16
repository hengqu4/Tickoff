package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.util.common.TableConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MissionDao {
    @Select("select * from " + TableConstants.MissionTable + " where missionID = #{missionId}")
    Mission getMissionById(String missionId);


}
