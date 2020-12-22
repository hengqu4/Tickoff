package com.tickoff.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.tickoff.domain.User;
import com.auth0.jwt.JWT;

import java.util.Date;
/**
 * @ClassName BaseController
 * @Description //JWT生成token
 * @Author poros
 * @Date 2020/12/21 0:06
 **/
public class GetToken {

    public static String getToken(User users){
        String token="";
        token = String.valueOf(JWT.create()
                .withKeyId(users.getUser_id())
                .withIssuer("www.tickoff.ltd")
                .withIssuedAt(new Date())
                .withJWTId("jwt.ikertimes.com")
                .withAudience(users.getUser_id()));
        return token;
    }

}
