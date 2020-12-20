package com.tickoff.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.tickoff.constant.ReturnCodeConstant;
import com.tickoff.domain.Mission;
import com.tickoff.service.MissionService;
import com.tickoff.util.RetrunJson;
import com.tickoff.util.UniqueId;
import com.tickoff.util.common.JSONtoObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/tickoff")
public class MissionController {

    @Autowired
    private MissionService missionService;

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
        Mission mission = jsontoObject.JSONtoMission(json);
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




}
