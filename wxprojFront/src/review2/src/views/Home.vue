<!--
 * @Description: 
 * @Version: 2.0
 * @Autor: renlinjie
 * @Date: 2021-01-14 18:47:06
 * @LastEditors: renlinjie
 * @LastEditTime: 2021-01-14 21:04:39
-->
<template>
  <div class="home">
    <project-header></project-header>
    <el-dialog
      title="创建项目"
      :visible.sync="addProjectVisible"
      width="30%"
      @close="AddProjectClose"
    >
      <el-form
        ref="addProjectFormRef"
        :model="addProjectForm"
        label-width="80px"
        @submit.native.prevent
      >
        <el-form-item label="项目名称">
          <el-input v-model="addProjectForm.projectName" @input="limitInputLength" @keyup.enter.native="addProject"></el-input>
          <!-- <el-input v-model="addProjectForm.projectName"></el-input> -->
        </el-form-item>
        <el-alert v-show="limitInputAlert" title="不能超过8个字符" type="error"> </el-alert>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addProjectVisible = false">取 消</el-button>
        <el-button type="primary" @click="addProject">确 定</el-button>
      </span>
    </el-dialog>
    <div class="line-project">
      <el-button
        type="primary"
        style="height: 30px; margin-top: 30px; margin-left: 75px"
        @click="addProjectVisible = true"
        plain
        >创建项目</el-button
      >
      <div class="search-project">
        <el-input
          placeholder="查找项目"
          v-model="projectInput"
          prefix-icon="el-icon-search"
        >
          <el-button slot="append" @click="searchProject">搜索</el-button>
        </el-input>
      </div>
    </div>

    <div class="project-list">
      <project-list :list="searchProject()"></project-list>
    </div>
  </div>
</template>

<script>
import { getUserInfo } from "@/api/user";
import {
  getUserId,
  setUserAvatar,
  setUserName,
  setUserMobile,
} from "@/utils/cache";
import {
  getUserAndVideo,
  getProjects,
  createProject,
  leaveProject
} from "@/api/project";
import ProjectList from "@/common/list/ProjectList";
import ProjectHeader from "@/common/header/ProjectHeader";
export default {
  name: "Home",
  components: {
    ProjectList,
    ProjectHeader,
  },
  data() {
    return {
      projectList: [],
      myProject: [],
      myJoinProject: [],
      projectInput: "",
      addProjectVisible: false,
      addProjectForm: {
        projectName: ""
      },

      limitInputAlert: false,
      inputContent: "",
    };
  },
  methods: {
    getProjects() {
      getProjects().then((res) => {
        this.projectList = res.data;
      });
    },
    AddProjectClose() {
      this.addProjectForm = {};
    },
    limitInputLength(val){//账号的实时输入
      // console.log(val.length);
      this.inputContent = val;
      if(val.length>8){
        this.limitInputAlert = true;
      }
      else{
        this.limitInputAlert = false;
      }
    },
    addProject() {
      // console.log("-----");
      // console.log(this.inputContent.length);
      if(this.inputContent.length<=8){
        createProject(this.addProjectForm).then(res => {
          this.getProjects();
          console.log(res);

          let _this = this;

          setTimeout(function() {
            console.log(_this.projectList);
            _this.activeIndex = _this.projectList.length - 1;
            _this.changeProject(
              _this.projectList[_this.activeIndex],
              _this.activeIndex
            );
          }, 300);
        });
        this.addProjectVisible = false;
      }
    },
    async getProjects() {
      getProjects().then(res => {
        this.projectList = res.data;
        let userId = getUserId();
        this.myJoinProject = this.projectList.filter(project => {
          return project.ownerId != userId;
        });
        this.myCreateProject = this.projectList.filter(project => {
          return project.ownerId == userId;
        });
      });
    },
    searchProject() {
      return this.projectList.filter((item) => {
        let that = this;
        if (item.projectName.includes(that.projectInput)) {
          console.log(item);
          return item;
        }
      });
    },
  },
  mounted() {
    this.getProjects();
    getUserInfo(getUserId()).then((res) => {
      setUserAvatar(res.data.avatar);
      setUserName(res.data.username);
      setUserMobile(res.data.mobileNum);
    });
  },
};
</script>

<style lang="scss" scoped>
.home {
  width: 100vw;
}

.line-project {
  display: flex;
}

.search-project {
  margin: 30px;
  width: 200px;
  height: 30px;
}
</style>
