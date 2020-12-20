package com.tickoff.service;

import com.tickoff.domain.IndexMission;
import com.tickoff.domain.IndexMset;
import com.tickoff.domain.Mission;
import com.tickoff.domain.Mission_set;

import java.sql.Date;
import java.util.List;

public interface IndexService {
    public List<Mission> getMissionsByMsetId(String setId, String ddl);

    public List<IndexMission> turnMissionToIndexMission(String setId,String ddl);

    public List<Mission_set> getMsetsByUserId(String open_id);

    public List<IndexMset> turnMsetToIndexMset(String open_id,String ddl);

    public Boolean tickOffMission(String missionId);
}
