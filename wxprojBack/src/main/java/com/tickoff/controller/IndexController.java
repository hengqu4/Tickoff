package com.tickoff.controller;

import com.tickoff.domain.IndexMset;
import com.tickoff.domain.Mission;
import com.tickoff.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/tickoff")
public class IndexController {
    @Autowired
    IndexService indexService;

    @RequestMapping(value="/api/indexMsetTest/{open_id}/date/{ddl}",method = RequestMethod.GET)
    public List<IndexMset> getIndexMsets(@PathVariable String open_id,@PathVariable String ddl){
        return indexService.turnMsetToIndexMset(open_id,ddl);
    }

    @RequestMapping(value="/api/test/{setId}/{ddl}",method=RequestMethod.GET)
    public List<Mission> getMissions(@PathVariable String setId,@PathVariable String ddl){
        return indexService.getMissionsByMsetId(setId,ddl);
    }

}
