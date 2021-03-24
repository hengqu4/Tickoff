<template>
  <div
    style="display: flex;flex-direction: column; align-items: center;margin-top: 100px"
  >
    <el-card style="width: 640px">
      <div>账号安全</div>
      <el-form
        ref="accountFormRef"
        :model="accountForm"
        label-width="80px"
        style="margin-left: 100px; width: 400px"
        label-suffix=":"
      >
        <el-form-item label="手机号">
          <el-input v-model="accountForm.mobileNum" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="登陆密码">
          <el-input
            v-model="accountForm.password"
            :disabled="pwddisable"
            show-password
          >
            <el-button slot="append" @click="changePasswordVisible = true"
              >修改</el-button
            >
          </el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="accountForm.mail" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog
      title="修改密码"
      :visible.sync="changePasswordVisible"
      width="40%"
    >

      <el-form :model="password">
        <el-form-item label="手机号">
        <el-input v-model="password.mobileNum" :disabled="true"></el-input>
      </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="password.checkcode">
            <el-button slot="append" @click="getCode" :disabled="disabled">{{content}}</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password.password"></el-input>
        </el-form-item>
        <el-form-item label="密码确认">
          <el-input v-model="password.checkPassword"></el-input>
        </el-form-item>
        <el-button type="primary" @click="changePassword">确定</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from "element-ui";
import { validPhone } from "@/utils/validate.js";
import { setUserMobile, getUserMobile } from "@/utils/cache";
import { resetPassword, getCheckCode } from "@/api/user";
export default {
  name: "Account",
  data() {
    return {
      accountForm: {
        mobileNum: "",
        password: "",
        mail: "1535045887@qq.com"
      },
      password: {
        mobileNum: "",
        password: "",
        checkPassword: "",
        checkcode: ""
      },
      pwddisable: true,
      changePasswordVisible: false,
      content: "获取验证码",
      totalTime: 60,
      disabled: false
    };
  },
  methods: {
    changePassword() {
      resetPassword(this.password).then(res => {
        console.log(res);
        this.accountForm.password = res.data;
      });
    },
    getCode() {
      let params = {
        mobileNum: this.accountForm.mobileNum,
        operation: "reset"
      };
      getCheckCode(params).then(res => {
        console.log(res);
      });
        this.content = "60s后重新发送";
        this.disabled = true;
        let clock = window.setInterval(() => {
          this.totalTime--;
          this.content = this.totalTime + "s后重新发送";
          if (this.totalTime < 0) {
            //当倒计时小于0时清除定时器
            window.clearInterval(clock);
            this.content = "重新发送验证码";
            this.totalTime = 60;
            this.disabled = false;
          }
        }, 1000);
      
    }
  },
  mounted() {
    this.accountForm.mobileNum = getUserMobile();
    this.accountForm.mobileNum = getUserMobile();
  }
};
</script>

<style scoped></style>
