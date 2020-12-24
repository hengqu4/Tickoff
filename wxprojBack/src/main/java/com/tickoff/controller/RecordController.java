package com.tickoff.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.Record;
import com.tickoff.domain.ReturnRecord;
import com.tickoff.service.IndexService;
import com.tickoff.service.RecordService;
import com.tickoff.util.common.JSONtoObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/tickoff")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/api/record/activeDays/UserID/{userid}",method = RequestMethod.GET)
    public JSONObject getActiveCount(@PathVariable String userid){
        int count=recordService.getActiveCount(userid);
        System.out.println("数量是："+count);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("days",count);
        JSONObject returnjson=new JSONObject();
        returnjson.put("error_code",200);
        returnjson.put("data",jsonObject);
        return returnjson;
    }

    @RequestMapping(value = "/api/record/HistoryRecord/UserID/{userid}",method = RequestMethod.GET)
    public JSONObject getHistoryRecord(@PathVariable String userid){
        int weekdays[]=new int[]{7,1,2,3,4,5,6};
        List<Record> list=recordService.getRecordList(userid);
        Calendar now=Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        int year= now.get(Calendar.YEAR);
        int month=now.get(Calendar.MONTH)+1;
        int day=now.get(Calendar.DAY_OF_MONTH);
        int week=now.get(Calendar.DAY_OF_WEEK);
        int total=133+weekdays[week-1];
        List<ReturnRecord> result=new ArrayList<>();
        for(int i=total-1;i>=0;i--){
            boolean flag=false;
            Calendar temp=Calendar.getInstance();
            temp.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            temp.add(Calendar.DATE,-i);
            java.util.Date d=(java.util.Date)temp.getTime();
            java.sql.Date sd=new java.sql.Date(d.getTime());
            for(Record r:list){
                if(r.getDate().toString().equals(sd.toString())){
                    ReturnRecord rr=ReturnRecord.builder()
                            .date(r.getDate().toString())
                            .openid(r.getUser_id())
                            .done(r.getDone_m())
                            .mission(r.getTotal_m())
                            .build();
                    result.add(rr);
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                ReturnRecord t = ReturnRecord.builder()
                        .date(sd.toString())
                        .openid(userid)
                        .done(0)
                        .mission(0)
                        .build();
                result.add(t);
            }
        }
        String str=JSON.toJSONString(result);
        JSONArray json=JSON.parseArray(str);

        JSONObject returnJson=new JSONObject();
        returnJson.put("error_code",200);
        returnJson.put("data",json);
        return returnJson;
    }

    @RequestMapping(value = "/api/record", method = RequestMethod.PUT)
    public JSONObject addRecord(@RequestBody String requestJson) throws ParseException {
        JSONtoObject jsontoObject = new JSONtoObject();
        JSONObject json = JSONObject.parseObject(requestJson);
        String open_id = jsontoObject.JSONtoUser(json);
        String date=jsontoObject.JSONtoDate(json);
        if (indexService.addRecord(open_id,date)) {
            JSONObject returnJson=new JSONObject();
            returnJson.put("error_code",200);
            returnJson.put("data",json);
            return returnJson;
        } else {
            JSONObject returnJson=new JSONObject();
            returnJson.put("error_code",500);
            returnJson.put("data",json);
            return returnJson;
        }
    }


}
