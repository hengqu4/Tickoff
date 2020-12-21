package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@ToString
@Builder
@Data
public class User {
    private String user_id;
    private String nickname;
    private String avatar_url;
    private int otherlike;
    @Tolerate
    public User(){

    }
}
