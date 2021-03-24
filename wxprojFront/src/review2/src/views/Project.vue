<template>
  <div class="project">
    <project-header @refreshProject="refreshProject"></project-header>
    <el-container>
      <el-aside class="home-aside" width="240px">
        <div class="aside-header">
          <template v-if="!searchProject">
            <div class="aside-project">
              <i class="fas fa-list"></i> 项目群组
            </div>
            <el-tooltip
              class="item"
              effect="light"
              content="搜索项目"
              placement="top-start"
            >
              <i
                class="el-icon-search"
                style="cursor: pointer"
                @click="searchProject = true"
              ></i>
            </el-tooltip>
          </template>
          <template v-else>
            <el-input
              class="search-input"
              v-model="searchInput"
              placeholder="搜索项目"
            ></el-input>
            <i class="el-icon-close" @click="searchProject = false"></i>
          </template>
        </div>
        <el-divider></el-divider>
        <div class="project-list">
          <div class="project-add" @click="addProjectVisible = true">
            <i class="icon"></i>
            <span>新建项目</span>
          </div>
          <!-- <span style="font-size:20px">我创建的项目</span>
          <div
            v-for="(project, index) in myCreateProject"
            :key="project.projectId + 'A'"
            :class="[
              'project-item',
              project.projectId === projectId ? 'project-item-active' : ''
            ]"
            @click="changeProject(project, index)"
          >
            <div class="project-name">
              <i class="fas fa-cube" style="margin-right:4px; "></i>
              {{ project.projectName }}
              <el-popover placement="right" trigger="hover">
                <div class="btns" style="display: flex;flex-direction: column;">
                  <el-button
                    type="text"
                    icon="el-icon-delete"
                    @click="deleteProject(project.projectId)"
                    >解散项目</el-button
                  >
                </div>
                <i slot="reference" class="el-icon-more project-more"></i>
              </el-popover>
            </div>
          </div>
          <span style="font-size:20px">我加入的项目</span>
          <div
            v-for="(project, index) in myJoinProject"
            :key="project.projectId"
            :class="[
              'project-item',
              project.projectId === projectId ? 'project-item-active' : ''
            ]"
            @click="changeProject(project, index)"
          >
            <div class="project-name">
              <i class="fas fa-cube" style="margin-right:4px; "></i>
              {{ project.projectName }}
              <el-popover placement="right" trigger="hover">
                <div class="btns" style="display: flex;flex-direction: column;">
                  <el-button type="text"></el-button>
                  <el-button
                    type="text"
                    icon="el-icon-delete"
                    @click="exitProject(project)"
                    >退出项目</el-button
                  >
                </div>
                <i slot="reference" class="el-icon-more project-more"></i>
              </el-popover>
            </div>
          </div> -->
          <el-dialog title="警告" :visible.sync="dialogVisible" width="30%">
            <span>确定要解散此项目吗</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="deleteProject">确 定</el-button>
            </span>
          </el-dialog>
          <el-tree :data="treeData" :props="defaultProps" default-expand-all>
            <div slot-scope="{ node, data }" style="width: 100%">
              <div
                :class="[
                  'project-item',
                  data.alive ? 'project-item-active' : '',
                ]"
                @click="changeProject(data.projectId, data.projectName)"
              >
                <i
                  class="fas fa-cube"
                  style="margin-right: 4px"
                  v-if="data.leaf"
                ></i>
                {{ node.label }}
                <el-popover placement="right" trigger="hover" v-if="data.leaf">
                  <div
                    class="btns"
                    style="display: flex; flex-direction: column"
                  >
                    <el-button
                      type="text"
                      icon="el-icon-delete"
                      @click="removeDialog(data.projectId)"
                    >
                      {{ data.word }}
                    </el-button>
                  </div>
                  <i slot="reference" class="el-icon-more project-more"></i>
                </el-popover>
              </div>
            </div>
          </el-tree>
        </div>
        <el-divider></el-divider>
        <div class="user-space">
          <el-button type="info" @click="goPersonal">个人空间</el-button>
        </div>
      </el-aside>
      <el-container>
        <el-header class="container-header">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="文件" name="file"></el-tab-pane>
            <el-tab-pane label="会议" name="meeting"></el-tab-pane>
            <!-- <el-tab-pane label="回收站" name="recycle"></el-tab-pane> -->
          </el-tabs>
        </el-header>
        <el-main class="content-wrap">
          <!-- <vue-scroll :ops="ops"> -->
          <router-view v-if="isRouterAlive"></router-view>
          <!-- </vue-scroll> -->
        </el-main>
      </el-container>
    </el-container>
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
        </el-form-item>
        <el-alert v-show="limitInputAlert" title="不能超过8个字符" type="error"> </el-alert>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addProjectVisible = false">取 消</el-button>
        <el-button type="primary" @click="addProject">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { MessageBox } from "element-ui";
