package com.tickoff.service;

import com.tickoff.domain.Record;

import java.util.List;

public interface RecordService {
    public List<Record> getRecordList(String openid);

    public int getActiveCount(String openid);

}
