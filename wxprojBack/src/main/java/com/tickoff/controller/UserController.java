package com.tickoff.controller;


import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.User;
import com.tickoff.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickoff")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/likes/UserID/{userId}",method= RequestMethod.GET)
    public JSONObject getUserLikes(@PathVariable String userId){
        User user=userService.getUserById(userId);
        JSONObject likes=new JSONObject();
        likes.put("likes",user.getOtherlike());
        JSONObject returnjson=new JSONObject();
        returnjson.put("error_code","200");
        returnjson.put("data",likes);
        return returnjson;
    }
}
