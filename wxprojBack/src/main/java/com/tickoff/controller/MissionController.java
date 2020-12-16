package com.tickoff.controller;


import com.tickoff.domain.Mission;
import com.tickoff.domain.User;
import com.tickoff.service.MissionService;
import com.tickoff.util.UniqueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat format2 = new SimpleDateFormat("yy-MM-dd");
//        SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");
//        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
//        format2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
//        format3.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
//
//        String s="2020-1-1 10:10:00";
//        String e="2020-1-1 11:10:00";
//
//
//
//        java.util.Date start=format.parse(s);
//        java.util.Date end=format.parse(e);
//        java.util.Date routine=format2.parse("2001-01-01");
//        java.util.Date notice=format3.parse("10:00:00");
//
//        return Mission.builder()
//                .missionId(UniqueId.getUUID())
//                .setId(UniqueId.getUUID())
//                .name("开始java")
//                .description("先学spring 在学谁家")
//                .done(true)
//                .isDelay(false)
//                .isNeedNotice(true)
//                .isRequire(false)
//                .workLoad(1)
//                .start(start)
//                .createDate(start)
//                .end(end)
//                .delay(end)
//                .notice(notice)
//                .routine(format2.format(routine))
//                .build();

    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public User getUser(){
        return new User((long) 1, "倪升武", "123456");
    }



}
