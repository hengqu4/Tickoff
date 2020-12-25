package com.tickoff.controller;

import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.User_mset;
import com.tickoff.service.User_msetService;
import com.tickoff.util.RetrunJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickoff")
public class User_msetController {
    @Autowired
    private User_msetService user_msetService;

    @RequestMapping(value = "/api/User_mset/openid/{openid}/mset_id/{mset_id}",method = RequestMethod.DELETE)
    public JSONObject quitMset(@PathVariable String openid,@PathVariable String mset_id){
        boolean result=user_msetService.deleteUser_mset(openid,mset_id);
        JSONObject json=new JSONObject();
        json.put("delete",result);
        String str=json.toJSONString();
        if(result){
            return RetrunJson.returnJsonSuccess(str);
        }else{
            return RetrunJson.returnJsonFailure(str);
        }
    }

    @RequestMapping(value = "/api/user_mset",method = RequestMethod.POST)
    public JSONObject inviteMset(@RequestBody String requestJson){
        JSONObject jsonObject=JSONObject.parseObject(requestJson);
        String openid=jsonObject.getString("userId");
        String setid=jsonObject.getString("setId");
        User_mset user_mset= User_mset.builder().openid(openid).mset_id(setid).build();

        Boolean result= user_msetService.addUser_mset(user_mset);
        JSONObject json=new JSONObject();
        json.put("data",user_mset);
        String str=json.toJSONString();
        if(result){
            return RetrunJson.returnJsonSuccess(str);
        }else{
            return RetrunJson.returnJsonFailure(str);
        }
    }

}
