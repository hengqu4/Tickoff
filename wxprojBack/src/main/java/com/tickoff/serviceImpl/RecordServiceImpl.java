package com.tickoff.serviceImpl;

import com.tickoff.dao.RecordDao;
import com.tickoff.domain.Record;
import com.tickoff.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RecordService")
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;

    @Override
    public List<Record> getRecordList(String openid) {
        return recordDao.getRecordList(openid);
    }

    @Override
    public int getActiveCount(String openid) {
        return recordDao.getActiveCount(openid);
    }
}
