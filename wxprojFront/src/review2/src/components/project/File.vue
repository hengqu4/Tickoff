<template>
  <div class="file-container">
    <el-row class="search-header">
      <el-col :span="6">
        <span class="project-name">{{ projectName }}</span>
      </el-col>
      <el-col :span="2">
        <span> 共{{ this.totalPage }}个视频</span>
      </el-col>
      <el-col :span="4">
        <el-input
          prefix-icon="el-icon-search"
          class="project-search"
          placeholder="搜索文件"
        ></el-input>
      </el-col>

      <el-col :offset="4" :span="7">
        <div class="user-list">
          <template v-for="(user, index) in userList">
            <div
              class="user-item"
              :key="index"
              :style="`zIndex:${7 - index}`"
              @click="showUserList"
              v-if="index < 3"
            >
              <el-tooltip
                class="item"
                effect="light"
                :content="user.userName"
                placement="bottom  "
              >
                <el-avatar :src="user.avatar"></el-avatar>
              </el-tooltip>
            </div>
          </template>
          <div
            v-if="userList.length > 3"
            class="user-item user-num"
            @click="showUserList"
          >
            {{ userList.length }}
          </div>
        </div>
      </el-col>
      <el-col :span="1">
        <el-tooltip effect="light" content="邀请成员" placement="bottom">
          <div class="user-add" @click="inviteUser">
            <i class="fas fa-user-plus"></i>
          </div>
        </el-tooltip>
      </el-col>
    </el-row>

    <video-list
      :videoList="videoList"
      :projectName="projectName"
      @uploadVideo="refreshVideoList"
    ></video-list>
    <!-- 视频下方的页码 -->
    <div class="pagination-container">
      <el-pagination
        background
        hide-on-single-page
        :page-size="7"
        layout="prev, pager, next"
        :total="totalPage"
        @current-change="handleCurrentChange"
        >
      </el-pagination>
    </div>
    <el-dialog
      :title="projectName"
      :visible.sync="showUserListVisible"
      width="35%"
      class="userlist-dialog"
    >
      <user-list :userList="userList" @deleteUser="refreshUserList"></user-list>
      <div class="invite-more pointer" @click="inviteUser">
        邀请更多成员
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showUserListVisible = false">取 消</el-button>
        <el-button type="primary" @click="showUserListVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="邀请用户"
      :visible.sync="showVisitUserVisible"
      width="45%"
      @close="VisitUserClose"
    >
      <el-row>
        <el-input
          placeholder="请输入用户信息"
          v-model="inviteInput"
          prefix-icon="el-icon-search"
          @keyup.enter.native="searchUser"
        >
          <template slot="append">
            <el-button @click="searchUser">搜索</el-button>
          </template>
        </el-input>
      </el-row>
      <div v-if="visitUser.length === 0">未找到指定用户</div>
      <visit-list :visitUser="visitUser"></visit-list>

      <span slot="footer" class="dialog-footer">
        <el-button @click="showVisitUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="showUserListVisible = false"
          >邀请</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";
import { getVideoListByPage, getMemberList } from "@/api/project";
import { getUserList } from "@/api/user";
import { getProjectId, getProjectName } from "@/utils/cache.js";
import VideoList from "../../common/list/VideoList.vue";
import UserList from "../../common/list/UserList.vue";
import VisitList from "@/common/list/VisitList";
export default {
  components: { VideoList, UserList, VisitList },
  name: "File",
  props: {},
  data() {
    return {
      projectName: "",
      loading: false,
      uploadVisible: false,
      video: "",
      imageUrl: "",
      srcList: [],
      userList: [],
      videoList: [],
      visitUser: [],
      //存放封面的数组
      index: [],
      showUserListVisible: false,
      showVisitUserVisible: false,
      showCreateMeetingVisible: false,
      projectId: "",
      inviteInput: "",


      // video: null
      x: 0,
      coverList: [],
      videoNum: 0,
      left: 0,
      right: 0,
      //上传
      param: new FormData(),
      totalPage: 1,
      page: 1
    };
  },
  methods: {
    handleCurrentChange(val){
      this.page = val;
      getVideoListByPage(getProjectId(), this.page)
        .then(res => {
          this.totalPage = res.data.total;
          this.videoList = res.data.videoList;
          // console.log(this.totalPage);
        })
        .catch(err => {
          console.log(err);
        });
    },
    getProjectInfo(id = getProjectId(), page = this.page, pageNum = 7) {
      getVideoListByPage(id, page, pageNum)
        .then(res => {
          this.totalPage = res.data.total;
          this.videoList = res.data.videoList;
          // console.log(this.totalPage);
        })
        .catch(err => {
          console.log(err);
        });
      getMemberList(getProjectId())
        .then(res => {
          this.userList = res.data;
          console.log(this.userList);
        })
      this.projectName = getProjectName();
    },
    searchUser() {
      let params = { data: this.inviteInput };
      getUserList(params).then(res => {
        console.log(res.data);
        this.visitUser = res.data;
        let userlist = this.userList;
        // console.log(userlist);
        userlist.forEach(user => {
          // user.visitWord = "";
          this.visitUser = this.visitUser.filter(v => {
            return v.userId != user.userId;
          });
        });
      });
    },
    refreshUserList() {
      this.getProjectInfo();
    },
    refreshVideoList() {
      this.getProjectInfo();
    },
    showUserList() {
      this.showUserListVisible = true;
    },
    inviteUser() {
      this.showUserListVisible = false;
      this.showVisitUserVisible = true;
      // this.searchUser();
    },
    VisitUserClose(done) {
      this.inviteInput = "";
      this.visitUser = [];
    },
    handleUploadError(error) {
      Message.error(error);
    },
    handleResponse(response) {}
  },

  mounted() {
    this.projectId = getProjectId();
    this.getProjectInfo();
  }
};
</script>

<style lang="scss" scoped>

.pagination-container{
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 2em;
}
.file-container {
  position: relative;
  height: 100%;
  /* 文件 header */
  .search-header {
    height: 46px;
    line-height: 46px;
    border-bottom: 1px solid #c0c0c0;
  }
}
.project-search {
  width: 180px;
}
.project-name {
  color: #409eff;
  font-size: 24px;
}
.user-list {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.user-item {
  margin-right: -10px;
  height: 35px;
  width: 35px;
}
.user-num {
  height: 34px;
  width: 34px;
  border-radius: 50%;
  background-color: #4aa3ff;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  font-size: 120%;
}
.el-avatar {
  height: 100%;
  width: 100%;
}
.user-add {
  margin-left: 15px;
  color: #4aa3ff;
  margin-top: 2px;
}
.user-add i {
  font-size: 28px;
}


.userlist-dialog {
  .invite-more {
    height: 32px;
    line-height: 32px;
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    margin-top: 5px;
  }
}
</style>
