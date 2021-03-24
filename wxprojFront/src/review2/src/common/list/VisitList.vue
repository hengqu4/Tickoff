<template>
  <div class="visit-list">
    <vue-scroll :ops="ops">
      <el-row
        type="flex"
        class="visit-user"
        v-for="user in visitUser"
        :key="user.userId"
      >
        <el-col :span="4" class="visit-user-col">
          <el-avatar
            style="width: 35px; height: 35px; object-fit: scale-down"
            :src="user.avatar"
          ></el-avatar>
        </el-col>
        <el-col :offset="5" :span="5" class="visit-user-col">
          <span>{{ user.username }}</span>
        </el-col>
<!--        <el-col :offset="3" :span="6" class="visit-user-col">-->
<!--          <span>{{ user.mobileNum }}</span>-->
<!--        </el-col>-->
        <el-dialog
          title="邀请成员"
          :visible.sync="addInviteWordVisible"
          width="30%"
          append-to-body
        >
          <el-input
            placeholder="请输入附加消息"
            v-model="user.visitWord"
            prefix-icon="el-icon-info"
          >
          </el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="addInviteWordVisible = false">取 消</el-button>
            <el-button
              type="primary"
              @click="inviteUserbyId(user.visitWord)"
              >确 定</el-button
            >
          </span>
        </el-dialog>
        <el-col :offset="3" :span="4" class="visit-user-col">
          <el-button @click="clickToInvite(user.userId)" type="primary" plain
            >邀请</el-button
          >
        </el-col>
      </el-row>
    </vue-scroll>
  </div>
</template>

<script>
import { inviteUser } from "@/api/project";
import { getProjectId } from "@/utils/cache";
import { Message } from "element-ui";
export default {
  props: {
    visitUser: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      addWordInput: "",
      addInviteWordVisible: false,
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
      inviteUserForm: {
        avatar: "",
        userName: "",
        mobile: "",
        userId: "",
        word: "",
      },
    };
  },
  methods: {
    clickToInvite(id){
      console.log(id);
      this.inviteUserForm.userId = id;
      this.addInviteWordVisible = true;
    },
    inviteUserbyId(word) {
      if (typeof(word) == "undefined"){
        this.inviteUserForm.word = "";
      } else {
        this.inviteUserForm.word = word;
      }
      console.log(word);
      // console.log(this.inviteUserForm);
      inviteUser(getProjectId(), this.inviteUserForm).then((res) => {
        // Message.success("用户邀请成功");
      });
      this.addInviteWordVisible = false;

    },
    addInviteWord() {
      this.addInviteWordVisible = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.visit-list {
  margin-top: 10px;
  height: 200px;
  overflow-y: auto;
}
.visit-user {
  margin-bottom: 3px;
}
.visit-user-col {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
