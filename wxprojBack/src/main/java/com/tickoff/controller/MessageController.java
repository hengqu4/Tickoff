package com.tickoff.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.tools.jconsole.JConsoleContext;
import com.tickoff.domain.User;
import com.tickoff.service.UserService;
import com.tickoff.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MessageController
 * @Description //测试发送推送消息功能 测试用，应为自动
 * @Author poros
 * @Date 2020/12/21 22:14
 **/
@RestController
public class MessageController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMessage")
    public boolean sendSubscribeMessage(@RequestParam("msgname") String msgname, @RequestParam("date") String date, @RequestParam("dscp") String dscp, @RequestParam("openid") String openid) {
        System.out.println("sendSubscribeMessage:");
        //接口调用凭证
        String access_token = getAccessToken();
        //接收者（用户）的 openid
        String touser = openid;
        //所需下发的订阅模板id
        String template_id = "IW9tcQJ8L1-9W6CqQysyh0PgruxfxjH-5HPHOuBdM1M";
        //点击模板卡片后的跳转页面，仅限本小程序内的页面。
        String page = "view";
        //模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
        JSONObject data = new JSONObject();

        JSONObject thing1 = new JSONObject();
        thing1.put("value", msgname);
        JSONObject time2 = new JSONObject();
        time2.put("value", date);
        JSONObject thing5 = new JSONObject();
        thing5.put("value", dscp);

        data.put("thing1", thing1);
        data.put("time2", time2);
        data.put("thing5", thing5);
        System.out.println("out_data:"+data);

        //1、向微信服务器 发送 发送订阅消息推送请求
        //请求参数
        String params = "access_token=" + access_token + "&touser=" + touser + "&template_id=" + template_id + "&page=" + page + "&data=" + data;
        //发送请求
        String sr = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send", params);
        System.out.println("11111111111111111111111111111111111"+access_token);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //用户的唯一标识（openid）
        String errmsg = (String) json.get("errmsg");
        int errcode = (int) json.get("errcode");
        System.out.println("out_errmsg:"+errmsg+"  errcode:"+errcode);
        return true;
    }

    public String getAccessToken() {
        //小程序唯一标识   (在微信小程序管理后台获取)
        String appid = "wxdefec0c13005a788";
        //小程序的 app secret (在微信小程序管理后台获取)
        String secret = "7bbd24fe2b80b425f3731a7548d0dd2f";
        //授权（必填）
        String grant_type = "client_credential";

        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + appid + "&secret=" + secret + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //用户的唯一标识（openid）
        String access_token = (String) json.get("access_token");

        String errmsg = (String) json.get("errmsg");
        int errcode = -2;
        if(json.get("errcode")==null){
            errcode = -99;
        }
        else {
            errcode = (int) json.get("errcode");
        }
        System.out.println("inner_errmsg:"+errmsg+"  errcode:"+errcode);

        return access_token;
    }
}