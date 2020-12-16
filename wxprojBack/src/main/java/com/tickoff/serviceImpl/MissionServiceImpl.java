package com.tickoff.serviceImpl;

import com.tickoff.dao.MissionDao;
import com.tickoff.domain.Mission;
import com.tickoff.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MissionService")
public class MissionServiceImpl implements MissionService {
    @Autowired
    private MissionDao missionDao;

    @Override
    public List<Mission> getMissionList(String setId) {
        return null;
    }

    @Override
    public Mission getMission(String missionId) {
        return missionDao.getMissionById(missionId);
    }

    @Override
    public boolean addMission(Mission mission) {
        return false;
    }

    @Override
    public boolean modifyMission(Mission mission) {
        return false;
    }

    @Override
    public boolean deleteMission(String missionId) {
        return false;
    }
}
