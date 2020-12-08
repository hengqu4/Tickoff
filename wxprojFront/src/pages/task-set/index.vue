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
      <van-button round type="info" @click="onClick($event)">+</van-button>
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
    onClick (event) {
      console.log(event)
      // 路由跳转有bug，不对劲
      const url = '/pages/create-set/main'
      wx.navigateTo({ url })
    }
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