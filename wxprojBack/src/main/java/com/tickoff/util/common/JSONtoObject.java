package com.tickoff.util.common;

import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.Mission;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class JSONtoObject {
    public Mission JSONtoMission(JSONObject json) throws ParseException {
        String a = json.getString("missionId");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        String createDate = json.getString("createDate");
        String startDate = json.getString("startDate");
        String endDate = json.getString("endDate");
        String delayDate = json.getString("delayDate");
        String noticeTime = json.getString("noticeTime");

        return Mission.builder()
                .m_id(json.getString("missionId"))
                .mset_id(json.getString("mset_id"))
                .name(json.getString("name"))
                .description(json.getString("description"))
                .createDate(createDate)
                .startDate(startDate)
                .endDate(endDate)
                .routine(Integer.parseInt(json.getString("routine")))
                .delay(Boolean.getBoolean(json.getString("delay")))
                .workLoad(Integer.parseInt(json.getString("workLoad")))
                .requireCheck(Boolean.getBoolean(json.getString("requireCheck")))
                .needNotice(Boolean.getBoolean(json.getString("needNotice")))
                .noticeTime(noticeTime)
                .done(Boolean.getBoolean(json.getString("done")))
                .delayDate(delayDate)
                .build();

    }

    public String JSONtoMissionId(JSONObject json) throws ParseException{
        return json.getString("missionId");
    }
}
