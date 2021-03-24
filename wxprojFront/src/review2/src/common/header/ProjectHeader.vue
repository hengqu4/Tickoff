<template>
  <el-header class="home-header">
    <div class="header-logo" @click="goHome">
      <el-image
        class="header-title"
        src="../../../static/images/logo.png"
      ></el-image>
      <div class="header-title2">视频审阅平台</div>
    </div>
    <div class="header-right">
      <el-popover placement="bottom-start" width="250" trigger="click">
        <message-list
          :messageList="messageList"
          :showMore="showMore"
          @refreshMessage="refreshMessageList"
          @refreshProject="refreshProject"
        ></message-list>
        <i
          class="el-icon-message-solid message-icon"
          style="font-size:28px;color:#333"
          slot="reference"
          @click="go()"
        >
          <div class="not-read" v-if="isRead">
            {{ userInfo.messageToRead }}
          </div>
        </i>
      </el-popover>

      <el-tooltip
        class="item"
        effect="light"
        :content="userInfo.username"
        placement="bottom"
      >
        <div class="header-user" @click="goUserInfo">
          <el-avatar size="medium" :src="userInfo.avatar"></el-avatar>
        </div>
      </el-tooltip>
    </div>
  </el-header>
</template>

<script>
import { Message } from "element-ui";
import {
  getMessages,
  processVisit,
  getUserInfo,
  unReadMessage,
  getMesByPage,
  readMessage
} from "@/api/user";
import { getUserId } from "@/utils/cache";
import MessageList from "../list/MessageList.vue";

export default {
  name: "ProjectHeader",
  components: {
    MessageList
  },
  data() {
    return {
      messageList: [],
      userInfo: {},
      isRead: false,
      showMore: true,
    };
  },
  methods: {
    refreshProject(){
      this.$emit('refreshProject')
    },
    goUserInfo() {
      this.$router.push("/user/userInfo");
    },
    goPersonal() {
      this.$router.push("/user");
    },
    goHome() {
      this.$router.push("/");
    },
    getMessageList() {
      getMesByPage(1).then((res) => {
        this.messageList = res.data;
        console.log(res.data);
        this.showMore = res.hasNextPage;
      });
    },

    pocessVisit(messageId, projectId, n) {
      let isAgree = n === 1;
      let params = {
        isAgree: isAgree,
        messageId: messageId
      };
      processVisit(params).then(res => {
        console.log(res);
      });
    },
    getInfo() {
      let userId = getUserId();
      getUserInfo(userId).then(res => {
        this.userInfo = res.data;
        if (res.data.messageToRead > 0) {
          this.isRead = true;
        }
      });
      unReadMessage().then(res=>{
        this.userInfo.messageToRead = res.data.num;
      });
    },
    refreshMessageList() {
      console.log("refresh");
      this.getMessageList()
    },
    go() {
      this.isRead = false;
    }
  },
  mounted() {
    this.getMessageList();
    this.getInfo();
  }
};
</script>
<style lang="scss" scoped>
.home-header {
  top: 0;
  width: 100%;
  display: fixed;
  z-index: 99;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  align-items: center;
  background-color: #fff;
  .header-logo {
    display: flex;
    .header-title {
      margin-left: -3px;
      font-weight: bold;
      color: #333;
      cursor: pointer;
      height: 46px;
      width: 100px;
    }
    .header-title2 {
      font-family: Arial, Helvetica, sans-serif;
      font-size: 28px;
      cursor: pointer;
    }
  }
  .header-right {
    display: flex;
    flex: 1;
    justify-content: flex-end;
    align-items: center;
  }
}
.message-icon {
  position: relative;
  cursor: pointer;
}
.header-user {
  margin-left: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}
.message-list {
  height: 380px;
  overflow-y: auto;
}
.message-item {
  height: 100px;
  position: relative;
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
  height: 50px;
  position: relative;
  cursor: pointer;
}
.message-time {
  position: absolute;
  bottom: 0;
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
