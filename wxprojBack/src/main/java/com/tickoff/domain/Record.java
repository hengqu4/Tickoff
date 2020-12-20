package com.tickoff.domain;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.sql.Date;


@ToString
@Builder
@Data
public class Record {
    private String User_id;
    private Date Date;
    private int Total_m;
    private int done_m;
    @Tolerate
    public Record(){

    }
}
