package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.domain.Mission_set;
import com.tickoff.util.common.MissionConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IndexDao {
    @Select("select * from " + MissionConstants.MissionTable + " where mset_id = #{setId} and Date(endDate) = #{ddl}")
    List<Mission> getMissionsByMsetId(String setId, String ddl);

    @Select("select a.* from mission_mset a " +
            "inner join user_mset b " +
            "on a.mset_id=b.mset_id " +
            "where b.openid=#{openid}" )
    List<Mission_set> getMsetsByUserId(String open_id);

    @Update("update " + MissionConstants.MissionTable + " set done = '1' where m_Id=#{missionId}")
    Boolean tickOffMission(String missionId);
}
