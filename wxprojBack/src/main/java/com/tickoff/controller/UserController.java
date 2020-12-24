package com.tickoff.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tickoff.domain.User;
import com.tickoff.service.UserService;
import com.tickoff.util.RetrunJson;
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

    @RequestMapping(value = "/api/user/UserID/{userId}",method= RequestMethod.GET)
    public JSONObject getUser(@PathVariable String userId){
        User user=userService.getUserById(userId);
        String str= JSON.toJSONString(user);
        return RetrunJson.returnJsonSuccess(str);
    }

    @RequestMapping(value = "/api/user/like/openid/{openid}",method = RequestMethod.PUT)
    public JSONObject addLike(@PathVariable String openid){
        Boolean isSuccess=userService.addLike(openid);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg","success");
        return jsonObject;
    }
}
