<template>
  <div class="video-list-box">
      <div class="file-upload" v-if="showList">
      <div class="video-upload-else" @click="uploadVisible = true">
        <i class="el-icon-plus upload-icon pointer"></i>
        <p class="Upload_pictures">
          <span
            style="color: rgb(134, 161, 236); font-size: 24px; cursor: pointer"
            >点击上传</span
          >
        </p>
      </div>
    </div>
    <div
      v-else
      class="video-list"
      :style="`grid-template-rows:repeat(${videoList.length / 4 + 1},180px)`"
    >
      <div class="video-upload" @click="uploadVisible = true">
        <i class="el-icon-plus upload-icon pointer"></i>
        <p class="Upload_pictures">
          <span
            style="color: rgb(134, 161, 236); font-size: 24px; cursor: pointer"
            >点击上传</span
          >
        </p>
      </div>
      <div
        v-for="(video, index) in videoList"
        :key="video.videoId"
        class="video-item"
        ref="vv"
        @mousemove="updateXY(index,$event)"
        @mouseout="outVideo"
      >
        <a-progress
          :percent="progress"
          size="small"
          :showInfo="false"
          :style="progressStyle"
          :stroke-color="{
            '0%': '#108ee9',
            '100%': '#87d068',
          }"
          :id="`progress${index}`"
        />
        <div
          class="video-cover-div"
          @mouseover="mousein(video.coverList, index)"
          @click="goReviewOffline(video.videoId)"
        >
          <div
            class="video-cover-sprite"
            :style="spriteStyle[index]"
            :id="`cover${index}`"
          ></div>

          <el-image
            class="video-cover"
            :src="video.cover"
            fit="contain"
            :id="`cover${index}`"
          ></el-image>
        </div>

        <el-dialog title="警告" :visible.sync="dialogVisible" width="30%">
          <span>确定要删除此视频吗</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="deleteVideo(video, video.videoId)">确 定</el-button>
          </span>
        </el-dialog>

        <div class="video-duration">{{ video.duration | timeFormat }}</div>
        <div class="video-description">
          {{ video.videoName }}
        </div>
        <div class="comment-num">
          <i style="margin-right: 2px" class="el-icon-s-comment"></i>
          {{ video.commentNum }}
        </div>
        <div class="video-time">
          {{ (video.createDate * 1000) | dateFormat }}
        </div>
        <el-popover placement="bottom" trigger="click" width="150px">
          <div style="display: flex; flex-direction: column">
            <!-- <el-button type="text"></el-button>
            <el-button type="text" icon="el-icon-delete">分享</el-button>-->
            <el-button
              type="text"
              icon="el-icon-delete"
              @click="dialogVisible = true"
              >删除</el-button
            >
          </div>

          <i class="el-icon-more require-more" slot="reference"></i>
        </el-popover>
      </div>
    </div>

    <el-dialog
      title="添加视频"
      :visible.sync="uploadVisible"
      width="500px"
      @close="handleUploadClose"
    >
      <el-form
        :model="addForm"
        v-loading="loading"
        ref="addFormRef"
        label-width="100px"
      >
        <el-form-item label="项目名称">
          <el-input v-model="projectName" disabled> </el-input>
        </el-form-item>
        <el-form-item label="视频上传">
          <!-- 选择需上传的文件 -->
          <div v-if="uploadProcess == 0">
            <div class="no-submit bg-block">
              点击上传视频
              <form action="/" enctype="multipart/form-data" method="post">
                <input
                  class="card-input"
                  type="file"
                  name="file"
                  placeholder="file"
                  @change="uploadFile($event)"
                />
              </form>
            </div>
          </div>
          <!-- 显示上传进度条 -->
          <div v-else-if="uploadProcess < 100" style="width: 50%">
            <el-progress :percentage="uploadProcess"></el-progress>
          </div>
          <!-- 显示被上传的文件 -->
          <div v-else>
            <el-tag closable @close="deSelectFile">
              {{ filename }}
            </el-tag>
          </div>
        </el-form-item>
        <el-form-item label="视频名称">
          <el-input v-model="addForm.videoName"> </el-input>
        </el-form-item>
        <el-form-item label="视频描述">
          <el-input v-model="addForm.description"> </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
<!--        <el-button @click.native="cancelUpload">取消上传</el-button>-->
        <el-button type="primary" @click.native="uploadVideo"
          >创建</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script >
import { deleteVideo } from "@/api/project";
import { getProjectId, setVideoId, getUserId } from "@/utils/cache";
import { createVideo, fastCreateVideo } from "@/api/video";
import { getToken } from "@/utils/auth";
import {
  genRandomUrl,
  uploadObject,
  genFullPath,
  deleteObject,
} from "@/utils/oss";
import { Message } from "element-ui";
import axios from "axios";
// import MediaInfo from "mediainfo.js";

