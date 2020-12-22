<template>
  <div>
    <div :style="{ textAlign: 'center' }">
      <p
        :style="{
          fontFamily: 'Segoe Print',
          fontSize: '300%',
          color: '#341f97',
        }"
      >
        invitition
      </p>
    </div>
    <div id="invite-div">
      <wux-wing-blank size="default">
        <wux-card prefixCls="invite-card" :title="title">
          <view slot="body">
            <div :style="{ height: '100px' }">{{ description }}</div>
          </view>
        </wux-card>
        <view :style="{ textAlign: 'center', marginTop: '30px' }">
          <wux-button outline type="positive" @click="onClick">
            接受邀请
          </wux-button>
        </view>
      </wux-wing-blank>
    </div>
  </div>
</template>

<script>
import store from "../../store";

export default {
  data() {
    return {
      setId: "",
      userId: "",
      title: "",
      description: "",
      create_date: "",
    };
  },
  onLoad() {
    this.setId = options.setId;
    this.userId= store.state.openId;
  },

  onShow() {
    this.$fly
      .request({
        method: "get",
        url: "tickoff/api/mission_set/mset_id/" + this.setId,
      })
      .then((res) => {
        console.log("res");
        console.log(res);
        this.setId = res.data.mset_id;
        this.title = res.data.name;
        this.create_date = res.data.create_date;
        this.description = res.data.description;
      })
      .catch(function (error) {
        console.log("error");
        console.log(error);
      });
  },

  methods: {
    // 页面跳转失败。。
    onClick() {
      this.$fly
        .request({
          method: "post",
          url: "tickoff/api/user_mset",
          body: {
            mset_id: this.setId,
            openid: this.userId,
          },
        })
        .then(() => {
          var pages = getCurrentPages(); //获取加载的页面
          var currentPage = pages[pages.length - 1]; //获取当前页面的对象
          var currentUrl = currentPage.route;
          console.log(currentUrl);
          const url = "../set-detail/main";
          wx.navigateTo({
            url: url,
            success: function (res) {
              console.log("跳转到页面成功"); // success
            },
            fail: function () {
              console.log("跳转到页面失败"); // fail
            },
          });
        })
        .catch(function (error) {
          console.log("error");
          console.log(error);
        });
    },
  },
};
</script>

<style lang="less">
@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
@import "./style.less";
</style>