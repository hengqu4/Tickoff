package com.tickoff.domain;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.sql.Date;

@ToString
@Builder
@Data
public class ReturnRecord {
    private String openid;
    private String date;
    private int mission;
    private int done;
    @Tolerate
    public ReturnRecord(){

    }
}
