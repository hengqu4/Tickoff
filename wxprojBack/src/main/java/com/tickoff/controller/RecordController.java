package com.tickoff.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.Record;
import com.tickoff.domain.ReturnRecord;
import com.tickoff.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/tickoff")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "/api/activeDays/UserID/{userid}")
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

    @RequestMapping(value = "/api/HistoryRecord/UserID/{userid}")
    public JSONObject getHistoryRecord(@PathVariable String userid){

        int weekdays[]=new int[]{7,1,2,3,4,5,6};
        List<Record> list=recordService.getRecordList(userid);
        Calendar now=Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        int year= now.get(Calendar.YEAR);
        int month=now.get(Calendar.MONTH)+1;
        int day=now.get(Calendar.DAY_OF_MONTH);
        int week=now.get(Calendar.DAY_OF_WEEK);
        int total=113+weekdays[week-1];

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


}