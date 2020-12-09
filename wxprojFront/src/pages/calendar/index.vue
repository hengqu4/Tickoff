<template>
  <div>
  <div class="userInfo">
    <wux-avatar v-if="isShow" class="userAvatar" :src="userInfo.avatarUrl" alt="Error Pic" size="large"/>
    <wux-button class="showUsrAvatar" v-else open-type="getUserInfo" @getuserinfo="getUserInfo">点击获取用户登录信息</wux-button>
    <p class="userName">{{userInfo.nickName}}</p>
    </div>
    <div class="btnNav">
    <wux-button class="addListBtn" type="royal">新建任务集合</wux-button>
    <wux-button type="royal" @swapcalendar="swapCalendar">查看月视图</wux-button>
    </div>
    <Calendar :events="events" v-if="isCalendarShow"/>
    <van-collapse class="taskList" :value="activeNames" @change="onChange($event)" v-for="(item,index) in dataList" :key='index'>
      <van-collapse-item :title="item.title" :name="index">
        <wux-button type="calm">新建任务</wux-button>
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
import Calendar from 'mpvue-calendar'

export default {
  data() {
    return{
    value:[],
    userInfo:{},
    isShow:false,
    isCalendarShow:true,
    activeNames: [],
      dataList:[
        {
          title:'任务集1',
          desc:'任务1任务1任务1'
        },
        {
          title:'任务集2',
          desc:'任务2222222'
        },
        {
          title:'任务集3',
          desc:'任务33333333'
        }
      ],
      events: {'2020-12-9': '4/10','2020-12-10': '2/10'},
    }
  },
  components:{
    Calendar
  },
  beforeMount(){
    this.handleGetUserInfo()
  },

  methods: {
    handleGetUserInfo(){
      wx.getUserInfo({
        success:(data)=>{
          console.log(data);
          this.userInfo=data.userInfo
          this.isShow=true
        },
        fail:()=>{
          console.log("获取用户登录信息失败");
        }
      })
    },
    getUserInfo(data){
      console.log(data);
      if(data.mp.detail.rawData){
        this.handleGetUserInfo();
      }
    },
    swapCalendar(){
      console.log(this.isCalendarShow)
      if(this.isCalendarShow==true){
        this.isCalendarShow=false;
      }
      else{
        this.isCalendarShow=true;
      }
    }
  }
}
</script>

<style>
.userInfo{
  height:50px;
  margin-bottom:10px;
  display:flex;
  align-items: center;
  background:linear-gradient(to right, lightcyan , lightblue);
}
.userAvatar{
  height:auto;
}
.userName{
  color:orange;
}

/*月视图样式*/
.mpvue-calendar{
  background:rgba(63,63,71) !important;
}
.mpvue-calendar ._table{
  color:#fff !important;
}
.mpvue-calendar ._td .mc-text{
  color:#fff !important;
}
.calender-info>.div .mc-month{
  color:#fff !important;
}
.calendar-tools{
  color:#fff !important;
}

.btnNav{
  margin-bottom:3%; 
}
/deep/.addListBtn wux-button{
  margin:20px;
}
</style>
