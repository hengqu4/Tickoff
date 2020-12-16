package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@ToString
@Builder
public class Mission implements Serializable {
    private String missionId;
    private String setId;
    private String name;
    private String description;
    private Date createDate;
    private Date start;
    private Date end;
    private String routine;
    private boolean isDelay;
    private Date delay;
    private int workLoad;
    private boolean isRequire;
    private boolean isNeedNotice;
    private Date notice;
    private boolean done;
}
