<script>
import store from "./store";
import * as mutationtypes from "./mutation-types";
export default {
  created() {
    // 调用API从本地缓存中获取数据
    /*
     * 平台 api 差异的处理方式:  api 方法统一挂载到 mpvue 名称空间, 平台判断通过 mpvuePlatform 特征字符串
     * 微信：mpvue === wx, mpvuePlatform === 'wx'
     * 头条：mpvue === tt, mpvuePlatform === 'tt'
     * 百度：mpvue === swan, mpvuePlatform === 'swan'
     * 支付宝(蚂蚁)：mpvue === my, mpvuePlatform === 'my'
     */
    let logs;
    if (mpvuePlatform === "my") {
      logs = mpvue.getStorageSync({ key: "logs" }).data || [];
      logs.unshift(Date.now());
      mpvue.setStorageSync({
        key: "logs",
        data: logs,
      });
    } else {
      logs = mpvue.getStorageSync("logs") || [];
      logs.unshift(Date.now());
      mpvue.setStorageSync("logs", logs);
    }
  },
  log() {
    console.log(`log at:${Date.now()}`);
  },
  onLaunch: function () {
    console.log("OonLaunch");
    // 操作者登录
    wx.login({
      success: function (r) {
        console.log("src/App.vue::onLaunch()::success::return", r); //r包含code
        //通过code获得openid并存入store
        var code = r.code;
        if (code) {
          //发送code到后台，分析openid
      
          //this.$fly.request({
          //   method: 'post',
          //   url: 'http:///login/regist?code=" + code,
          //   header: {
          //     "content-type": "application/json",
          //   },
          // }).then(res => {
          //   console.log(res)
          //   if (res.data.status == 0) {
          //       //status为空时登录凭证code为空
          //       wx.showToast({
          //         title: "登录凭证code为空...",
          //         icon: "none",
          //         duration: 2500,
          //       });
          //     } else if (res.data.status == 1) {
          //       //status为1时openid已存在,是登录
          //       this.globalData.userInfo = res.data.userInfo;
          //       console.log(userInfo);
          //     } else if (res.data.status == 2) {
          //       //status为2时openid不存在
          //       this.globalData.userInfo = res.data.userInfo;
          //       console.log(userInfo);
          //     }
          // }).catch(function (error) {
          //     console.log(error);
          // });
        }
      },
      fail: function (res) {
        console.log("login()失败");
      },
      complete: function (res) {},
    });
  },
  beforeMount() {
    console.log("ObeforeMount");
    //把公开信息存储在全局store里
    //获取操作者公开信息并存入store
    wx.getUserInfo({
      success: (data) => {
        console.log("getUserInfo()::", data);
        store.commit(mutationtypes.USERINFO_MUTATION, data.userInfo);
      },
      fail: () => {
        console.log("getUserInfo()失败");
      },
    });
  },
};
</script>

<style>
.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 200rpx 0;
  box-sizing: border-box;
}
/* this rule will be remove */
* {
  transition: width 2s;
  -moz-transition: width 2s;
  -webkit-transition: width 2s;
  -o-transition: width 2s;
}
</style>
