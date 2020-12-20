package com.tickoff.serviceImpl;

import com.tickoff.dao.MissionDao;
import com.tickoff.dao.Mission_setDao;
import com.tickoff.dao.User_msetDao;
import com.tickoff.domain.Mission;
import com.tickoff.domain.User_mset;
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
        return missionDao.saveMission(mission);
    }

    @Override
    public boolean modifyMission(Mission mission) {
        return missionDao.modifyMission(mission);
    }

    @Override
    public boolean deleteMission(String missionId) {
        return missionDao.deleteMissionById(missionId);
    }
}
