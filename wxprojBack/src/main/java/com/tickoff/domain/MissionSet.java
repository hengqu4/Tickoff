package com.tickoff.domain;

import lombok.experimental.Tolerate;

import java.io.Serializable;

public class MissionSet implements Serializable {
    private String setId;
    private String name;
    private String description;

    @Tolerate
    public MissionSet(){}
}
