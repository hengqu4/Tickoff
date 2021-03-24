<template>
  <div class="login-container">
    <!-- 忘记密码弹出框 -->
    <el-dialog title="修改密码" :visible.sync="newPassVisible" width="30%">
      <el-form
        ref="ForgetPasswordForm"
        :model="ForgetPasswordForm"
        :rules="ForgetPasswordFormRules"
      >
        <el-form-item prop="mobileNum">
          <el-input
            v-model="ForgetPasswordForm.mobileNum"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        <el-form-item prop="checkcode">
          <el-input
            v-model="ForgetPasswordForm.checkcode"
            prefix-icon="el-icon-lock"
          >
            <el-button
              slot="append"
              @click="getForgetPasswordcheckcode('reset')"
              :disabled="forgetPassDisabled"
            >
              {{ content }}</el-button
            >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ForgetPasswordForm.password"
            prefix-icon="el-icon-lock"
            placeholder="输入新密码"
            type="password"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="checkPassword">
          <el-input
            v-model="ForgetPasswordForm.checkPassword"
            prefix-icon="el-icon-lock"
            placeholder="确认新密码"
            type="password"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelForgetPassword">取 消</el-button>
        <el-button type="primary" @click="confirmForgetPassword"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <el-card class="login-box" v-if="loginOrRegister">
      <!-- 头像 -->
      <div class="avatar-box">
        <img :src="logo" alt="" />
      </div>
      <!-- 表单 -->
      <el-form
        ref="LoginFormRef"
        :model="loginForm"
        label-width="0"
        :rules="LoginFormRules"
        class="login-form"
      >
        <el-tabs
          v-model="activeIndex"
          :before-leave="beforeTabLeave"
          :tab-position="'top'"
          @tab-click="tabClicked"
          class="login-tabs"
        >
          <el-tab-pane label="手机号登陆" name="0">
            <el-form-item prop="mobileNum">
              <el-input
                v-model="loginForm.mobileNum"
                prefix-icon="el-icon-phone"
                @blur="getUserAvatar"
              ></el-input>
            </el-form-item>
            <el-form-item prop="checkcode">
              <el-input
                v-model="loginForm.checkcode"
                prefix-icon="el-icon-lock"
              >
                <el-button
                  slot="append"
                  @click="getCheckcode('login')"
                  :disabled="loginDisabled"
                >
                  {{ content }}</el-button
                ></el-input
              >
            </el-form-item>
            <el-form-item class="btns">
              <el-button type="primary" @click="login">登录</el-button>
              <el-button type="info" @click="forgetPassword"
                >忘记密码</el-button
              >
              <el-button type="info" @click="goRegister">注册</el-button>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="账号密码登陆" name="1">
            <el-form-item prop="mobileNum">
              <el-input
                v-model="loginForm.mobileNum"
                prefix-icon="el-icon-phone"
                @blur="getUserAvatar"
              ></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                prefix-icon="el-icon-lock"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item class="btns">
              <el-button type="primary" @click="login">登录</el-button>
              <el-button type="info" @click="forgetPassword"
                >忘记密码</el-button
              >
              <el-button type="info" @click="goRegister">注册</el-button>
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>
    <el-card class="register-box" v-else>
      <div style="cursor: pointer" @click="goLogin">
        <i class="el-icon-arrow-left"></i>登陆
      </div>

      <el-form
        ref="RegisterFormRef"
        :model="registerForm"
        status-icon
        label-width="100px"
        :rules="RegisterFormRules"
        class="login-form"
      >
        <el-form-item label="账号" prop="username">
          <el-input
            v-model="registerForm.username"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            prefix-icon="el-icon-lock"
            type="password"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input
            v-model="registerForm.checkPassword"
            prefix-icon="el-icon-lock"
            type="password"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobileNum">
          <el-input
            v-model="registerForm.mobileNum"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="checkCode">
          <el-input v-model="registerForm.checkCode" prefix-icon="el-icon-lock">
            <el-button
              slot="append"
              @click="getRegistercheckcode('register')"
              :disabled="registerDisabled"
              >{{ content }}</el-button
            >
          </el-input>
        </el-form-item>
        <el-form-item class="register-btn">
          <el-button type="primary" @click="register">注册</el-button>
          <el-button type="info" @click="resetRegisterForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
  login,
  getUserAvatar,
  register,
  getCheckCode,
  getUserInfo,
  resetPassword,
} from "@/api/user.js";
import { validPhone } from "@/utils/validate.js";
import { Message } from "element-ui";
import {
  setUserId,
  setUserAvatar,
  setUserMobile,
  setUserName,
  removeProjectId,
  removeProjectName,
  removeVideoId,
  removeUserId,
  removeUserName,
  removeUserMobile,
} from "@/utils/cache.js";

