package com.tickoff.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tickoff.domain.User;
import com.auth0.jwt.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BaseController
 * @Description //JWT生成token
 * @Author poros
 * @Date 2020/12/21 0:06
 **/
public class GetToken {

    private static final long EXPIRE_DATE = 30 * 60 * 1000;

    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";

    public static String getToken(User users) {
        String token = "";
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("openid", users.getUser_id())
                    .withExpiresAt(date)
                    .sign(algorithm);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim customStringClaim = claims.get("openid");
            String str=customStringClaim.asString();
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getOpenId(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim customStringClaim = claims.get("openid");
            String str=customStringClaim.asString();
            return str;
        }
        catch (IllegalArgumentException e) {
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        User user=User.builder().avatar_url("").user_id("open91cd84d64333821d73e2751f").like(1).nickname("xenon").build();
//        String token = getToken(user);
//        System.out.println(token);
//        boolean b = verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuaWQiOiJvcGVuaWQxMjMxMjMxIiwiZXhwIjoxNjA4NzM2MTkyfQ.uDMrMfIxzT3of8w1JYkx8mNqJ8ZZ9h7QTjzoYHaL6h8");
//        System.out.println(b);
//    }

}
