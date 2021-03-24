<template>
  <div class="meeting-container">
    <el-header style="position: relative;margin-top:20px">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="全部会议" name="first">
          <meeting-list :meetingList="meetingList"></meeting-list>
        </el-tab-pane>
        <el-tab-pane label="历史会议" name="history">
          <meeting-list :meetingList="historyMeetingList"></meeting-list
        ></el-tab-pane>
        <el-tab-pane label="待办列表" name="todo">
          <meeting-list :meetingList="mineMeetingList"></meeting-list>
        </el-tab-pane>
      </el-tabs>

      <el-input
        placeholder="查找会议"
        v-model="meetingInput"
        prefix-icon="el-icon-search"
        class="search-meeting"
      >
        <el-button slot="append" @click="searchMeeting">搜索</el-button>
      </el-input>
      <el-button
        type="text"
        @click="showCreateMeetingVisible = true"
        class="create-meeting"
        >创建会议</el-button
      >
    </el-header>
    <el-dialog
      title="创建会议"
      :visible.sync="showCreateMeetingVisible"
      width="40%"
      @close="CreateMeetingClose"
    >
      <el-form
        :model="meeting"
        label-width="120px"
        label-suffix=":"
        label-position="left"
      >
        <el-form-item label="会议名称">
          <el-input v-model="meeting.title"></el-input>
        </el-form-item>
        <el-form-item label="所属 projectId">
          <el-input v-model="meeting.belongTo" disabled></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="meeting.startTime"
            type="datetime"
            placeholder="选择开始时间"
            value-format="timestamp"
            prefix-icon="el-icon-caret-bottom"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="预计结束时间">
          <el-date-picker
            v-model="meeting.endTime"
            type="datetime"
            placeholder="选择结束时间"
            value-format="timestamp"
            prefix-icon="el-icon-caret-bottom"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="meeting.note"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createMeeting">创建会议</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from "element-ui";
import MeetingList from "../../common/list/MeetingList.vue";
import { getProjectId } from "@/utils/cache";
import { getMeetings } from "@/api/project";
import {
  searchMeeting,
  getMyMeeting,
  getTodoMeeting,
  getHistoryMeeting,
  createMeeting
} from "@/api/meeting";
export default {
  name: "Meeting",
  components: { MeetingList },
  data() {
    return {
      todoMeetingList: [],
      meetingList: [],
      historyMeetingList: [],
      mineMeetingList: [],
      activeName: "first",
      meetingInput: "",
      projectId: "",
      showCreateMeetingVisible: false,
      meeting: {
        title: "",
        belongTo: "",
        startTime: "",
        endTime: "",
        note: ""
      }
    };
  },
  methods: {
    CreateMeetingClose() {
      this.meeting.title = "";
      this.meeting.startTime = "";
      this.meeting.endTime = "";
      this.meeting.note = "";
    },
    createMeeting() {
      createMeeting(this.meeting).then(res => {
        this.getMeeting(this.projectId);
        this.activeName = "first";
        // Message.success("会议创建成功");
        this.showCreateMeetingVisible = false;
      });
    },
    searchMeeting() {
      let params = {
        meetingId: this.meetingInput
      };
      searchMeeting(params).then(res => {
        this.meetingList = [res.data];
      });
    },
    getMeetingByName(name) {
      switch (name) {
        case "first":
          this.getMeeting(this.projectId);
          break;
        case "todo":
          this.getTodoMeeting(this.projectId);
          break;

        case "history":
          this.getHistoryMeeting(this.projectId);
          break;
      }
    },
    getTodoMeeting(projectId) {
      getTodoMeeting({
        projectId: projectId
      }).then(res => {
        this.todoMeetingList = res.data.meetingList;
      });
    },
    getHistoryMeeting(projectId) {
      getHistoryMeeting({
        projectId: projectId
      }).then(res => {
        this.historyMeetingList = res.data;
      });
    },

    handleClick(tab, event) {
      this.getMeetingByName(tab.name);
    },
    getMeeting(project) {
      getMeetings().then(res => {
        // Message.success("成功获取会议列表");
        this.meetingList = res.data;
      });
    }
  },
  mounted() {
    this.projectId = getProjectId();
    this.meeting.belongTo = getProjectId();
    this.getMeeting(this.projectId);
  }
};
</script>
<style scoped>
.meeting-container {
  margin-top: 80px;
}
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
  background-color: #ccc;
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
