package com.tickoff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.tickoff.domain.Mission_set;
import com.tickoff.domain.User_mset;
import com.tickoff.service.Mission_setService;
import com.tickoff.service.User_msetService;
import com.tickoff.util.RetrunJson;
import com.tickoff.util.UniqueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickoff")
public class Mission_setController {

    @Autowired
    private Mission_setService mission_setService;

    @Autowired
    private User_msetService user_msetService;

    @RequestMapping(value = "/api/mission_set/openid/{openid}",method = RequestMethod.GET)
    public JSONObject getMissionSetList(@PathVariable String openid){
        List<Mission_set> list= mission_setService.getMissionSetList(openid);
        String str=JSON.toJSONString(list);
        return RetrunJson.returnJsonArraySuccess(str);
    }

    @RequestMapping(value = "/api/mission_set/mset_id/{mset_id}",method = RequestMethod.GET)
    public JSONObject getMissionSetById(@PathVariable String mset_id){
        Mission_set mission_set= mission_setService.getMissionSetById(mset_id);
        String str=JSON.toJSONString(mission_set);
        return RetrunJson.returnJsonSuccess(str);
    }

    @RequestMapping(value = "/api/mission_set",method = RequestMethod.POST)
    public JSONObject addMission_set(@RequestBody String requestJson){
        JSONObject json = JSONObject.parseObject(requestJson);
        json.put("mset_id", UniqueId.getUUID());
        Mission_set mission_set=JSON.toJavaObject(json,Mission_set.class);
        if( mission_setService.addMissionSet(mission_set)){
            return RetrunJson.returnJsonSuccess(JSON.toJSONString(mission_set));
        }
        else{
            return RetrunJson.returnJsonFailure(JSON.toJSONString(mission_set));
        }
    }

    @RequestMapping(value = "/api/mission_set/openid/{openid}",method = RequestMethod.POST)
    public JSONObject addMission_setAndUser_mset(@RequestBody String requestJson,@PathVariable String openid){
        JSONObject json = JSONObject.parseObject(requestJson);
        String mset_id=UniqueId.getUUID();
        json.put("mset_id", mset_id);

        User_mset user_mset =User_mset.builder().mset_id(mset_id).openid(openid).build();

        Mission_set mission_set=JSON.toJavaObject(json,Mission_set.class);
        if( mission_setService.addMissionSet(mission_set) && user_msetService.addUser_mset(user_mset)){
            return RetrunJson.returnJsonSuccess(JSON.toJSONString(mission_set));
        }
        else{
            return RetrunJson.returnJsonFailure(JSON.toJSONString(mission_set));
        }
    }

    @RequestMapping(value = "/api/mission_set",method = RequestMethod.PUT)
    public JSONObject modifyMission_set(@RequestBody String requestJson){
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission_set mission_set=JSON.toJavaObject(json,Mission_set.class);
        if( mission_setService.modifyMissionSet(mission_set)){
            return RetrunJson.returnJsonSuccess(JSON.toJSONString(mission_set));
        }
        else {
            return RetrunJson.returnJsonFailure(JSON.toJSONString(mission_set));
        }
    }

    @RequestMapping(value = "/api/mission_set/mset_id/{mset_id}",method = RequestMethod.DELETE)
    public JSONObject deleteMission_set(@PathVariable String mset_id){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("deleteId",mset_id);
        if( mission_setService.deleteMissionSet(mset_id)){
            return RetrunJson.returnJsonSuccess(jsonObject.toJSONString());
        }
        else{
            return RetrunJson.returnJsonFailure(jsonObject.toJSONString());
        }
    }
}