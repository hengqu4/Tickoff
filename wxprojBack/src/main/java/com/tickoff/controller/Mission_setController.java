package com.tickoff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.Mission_set;
import com.tickoff.domain.User_mset;
import com.tickoff.service.Mission_setService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickoff")
public class Mission_setController {

    @Autowired
    private Mission_setService mission_setService;

    @RequestMapping(value = "/api/mission_set/openid/{openid}",method = RequestMethod.GET)
    public List<Mission_set> getMissionSetList(@PathVariable String openid){
        return mission_setService.getMissionSetList(openid);
    }

    @RequestMapping(value = "/api/mission_set/mset_id/{mset_id}",method = RequestMethod.GET)
    public Mission_set getMissionSetById(@PathVariable String mset_id){
        return mission_setService.getMissionSetById(mset_id);
    }

    @RequestMapping(value = "/api/mission_set",method = RequestMethod.POST)
    public Boolean addMission_set(@RequestBody String requestJson){
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission_set mission_set=JSON.toJavaObject(json,Mission_set.class);
        return mission_setService.addMissionSet(mission_set);
    }

    @RequestMapping(value = "/api/mission_set",method = RequestMethod.PUT)
    public Boolean modifyMission_set(@RequestBody String requestJson){
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission_set mission_set=JSON.toJavaObject(json,Mission_set.class);
        return mission_setService.modifyMissionSet(mission_set);
    }

    @RequestMapping(value = "/api/mission_set/mset_id/{mset_id}",method = RequestMethod.DELETE)
    public Boolean deleteMission_set(@PathVariable String mset_id){
        return mission_setService.deleteMissionSet(mset_id);
    }
}
