package com.tickoff.controller;


import com.alibaba.fastjson.JSONObject;
import com.tickoff.util.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName LoginController
 * @Description //TODO
 * @Author poros
 * @Date 2020/12/20 22:50
 **/

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/regist")
    public Map gettingOpenID(@RequestParam("code") String code){
        Map map=new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxdefec0c13005a788";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "7bbd24fe2b80b425f3731a7548d0dd2f";
        //授权（必填）
        String grant_type = "authorization_code";

        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //查询数据库中该openid是否存在
        if(usersService.queryOpenIdIsExist(openid)){
            //openid存在，则查找该user对象进行返回
            Users users=usersService.queryUserByOpenID(openid);
            //status为1，用户openid已存在
            map.put("status", 1);
            map.put("userInfo",users);
        }else{
            //openid不存在，则创建新user对象
            Users users=new Users();
            users.setOpenId(openid);
            users=usersService.saveUsers(users);
            //status为2，用户openid未存在
            map.put("status", 2);
            map.put("userInfo",users);
        }
        return map;
    }
}
