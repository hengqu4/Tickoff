<template>
  <div>
    <view class="page">
    <view class="page__bd">
        <wux-cell-group title="基本信息">
            <wux-cell title="任务名称" :extra="title"></wux-cell>
            <wux-cell title="任务详情"></wux-cell>
            <wux-cell :title="description" disabled></wux-cell>
            <wux-cell title="任务集" :extra='list[listNumber]'></wux-cell>
            <wux-cell title="开始时间" :extra='startDate'></wux-cell>
            <wux-cell title="结束时间" :extra='endDate'></wux-cell>

        </wux-cell-group>
        <wux-cell-group title="高级信息">
            <wux-cell title="是否需要结算"  :extra="isRequire"></wux-cell>
            <wux-cell title="工作量"  :extra="workLoad"></wux-cell>
            <wux-cell title="循环信息" :extra="loopText"></wux-cell>
            <wux-cell title="提醒信息" :extra="noticeText"></wux-cell>
            <wux-cell title="拖延信息" :extra="delayText" ></wux-cell>
        </wux-cell-group>


        <wux-cell-group title="信息修改">
            <wux-cell title="修改信息" is-link @click="gotoChange(taskId)"></wux-cell>

        </wux-cell-group>
    </view>
</view>
  </div>
</template>

<script>

export default {
  data() {
    return {
      userId:'',
      taskId:'',
      createDate: "",
      delayDate: "",
      description: "",
      endDate: "",
      isDelay: false,
      isNeedNotice: false,
      isRequire: true,
      listNumber: "",
      noticeBefore: "",
      routine: "",
      startDate: "",
      title: "",
      workLoad: 1,
      list:[],

      loopText:'',
      noticeText:'',
      delayText:''
    };
  },

  onLoad:function(options) {
    console.log(options)
    this.taskId=options.tId
    this.userId=options.uId
  },
   onAction(e) {
        console.log('onAction', e.detail)
    },
  created(){

  },

  beforeMount() {
    wx.getUserInfo({
      withCredentials: false,
      success: (res) => {
      },
      fail: () => {
        console.log("shibai");
      },
      complete: () => {},
    });
  },
  mounted() {
    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'api/getTask?taskId='+this.taskId,
    }).then(res => {
      console.log(res)
      this.title=res.title
      this.description=res.description
      this.startDate=res.startDate
      this.endDate=res.endDate
      this.routine=res.routine
      this.listNumber=res.listNumber
      this.isDelay=res.isDelay
      this.delayDate=res.delayDate
      this.workLoad=res.workLoad
      this.isRequire=res.isRequire
      this.isNeedNotice=res.isNeedNotice
      this.noticeBefore=res.noticeBefore
      this.description='获取用户信息获取用户信息获取用户信息获取用户信息获取用户信息获取用户信息获取用户信息'

      var str=this.routine.split("-")
      if(str[0]=="01"){
        this.loopText="每月循环"
      }
      else if(str[1]=="01"){
        this.loopText="每周循环"
      }
      else if( str[2]=="01"){
        this.loopText="每日循环"
      }
      else{
        this.loopText="不循环"
      }

      if(this.isNeedNotice){
        this.noticeText="提前"+this.noticeBefore
      }
      else{
        this.noticeText="不需要提醒"
      }

      if(this.isDelay){
        this.delayText=this.delayDate
      }
      else{
        this.delayText="不需要延期"
      }
      
    }).catch(function (error) {
        console.log(error);
    });

    this.$fly.request({
      method: 'get', // post/get 请求方式
      url: 'api/get_taskset?uId='+this.userId,
    }).then(res => {
      console.log(res.taskSetList)
      this.list=res.taskSetList
      console.log(this.list)
    }).catch(function (error) {
        console.log(error);
    });


  },

  computed: {},

  methods: {
    onContact(e) {
        console.log('onContact', e)
    },
    onGotUserInfo(e) {
        console.log('onGotUserInfo', e)
    },
    onGotPhoneNumber(e) {
        console.log('onGotPhoneNumber', e)
    },

    gotoChange(id) {
      console.log(id)
      wx.navigateTo({url: '/pages/changeTask/main?tId='+id+"&uId="+this.userId})
    }

  }
};
</script>

