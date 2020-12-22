<template>
  <div>
    <view class="container">
      <view class="userinfo">
      <wux-avatar
          class="userAvatar"
          :src="userInfo.avatarUrl"
          alt="登陆失败"
          size="large"
          shape="square"
          scale="true"
        />
        <wux-white-space size="small" />
        <p class="userName">微信名:{{ userInfo.nickName }}</p>
    </view>
    </view>
     <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-white-space size="large" />
    <wux-grids wux-class="grid-box" col="2">
      <wux-grid>
        <view class="grid-title">{{activeDays}}</view>
        <view class="grid-desc">
          <wux-icon wux-class="grid-icon" type="ios-happy" size="22" />
          <view>活跃天数</view>
        </view>
      </wux-grid>
      <wux-grid>
        <view class="grid-title">{{likes}}</view>
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
        @click="viewHistory"
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
        title="赞助"
        is-link
        data-url="http://cdn.skyvow.cn/wxpay.jpg"
        @click="onPreview($event)"
      >
        <wux-icon wux-class="icon" slot="header" type="ios-create" size="22" />
      </wux-cell>
      <wux-cell
        title="帮助"
        is-link
        @click="viewHelp"
      >
        <wux-icon wux-class="icon" slot="header" type="ios-paper" size="22" />
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
      likes:100,
      activeDays:100
    };
  },
  beforeMount() {
    //console.log("IbeforeMount");
    // console.log("vuex global test:"+store.state.nickname+store.state.id)  //vuex 读取全局变量
    //console.log("beforeMount",this.userInfo);
    //console.log("vuex global test1:"+store.state.isShow)
    // store.commit(mutationtypes.ISSHOW_MUTATION,true);
    //console.log("vuex global test2:"+store.state.isShow)
  },
  onShow(){
    this.handleGetUserInfo();
    //this.randomNum();
    // console.log("token11111111111:",store.state.token);
    // store.commit(mutationtypes.TOKEN_MUTATION, "111szdghjfhjkdasbfhug");
    // console.log("token1222222222222222:",store.state.token);
    this.$fly.request({
      method: 'get',
      url: '/tickoff/api/likes/UserID/'+store.state.openId,
    }).then(res => {
      console.log(res)
      this.likes=res.data.likes
    }).catch(function (error) {
        console.log(error);
    });
    this.$fly.request({
      method: 'get',
      url: 'tickoff/api/activeDays/UserID/'+store.state.openId,
    }).then(res => {
      console.log(res)
      this.activeDays=res.data.days
    }).catch(function (error) {
        console.log(error);
    });
  },
  methods: {
    handleGetUserInfo() {
      this.userInfo = store.state.userInfo;
    },
    randomNum(){
      this.likes = Math.round(Math.random()*1000);
      this.activeDays = Math.round(Math.random()*100);
    },
    onPreview(e) {
      wx.previewImage({
          urls: [e.currentTarget.dataset.url],
      })
    },
    viewHistory () {
      mpvue.navigateTo({url:"../history/main"})
    },
    viewHelp () {
      mpvue.navigateTo({url:"../help/main"})
    }
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
