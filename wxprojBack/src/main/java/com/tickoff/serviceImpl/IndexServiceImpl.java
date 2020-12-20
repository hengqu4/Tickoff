package com.tickoff.serviceImpl;

import com.tickoff.dao.IndexDao;
import com.tickoff.domain.IndexMission;
import com.tickoff.domain.IndexMset;
import com.tickoff.domain.Mission;
import com.tickoff.domain.Mission_set;
import com.tickoff.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Service("IndexService")
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexDao indexDao;

    @Override
    public List<Mission> getMissionsByMsetId(String setId, String ddl){
        return indexDao.getMissionsByMsetId(setId,ddl);
    }

    @Override
    public List<IndexMission> turnMissionToIndexMission(String setId,String ddl){
        List<Mission> missionList=getMissionsByMsetId(setId,ddl);
        List<IndexMission> indexMissionList=new LinkedList<>();
        for(Mission mission:missionList){
            IndexMission indexMission= IndexMission.builder()
                    .missionId(mission.getM_id())
                    .name(mission.getName())
                    .description(mission.getDescription())
                    .done(mission.isDone()?1:0)
                    .build();
            indexMissionList.add(indexMission);
        }
        return indexMissionList;
    }

    @Override
    public List<Mission_set> getMsetsByUserId(String open_id){
        return indexDao.getMsetsByUserId(open_id);
    }

    @Override
    public List<IndexMset> turnMsetToIndexMset(String open_id,String ddl){
        List<Mission_set> mission_setList=getMsetsByUserId(open_id);
        List<IndexMset> indexMsetList=new LinkedList<>();
        for(Mission_set mission_set:mission_setList){
            List<IndexMission> indexMissionList=turnMissionToIndexMission(mission_set.getMset_id(),ddl);
            if(indexMissionList.size()==0)continue;
            IndexMset indexMset=IndexMset.builder()
                    .setId(mission_set.getMset_id())
                    .setName(mission_set.getName())
                    .setDescription(mission_set.getDescription())
                    .missions(indexMissionList)
                    .build();
            indexMsetList.add(indexMset);
        }
        return indexMsetList;
    }

    @Override
    public Boolean tickOffMission(String missionId){return indexDao.tickOffMission(missionId);}
}
