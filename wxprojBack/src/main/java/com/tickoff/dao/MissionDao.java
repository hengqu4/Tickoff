package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.util.constant.MissionConstants;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MissionDao {
    @Select("select * from " + MissionConstants.MissionTable + " where m_id = #{m_id}")
    Mission getMissionById(String m_id);

    @Delete("delete from "+ MissionConstants.MissionTable+" where m_id = #{m_id}")
    Boolean deleteMissionById(String m_id);

    @Insert({"insert into mission" +
            "(m_id, mset_id, name, description, createDate, " +
            "startDate, endDate, routine, delay, workload, " +
            "requireCheck, needNotice, noticeTime, done, delayDate) " +
            "values(#{m_id}, #{mset_id}, #{name}, #{description}, #{createDate}, " +
            "#{startDate}, #{endDate}, #{routine}, #{delay}, #{workLoad}, " +
            "#{requireCheck}, #{needNotice}, #{noticeTime}, #{done}, #{delayDate})" })
    Boolean saveMission(Mission mission);

    @Update("INSERT INTO mission" +
            "(m_id, mset_id, name, description, createDate, " +
            "startDate, endDate, routine, delay, workload, " +
            "requireCheck, needNotice, noticeTime, done, delayDate) " +
            "VALUE(#{m_id},#{mset_id},#{name}, #{description}, #{createDate}, " +
            "#{startDate},#{endDate},#{routine}, #{delay}, #{workLoad}, " +
            "#{requireCheck},#{needNotice},#{noticeTime}, #{done}, #{delayDate}) " +
            "ON DUPLICATE KEY UPDATE " +
            "mset_id=#{mset_id},name=#{name},description=#{description},createDate=#{createDate}," +
            "startDate=#{startDate},endDate=#{endDate},routine=#{routine},delay=#{delay},workload=#{workLoad}," +
            "requireCheck=#{requireCheck},needNotice=#{needNotice},noticeTime=#{noticeTime},done=#{done},delayDate=#{delayDate}" )
    Boolean modifyMission(Mission mission);



}
