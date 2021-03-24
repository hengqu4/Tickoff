<template>
  <div class="meeting-list">
    <div class="meeting-title">会议列表</div>
    <div class="meeting-divider"></div>

    <el-row
      v-for="meeting in meetingList"
      :key="meeting.meetingId"
      class="meeting-item"
    >
      <el-col :span="2"
        ><el-image
          class="meeting-icon"
          src="../../../static/images/meeting-icon.png"
        ></el-image
      ></el-col>
      <el-col :offset="1" :span="6">
        <div>
          <h3>《{{ meeting.title }}》&ensp;审阅会</h3>
        </div>
        <div class="meeting-owner">
          创建者: &ensp;{{ meeting.ownerName }}
        </div>
      </el-col>
      <el-col :span="5">
        备注: &ensp;{{ meeting.note }}
      </el-col>
      <el-col :span="6" class="meeting-time">
        <div style="margin-right:10px">
          开始时间:{{ meeting.startTime | dateFormat }}
        </div>
        <div>结束时间:{{ meeting.endTime | dateFormat }}</div>
      </el-col>
      <el-col
        :offset="1"
        :span="3"
        style="display:flex;justify-content: center;"
      >
        <el-button
          v-if="showType(meeting.startTime, meeting.endTime) == 0"
          @click="enterMeeting(meeting.meetingId, meeting.ownerId)"
          >未开始</el-button
        >
        <el-button
          v-if="showType(meeting.startTime, meeting.endTime) == 1"
          @click="enterMeeting(meeting.meetingId, meeting.ownerId)"
          type="primary"
          >加入会议</el-button
        >
        <el-button
          v-if="showType(meeting.startTime, meeting.endTime) == 2"
          disabled
          >已经结束</el-button
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getUserId} from '@/utils/cache'
export default {
  name:'MeetingList',
  props:{
    meetingList:{
        type:Array,
        required:true,
    }
  },
  methods:{
        showType(start, end) {
      var now = new Date().getTime();
      if (start > now) {
        return 0;
      } else if (now < end) {
        return 1;
      } else {
        return 2;
      }
    },
      enterMeeting(meetingId, ownerId) {
      let isAdmin =
        getUserId() === ownerId ? true : false;
      this.$router.push({
        name: "审阅",
        query: { meetingId: meetingId, isAdmin: isAdmin }
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.meeting-list {
  width: 80%;
  display: flex;
  flex-direction: column;
  margin-left: 10%;
  padding: 10px 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  max-height: 68vh;
  overflow-y: auto;
}
.meeting-divider {
  margin-left: 20px;
  width: 98%;
  height: 1px;
  border-bottom: 1px solid #ccc;
  margin: 4px 0;
}
.meeting-title {
  height: 18px;
  line-height: 18px;
  font-size: 16px;
  margin: 4px 0 4px 30px;
}
.meeting-item {
  width: 100%;
  position: relative;
  height: 80px;
  display: flex;
  align-items: center;
}
.meeting-item:not(:last-child) {
  border-bottom: 1px solid #ccc;
}

.meeting-icon {
  height: 60px;
  margin-top: 10px;
  margin-left: 15px;
}
.meeting-time {
  display: flex;
  flex-direction: column;
  justify-content: end;
}
.search-meeting {
  width: 300px;
  position: absolute;
  top: 2px;
  left: 450px;
}
.create-meeting {
  position: absolute;
  left: 780px;
  top: 2px;
}
</style>
