package com.tickoff.controller;

import com.alibaba.fastjson.JSONObject;
import com.tickoff.service.User_msetService;
import com.tickoff.util.RetrunJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
