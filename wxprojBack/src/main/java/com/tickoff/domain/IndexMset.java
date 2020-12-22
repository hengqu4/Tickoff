package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.List;

@Builder
@ToString
@Data
public class IndexMset implements Serializable {
    private String setId;
    private String setName;
    private String setDescription;
    private List<IndexMission> missions;

    @Tolerate
    public IndexMset(){}
}
