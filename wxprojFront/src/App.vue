<script>
import store from "./store";
import * as mutationtypes from "./mutation-types";
import BeautyWe from '@beautywe/core';
import event from '@beautywe/plugin-event';
import status from '@beautywe/plugin-status';
import fly from './utils/wx-request'

const app = new BeautyWe.BtApp();
// status 插件依赖于 beautywe-plugin-event
app.use(event());

// 使用 status 插件
app.use(status({
    statuses: ['getUserInfo'],
}));

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
    console.log("OnLaunch");
    console.log( this.$root.$mp.appOptions.scene)
    store.commit(mutationtypes.SENCE_MUTATION,this.$root.$mp.appOptions.scene)
    console.log("场景值设置完成"+ store.state.scene)
    this.getInfo();
  },
  beforeMount() {
    console.log("ObeforeMount");
  },

  methods:{
    getInfo(){
      wx.getUserInfo({
        success: (data) => {
          console.log("getUserInfo()::", data);
          store.commit("USERINFO_MUTATION", data.userInfo);
          console.log(data.userInfo)
          this.getOpenidAndToken();
        },
        fail: () => {
          console.log("getUserInfo()失败");
        },
      })
    },
    getOpenidAndToken(){
      wx.login({
        success: function (r) {
          console.log("pages/loading::onLaunch()::success::return", r); //r包含code
          //通过code获得openid并存入store
          var code = r.code;
          var rawUrl = store.state.userInfo.avatarUrl;
          var newUrl = rawUrl.replace("/", "-");
          for (var i = 0; i < 100; i++) {
            newUrl = newUrl.replace("/", "-");
          }
          if (code) {
            //发送code到后台，分析openid
            fly.request({
                method: "post",
                url:
                  "login/regist/code/" +
                  code +
                  "/avatar/" +
                  newUrl +
                  "/nickname/" +
                  store.state.userInfo.nickName,
                header: {
                  "content-type": "application/json",
                },
              })
              .then((res) => {
                console.log("oL,res:", res);
                console.log("token:", res.token);
                store.commit("TOKEN_MUTATION", res.token);
                store.commit("OPENID_MUTATION", res.openid);
              })
              .catch(function (error) {
                console.log(error);
              });
          }        
        },
        fail: function (res) {
          console.log("login()失败");
        },
        complete: function (res) {},
      });
    }
  }
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
