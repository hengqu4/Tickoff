package com.tickoff;

import com.tickoff.domain.User;
import com.tickoff.util.GetToken;

public class GettokenTest {
        public static void main(String[] args) {
        User user=User.builder().avatar_url("").user_id("open91cd84d64333821d73e2751f").otherlike(1).nickname("xenon").build();
        String token = GetToken.getToken(user);
        System.out.println(token);
        boolean b = GetToken.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuaWQiOiJvcGVuaWQxMjMxMjMxIiwiZXhwIjoxNjA4NzM2MTkyfQ.uDMrMfIxzT3of8w1JYkx8mNqJ8ZZ9h7QTjzoYHaL6h8");
        System.out.println(b);
    }
}
