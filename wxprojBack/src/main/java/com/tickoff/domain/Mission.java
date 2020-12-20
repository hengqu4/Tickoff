package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@ToString
@Builder
@Data
public class Mission implements Serializable {
    private String missionId;
    private String mset_id;
    private String name;
    private String description;
    private String createDate;
    private String startDate;
    private String endDate;
    private int routine;
    private boolean delay;
    private int workLoad;
    private boolean requireCheck;
    private boolean needNotice;
    private String noticeTime;
    private boolean done;
    private String delayDate;

    @Tolerate
    public Mission(){

    }
}
