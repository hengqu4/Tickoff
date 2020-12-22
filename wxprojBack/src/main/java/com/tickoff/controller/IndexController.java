package com.tickoff.controller;

import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.IndexMset;
import com.tickoff.domain.Mission;
import com.tickoff.service.IndexService;
import com.tickoff.util.common.JSONtoObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tickoff")
public class IndexController {
    @Autowired
    IndexService indexService;

    @RequestMapping(value="/api/indexMissions/{open_id}/date/{ddl}",method = RequestMethod.GET)
    public List<IndexMset> getIndexMsets(@PathVariable String open_id,@PathVariable String ddl){
        return indexService.turnMsetToIndexMset(open_id,ddl);
    }

    @RequestMapping(value = "/api/tickoffMission", method = RequestMethod.PUT)
    public String modifyMission(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        String missionId = jsontoObject.JSONtoMissionId(json);
        if (indexService.tickOffMission(missionId)) {
            return "任务打卡成功";
        } else {
            return "打卡失败";
        }
    }


    @RequestMapping(value = "/api/addRecord", method = RequestMethod.PUT)
    public String addRecord(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        String open_id = jsontoObject.JSONtoUser(json);
        String date=jsontoObject.JSONtoDate(json);
        if (indexService.addRecord(open_id,date)) {
            return "任务登记成功";
        } else {
            return "任务登记失败";
        }
    }

    @RequestMapping(value="/api/doneMissions/{open_id}/date/{date}",method = RequestMethod.GET)
    public int getDoneMissionToday(@PathVariable String open_id,@PathVariable String date){
     return indexService.getDoneMissionToday(open_id,date);
    }
}