import { Message } from "element-ui";
import {
  getProjectId,
  setProjectId,
  setProjectName,
  getUserId,
} from "@/utils/cache.js";
import {
  getUserAndVideo,
  getProjects,
  createProject,
  leaveProject,
} from "@/api/project";
import ProjectHeader from "@/common/header/ProjectHeader";
export default {
  components: {
    ProjectHeader,
  },
  // provide() {
  //   //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
  //   return {
  //     reload: this.reload
  //   };
  // },
  data() {
    return {
      //       ops: {
      //   vuescroll: {
      //     checkShiftKey: true,
      //     locking: false
      //   },
      //   bar: {
      //     showDelay: 500,
      //     onlyShowBarOnScroll: true,
      //     keepShow: true,
      //     background: "#c1c1c1",
      //     opacity: 1,
      //     hoverStyle: false,
      //     specifyBorderRadius: false,
      //     minSize: 0,
      //     size: "6px",
      //     disable: false
      //   }
      // },
      dialogVisible: false,
      dialogProjectId: "",
      projectId: "",
      activeIndex: 0,
      isRouterAlive: true,
      searchInput: "",
      searchProject: false,
      addProjectVisible: false,
      addProjectForm: {
        projectName: "",
      },
      activeName: "file",
      projectList: [],
      myCreateProject: [],

      myJoinProject: [],
      currentProjectId: "",
      isRead: false,
      defaultProps: {
        children: "children",
        label: "label",
        projectId: "projectId",
      },

      limitInputAlert: false,
      inputContent: "",
    };
  },
  methods: {
    refreshProject() {
      this.getProjects();
    },
    goPersonal() {
      this.$router.push("/user");
    },
    goWelcome() {
      this.$router.push("/welcome");
    },
    handleClick(tab, event) {
      let projectId = getProjectId();
      let path = "/project/" + this.activeName;
      this.$router.push(path);
    },
    changeProject(projectId, projectName) {
      console.log("------");

      console.log(projectId);
      console.log(projectName);
      console.log("------");

      if (!projectId || !projectName) {
        return;
      }
      // console.log('changeProject:', projectId, projectName)
      setProjectId(projectId);
      this.currentProjectId = projectId;
      setProjectName(projectName);
      this.projectName = projectName;
      this.activeName = "file";
      //  this.$router.go(0);
      location.reload();
    },
    // 刷新页面
    // reload() {
    //   this.isRouterAlive = false; //先关闭，
    //   this.$nextTick(function() {
    //     this.isRouterAlive = true; //再打开
    //   });
    // },
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
      createProject(this.addProjectForm).then((res) => {
        this.getProjects();
        console.log(res);
        let _this = this;
        setTimeout(function () {
          _this.activeIndex = _this.myCreateProject.length - 1;
          _this.changeProject(
            _this.myCreateProject[_this.activeIndex].projectId,
            _this.myCreateProject[_this.activeIndex].projectName
          );
        }, 300);
      });
      this.addProjectVisible = false;
      }
    },
    async getProjects() {
      getProjects().then((res) => {
        this.projectList = res.data;
        let userId = getUserId();
        this.myJoinProject = this.projectList.filter((project) => {
          return project.ownerId != userId;
        });
        this.myCreateProject = this.projectList.filter((project) => {
          return project.ownerId == userId;
        });
      });
    },
    exitProject(id) {
      leaveProject(id).then((res) => {
        this.init();
      });
    },
    deleteProject() {
      leaveProject(this.dialogProjectId).then((res) => {
        // Message.success("解散项目成功");
        this.init();
        this.$router.push("/");
      });

      this.dialogVisible = false;
    },
    removeDialog(id) {
      this.dialogVisible = true;
      this.dialogProjectId = id;
    },
    init() {
      this.getProjects();
      this.projectId = getProjectId();
    },
  },
  computed: {
    treeData: function () {
      let t = [
        {
          label: "我创建的项目",
          leaf: false,
          children: [],
        },
        {
          label: "我加入的项目",
          leaf: false,
          children: [],
        },
      ];
      // console.log(this.myCreateProject, this.myJoinProject)
      for (const project of this.myCreateProject) {
        // console.log(project);
        t[0].children.push(project);
        t[0].children[t[0].children.length - 1].label = project.projectName;
        t[0].children[t[0].children.length - 1].leaf = true;
        t[0].children[t[0].children.length - 1].alive =
          this.projectId == project.projectId;
        t[0].children[t[0].children.length - 1].word = "解散项目";
      }
      for (const project of this.myJoinProject) {
        t[1].children.push(project);
        t[1].children[t[1].children.length - 1].label = project.projectName;
        t[1].children[t[1].children.length - 1].leaf = true;
        t[1].children[t[1].children.length - 1].alive =
          this.projectId == project.projectId;
        t[1].children[t[1].children.length - 1].word = "退出项目";
      }
      // console.log(t);
      return t;
    },
  },
  mounted() {
    this.projectId = this.$route.query.projectId;
    this.getProjects();

    if (getProjectId()) {
      this.projectId = getProjectId();
      this.projectId = getProjectId();

      let projectList = this.projectList;
      let len = projectList.length;
      for (var i = 0; i < len; i++) {
        if (projectList[i].projectId == this.projectId) {
          this.activeIndex = index;
          console.log(this.activeIndex);
          break;
        }
      }
    } else {
      this.projectId = this.projectList[0].projectId;
      this.activeIndex = 0;
    }
  },
};
</script>


