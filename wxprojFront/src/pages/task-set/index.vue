<template>
  <div>
    <div v-for="(item, index) in dataList" :key="index" :style="{marginBottom: '5px'}">
      <wux-wing-blank size="default"  :key="index">
        <!-- <i-card :title="item.title" extra="额外内容">
          <view slot="content">{{item.desc}}</view>
          <view slot="footer">
              <p>成员</p>
              <div v-for="(mem, aid) in item.member" :key="aid">
                <wux-avatar :src="mem.avater" />
              </div>
          </view>
        </i-card> -->
        <wux-card prefixCls="set-card" :title="item.title" extra="额外内容">
          <view slot="body" wux-class='viewbody'>{{item.desc}}</view>
          <view slot="footer">
              <p>成员</p>
              <span v-for="(mem, aid) in item.member" :key="aid">
                <wux-avatar :src="mem.avater" />
              </span>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
    <div :style="{height:'80px'}"/>
    <div class="set-create-button">
      <van-button round type="info" @click="onClick">
          +
      </van-button>
    </div>
  </div>
</template>

<script>

export default {
  data(){
    return{
      dataList:[
        {
          title:'任务集1',
          desc:'1111',
          member:[
            {avater:'http://cdn.skyvow.cn/logo.png'},
            {avater:'http://cdn.skyvow.cn/logo.png'},
            {avater:'http://cdn.skyvow.cn/logo.png'}
          ]
        },
        {
          title:'任务集2',
          desc:'2222',
          member:[
            {avater:'../../../static/images/user.png'},
            {avater:'../../../static/images/user.png'},
            {avater:'http://cdn.skyvow.cn/logo.png'}
          ]
        }
      ]
    }
  },

  methods:{
    // 页面跳转失败。。
    onClick () {
      var pages = getCurrentPages()    //获取加载的页面
      var currentPage = pages[pages.length-1]    //获取当前页面的对象
      var currentUrl = currentPage.route
      console.log(currentUrl)
      const url = '../create-set/main'
      wx.navigateTo({ 
        url: url,
        success: function(res){
          console.log('跳转到页面成功')// success
        },
        fail: function() {
        console.log('跳转到页面失败')  // fail
        },
      })
    },
  }
}
</script>

<style lang="less">

@import "../../../static/wux/styles/mixins/index.less";
@import "../../../static/wux/styles/themes/index.less";
@import "./style.less";

.set-create-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}


</style>