<template>
  <div class='wrapper'>
    <view class="page">
    <view class="page__bd">
        <wux-cell-group title="基本信息">
            <wux-cell title="任务名称" :extra="title"></wux-cell>
            <wux-cell title="任务详情"></wux-cell>
            <wux-cell :title="description" disabled></wux-cell>
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

        <div class="buttonObject">
          <wux-button block outline type="positive" @click="gotoChange(taskId)">编辑</wux-button>
        </div>
        
        <!-- <wux-cell-group title="信息修改">
            <wux-cell title="修改信息" is-link @click="gotoChange(taskId)"></wux-cell>
        </wux-cell-group> -->
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
      mset_id:'',
      taskSet:'',

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

  },
  onShow() {
    
    // this.taskId='1d6cb51e63cf407bb5203ef2ac19ec47'
    // this.userId='open91cd84d64333821d73e2751f'


    this.$fly.request({
      method: 'get', // post/get 请求方式
      // url: 'tickoff/api/missions/'+"0dbaff90-98ee-44e1-8265-1310252e5a59",
      url: 'tickoff/api/missions/'+this.taskId,

    }).then(ress => {
      console.log(ress)
      var res=ress.data
      this.title=res.name
      this.description=res.description
      this.startDate=res.startDate
      this.endDate=res.endDate
      this.routine=res.routine
      this.listNumber=res.listNumber
      this.isDelay=res.delay
      this.delayDate=res.delayDate
      this.workLoad=res.workLoad
      this.isRequire=res.requireCheck
      this.isNeedNotice=res.needNotice
      this.noticeBefore=res.noticeTime
      this.mset_id=res.mset_id
      

      var temp=this.startDate.split(":")
      temp=temp[0]+":"+temp[1]
      this.startDate=temp

      var temp=this.endDate.split(":")
      temp=temp[0]+":"+temp[1]
      this.endDate=temp

      var temp=this.delayDate.split(":")
      temp=temp[0]+":"+temp[1]
      this.delayDate=temp

      var temp=this.noticeBefore.split(":")
      temp=temp[0]+":"+temp[1]
      this.noticeTime=temp

      if(this.routine==1){
          this.loopText="每日循环"
      }
      else if(this.routine==0){
          this.loopText='不循环'
      }
      else if(this.routine==100){
          this.loopText='每月循环'
      }
      else{
          this.loopText='每周'+this.routine/10+"循环"
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


<style>
page {
  background-color: rgb(245, 245, 245);
}
</style>
<style  lang="less" scoped>

.buttonObject {
  margin: auto;
  width: 70%;
  padding: 5rpx;
}

</style>