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
public class Mission_set implements Serializable {
    private String mission_Set_Id;
    private String name;
    private String description;
    private Timestamp createDate;
}
