package com.tickoff.dao;

import com.tickoff.domain.Mission;
import com.tickoff.domain.MissionSet;
import com.tickoff.util.common.MissionConstants;
import com.tickoff.util.common.TableConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MissionSetDao {
    @Select("select * from " + TableConstants.MissionSetTable + " where setId = #{setId}")
    MissionSet getMissionSetById(String setId);
}
