package com.tickoff.controller;

import com.tickoff.domain.MissionSet;
import com.tickoff.service.MissionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/tickoff")
public class MissionSetController {
    @Autowired
    private MissionSetService missionSetService;
    @RequestMapping(value="/api/missionsets",method= RequestMethod.GET)
    public MissionSet getAllMissionSets(@RequestParam(value="setId")String id) throws ParseException {
        return missionSetService.getMissionSetById(id);
    }
}