export default {
  name: "VideoList",
  props: ["projectName", "videoList"],
  data() {
    return {
      dialogVisible: false,
      successCreateFlag: false,
      uploadProcess: 0,
      filename: "",
      addForm: {
        videoName: "",
        description: "",
        uploadToProject: "",
        permission: 0,
        url: "",
      },
      loading: false,
      uploadVisible: false,
      video: "",
      imageUrl: "",
      srcList: [],
      userList: [],
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
      progress: 0,
      progressStyle:
        "position: absolute;z-index: 8; margin-top: -10px; display:none;",
      spriteStyle: [],
      //上传
      // param: new FormData(),
      config: {
        headers: {
          Authorization: "Bearer " + window.sessionStorage.getItem("token"),
          "Content-Type": "multipart/form-data",
        },
      },
    };
  },
  computed: {
    showList() {
      return this.videoList.length === 0;
    },
  },
  methods: {
    deSelectFile() {
      deleteObject(this.addForm.url);
      this.uploadProcess = 0;
      this.addForm.videoName = "";
      this.filename = "";
    },
    updateUploadProcess(process) {
      process = parseFloat(process) * 100;
      // console.log(process);
      this.uploadProcess = parseFloat(process.toFixed(1));
    },
    handleUploadClose() {
      // if(!this.successCreateFlag){
      //   deleteObject(this.addForm.url);
      // }
      this.filename = "";
      this.addForm.videoName = "";
      this.addForm.description = "";
      this.addForm.permission = 0;
      this.addForm.url = "";
      this.uploadProcess = 0;
      // this.param = new FormData();
    },
    uploadFile(event) {
      let file = event.target.files[0];
      // this.addForm.videoName = file.name.substring(0, file.name.length - 4);
      let fileType = file.name
        .substring(file.name.lastIndexOf(".") + 1)
        .toLowerCase();
      if (fileType !== "mp4") {
        this.$message({
          type: "warning",
          message: "目前只支持MP4/h264格式文件，敬请期待",
        });
        return;
      }



      if (file.size > 1024 * 1024 * 500) {
        this.$message({
          type: "warning",
          message: "上传视频过大",
        });
        return;
      }
      // file 是视频文件对象.
      console.log(file);
      this.filename = file.name;
      console.log('++++++++++++',file.type)
      this.addForm.videoName = file.name;
      let filepath = genRandomUrl(getUserId(), fileType);
      uploadObject(file, filepath, this.updateUploadProcess);
      this.addForm.url = genFullPath(filepath);
      this.successCreateFlag = false;
      console.log(this.addForm.url);
      this.addForm.size = file.size;
      // 获取视频时长
      var video = document.createElement("video");
      video.preload = "metadata";
      let self = this;
      video.onloadedmetadata = function () {
        window.URL.revokeObjectURL(video.src);
        let duration = video.duration;
        console.log(duration);
        if (!duration) {
          self.$message({
            type: "warning",
            message: "视频加载出错",
          });
          return;
        }
        // console.log(self.param)
        self.addForm.duration = duration;
      };
      video.src = URL.createObjectURL(file);
    },
    cancelUpload() {
      this.handleUploadClose();
    },
    async uploadVideo() {
      this.loading = true;
      let headerObj = {
        Authorization: getToken(),
      };
      var self = this;
      let res = fastCreateVideo(this.addForm);
      // console.log(res);
      Promise.all([res]).then((res) => {
        res = res[0];
        if (res.result == 1) {
          this.addForm.url = "";
          this.successCreateFlag = true;
          this.loading = false;
          // this.uploadVisible = false;
          // 通知父组件刷新视频列表
          this.$emit("uploadVideo");
        }
      });
      this.uploadVisible = false;
    },

    goReviewOffline(videoId) {
      this.$router.push({
        path: "/reviewOffline",
        query: {
          videoId: videoId,
        },
      });
    },
    updateXY: function (index,event) {
      this.x = event.offsetX;
      // let coverLength = this.coverList.length;
      // console.log(this.videoList)
      this.videoNum = index;
      let coverLength = this.videoList[this.videoNum].spriteLen;
      let coverNum = (
        ((this.x + 1) / (this.right - this.left)) *
        coverLength
      ).toFixed(0);
      this.progress = coverNum * (100 / coverLength);
      // videolist [index] 这一项cover赋值
      // let img = new Image();
      // img.src = this.coverList[coverNum];
      // let that = this;
      // img.onload = function() {
      //   that.videoList[that.videoNum].cover = that.coverList[coverNum];
      // };
      //雪碧图
      document.getElementById("cover" + this.videoNum).style.opacity = "1";
      document.getElementById(
        "cover" + this.videoNum
      ).style.backgroundPosition = "-" + 244 * coverNum + "px 0px";
      document.getElementById("progress" + this.videoNum).style.display =
        "block";
    },
    outVideo() {
      document.getElementById("progress" + this.videoNum).style.display =
        "none";
      document.getElementById("cover" + this.videoNum).style.opacity = "0";
      // this.videoList[this.videoNum].cover = this.coverList[0];
    },
    mousein(coverList, index) {
      this.videoNum = index;
      this.left = this.$refs.vv[index].getBoundingClientRect().left;
      this.right = this.$refs.vv[index].getBoundingClientRect().right;
      // this.coverList = coverList;
    },
    deleteVideo(video, id) {
      this.dialogVisible = false;
      let params = { videoId: id };
      deleteVideo(params).then((res) => {
        console.log(res);
        // this.videoList = this.videoList.filter(v => {
        //   return v != video;
        // });
        // 通知父组件刷新视频列表
        // Message.success("视频删除成功");
        this.$emit("uploadVideo");
      });
    },

    // MediaInfo({ format: 'text' },
    //     (mediainfo) => {
    //     this.getVideoCodes(file,mediainfo)
    //   })

    getVideoCodes(file,mediainfo){
      // const file = fileinput.files[0]
      if (file) {
        // output.value = 'Working…'
        //
        // const getSize = () => file.size

        const readChunk = (chunkSize, offset) =>
          new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.onload = (event) => {
              if (event.target.error) {
                reject(event.target.error)
              }
              resolve(new Uint8Array(event.target.result))
            }
            reader.readAsArrayBuffer(file.slice(offset, offset + chunkSize))
          })

        mediainfo
          .analyzeData(getSize, readChunk)
          .then((result) => {
            // output.value = result
            console.log(result)
          })
          .catch((error) => {
            // output.value = `An error occured:\n${error.stack}`
          })
      }
    }



  },
  mounted() {
    this.projectId = getProjectId();
    this.addForm.uploadToProject = this.projectId;
    this.videoList.forEach((item, index, array) => {
      //执行代码
      this.spriteStyle[index] =
        "background-position: 0px 0px;" +
        "background-image: url(" +
        item.sprite +
        "); " +
        "background-size: " +
        244 * item.spriteLen +
        "px 126px;" +
        "width: 100%; " +
        "position: absolute;" +
        "opacity:0;";
    });
  },
  watch: {
    videoList(val, newval) {
      console.log("newval", newval);
      val.forEach((item, index, array) => {
        //执行代码
        console.log("item", index, "in val", item);
        this.spriteStyle[index] =
          "background-position: 0px 0px;" +
          " background-image: url(" +
          item.sprite +
          "); " +
          "width: 100%; " +
          "position: absolute;" +
          "opacity:0;" +
          "background-size: " +
          244 * item.spriteLen +
          "px 126px;";
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.video-list-box {
  height: 70%;
  position: relative;
}
.video-list {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(4, 1fr);

  grid-row-gap: 20px;
  grid-column-gap: 50px;
  padding: 20px 50px;
}
.video-upload {
  width: 244px;
  height: 177px;
  border-radius: 10px;
  padding: 1em;
  background-image: url("../../../static/images/border.png");
  background-size: 100% 100%;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}

.video-item {
  position: relative;
  border-radius: 10px;
  border: 1px solid #333;
  overflow: hidden;
  width: 244px;
  height: 177px;
}
.video-item .video-duration {
  position: absolute;
  bottom: 52px;
  right: 0px;
  color: #fff;
  z-index: 5;
  width: 48px;
  text-align: center;
  background-color: #333;
  font-size: 10px;
  opacity: 0.9;
}
.video-item .video-cover-div {
  height: 126px;
  width: 100%;
  overflow: hidden;
  background-color: #454545;
  z-index: 7;
}
.video-cover {
  width: 100%;
  height: 100%;
  cursor: pointer;
  transition: all 0.5s ease-in-out;
}
.video-cover-sprite {
  /*width: 100%;*/
  /*width: auto;*/
  height: 126px;
  position: absolute;
  z-index: 6;
}

.video-item .video-description {
  height: 44px;
  margin-top: 2px;
  margin-left: 5px;
  white-space: nowrap;
  overflow: hidden;
  font-size: 16px;
  text-overflow: ellipsis;
}
.comment-num {
  position: absolute;
  bottom: 30px;
  right: 0px;
  display: flex;
  align-items: center;
  margin-right: 5px;
  font-size: 12px;
}
.comment-num span {
  font-size: 9px;
  color: #333;
}
.video-time {
  font-size: 9px;
  color: #333;
  position: absolute;
  bottom: 5px;
  left: 5px;
}

.require-more {
  transform: rotate(90deg);
  position: absolute;
  right: 10px;
  bottom: 10px;
  cursor: pointer;
}

.upload-icon {
  font-size: 100px;
  color: rgb(134, 161, 236);
}
.Upload_pictures {
  margin-top: 10px;
  font-weight: bold;
}
/* 上传视频 */
.no-submit {
  position: relative;
  width: 150px;
  text-align: center;
}
.bg-block {
  border-radius: 4px;
  border: 1.5px dashed #00a0e9;
  padding: 30px 0;
  font-size: 13px;
  color: #00a0e9;
}
.card-input {
  cursor: pointer;
  font-size: 0;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  outline: none;
  background-color: transparent;
  opacity: 0;
}
.file-upload {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  justify-content: center;
  align-items: center;
  .video-upload-else {
    padding: 35px 0 15px 0;
    width: 350px;
    height: 250px;
    border-radius: 10px;
    background-image: url("../../../static/images/border.png");
    background-size: 100% 100%;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    align-items: center;
    .upload-icon {
      font-size: 100px;
      color: rgb(134, 161, 236);
    }
    .Upload_pictures {
      margin-top: 10px;
      font-weight: bold;
    }
  }
}
</style>
