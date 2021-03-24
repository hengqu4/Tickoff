<template>
  <div class="info-container">
    <!-- 上方标题栏 -->
    <project-header></project-header>
    <!-- 个人主体内容 -->
    <el-card class="info-box">
      <el-button size="small" style="z-index:9; position: absolute; right: 28px; top:20px" @click="goLoginOut" type="danger">退出登陆</el-button>
      <el-tabs
          v-model="activeIndex"
          :before-leave="beforeTabLeave"
          :tab-position="'top'"
          @tab-click="tabClicked"
          class="login-tabs">
          <el-tab-pane label="个人信息" name="0" >
            <el-form ref="UserForm" :model="userInfo" label-width="80px" style="width: 80%">
              <el-form-item label="头像" size="small">
                <el-avatar class="user-avatar" :src="userInfo.avatar"></el-avatar>
                <el-upload
                  v-if="!disabled"
                  class="upload-demo"
                  action=""
                  :http-request="request"
                  :before-upload="onBeforeUpload"
                  accept="image/jpeg, image/gif, image/png, image/jpg"
                  :on-success="handleSuccess"
                  :on-error="handleError"
                  :on-exceed="handleExceed"
                >
                  <el-button size="text" style="margin-top: -3px">更换头像</el-button>
                </el-upload>
              </el-form-item>

              <el-form-item label="姓名">
                <el-input v-model="userInfo.username" :disabled="disabled"></el-input>
              </el-form-item>

              <el-form-item label="公司">
                <el-input
                  v-model="userInfo.company"
                  clearable
                  :disabled="disabled"
                ></el-input>
              </el-form-item>
            </el-form>
            <el-button type="primary" style="margin-left:80px" @click="changeDisabled">
              {{this.disabled ? "修改信息" : "确定"}}
            </el-button>
          </el-tab-pane>
          <el-tab-pane label="账号绑定" name="1" >
            <el-row :gutter="20">
              <el-col :span="3" :offset="2"><div class="left-tag">手机号</div></el-col>
              <el-col :span="12" :offset="1"><div>{{userInfo.mobileNum}}</div></el-col>
              <el-col :span="2" :offset="1"><el-button type="text" style="margin-top: -7px" @click="bindMobileVisible=true">修改</el-button></el-col>
            </el-row>
          </el-tab-pane>

      </el-tabs>
    </el-card>
    <el-dialog title="修改手机号"
               :visible.sync="bindMobileVisible"
               width="25%">
      <el-form
        ref="bindMobileFormRef"
        :model="bindMobileForm"
        :rules="bindMobileFormRules"
      >
        <el-form-item prop="mobileNum">
          <el-input
            v-model="bindMobileForm.mobileNum"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        <el-form-item prop="checkcode">
          <el-input v-model="bindMobileForm.checkCode"
                    prefix-icon="el-icon-lock">
            <el-button slot="append"
                       @click="getSetNumberCheckCode"
                       :disabled="resetNumDisabled">
              {{ content }}
            </el-button>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <!-- <el-button @click="dialogVisible = false">取 消</el-button> -->
        <el-button type="primary" @click="bindMobileNumRequest">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { bindMobileNum } from '@/api/user.js';
