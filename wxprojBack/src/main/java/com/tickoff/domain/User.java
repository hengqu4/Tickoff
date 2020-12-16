package com.tickoff.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    /* 省略get、set和带参构造方法 */
    public User(Long id,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }
}