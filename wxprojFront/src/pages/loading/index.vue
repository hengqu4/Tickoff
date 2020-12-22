<template>
  <view @click="naviTo()"> Loading... </view>
</template>
<script>
import store from "../../store";
import fly from "../../utils/wx-request";
export default {
  mounted() {},
  beforeMount() {
    wx.getUserInfo({
      success: (data) => {
        console.log("getUserInfo()::", data);
        store.commit("USERINFO_MUTATION", data.userInfo);
      },
      fail: () => {
        console.log("getUserInfo()失败");
      },
    });
    console.log("ssss");
    // 操作者登录
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
          fly
            .request({
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
    this.randomNaviToView();
  },
  data() {
    return {};
  },
  onLoad() {},
  onShow: function () {
    wx.hideHomeButton({
      success: function () {
        console.log("hide home success");
      },
      fail: function () {
        console.log("hide home fail");
      },
      complete: function () {
        console.log("hide home complete");
      },
    });
  },
  methods: {
    randomNaviToView() {
      var randomTime = Math.random;
      wx.showLoading({
        title: "加载中！",
      });
      setTimeout(function () {
        wx.hideLoading();
      }, 2000);
    },
    naviTo() {
      wx.switchTab({
        url: "../view/main",
        success: function (res) {
          console.log("跳转到页面成功"); // success
        },
        fail: function () {
          console.log("跳转到页面失败"); // fail
        },
      });
    },
  },
};
</script>
<style lang="less" scoped></style>
