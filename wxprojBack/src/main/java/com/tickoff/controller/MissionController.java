package com.tickoff.controller;


import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.Mission;
import com.tickoff.service.MissionService;
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
    public Mission getMission(@PathVariable String id) throws ParseException {
        return missionService.getMission(id);
    }

    @ResponseBody
    @RequestMapping(value = "/api/missions", method = RequestMethod.POST)
    public String addMission(@RequestBody String requestJson) throws JSONException, ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission mission = jsontoObject.JSONtoMission(json);
        mission.setM_id(UniqueId.getUUID());
        missionService.addMission(mission);
        return mission.toString();
    }

    @RequestMapping(value = "/api/missions/{id}", method = RequestMethod.DELETE)
    public String deleteMission(@PathVariable String id) {
        if (missionService.deleteMission(id)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "/api/missions", method = RequestMethod.PUT)
    public String modifyMission(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        Mission mission = jsontoObject.JSONtoMission(json);
        if (missionService.modifyMission(mission)) {
            return "success";
        } else {
            return "fail";
        }
    }


}
