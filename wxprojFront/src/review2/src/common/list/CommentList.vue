<template>
  <div class="comment-list">
    <vue-scroll :ops="ops">
      <div
        class="comment-item"
        v-for="(comment, index) in commentList"
        :key="comment.commentId"
      >
        <div class="comment-user">
          <el-avatar class="comment-avatar" :src="comment.avatar"></el-avatar>
          <div class="comment-name">
            {{ comment.fromName }}
          </div>
          <div class="comment-date">
            {{ (comment.date * 1000) | dateFormat }}
          </div>
        </div>
        <div class="comment-container">
          <div
            class="comment-position pointer"
            @click.stop="getPosition(comment.position, comment.imageUrl)"
          >
            {{ comment.position }}
          </div>
          <div class="comment-content">{{ comment.content }}</div>
          <div class="comment-operate" v-if="replyIndex != index">
            <span class="pointer" @click="replyIndex = index">
              回复
            </span>
            <el-popconfirm
              title="确认要删除批注吗？"
              @confirm="deleteComment(comment.commentId)"
            >
              <span class="pointer" slot="reference">删除</span>
            </el-popconfirm>
          </div>
          <div v-else class="reply-box">
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入回复信息"
              v-model="replyContent"
            >
            </el-input>
            <div class="reply-operate clearfix">
              <span
                class="reply-sent pointer fr"
                @click="replyComment(comment.commentId)"
                >发送</span
              >
              <span class="reply-cannel pointer fr" @click="replyIndex = -1"
                >取消</span
              >
            </div>
          </div>
        </div>

        <div class="reply-list">
          <div
            v-for="(reply, replyIndex) in comment.replies"
            :key="reply.replyId"
            class="reply-item"
          >
            <div class="reply-user">
              <el-avatar class="reply-avatar" :src="reply.avatar"></el-avatar>
              <div class="reply-name">
                {{ reply.fromName }}
              </div>
              <div class="reply-date">
                {{ (reply.date * 1000) | dateFormat }}
              </div>
            </div>
            <div class="reply-container">
              <div class="reply-content">{{ reply.content }}</div>
              <div class="reply-operate" v-if="replyIndex2 != replyIndex">
                <span class="pointer" @click="replyIndex2 = replyIndex">
                  回复</span
                >
                <el-popconfirm
                  title="这是一段内容确定删除吗？"
                  @confirm="deleteReply(comment.commentId, reply.replyId)"
                >
                  <span class="pointer " slot="reference">删除</span>
                </el-popconfirm>
              </div>
              <div v-else class="reply-box">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入回复信息"
                  v-model="replyContent2"
                >
                </el-input>
                <div class="reply-operate2 clearfix">
                  <span
                    class="reply-sent pointer fr"
                    @click="
                      replyReply(
                        comment.commentId,
                        reply.replyId,
                        reply.fromName
                      )
                    "
                    >发送</span
                  >
                  <span
                    class="reply-cannel pointer fr"
                    @click="replyIndex2 = -1"
                    >取消</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </vue-scroll>
  </div>
</template>

