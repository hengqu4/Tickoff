<template>
  <div>
    <div v-for="(item, index) in dataList" :key="index" :style="{marginBottom: '5px'}">
      <wux-wing-blank size="default"  :key="index">
        <wux-card :title="item.title" extra="额外内容">
          <view slot="body">{{item.desc}}</view>
          <view slot="footer">
            <view>
              <p>成员</p>
              <wux-avatar src="../../../static/images/user.png" />
              <wux-avatar src="../../../static/images/user.png" />
              <wux-avatar src="../../../static/images/user.png" />
            </view>
          </view>
        </wux-card>
      </wux-wing-blank>
    </div>
    <div :style="{height:'80px'}"/>
    <div class="set-create-button">
      <van-button round type="info" @click="onClick">
        <!-- <navigator url='/pages/create-set/main' open-type="navigate"> -->
          +
        <!-- </navigator> -->
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
        },
        {
          title:'任务集2',
          desc:'2222',
        },
        {
          title:'任务集3',
          desc:'3333',
        },
        {
          title:'任务集4',
          desc:'44444',
        },
        {
          title:'任务集5',
          desc:'55555555',
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
      const url = 'pages/create-set/main'
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

    // onClick(){
    //   this.$router.onClick({
    //     path:'pages/create-set/main',
    //     query:{
    //       name: "lalala",
    //       age: 89
    //     }
    //   })
    // }
  }
}
</script>

<style>
.set-create-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}
</style>