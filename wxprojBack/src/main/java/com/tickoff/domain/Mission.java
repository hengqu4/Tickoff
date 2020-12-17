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
public class Mission implements Serializable {
    private String missionId;
    private String setId;
    private String name;
    private String description;
    private Timestamp createDate;
    private Timestamp start;
    private Timestamp end;
    private int routine;
    private boolean isDelay;
    private int workLoad;
    private boolean isRequire;
    private boolean isNeedNotice;
    private Timestamp notice;
    private boolean done;
    private Timestamp delay;

}
