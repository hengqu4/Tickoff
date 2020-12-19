package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.util.common.MissionConstants;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MissionDao {
    @Select("select * from " + MissionConstants.MissionTable + " where missionId = #{missionId}")
    Mission getMissionById(String missionId);

    @Delete("delete from "+ MissionConstants.MissionTable+" where missionId = #{missionId}")
    Boolean deleteMissionById(String missionId);

    @Insert({"insert into mission" +
            "(missionId, setId, name, description, createDate, " +
            "startDate, endDate, routine, delay, workLoad, " +
            "requireCheck, needNotice, noticeTime, done, delayDate) " +
            "values(#{missionId}, #{setId}, #{name}, #{description}, #{createDate}, " +
            "#{startDate}, #{endDate}, #{routine}, #{delay}, #{workLoad}, " +
            "#{requireCheck}, #{needNotice}, #{noticeTime}, #{done}, #{delayDate})" })
    Boolean saveMission(Mission mission);

    @Update("INSERT INTO mission" +
            "(missionId, setId, name, description, createDate, " +
            "startDate, endDate, routine, delay, workLoad, " +
            "requireCheck, needNotice, noticeTime, done, delayDate) " +
            "VALUE(#{missionId},#{setId},#{name}, #{description}, #{createDate}, " +
            "#{startDate},#{endDate},#{routine}, #{delay}, #{workLoad}, " +
            "#{requireCheck},#{needNotice},#{noticeTime}, #{done}, #{delayDate}) " +
            "ON DUPLICATE KEY UPDATE " +
            "setId=#{setId},name=#{name},description=#{description},createDate=#{createDate}," +
            "startDate=#{startDate},endDate=#{endDate},routine=#{routine},delay=#{delay},workLoad=#{workLoad}," +
            "requireCheck=#{requireCheck},needNotice=#{needNotice},noticeTime=#{noticeTime},done=#{done},delayDate=#{delayDate}" )
    Boolean modifyMission(Mission mission);



}
