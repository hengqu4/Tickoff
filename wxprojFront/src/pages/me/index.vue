<template>
  <div>
    <view class="container">
      <view class="userinfo">
      <wux-avatar
          v-if="isShow"
          class="userAvatar"
          :src="userInfo.avatarUrl"
          alt="登陆失败"
          size="large"
          shape="square"
          scale="true"
        />
        <!-- <wux-button
          class="showUsrAvatar"
          v-else
          open-type="getUserInfo"
          @getuserinfo="getUserInfo"
          >点击获取用户登录信息</wux-button
        >
        <wux-white-space size="small" /> -->
        <p class="userName">微信名:{{ userInfo.nickName }}</p>
    </view>
    </view>
     <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-grids wux-class="grid-box" col="2">
      <wux-grid>
        <view class="grid-title">232</view>
        <view class="grid-desc">
          <wux-icon wux-class="grid-icon" type="ios-happy" size="22" />
          <view>活跃天数</view>
        </view>
      </wux-grid>
      <wux-grid>
        <view class="grid-title">23,423</view>
        <view class="grid-desc">
          <wux-icon wux-class="grid-icon" type="ios-thumbs-up" size="22" />
          <view>收到的赞</view>
        </view>
      </wux-grid>
    </wux-grids>
   
    <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-cell-group>
      <wux-cell
        title="历史记录"
        is-link
        data-clipboard="https://github.com/skyvow"
        bind:click="clipboard"
      >
        <wux-icon wux-class="icon" slot="header" type="ios-trending-up" size="22" />
      </wux-cell>
      <wux-cell title="分享数据" is-link open-type="share">
        <wux-icon
          wux-class="icon"
          slot="header"
          type="ios-ribbon"
          size="22"
        />
      </wux-cell>
    </wux-cell-group>
    <wux-white-space size="large" />
    <wux-cell-group>
      <wux-cell
        title="反馈"
        is-link
        data-clipboard="https://github.com/skyvow"
        bind:click="clipboard"
      >
        <wux-icon wux-class="icon" slot="header" type="ios-create" size="22" />
      </wux-cell>
      <wux-cell
        title="帮助"
        is-link
        data-url="http://cdn.skyvow.cn/wxpay.jpg"
        bind:click="onPreview"
      >
        <wux-icon
          wux-class="icon"
          slot="header"
          type="ios-paper"
          size="22"
        />
      </wux-cell>
      <wux-cell
        title="关于"
        is-link
        data-clipboard="https://github.com/skyvow"
        bind:click="clipboard"
      >
        <wux-icon wux-class="icon" slot="header" type="ios-information-circle" size="22" />
      </wux-cell>
      <wux-cell title="设置" is-link open-type="share">
        <wux-icon
          wux-class="icon"
          slot="header"
          type="ios-settings"
          size="22"
        />
      </wux-cell>
    </wux-cell-group>
  </div>
</template>

<script>
import store from "../../store" //vuex 读取全局变量
import * as mutationtypes from '../../mutation-types'
export default {
  data() {
    return {
      userInfo: {},
      isShow: false,
    };
  },
  beforeMount() {
    console.log("IbeforeMount");
    // console.log("vuex global test:"+store.state.nickname+store.state.id)  //vuex 读取全局变量
    this.handleGetUserInfo();
    console.log("vuex global test1:"+store.state.isShow)
    // store.commit(mutationtypes.ISSHOW_MUTATION,true);
    console.log("vuex global test2:"+store.state.isShow)
  },
  methods: {
    handleGetUserInfo() {
      this.userInfo = store.state.userInfo;
      this.isShow = store.state.isShow;
    },
  },
};
</script>

<style>
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    padding: 60rpx 0;
    box-sizing: border-box;
    text-align: center;
}

.userinfo {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.userinfo-avatar {
    width: 128rpx;
    height: 128rpx;
    margin: 20rpx;
    border-radius: 50%;
}

.userinfo-nickname {
    color: rgba(0, 0, 0, .85);
}
</style>
