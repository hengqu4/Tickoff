package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


@Data
@ToString
@Builder
public class Sub_mission implements Serializable{
    private String title;
    private Long misson_Id;
    private Timestamp createDate;
    private boolean done;
}