import { setToken, removeToken } from "@/utils/auth.js";
export default {
  name: "Login",
  data() {
    var validateCheckPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空"));
      } else {
        if (validPhone(value)) {
          callback();
        } else {
          return callback(new Error("请输入正确的手机号"));
        }
      }
    };
    var validateConfirmPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ForgetPasswordForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      loginOrRegister: true,
      registerDisabled: false,
      loginDisabled: false,
      forgetPassDisabled: false,
      newPassVisible: false,
      ForgetPasswordForm: {
        mobileNum: "",
        checkcode: "",
        password: "",
        checkPassword: "",
      },
      formLabelWidth: "120px",
      activeIndex: "0",
      loginForm: {
        mobileNum: "",
        password: "",
        checkcode: "",
      },
      registerForm: {
        username: "",
        password: "",
        checkPassword: "",
        mobileNum: "",
        checkCode: "",
      },
      RegisterFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3 到 10 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 15,
            message: "密码长度在6 到 15 个字符",
            trigger: "blur",
          },
        ],
        checkPassword: [
          { required: true, validator: validateCheckPassword, trigger: "blur" },
          {
            min: 6,
            max: 15,
            message: "密码长度在6 到 15 个字符",
            trigger: "blur",
          },
        ],
        mobileNum: [{ required: true, validator: checkPhone, trigger: "blur" }],
        checkCode: [{ required: true, trigger: "blur" }],
      },
      LoginFormRules: {
        mobileNum: [{ required: true, validator: checkPhone, trigger: "blur" }],
        password: [
          { required: false, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "密码长度在6 到 15 个字符",
            trigger: "blur",
          },
        ],
        checkCode: [{ required: false, trigger: "blur" }],
      },
      ForgetPasswordFormRules: {
        mobileNum: [{ required: true, validator: checkPhone, trigger: "blur" }],
        password: [
          { required: false, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "密码长度在6 到 15 个字符",
            trigger: "blur",
          },
        ],
        checkPassword: [
          { required: false, validator: validateConfirmPass, trigger: "blur" },
        ],
        checkcode: [{ required: false, trigger: "blur" }],
      },
      logo: "",
      content: "获取验证码",
      totalTime: 60,
    };
  },
  methods: {
    getUserAvatar() {
      let params = { mobileNum: this.loginForm.mobileNum };
      getUserAvatar(params).then((res) => {
        this.logo = res.data.avatarUrl;
      });
    },
    forgetPassword() {
      this.newPassVisible = true;
    },
    cancelForgetPassword() {
      this.newPassVisible = false;
      this.resetForgetPasswordForm();
    },
    confirmForgetPassword() {
      this.$refs["ForgetPasswordForm"].validate((valid) => {
        if (valid) {
          resetPassword(this.ForgetPasswordForm).then((res) => {
            console.log(res);
            // Message.success("修改密码成功");
          });
          this.newPassVisible = false;
        } else {
          return false;
        }
      });
    },
    resetForgetPasswordForm() {
      this.$refs['ForgetPasswordForm'].resetFields();
    },
    resetLoginForm() {
      // 通过ref获取表单, resetFields重置表单
      this.$refs.LoginFormRef.resetFields();
    },
    resetRegisterForm() {
      this.$refs.RegisterFormRef.resetFields();
    },
    tabClicked(e) {},
    beforeTabLeave(activeName, oldActiveName) {
      // console.log(activeName, oldActiveName);
    },
    goRegister() {
      this.content = "获取验证码";
      this.totalTime = 60;
      this.loginOrRegister = false;
    },
    goLogin() {
      this.content = "获取验证码";
      this.totalTime = 60;
      this.loginOrRegister = true;
    },
    login() {
      // 通过调用validate 对表单进行校验
      this.$refs["LoginFormRef"].validate((valid) => {
        if (valid) {
          login(this.loginForm).then((res) => {
            console.log(res);
            // Message.success("登录成功");
            // 将登录成功之后的保存token,和用户信息
            setToken(res.token);
            let userId = res.userId;
            setUserId(userId);
            getUserInfo(userId).then((res) => {
              console.log(res);
              setUserMobile(res.data.mobileNum);
              setUserName(res.data.username);
              setUserAvatar(res.data.avatar);
            });
            this.$router.push("/");
          });
          this.resetLoginForm();
        } else {
          return false;
        }
      });
    },
    register() {
      this.$refs["RegisterFormRef"].validate((valid) => {
        if (valid) {
          register(this.registerForm).then((res) => {
            // Message.success("注册成功");
            this.loginOrRegister = true;
          });
          // this.loginForm.mobileNum = this.registerForm.mobileNum;
          // this.resetRegisterForm();

        } else {
          return false;
        }
                  let params = {
            mobileNum:this.registerForm.mobileNum,
            password:this.registerForm.password
          }
          login(params).then((res) => {
            console.log(res);
            // Message.success("登录成功");
            // 将登录成功之后的保存token,和用户信息
            setToken(res.token);
            let userId = res.userId;
            setUserId(userId);
            getUserInfo(userId).then((res) => {
              console.log(res);
              setUserMobile(res.data.mobileNum);
              setUserName(res.data.username);
              setUserAvatar(res.data.avatar);
            });
            this.$router.push("/");
          });
      });
    },
    getCheckcode(operation) {
      let params = {
        mobileNum: this.loginForm.mobileNum,
        operation: operation,
      };
      getCheckCode(params).then((res) => {});
      if (validPhone(this.loginForm.mobileNum)) {
        this.loginDisabled = true;
        this.content = "60s后重新发送";
        let clock = window.setInterval(() => {
          this.totalTime--;
          this.content = this.totalTime + "s后重新发送";
          if (this.totalTime < 0) {
            //当倒计时小于0时清除定时器
            window.clearInterval(clock);
            this.content = "重新发送验证码";
            this.totalTime = 60;
            this.loginDisabled = false;
          }
        }, 1000);
      }
    },
    getRegistercheckcode(operation) {
      let params = {
        mobileNum: this.registerForm.mobileNum,
        operation: operation,
      };
      getCheckCode(params).then((res) => {});
      if (validPhone(this.registerForm.mobileNum)) {
        this.registerDisabled = true;
        this.content = "60s后重新发送";
        let clock = window.setInterval(() => {
          this.totalTime--;
          this.content = this.totalTime + "s后重新发送";
          if (this.totalTime < 0) {
            //当倒计时小于0时清除定时器
            window.clearInterval(clock);
            this.content = "重新发送验证码";
            this.totalTime = 60;
            this.registerDisabled = false;
          }
        }, 1000);
      }
    },
    getForgetPasswordcheckcode(operation) {
      let params = {
        mobileNum: this.ForgetPasswordForm.mobileNum,
        operation: operation,
      };
      getCheckCode(params).then((res) => {});
      if (validPhone(this.ForgetPasswordForm.mobileNum)) {
        this.forgetPassDisabled = true;
        this.content = "60s后重新发送";
        let clock = window.setInterval(() => {
          this.totalTime--;
          this.content = this.totalTime + "s后重新发送";
          if (this.totalTime < 0) {
            //当倒计时小于0时清除定时器
            window.clearInterval(clock);
            this.content = "重新发送验证码";
            this.totalTime = 60;
            this.forgetPassDisabled = false;
          }
        }, 1000);
      }
    },
    removeInfo() {
      removeToken();
      removeProjectId();
      removeProjectName();
      removeUserMobile();
      removeVideoId();
      removeUserName();
      removeUserId();
    },
  },
  mounted() {
    this.removeInfo();
    this.getUserAvatar();
  },
};
</script>

<style lang="scss" scoped>
.login-container {
  background-color: #e1e8fa;
  height: 100vh;
}
.el-card {
  overflow: unset;
}
.el-form-item {
  margin-right: 45px;
}
.login-box {
  width: 450px;
  height: 320px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.register-box {
  width: 450px;
  height: 390px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.avatar-box {
  width: 110px;
  height: 110px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}
.avatar-box img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}
.login-form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}
</style>
