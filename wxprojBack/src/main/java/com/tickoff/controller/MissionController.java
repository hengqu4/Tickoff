package com.tickoff.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.tickoff.constant.ReturnCodeConstant;
import com.tickoff.domain.IndexMset;
import com.tickoff.domain.Mission;
import com.tickoff.service.IndexService;
import com.tickoff.service.MissionService;
import com.tickoff.util.RetrunJson;
import com.tickoff.util.UniqueId;
import com.tickoff.util.common.JSONtoObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/tickoff")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/api/missions", method = RequestMethod.GET)
    public String getAllMission() {
        return "mission" +
                "mission" +
                "mission";
    }

    @RequestMapping(value = "/api/missions/{id}", method = RequestMethod.GET)
    public JSONObject getMission(@PathVariable String id) throws ParseException {
        Mission mission= missionService.getMission(id);
        String str= JSON.toJSONString(mission);
        return RetrunJson.returnJsonSuccess(str);
    }

    @ResponseBody
    @RequestMapping(value = "/api/missions", method = RequestMethod.POST)
    public JSONObject addMission(@RequestBody String requestJson) throws JSONException, ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        json.put("missionId",UniqueId.getUUID());
        Mission mission = jsontoObject.JSONtoMission(json);
        if(missionService.addMission(mission)){
            return RetrunJson.returnJsonSuccess(JSON.toJSONString(mission));
        }
        else{
            return RetrunJson.returnJsonFailure(JSON.toJSONString(mission));
        }
    }

    @RequestMapping(value = "/api/missions", method = RequestMethod.PUT)
    public JSONObject modifyMission(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission mission = JSON.toJavaObject(json,Mission.class);
        if (missionService.modifyMission(mission)) {
            return RetrunJson.returnJsonSuccess(JSON.toJSONString(mission));
        } else {
            return RetrunJson.returnJsonFailure(JSON.toJSONString(mission));
        }
    }

    @RequestMapping(value = "/api/missions/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteMission(@PathVariable String id) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("deletedId",id);
        if (missionService.deleteMission(id)) {
            return RetrunJson.returnJsonSuccess(jsonObject.toJSONString());
        } else {
            return RetrunJson.returnJsonFailure(jsonObject.toJSONString());
        }
    }

    @RequestMapping(value="/api/indexMissions/{open_id}/date/{ddl}",method = RequestMethod.GET)
    public List<IndexMset> getIndexMsets(@PathVariable String open_id, @PathVariable String ddl){
        return indexService.turnMsetToIndexMset(open_id,ddl);
    }

    @RequestMapping(value = "/api/tickoffMission", method = RequestMethod.PUT)
    public String modifyMissionDone(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        String missionId = jsontoObject.JSONtoMissionId(json);
        if (indexService.tickOffMission(missionId)) {
            return "任务打卡成功";
        } else {
            return "打卡失败";
        }
    }




    @RequestMapping(value="/api/doneMissions/{open_id}/date/{date}",method = RequestMethod.GET)
    public int getDoneMissionToday(@PathVariable String open_id,@PathVariable String date){
        return indexService.getDoneMissionToday(open_id,date);
    }




}