import { Message } from "element-ui";
import { uploadImg, getUserInfo, updateUserInfo, getCheckCode } from "@/api/user";
import { getUserId } from "@/utils/cache";
import { validPhone } from "@/utils/validate.js";
import ProjectHeader from "@/common/header/ProjectHeader";
export default {
  name: "User",
  components: {
    ProjectHeader,
  },
  data() {
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
    return {
      activeIndex: 0,
      content: "获取验证码",
      userInfo: {},
      disabled: true,
      bindMobileVisible: false,
      bindMobileFormRules: {
        mobileNum: [{required: true, validator: checkPhone, trigger: "blur"}],
        checkCode: [{required: true, trigger: "blur" }],
      },
      bindMobileForm: {
        mobileNum: "",
        checkCode: ""
      },
      resetNumDisabled: false,
      totalTime: 60
    }
  },
  methods: {
    getSetNumberCheckCode() {
      let params = {
        mobileNum: this.bindMobileForm.mobileNum
      };
      getCheckCode(params).then((res) => {});
      if (validPhone(this.bindMobileForm.mobileNum)) {
        this.resetNumDisabled = true;
        this.content = "60s后重新发送";
        let clock = window.setInterval(() => {
          this.totalTime--;
          this.content = this.totalTime + "s后重新发送";
          if (this.totalTime < 0) {
            //当倒计时小于0时清除定时器
            window.clearInterval(clock);
            this.content = "重新发送验证码";
            this.totalTime = 60;
            this.resetNumDisabled = false;
          }
        }, 1000);
      }
    },
    getInfo() {
      let userId = getUserId();
      getUserInfo(userId).then(res => {
        // Message.success("获取个人信息成功");
        this.userInfo = res.data;
      });
    },
    changeInfo() {
      updateUserInfo(this.userInfo).then(res => {
        console.log(res);
      });
    },
    async request(file) {
      console.log(file.file);
      const { data: res } = await axios.post(
        "https://api.video-review.top:1314/api/uploadImg/",
        this.dataObj,
        {
          headers: this.headerobj
        }
      );
      if (res.result === 1) {
        this.userInfo.avatar = res.data.url;
        this.dataObj = new FormData();
      } else {
        Message.error("上传失败");
      }
    },
    handleSuccess() {
      console.log("success");
    },
    handleError(e) {
      console.log("error");
    },

    onBeforeUpload(file) {
      this.dataObj.append("image", file);

      const isIMAGE =
        file.type === "image/jpeg" || "image/gif" || "image/png" || "image/jpg";
      const isLt1M = file.size / 1024 / 1024 < 1;

      if (!isIMAGE) {
        // this.$message.error("上传文件只能是图片格式!");
        Message.error("上传文件只能是图片格式!");
      }
      if (!isLt1M) {
        // this.$message.error("上传文件大小不能超过 1MB!");
        Message.error("上传文件大小不能超过 1MB!");
      }
      return isIMAGE && isLt1M;
    },
    changeDisabled() {
      if (!this.disabled) {
        this.changeInfo();
      }
      this.disabled = !this.disabled;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      Message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    goLoginOut() {
      this.$router.push("/login");
    },
    goHome() {
      this.$router.push("/");
    },

    tabClicked(e) {},
    beforeTabLeave(activeName, oldActiveName) {
      // console.log(activeName, oldActiveName);
    },
    bindMobileNumRequest(){
      this.$refs["bindMobileFormRef"].validate(async valid => {
        if(valid){
          bindMobileNum(this.bindMobileForm).then(res=>{
            console.log(res);
            if(res.result){
              this.bindMobileVisible = false;
              // Message.success('绑定成功');
              this.getInfo();
            }else{
              Message.error(res.message);
            }
          })
        }
      })
    }
  },
  mounted() {
    this.getInfo();
  },
};
</script>

<style lang="scss" scoped>
.user-header {
  top: 0;
  width: 100%;
  position: fixed;
  z-index: 9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  background: white;
}
.header-title {
  cursor: pointer;
  width: 100px;
  height: 46px;
}
.header-title2 {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 28px;
  cursor: pointer;
  margin-top: 8px;
}
.header-right {
  display: flex;
  flex: 1;
  justify-content: flex-end;
  align-items: center;
}
.info-container {
  background-color: #e1e8fa;
  height: 100vh;
}
.el-card {
  overflow: unset;
}

.info-box {
  width: 520px;
  height: 350px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.btns {
  display: flex;
  justify-content: flex-end;
}
.left-tag{
  display: flex;
  align-items: center;
  text-align:center

}

</style>
