<template>
  <div class="message-list">
    <vue-scroll :ops="ops">
      <h2 style="font-size: 16px">消息</h2>
      <div class="divider"></div>
      <el-row
        v-for="message in messageList.slice(0, this.page>1?messageListLength:(messageListLength>5?5:messageListLength))"
        :key="message.messageId"
        class="message-item"
        @click.native="handleRead(message.messageId)"
      >
        <el-col :span="6">
          <el-avatar :src="message.avatar"></el-avatar>
        </el-col>
        <el-col :span="18">
          <div class="message-content">
            {{ message.sentence }}
            <span class="message-time">{{ message.date | dataFilter }}</span>
          </div>
          <div class="message-handle">
            <div v-if="message.hasProcess === 1 || message.type != 3">
              已处理
            </div>
            <div v-if="message.hasProcess === 0 && message.type === 3">
              <div class="message-word">附加信息: {{ message.word }}</div>
              <div class="choose-button">
                <el-button
                  @click="pocessVisit(message.messageId, message.projectId, 1)"
                  size="mini"
                  plain
                  >同意</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="pocessVisit(message.messageId, message.projectId, 0)"
                  plain
                  >拒绝</el-button
                >
              </div>
            </div>
          </div>
        </el-col>
        <div class="message-item-isread" v-if="message.hasRead === 0"></div>
      </el-row>
      <div class="divider"></div>
      <div
        v-if="showMore2"
        class="get-more"
        @click="showMoreMess()"
      >
        显示更多
      </div>
    </vue-scroll>
  </div>
</template>

<script>
import {
  readMessage,
  getMesByPage, } from "@/api/user";
import { joinProject } from "@/api/project";
export default {
  props: {
    messageList: {
      type: Array,
      required: true,
    },
    showMore: {
      type: Boolean,
      required: true,
    }
  },
  data() {
    return {

      ops: {
        vuescroll: {
          checkShiftKey: true,
          locking: false,
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
          disable: false,
        },
      },
      page:1,
      showMore2:true,
    };
  },
  methods: {
    pocessVisit(messageId, projectId, n) {
      let isAgree = n === 1;
      let params = {
        isAgree: isAgree,
        messageId: messageId,
      };
      joinProject(params, projectId).then((res) => {
        this.$emit("refreshProject");
      });
    },
    handleRead(id) {
      readMessage(id).then((res) => {
        this.$emit("refreshMessage");
      });
    },
    showMoreMess(){
      this.page = this.page+1
      getMesByPage(this.page).then((res) => {
        res.data.forEach((item) => {
          //执行代码
          this.messageList.push(item);
        });
        this.showMore2 = (res.hasNextPage);
      });
    }
  },
  mounted() {
    this.showMore2 = this.showMore;
  },
  computed: {
    messageListLength() {
      return this.messageList.length;
    },
  },
};
</script>

<style lang="scss" scoped>
.message-list {
  height: 380px;
  overflow-y: auto;
}
.message-item {
  height: 100px;
  position: relative;
  width: 92%;
}
.message-item-isread {
  height: 8px;
  width: 8px;
  border-radius: 50%;
  background-color: red;
  position: absolute;
  top: -8px;
  right: 0px;
}
.message-content {
  min-height: 50px;
  position: relative;
  cursor: pointer;
  padding-bottom: 16px;
}
.message-time {
  position: absolute;
  bottom: 0px;
  right: 0;
  font-size: 8px;
}

.get-more {
  width: 100%;
  text-align: center;
  cursor: pointer;
}
.divider {
  width: 100%;
  height: 1px;
  border: 1px solid #f0f0f0;
  margin: 4px 0 12px 0;
}

.not-read {
  position: absolute;
  top: -5px;
  right: -5px;
  height: 14px;
  width: 14px;
  background-color: red;
  border-radius: 50%;
  font-size: 9px;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
