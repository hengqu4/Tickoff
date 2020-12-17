package com.tickoff.controller;

import com.tickoff.domain.Mission;
import com.tickoff.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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
}
