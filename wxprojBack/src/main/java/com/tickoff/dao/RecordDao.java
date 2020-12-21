package com.tickoff.dao;

import com.tickoff.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordDao {
    @Select("select count(*) from record where user_id=#{userid}")
    public int getActiveCount(String userid);

    @Select("select * from record where user_id=#{openid}")
    public List<Record> getRecordList(String openid);
}
