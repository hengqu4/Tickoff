<template>
  <div id="meet">
    <a-layout-header class="user">
      <div class="title">
        {{ this.videoInfo.videoName }}
      </div>
      <div class="message">
        <!-- <el-button @click="finishReview()">完成审阅</el-button> -->
        <a-avatar :src="avatar" size="large" />
      </div>
    </a-layout-header>
    <!--  视频区 -->
    <div class="left">
      <div class="videos">
        <video-player
          class="video"
          width="60%"
          ref="videoPlayer"
          :playsinline="true"
          :options="playerOptions"
          @play="onPlayerPlay($event)"
          @pause="onPlayerPause($event)"
        />
        <Draw
          class="draw"
          ref="draw"
          style="position: absolute"
          v-bind:Size="videoSize"
          v-if="isDraw == true"
          v-on:getImg="getImg"
        >
        </Draw>
        <div
          :style="
            `width:${this.videoSize[0]}px;height:${this.videoSize[1]}px;position: absolute`
          "
        >
          <img
            v-cloak
            v-if="isCheckComment"
            :src="currentImageUrl"
            style="width: 100%;"
          />
        </div>
        <div
          class="mask"
          v-if="canControl == false"
          :style="
            `width:${this.videoSize[0]}px;height:${this.videoSize[1]}px;position: absolute`
          "
        ></div>
      </div>
      <!--   评论框-->
      <div class="input">
        <textarea
          v-if="canComment == true"
          class="text"
          placeholder="在这写批注"
          v-on:keyup="isEmpty()"
          v-model="commentContent"
        ></textarea>
        <textarea
          v-else
          class="text"
          placeholder="在这写批注"
          v-on:keyup="isEmpty()"
          v-model="commentContent"
          disabled="disabled"
        ></textarea>
        <div class="tools">
          <!-- <a-switch @change="changeRtcState" /> -->
          <a-icon
            type="close-circle"
            theme="twoTone"
            style="margin-right: 27px;cursor:pointer;font-size:20px"
            @click="cancelDraw"
          />
          <a-icon
            type="delete"
            theme="twoTone"
            style="margin-right: 20px;cursor:pointer;font-size:20px"
            @click="cleanDraw"
          />
          <a-icon
            type="edit"
            theme="twoTone"
            style="margin-right: 10px;cursor:pointer;font-size:20px"
            @click="draw"
          />
          <a-button
            class="submit"
            type="primary"
            v-if="textEmpty == false"
            @click="submitComment()"
          >
            提交
          </a-button>
          <a-button class="submit" type="primary" v-else disabled>
            提交
          </a-button>
        </div>
      </div>
    </div>
    <!--  批注区 -->
    <div class="commant">
      <a-menu class="commantHead" v-model="current" mode="horizontal">
        <a-menu-item key="commant"> 批注</a-menu-item>
        <a-menu-item key="fileinfo"> 文件信息</a-menu-item>
      </a-menu>
      <div class="commantCards" v-if="current == 'commant'">
        <comment-list
          :commentList="comments"
          @deleteComment="refreshComment"
          @replyComment="refreshComment"
          @deleteReply="refreshComment"
          @getPosition="toPosition"
        ></comment-list>
      </div>
      <div class="fileInfo" v-if="current == 'fileinfo'">
        <file-info :videoInfo="videoInfo"></file-info>
      </div>
    </div>
  </div>
</template>

<script>
import Draw from "@/components/review/Draw";
import FileInfo from "@/components/review/FileInfo";
import CommentList from "@/common/list/CommentList";
import { Message } from "element-ui";
import axios from "axios";
import { getUserAndVideo } from "@/api/project";
import { uploadImg, getUserInfo } from "@/api/user";
import { getUserAvatar, getUserId, setVideoId } from "@/utils/cache";
import { getToken } from "@/utils/auth";
import {
  getComments,
  getVideoInfo,
  replyComment,
  deleteVideoComment,
  finishReview,
  createComment,
  deleteReply
} from "@/api/video";

