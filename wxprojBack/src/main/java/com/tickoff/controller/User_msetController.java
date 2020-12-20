package com.tickoff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.User_mset;
import com.tickoff.service.User_msetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickoff")
public class User_msetController {


    //这部分的代码有需要再改




    @Autowired
    private User_msetService user_msetService;

    @RequestMapping(value = "/api/user_mset/{openid}",method = RequestMethod.GET)
    public List<User_mset> getUserAllMset(@PathVariable String openid){
        return user_msetService.getAllUser_mset(openid);
    }

    @RequestMapping(value = "/api/user_mset/openid/{openid}/mset_id/{mset_id}",method = RequestMethod.GET)
    public User_mset getUser_msetById(@PathVariable String openid,@PathVariable String mset_id){
        return user_msetService.getUser_mset(openid, mset_id);
    }

    @RequestMapping(value = "/api/user_mset/openid/{openid}/mset_id/{mset_id}",method = RequestMethod.DELETE)
    public String deleteUser_mset(@PathVariable String openid,@PathVariable String mset_id){
        return String.valueOf(user_msetService.deleteUser_mset(openid, mset_id));
    }

    @RequestMapping(value = "/api/user_mset",method = RequestMethod.POST)
    public String addUser_mset(@RequestBody String requestJson){
        JSONObject json = JSONObject.parseObject(requestJson);
        User_mset user_mset= JSON.toJavaObject(json,User_mset.class);
        return String.valueOf(user_msetService.addUser_mset(user_mset));
    }
}