<style lang="scss" scoped>
// 这些样式是为了调整element tree项目列表, 需要全局修改, 所以没有scoped
/deep/ .el-tree-node__content:hover {
  background: rgba(255, 255, 255, 0);
}
/deep/ .el-tree-node:focus > .el-tree-node__content {
  background-color: rgba(255, 255, 255, 0);
}
/deep/ .el-tree-node__content {
  height: 42px;
}

// $headerHeight:60px;

.project {
  max-height: 100vh;
}
/* 侧边栏 */
.home-aside {
  display: flex;
  flex-direction: column;
  height: 90vh;
  margin-left: 5px;
  border-right: 1px solid #dfdfdf;
}
.aside-header {
  margin: 15px 0 -20px 18px;
  display: flex;
  align-items: center;
  height: 56px;
}
.aside-project {
  flex: 1;
  font-size: 20px;
}
.aside-project i {
  font-size: 18px;
}
.el-icon-search,
.el-icon-close {
  font-size: 16px;
  margin-right: 16px;
}
.project-list {
  width: 100%;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.project-add {
  margin: -16px 40px 10px 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 42px;
  border-radius: 25px;
  width: 120px;
  background-color: #fff;
  border: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 1s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.project-add:hover {
  margin-left: 35px;
}
.project-add .icon {
  display: inline-block;
  height: 15px;
  width: 15px;
  background-size: 25px 25px;
  background-image: url("../../static/images/addProject.png");
  background-position: center;
  margin-right: 4px;
}
.project-add span {
  font-size: 16px;
  color: #333;
}

.project-item {
  height: 42px;
  line-height: 42px;
  width: 100%;
  cursor: pointer;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding: 0 5px 0 20px;
  transition: all 0.5s;
  position: relative;
}
.project-more {
  position: absolute;
  right: 2px;
  top: 14px;
  transform: rotate(90deg);

  opacity: 0;
}
.project-item:hover .project-more {
  opacity: 1;
}
.btns .el-button--text {
  color: #333;
}
.project-item-active {
  background-color: rgb(228, 241, 255);
}
.project-item-active .project-name {
  color: rgb(45, 145, 245);
}
.project-name {
  color: #333333;
  transition: all 0.1s;
}

.project-item:hover {
  background-color: rgba(228, 241, 255, 0.589);
}
.project-item:hover .project-name {
  color: rgb(45, 145, 245);
}
.user-space {
  margin-bottom: 20px;
  align-self: center;
}
.container-header {
  margin-top: 23px;
}
.content-wrap {
  margin-top: -35px;
}
.search-input >>> .el-input__inner {
  -webkit-appearance: none;
  background-color: #fff;
  background-image: none;
  border-radius: 0px;
  border: 0px;
  width: 100%;
  padding-left: 0;
  height: 18px;
  border-bottom: 1px solid #409eff;
}
</style>
