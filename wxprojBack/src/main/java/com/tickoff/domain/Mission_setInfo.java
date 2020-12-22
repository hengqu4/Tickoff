package com.tickoff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.List;

@ToString
@Builder
@Data
public class Mission_setInfo {

    private String mset_id;
    private String title;
    private String description;
    private String create_date;
    private List<User> member;

    @Tolerate
    public Mission_setInfo(Mission_set mission_set,List<User> userList){
        mset_id=mission_set.getMset_id();
        title=mission_set.getName();
        description=mission_set.getDescription();
        create_date=mission_set.getCreate_date();
        member=userList;
    }
}