export default {
  components: { Draw, FileInfo, CommentList },
  name: "Review",
  data() {
    return {
      current: ["commant"],
      textEmpty: true,
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0], //播放速度
        autoplay: false, // 如果true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 导致视频一结束就重新开始。
        preload: "auto", // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: "zh-CN",
        aspectRatio: "16:9", // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [
          {
            type: "video/mp4", // 这里的种类支持很多种：基本视频格式、直播、流媒体等，具体可以参看git网址项目
            src:
              '"https://hexo-blog-1258787237.cos.ap-beijing.myqcloud.com/video_review/初音未来.mp4"' // url地址
          }
        ],
        hls: true,
        poster: "", // 你的封面地址
        width: 400, // 播放器宽度
        notSupportedMessage: "此视频暂无法播放，请稍后再试", // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: true // 全屏按钮
        }
      },
      userId: "5fcb46a9872ad7704cb534c1",
      meetingId: "5fcb98682fd62669086c8dad",
      videoId: "",
      videoInfo: {},
      videoSize: [],
      comments: [],
      avatar:
        "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
      commentContent: "",
      isDraw: false,
      imgFile: new FormData(),
      imgUrl: "",
      currentImageUrl: "",
      isCheckComment: false,
      canControl: true,
      canComment: true,
      reply: "爱国"
    };
  },
  methods: {
    refreshComment() {
      this.getComments();
    },
    isEmpty() {
      if (this.commentContent !== "") {
        this.textEmpty = false;
      } else {
        this.textEmpty = true;
      }
    },
    // 获取批注列表
    getComments() {
      getComments(this.videoId).then(res => {
        this.comments = res.data;
        let len = this.comments.length;
        for (var i = 0; i < len; i++) {
          this.comments[i].isShow = false;
        }
      });
    },
    // 添加批注
    submitComment() {
      if (this.isDraw) {
        setTimeout(()=>{
    this.saveImg();
        },200)
    
        this.cleanDraw();
        this.isDraw = false;
      }
       console.log('2');
      // 提交批注 todo
      let params = {
        content: this.commentContent,
        imageUrl: this.imgUrl,
        position: this.player.currentTime()
      };
      createComment(params, this.videoId).then(res => {
        this.getComments();
      });
      this.commentContent = "";
      this.commentPosition = "";
      this.imgUrl = "";
    },
    // 回复批注
    replyComment() {
      let params = {
        commentId: commentId,
        content: this.reply
      };
      replyComment(params, this.videoId).then(res => {
        console.log(res);
      });
    },
    // 删除批注
    deleteComment(commentId) {
      let params = {
        commentId: commentId
      };
      deleteVideoComment(params, this.videoId).then(res => {
        console.log(res);
      });
    },
    // 删除回复的批注
    deleteReply(a, b) {
      deleteReply(this.videoId, a, b).then(res => {
        console.log(res);
      });
    },
    formatSecond(second) {
      // const days = Math.floor(second / 86400);
      const hours = Math.floor((second % 86400) / 3600);
      const minutes = Math.floor(((second % 86400) % 3600) / 60);
      const seconds = Math.floor(((second % 86400) % 3600) % 60);
      let forMatDate = "";
      hours < 10
        ? (forMatDate += "0" + hours.toString() + ":")
        : (forMatDate += hours.toString() + ":");
      minutes < 10
        ? (forMatDate += "0" + minutes.toString() + ":")
        : (forMatDate += minutes.toString() + ":");
      seconds < 10
        ? (forMatDate += "0" + seconds.toString())
        : (forMatDate += seconds.toString());
      // console.log(forMatDate)
      return forMatDate;
    },
    formatTimestamp(second) {
      let temp = parseInt(second);
      var timestamp = new Date(temp * 1000);
      var timestamp1 =
        timestamp.toLocaleDateString().replace(/\//g, "-") +
        " " +
        timestamp.toTimeString().substr(0, 8);
      return timestamp1;
    },
    //完成审阅
    finishReview() {
      let params = {
        reviewResult: 1,
        summary: "继续努力"
      };
      finishReview(params, this.videoId);
    },

    // 点击批注跳转到指定位置
    toPosition: function(position, url) {
      this.player.currentTime(position);
      this.player.pause();
      this.currentImageUrl = url;
      this.isCheckComment = true;
    },
    draw() {
      this.isDraw = true;
      this.player.pause();
    },
    cancelDraw() {
      this.isDraw = false;
    },
    cleanDraw() {
      this.$refs.draw.clearPalette();
    },
    saveImg() {
      this.$refs.draw.savePalette("png");
    },
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
    },
    getImg: function(lastBase64) {
      this.imgFile.append("image", this.dataURLtoFile(lastBase64, "img"));
      this.request();
    },
    // 上传图片
    request() {
      let headerObj = {
        Authorization: getToken(),
        "Content-Type": "multipart/form-data"
      };
      var self = this;
      axios
        .post(
          "https://api.video-review.top:1314/api/uploadImg/",
          this.imgFile,
          {
            headers: headerObj
          }
        ).then(res=>{
          console.log(res);
          
      if (res.status == 200) {
        console.log('1');
        
        this.imgFile = new FormData();
        this.imgUrl = res.data.url;

      }
        })
    },
    // 获取视频信息
    getVideoInfo() {
      getVideoInfo(this.videoId).then(res => {
        this.videoInfo = res.data;
        console.log('11111111111111');
        console.log(this.videoInfo);
      });
    },
    // 设置视频封面
    setVideoCover(url) {
      this.player.poster(url);
    },

    // 播放回调
    onPlayerPlay(player) {
      console.log("player play!", player);
      this.isCheckComment= false
    },

    // 暂停回调
    onPlayerPause(player) {
      console.log("player pause!", player);
    },

    getVideoSize() {
      // this.videoSize.videoWidth = this.$refs.videoPlayer.$el.clientWidth;
      this.$set(this.videoSize, 0, this.$refs.videoPlayer.$el.clientWidth);
      // this.videoSize.videoHeight = this.$refs.videoPlayer.$el.clientHeight;
      this.$set(this.videoSize, 1, this.$refs.videoPlayer.$el.clientHeight);
      console.log(this.videoSize); //高度
    }
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player; //自定义播放
    }
  },
  watch: {
    videoSize: {
      handler(newValue, oldValue) {},
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.userId = getUserId();
    this.videoId = this.$route.query.videoId;
    setVideoId(this.videoId);
    this.getVideoInfo();
    this.getComments();
    this.avatar = getUserAvatar();
    let _this = this;
    console.log('222222222');
    
    setTimeout(function() {
      _this.setVideoCover(_this.videoInfo.covers[1]);
      _this.player.src(_this.videoInfo.url);
    }, 300);

    this.getVideoSize();
    let that = this;
    window.onresize = () => {
      that.getVideoSize();
    };
  },
  destroyed() {
    window.onresize = null;
  }
};
</script>
<style scoped>
#meet {
  width: 100%;
  height: 100%;
  background: #dcdee2;
}
.user {
  background: #303133;
  color: #fff;
  align-items: center;
}
.title {
  float: left;
  color: white;
  font-size: 17px;
}
.message {
  margin-right: 14px;
  align-items: center;
  float: right;
}
.selectVideo {
  margin-right: 20px;
  float: left;
  margin-top: 4px;
}
.left {
  background: #dcdee2;
  height: 90.5%;
  width: 75%;
  float: left;
  /*text-align: center;*/
}
.mask {
  z-index: 101;
}
.input {
  float: top;
  display: flex;
  justify-content: center;
  height: 28%;
  margin-top: 14px;
  position: relative;
}
.tools {
  position: absolute;
  bottom: 9%;
  right: 16%;
}
.text {
  padding: 8px;
  width: 70%;
  height: 95%;
  border-radius: 10px;
  font-family: none;
  font-size: 16px;
  outline: none;
  resize: none;
  border: 0;
}
.text:focus {
  /*border: #658bad solid 2px;*/
  /*box-shadow: #888888;*/
  box-shadow: 2px 2px 7px 1px #888888;
}
.commant {
  margin-top: 0;
  background: #dcdee2;
  float: right;
  float: top;
  width: 25%;
  height: 90.5%;
  /*overflow:auto;*/
}
.videos {
  width: 100%;
  /*height:66%;*/
  display: flex;
  justify-content: center;
  margin-top: 14px;
}
.video {
  width: 70%;
  height: 100%;
}

.commantHead {
  background: #dcdee2;
  color: black;
  font-size: 10px;
}
.commantCards {
  /*text-align: center;*/
  height: 90.5%;
  margin-top: 3px;
  overflow: auto;
}

#user {
  float: left;
  /*margin-top: 11px;*/
  /*margin-left: 12px;*/
}
ul {
  margin-bottom: 0.1em;
}

.fileInfo {
  background: #cfd3d694;
  height: max-content;
  float: bottom;
  color: black;
  padding: 18px;
  font-size: 14px;
  font-family: fangsong;
  color: #0b034d;
}
[v-cloak] {
  display: none;
}
</style>
