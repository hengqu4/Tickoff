package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@ToString
@Builder
@Data
public class User_mset {
    private String openid;
    private String mset_id;
    @Tolerate
    public User_mset(){

    }
}
