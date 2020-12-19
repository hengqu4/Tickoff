//package com.tickoff.dao;
//
//import com.tickoff.domain.Mission;
//import com.tickoff.util.UniqueId;
//import com.tickoff.util.common.MissionConstants;
//import org.apache.ibatis.jdbc.SQL;
//
//
//public class MissionDynaSqlProvider {
//    public String insertMission(final Mission mission){
//        String sql="INSERT INTO mission (missionId, setId, name, description, createDate, startDate, endDate, routine, delay, workLoad, requireCheck, needNotice, noticeTime, done, delayDate)" +
//                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
////        SQL sql=new SQL();
////        sql.INSERT_INTO(MissionConstants.MissionTable);
////        sql.VALUES(MissionConstants.missionId, UniqueId.getUUID());
////        if(mission.getSetId()!=null){
////            sql.VALUES(MissionConstants.setId,mission.getSetId());
////        }
////        if(mission.getName()!=null){
////            sql.VALUES(MissionConstants.name,mission.getName());
////        }
////        if(mission.getDescription()!=null){
////            sql.VALUES(MissionConstants.description,mission.getDescription());
////        }
////        if(mission.getCreateDate()!=null){
////            sql.VALUES(MissionConstants.createDate,mission.getCreateDate().toString());
////        }
////        if(mission.getStartDate()!=null){
////            sql.VALUES(MissionConstants.startDate,mission.getStartDate().toString());
////        }
////        if(mission.getEndDate()!=null){
////            sql.VALUES(MissionConstants.endDate,mission.getEndDate().toString());
////        }
////        sql.VALUES(MissionConstants.routine,Integer.toString(mission.getRoutine()));
////        sql.VALUES(MissionConstants.delay,String.valueOf(mission.isDelay()));
////        sql.VALUES(MissionConstants.workLoad,Integer.toString(mission.getWorkLoad()));
////        sql.VALUES(MissionConstants.require,String.valueOf(mission.isRequireCheck()));
////        sql.VALUES(MissionConstants.needNotice,String.valueOf(mission.isNeedNotice()));
////        if(mission.getNoticeTime()!=null){
////            sql.VALUES(MissionConstants.noticeTime,mission.getNoticeTime().toString());
////        }
////        sql.VALUES(MissionConstants.done,String.valueOf(mission.isDone()));
////        if(mission.getDelayDate()!=null){
////            sql.VALUES(MissionConstants.delayDate,mission.getDelayDate().toString());
////        }
////        return sql.toString();
//    }
//}
