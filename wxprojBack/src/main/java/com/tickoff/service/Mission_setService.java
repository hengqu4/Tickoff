package com.tickoff.service;

import com.tickoff.domain.Mission_set;

import java.util.List;

public interface Mission_setService {
    public List<Mission_set> getMissionSetList(String open_id);

    public Mission_set getMissionSetById(String mset_id);

    public boolean addMissionSet(Mission_set mission_set);

    public boolean modifyMissionSet(Mission_set mission_set);

    public boolean deleteMissionSet(String mset_id);
}
