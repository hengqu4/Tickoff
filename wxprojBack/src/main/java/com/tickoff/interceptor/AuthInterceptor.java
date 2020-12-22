package com.tickoff.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.tickoff.dao.UserDao;
import com.tickoff.util.GetToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try{
            System.out.println("=====================================进入拦截器=====================================");
            String token=request.getHeader("Authorization");
            System.out.println("拦截器获取token为："+token);
            response.setContentType("application/json;charset=UTF-8");

            if(StringUtils.isEmpty(token)){
                System.out.println("no token");
                JSONObject res=new JSONObject();
                res.put("msg","no token");
                res.put("code",401);
                response.getWriter().write(res.toJSONString());
                return false;
            }

            if(GetToken.verify(token)){
                String str=GetToken.getOpenId(token);
                return userDao.getUserExist(str) == 1;
            }
            else{
                JSONObject res=new JSONObject();
                res.put("msg","token error:用户不存在或token错误");
                res.put("code",401);
                response.getWriter().write(res.toJSONString());
                return false;
            }
        }
        catch (Exception e){
            System.out.println("exception:"+e);
            response.getOutputStream().write(JSONObject.toJSONString(e).getBytes("utf-8"));
            response.getOutputStream().flush();
            response.sendError(500);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle执行了。。。。。。。");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("afterCompletion执行了。。。。。。。");

    }

}
