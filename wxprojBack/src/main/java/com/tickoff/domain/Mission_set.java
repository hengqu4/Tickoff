package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@ToString
@Builder
@Data
public class Mission_set {
    private String mset_id;
    private String name;
    private String dscb;
    private String create_date;
    @Tolerate
    public Mission_set(){

    }
}
