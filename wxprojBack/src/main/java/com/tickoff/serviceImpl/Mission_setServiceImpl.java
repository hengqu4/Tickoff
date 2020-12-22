package com.tickoff.serviceImpl;

import com.tickoff.dao.Mission_setDao;
import com.tickoff.domain.Mission_set;
import com.tickoff.service.Mission_setService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Mission_setService")
public class Mission_setServiceImpl implements Mission_setService {

    @Autowired
    private Mission_setDao missionSetDao;

    @Override
    public List<Mission_set> getMissionSetList(String open_id) {
        return missionSetDao.getMissionSetList(open_id);
    }

    @Override
    public Mission_set getMissionSetById(String mset_id) {
        return missionSetDao.getMissionSetById(mset_id);
    }

    @Override
    public boolean addMissionSet(Mission_set mission_set) {
        return missionSetDao.saveMissionSet(mission_set);
    }

    @Override
    public boolean modifyMissionSet(Mission_set mission_set) {
        return missionSetDao.modifyMissionSet(mission_set);
    }

    @Override
    public boolean deleteMissionSet(String mset_id) {
        return missionSetDao.deleteMissionSet(mset_id);
    }



}
