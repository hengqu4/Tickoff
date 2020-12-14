<template>
  <div>
    <wux-cell-group title="任务集信息">
      <wux-cell title="标题" :extra="title"></wux-cell>
      <wux-cell title="描述" :extra="description"></wux-cell>
      <wux-cell title="成员">
        <div v-for="(item, index) in memberList" :key="index" :style="{marginBottom: '5px'}">
          <view>
            <wux-avatar :src="item.avater" />
            <span>{{item.name}}</span>
          </view>
        </div>
      </wux-cell>
    </wux-cell-group>
    
    <div :style="{height:'80px'}"/>
    <div class="set-edit-button">
      <van-button round type="info" @click="gotoChange(setId)">
          修改
      </van-button>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      userId:'',
      setId:'',
      title: "",
      description: "",
      memberNum:1,
      memberList:[
        {
          name:'github',
          avater:"../../../static/images/user.png",
        },
        {
          name:'hq',
          avater:"http://cdn.skyvow.cn/logo.png",
        }
        
      ]
    };
  },

  onLoad:function(options) {
    console.log(options)
    this.setId=options.setId
    this.userId=options.uId
  },

  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
      },
      fail: () => {
        console.log("获取信息失败");
      },
      complete: () => {},
    });
  },

  mounted() {
    this.$fly.request({
      method: 'get', // post/get 请求方式
      // url: 'api/getSet?setId='+this.setId,
      url: 'api/getSet?setId=1',
    }).then(res => {
      console.log(res)
      this.title=res.title
      this.description=res.description
      this.memberNum=res.memberNum
      this.member=res.member
      
    }).catch(function (error) {
        console.log(error);
    });

  },

  methods:{
     gotoChange(id) {
      console.log(id)
      const url='/pages/change-set/main?setId='+id+"&uId="+this.userId
      wx.navigateTo({
        url: url,
        success: function(res){
          console.log('跳转到页面成功')// success
        },
        fail: function() {
        console.log('跳转到页面失败')  // fail
        },
      })
    }
  }
    
}
</script>

<style>
.set-edit-button{
  z-index: 1;
  position: fixed;
  left: 80%;
  top:85%
}
</style>