<script>
import { Message } from "element-ui";
import { deleteVideoComment, replyComment, deleteReply } from "@/api/video.js";
import { getVideoId } from "@/utils/cache";
export default {
  props: {
    commentList: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      replyIndex: -1,
      replyIndex2: -1,
      ops: {
        vuescroll: {
          checkShiftKey: true,
          locking: false
        },
        bar: {
          showDelay: 500,
          onlyShowBarOnScroll: true,
          keepShow: true,
          background: "#c1c1c1",
          opacity: 1,
          hoverStyle: false,
          specifyBorderRadius: false,
          minSize: 0,
          size: "6px",
          disable: false
        }
      },
      replyContent: "",
      replyContent2: ""
    };
  },
  methods: {
    // 删除批注
    deleteComment(id) {
      deleteVideoComment({ commentId: id }, getVideoId()).then(res => {
        console.log("delete");

        // if (res.result == 1) {
        //   Message.success("删除批注成功");
        // }
        this.$emit("deleteComment");
      });
    },
    // 回复批注
    replyComment(commentId) {
      let params = {
        commentId: commentId,
        content: this.replyContent
      };
      replyComment(params, getVideoId()).then(res => {
        // 通知父组件批注列表
        this.$emit("replyComment");
      });
      this.replyContent = "";
      this.replyIndex = -1;
    },
    // 回复 回复
    replyReply(commentId, replyId, name) {
      let content = "回复@" + name + " ：" + this.replyContent2;
      let params = {
        commentId: commentId,
        replyId: replyId,
        content: content
      };
      replyComment(params, getVideoId()).then(res => {
        // 通知父组件批注列表
        this.$emit("replyComment");
      });
      this.replyContent2 = "";
      this.replyIndex2 = -1;
    },
    // 删除批注回复
    deleteReply(commentId, replyId) {
      deleteReply(getVideoId(), commentId, replyId).then(res => {
        // if (res.result == 1) {
        //   Message.success("删除批注成功");
        // }
        // 通知父组件批注列表
        this.$emit("deleteReply");
      });
    },
    // 显示回复列表
    showReply(index) {
      this.selectIndex = index;
      // this.$set(this.commentList[index], "isShow", true);
      // console.log(this.commentList);
    },
    // 跳转视频某一帧
    getPosition(t, url) {
      console.log(t, url);
      this.$emit("getPosition", t, url);
    }
  },
  mounted() {
    for (var i = 0; i < this.commentList.length; i++) {
      this.reply[i] = "";
    }
  }
};
</script>

<style lang="scss" scoped>
.comment-list {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.comment-item {
  padding: 8px 14px;
  min-height: 140px;
  background-color: #dcdee2;
  margin-bottom: 5px;
  border: 1px solid #fff;
  .comment-user {
    display: flex;
    padding-bottom: 2px;
    border-bottom: 1px solid #c0c0c0;
    .comment-avatar {
      height: 35px;
      width: 35px;
    }
    .comment-name {
      margin-left: 15px;
      margin-top: 10px;
      color: black;
      font-size: 9px;
      font-family: -webkit-pictograph;
    }
    .comment-date {
      font-size: 9px;
      margin-left: 15px;
      margin-top: 13px;
    }
  }

  .comment-container {
    position: relative;
    min-height: 80px;
    .comment-position {
      color: #4189d3;
      max-width: 100px;
    }
    .comment-content {
      min-height: 60px;
      color: #111;
    }
    .comment-operate {
      position: absolute;
      bottom: 4px;
      right: 10px;
      span {
        color: #4189d3;
      }
    }
    .reply-box {
      min-height: 60px;
      position: relative;
      .reply-operate {
        width: 100%;
        margin-bottom: 4px;
        .reply-cannel {
          color: #c0c0c0;
          margin-right: 8px;
        }
        .reply-sent {
          color: #333;
          margin-right: 4px;
        }
      }
    }
  }
}
.reply-list {
  margin-top: 6px;
}
.reply-btn {
  float: right;
  margin-right: 10px;
  background-color: #fff;
  border-radius: 10px;

  padding: 2px 10px;
}
.reply-item {
  padding: 3px 14px;
  min-height: 120px;
  margin-top: 2px;
  background-color: #fafafa;
  margin-bottom: 2px;
  margin-left: 8px;
  border-radius: 5px;

  .reply-user {
    display: flex;
    padding-bottom: 2px;
    border-bottom: 1px solid #c0c0c0;
    .reply-avatar {
      height: 35px;
      width: 35px;
    }
    .reply-name {
      margin-left: 15px;
      margin-top: 10px;
      color: black;
      font-size: 9px;
      font-family: -webkit-pictograph;
    }
    .reply-date {
      margin-left: 15px;
      margin-top: 13px;
      font-size: 9px;
    }
  }
  .reply-container {
    padding-top: 4px;
    position: relative;
    min-height: 80px;
    .reply-content {
      min-height: 60px;
      color: #111;
    }
    .reply-operate {
      position: absolute;
      bottom: 0;
      right: 10px;
      span {
        color: #4189d3;
      }
    }
    .reply-box {
      min-height: 60px;

      .reply-operate2 {
        width: 100%;
        margin-bottom: 4px;
        .reply-cannel {
          color: #c0c0c0;
          margin-right: 8px;
        }
        .reply-sent {
          color: #333;
          margin-right: 4px;
        }
      }
    }
  }
}
</style>
