package com.tickoff.service;

import com.tickoff.domain.Mission;

import java.util.List;

public interface MissionService {

    public List<Mission> getMissionList(String setId);

    public Mission getMission(String missionId);

    public boolean addMission(Mission mission);

    public boolean modifyMission(Mission mission);

    public boolean deleteMission(String missionId);
}
