package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.io.Serializable;

@Builder
@ToString
@Data
public class IndexMission implements Serializable {
    private String missionId;
    private String name;
    private String description;
    private int done;

    @Tolerate
    public IndexMission(){}
}
