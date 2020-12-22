package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.domain.Mission_set;
import com.tickoff.domain.Record;
import com.tickoff.util.common.MissionConstants;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IndexDao {
    @Select("select * from " + MissionConstants.MissionTable + " where mset_id = #{setId} and Date(endDate) >= #{ddl}")
    List<Mission> getMissionsByMsetId(String setId, String ddl);

    @Select("select a.* from mission_mset a " +
            "inner join user_mset b " +
            "on a.mset_id=b.mset_id " +
            "where b.openid=#{openid}" )
    List<Mission_set> getMsetsByUserId(String open_id);

    @Update("update " + MissionConstants.MissionTable + " set done = '1' where m_id=#{missionId}")
    Boolean tickOffMission(String missionId);

    @Select("select * from record where user_id=#{openid} and date=#{date}")
    List<Record> getRecordList(String openid,String date);

    @Insert("insert into record(user_id,date,done_m) value(#{open_id},#{date},1)")
    Boolean addRecord(String open_id,String date);

    @Update("update record set done_m=done_m+1 where user_id=#{open_id} and date=#{date}")
    Boolean changeRecord(String open_id,String date);

    @Select("select done_m from record where user_id=#{open_id} and date=#{date}")
    Integer getDoneMissionToday(String open_id,String date);
}
